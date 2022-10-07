FROM openjdk:17.0.2 as builder
WORKDIR workspace
COPY target/*.jar app.jar
RUN java -Djarmode=layertools -jar app.jar extract

FROM openjdk:17.0.2
WORKDIR application
COPY --from=builder workspace/dependencies/ ./
COPY --from=builder workspace/snapshot-dependencies/ ./
COPY --from=builder workspace/spring-boot-loader/ ./
COPY --from=builder workspace/application/ ./
ENV TZ="Asia/Shanghai"
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone
ENV JVM_OPTS="-XX:MaxRAMPercentage=80.0"
ENV JAVA_OPTS=""
ENTRYPOINT ["sh","-c","java $JVM_OPTS $JAVA_OPTS org.springframework.boot.loader.JarLauncher"]