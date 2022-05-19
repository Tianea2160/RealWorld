FROM mdsol/java11-corretto-experimental
ARG jar_path="build/libs"
COPY $jar_path/*.jar app.jar
ENTRYPOINT ["java","-XX:UseAVX=0","-jar","app.jar"]