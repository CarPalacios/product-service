FROM openjdk:11
COPY "./target/product-service-0.0.1-SNAPSHOT.jar" "app.jar"
EXPOSE 8091
ENTRYPOINT ["java","-jar","app.jar"]