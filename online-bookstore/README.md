## configure
- edit the application.properties 
```agsl
## nacos config
dubbo.registry.address=nacos://127.0.0.1:8848
## db configure
spring.datasource.druid.url=jdbc:mysql://127.0.0.1:3306/bs?useUnicode=true&characterEncoding=utf-8&useSSL=false
spring.datasource.druid.username=root
spring.datasource.druid.password=Abcd1234
## redis config
spring.redis.database=0
spring.redis.host=127.0.0.1
spring.redis.port=6379
spring.redis.password=
```
- 
## deploy and run
- execute the command 'mvn clean package'
- move the package 'book-store-server.war' to the webapps dir of tomcat
- start the tomcat