FROM resin/rpi-raspbian:jessie-20170308
RUN apt-get update && apt-get install -y oracle-java8-jdk maven
EXPOSE 8080
ADD iot-runtime.iml iot-runtime.iml
ADD pom.xml pom.xml
ADD src src
RUN mvn package -DskipTests
CMD ["java","-jar", "target/iot-runtime-1.0-SNAPSHOT.jar"]