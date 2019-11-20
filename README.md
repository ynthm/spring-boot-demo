```sh
mkdir -p ~/Applications/docker/mysql/data ~/Applications/docker/mysql/conf ~/Applications/docker/mysql/logs
# mysql 5 以上的版本会连接报错
docker run --name my-mysql -p 3306:3306 -v ~/Applications/docker/mysql/conf:/etc/mysql/conf.d -v ~/Applications/docker/mysql/data:/var/lib/mysql -v ~/Applications/docker/mysql/logs:/var/log/mysql -e MYSQL_ROOT_PASSWORD=123432 -d mysql:5 --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci
```



```sql
CREATE DATABASE demo;
```

```properties
spring.datasource.url=jdbc:mysql://172.27.4.209:3306/demo?charset=uft8mb4&useSSL=false&serverTimezone=UTC
```





https://github.com/callicoder/spring-security-react-ant-design-polls-app

https://blog.csdn.net/sxdtzhaoxinguo/article/details/77965226

https://juejin.im/post/59d5bbebf265da066c233d0e#heading-7

https://juejin.im/post/58c29e0b1b69e6006bce02f4