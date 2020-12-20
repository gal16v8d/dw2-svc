# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/2.2.4.RELEASE/maven-plugin/)

### Swagger

http://localhost:8094/swagger-ui/

### Spring Admin Client

I add some dependencies for admin this project as client,
check admin-server project for further related info.

### Spring Zipkin Client

I add some dependencies for admin logging in this project,
please check https://zipkin.io/pages/quickstart for more info.
Docker image must be running with a command like:
docker run -d -p 9411:9411 openzipkin/zipkin