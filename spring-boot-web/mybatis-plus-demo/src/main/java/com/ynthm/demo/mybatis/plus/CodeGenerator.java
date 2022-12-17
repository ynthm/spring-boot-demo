package com.ynthm.demo.mybatis.plus;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.PostgreSqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.PostgreSqlQuery;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.VelocityTemplateEngine;
import com.baomidou.mybatisplus.generator.fill.Column;
import com.baomidou.mybatisplus.generator.fill.Property;
import com.baomidou.mybatisplus.generator.keywords.PostgreSqlKeyWordsHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.env.YamlPropertySourceLoader;
import org.springframework.core.env.PropertySource;
import org.springframework.core.io.ClassPathResource;

import java.io.IOException;
import java.util.Collections;
import java.util.Objects;
import java.util.Scanner;

/**
 * https://mp.baomidou.com/config/generator-config.html
 *
 * @author Ethan Wang
 */
@Slf4j
public class CodeGenerator {

  /** 数据库表前缀，生成类排除 */
  public static final String TABLE_PREFIX = "";
  /** 生产类 javadoc 作者 */
  public static final String AUTHOR = "Ethan Wang";
  /** 子模块需要加模块文件夹名字，根项目就为空 */
  public static final String FOLDER = "";

  public static final String PARENT_PACKAGE_PATH = "com.ynthm.demo.mybatis.plus";

  public static final String EVN_APPLICATION = "application-dev";

  public static void main(String[] args) throws IOException {

    PropertySource<?> applicationYaml =
        new YamlPropertySourceLoader()
            .load(EVN_APPLICATION, new ClassPathResource(EVN_APPLICATION + ".yaml"))
            .get(0);

    String uri =
        Objects.requireNonNull(
                applicationYaml.getProperty("spring.datasource.dynamic.datasource.bo.url"))
            .toString();
    String username =
        Objects.requireNonNull(
                applicationYaml.getProperty("spring.datasource.dynamic.datasource.bo.username"))
            .toString();
    String password =
        Objects.requireNonNull(
                applicationYaml.getProperty("spring.datasource.dynamic.datasource.bo.password"))
            .toString();

    ITypeConvert typeConvert = new PostgreSqlTypeConvert();
    IKeyWordsHandler keyWordsHandler = new PostgreSqlKeyWordsHandler();
    IDbQuery dbQuery = new PostgreSqlQuery();
    String schema = "trade_gts2";

    DataSourceConfig dataSourceConfig =
        new DataSourceConfig.Builder(uri, username, password)
            .schema(schema)
            .typeConvert(typeConvert)
            .keyWordsHandler(keyWordsHandler)
            .dbQuery(dbQuery)
            .build();

    // 代码生成器
    AutoGenerator autoGenerator = new AutoGenerator(dataSourceConfig);

    String projectPath = System.getProperty("user.dir") + FOLDER;
    log.info(projectPath);

    // 全局配置
    GlobalConfig globalConfig =
        new GlobalConfig.Builder()
            .outputDir(projectPath + "/src/main/java")
            .author(AUTHOR)
            .fileOverride()
            .disableOpenDir()
            .build();

    autoGenerator.global(globalConfig);

    // 包配置
    PackageConfig packageConfig =
        new PackageConfig.Builder()
            .parent(PARENT_PACKAGE_PATH)
            .moduleName(scanner("模块名"))
            .pathInfo(
                Collections.singletonMap(
                    OutputFile.xml, projectPath + "/src/main/resources/mapper"))
            .build();

    autoGenerator.packageInfo(packageConfig);

    // 自定义配置
    InjectionConfig injectionConfig =
        new InjectionConfig.Builder().beforeOutputFile((tableInfo, stringObjectMap) -> {}).build();

    autoGenerator.injection(injectionConfig);

    // 配置模板  禁用 Controller
    TemplateConfig templateConfig =
        new TemplateConfig.Builder().disable(TemplateType.CONTROLLER).build();
    autoGenerator.template(templateConfig);

    // 策略配置
    StrategyConfig strategyConfig =
        new StrategyConfig.Builder()
            .addInclude(scanner("表名，多个英文逗号分割").split(","))
            .addTablePrefix(TABLE_PREFIX)
            .entityBuilder()
            .enableLombok()
            .enableTableFieldAnnotation()
            // 继承 Model 进行强大的 CRUD
            .enableActiveRecord()
            // 全局主键类型
            .idType(IdType.AUTO)
            .enableChainModel()
            .versionColumnName("version")
            .naming(NamingStrategy.underline_to_camel)
            .columnNaming(NamingStrategy.underline_to_camel)
            .addTableFills(new Column("create_time", FieldFill.INSERT))
            .addTableFills(new Property("updateTime", FieldFill.INSERT_UPDATE))
            // .enableColumnConstant()
            //            .controllerBuilder()
            //            .enableRestStyle()
            .mapperBuilder()
            .enableBaseColumnList()
            .enableBaseResultMap()
            .serviceBuilder()
            .formatServiceFileName("%sService")
            .build();

    autoGenerator.strategy(strategyConfig);

    autoGenerator.execute(new VelocityTemplateEngine());
  }

  /** 读取控制台内容 */
  private static String scanner(String tip) {
    Scanner scanner = new Scanner(System.in);
    log.info("请输入" + tip + "：");
    if (scanner.hasNext()) {
      String ipt = scanner.next();
      if (StringUtils.isNotEmpty(ipt)) {
        return ipt;
      }
    }
    throw new MybatisPlusException("请输入正确的" + tip + "！");
  }
}
