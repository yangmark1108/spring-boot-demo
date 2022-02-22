FROM openjdk:8-jdk-alpine
COPY ./target/*.jar /Documents/mydocker/demo.jar
WORKDIR /Documents/mydocker
RUN sh -c 'touch demo.jar'
ENTRYPOINT ["java","-jar","demo.jar"]