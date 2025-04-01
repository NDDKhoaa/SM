FROM openjdk:23-jdk

COPY /target/SM-0.1.jar .

EXPOSE 8080

CMD ["java","-jar","SM-0.1.jar"]