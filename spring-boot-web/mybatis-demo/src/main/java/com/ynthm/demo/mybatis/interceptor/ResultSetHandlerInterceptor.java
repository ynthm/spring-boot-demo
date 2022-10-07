package com.ynthm.demo.mybatis.interceptor;

import com.ynthm.common.constant.CharPool;
import com.ynthm.common.exception.BaseException;
import com.ynthm.common.lang.Tuple2;
import com.ynthm.common.util.StringUtil;
import com.ynthm.demo.mybatis.annotation.Map2Field;
import org.apache.ibatis.executor.resultset.ResultSetHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.SystemMetaObject;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * @author Ynthm Wang
 * @version 1.0
 */
@Component
@Intercepts({
  @Signature(
      type = ResultSetHandler.class,
      method = "handleResultSets",
      args = {Statement.class})
})
public class ResultSetHandlerInterceptor implements Interceptor {

  @Override
  public Object intercept(Invocation invocation) throws Throwable {
    MetaObject metaStatementHandler = getRealTarget(invocation);

    MappedStatement mappedStatement =
        (MappedStatement) metaStatementHandler.getValue("mappedStatement");
    // 当前类
    String className = StringUtil.subBefore(mappedStatement.getId(), ".", true);
    // 当前方法
    String currentMethodName = StringUtil.subAfter(mappedStatement.getId(), CharPool.DOT, true);
    // 获取当前Method
    Method currentMethod = findMethod(className, currentMethodName);

    if (currentMethod == null || currentMethod.getAnnotation(Map2Field.class) == null) {
      // 如果当前Method没有注解MapF2F
      return invocation.proceed();
    }
    // 如果有MapF2F注解，则这里对结果进行拦截并转换
    Map2Field mapF2FAnnotation = currentMethod.getAnnotation(Map2Field.class);
    Statement statement = (Statement) invocation.getArgs()[0];
    Tuple2<Class<?>, Class<?>> kvTypePair =
        getKVTypeOfReturnMap(currentMethod); // 获取返回Map里key-value的类型
    TypeHandlerRegistry typeHandlerRegistry =
        mappedStatement.getConfiguration().getTypeHandlerRegistry(); // 获取各种TypeHander的注册器
    return result2Map(statement, typeHandlerRegistry, kvTypePair, mapF2FAnnotation);
  }

  private Method findMethod(String className, String targetMethodName) {
    // 该类所有声明的方法
    Method[] methods;
    try {
      methods = Class.forName(className).getDeclaredMethods();
    } catch (ClassNotFoundException e) {
      throw new BaseException(e);
    }

    for (Method method : methods) {
      if (StringUtil.equals(method.getName(), targetMethodName, false)) {
        return method;
      }
    }

    return null;
  }

  private MetaObject getRealTarget(Invocation invocation) {
    MetaObject metaStatementHandler = SystemMetaObject.forObject(invocation.getTarget());

    while (metaStatementHandler.hasGetter("h")) {
      Object object = metaStatementHandler.getValue("h");
      metaStatementHandler = SystemMetaObject.forObject(object);
    }

    while (metaStatementHandler.hasGetter("target")) {
      Object object = metaStatementHandler.getValue("target");
      metaStatementHandler = SystemMetaObject.forObject(object);
    }
    return metaStatementHandler;
  }

  /**
   * 获取函数返回Map中key-value的类型
   *
   * @param mapF2FMethod
   * @return left为key的类型，right为value的类型
   */
  private Tuple2<Class<?>, Class<?>> getKVTypeOfReturnMap(Method mapF2FMethod) {
    Type returnType = mapF2FMethod.getGenericReturnType();

    if (returnType instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) returnType;
      if (!Map.class.equals(parameterizedType.getRawType())) {
        throw new RuntimeException(
            "[ERROR-MapF2F-return-map-type]使用MapF2F,返回类型必须是java.util.Map类型！！！method="
                + mapF2FMethod);
      }

      return new Tuple2<>(
          (Class<?>) parameterizedType.getActualTypeArguments()[0],
          (Class<?>) parameterizedType.getActualTypeArguments()[1]);
    }

    return new Tuple2<>(null, null);
  }

  /**
   * 将查询结果映射成Map，其中第一个字段作为key，第二个字段作为value.
   *
   * @param statement
   * @param typeHandlerRegistry MyBatis里typeHandler的注册器，方便转换成用户指定的结果类型
   * @param kvTypePair 函数指定返回Map key-value的类型
   * @param mapF2FAnnotation
   * @return
   * @throws Throwable
   */
  private Object result2Map(
      Statement statement,
      TypeHandlerRegistry typeHandlerRegistry,
      Tuple2<Class<?>, Class<?>> kvTypePair,
      Map2Field mapF2FAnnotation)
      throws Throwable {
    ResultSet resultSet = statement.getResultSet();
    List<Object> res = new ArrayList();
    Map<Object, Object> map = new HashMap();

    while (resultSet.next()) {
      Object key = this.getObject(resultSet, 1, typeHandlerRegistry, kvTypePair.getT1());
      Object value = this.getObject(resultSet, 2, typeHandlerRegistry, kvTypePair.getT2());
      // 第一列作为key,第二列作为value。
      map.put(key, value);
    }

    res.add(map);
    return res;
  }

  /**
   * 结果类型转换。
   *
   * <p>这里借用注册在MyBatis的typeHander（包括自定义的），方便进行类型转换。
   *
   * @param resultSet
   * @param columnIndex 字段下标，从1开始
   * @param typeHandlerRegistry MyBatis里typeHandler的注册器，方便转换成用户指定的结果类型
   * @param javaType 要转换的Java类型
   * @return
   * @throws SQLException
   */
  private Object getObject(
      ResultSet resultSet,
      int columnIndex,
      TypeHandlerRegistry typeHandlerRegistry,
      Class<?> javaType)
      throws SQLException {
    final TypeHandler<?> typeHandler =
        typeHandlerRegistry.hasTypeHandler(javaType)
            ? typeHandlerRegistry.getTypeHandler(javaType)
            : typeHandlerRegistry.getUnknownTypeHandler();

    return typeHandler.getResult(resultSet, columnIndex);
  }

  @Override
  public Object plugin(Object target) {
    return Plugin.wrap(target, this);
  }

  @Override
  public void setProperties(Properties properties) {}
}
