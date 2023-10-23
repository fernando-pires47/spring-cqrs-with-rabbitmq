FROM eclipse-temurin:17-jdk-jammy as builder
WORKDIR /opt/app
COPY .mvn/ .mvn
COPY mvnw pom.xml ./
RUN ./mvnw dependency:go-offline
COPY ./src ./src
RUN ./mvnw -B -f pom.xml clean package -DskipTests
 
FROM eclipse-temurin:17-jre-jammy
WORKDIR /opt/app
COPY --from=builder /opt/app/target/*.jar /opt/app/app.jar
ENTRYPOINT ["java", "-jar",  "app.jar" ]
