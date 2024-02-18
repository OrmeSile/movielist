FROM eclipse-temurin:21-jdk-alpine as build
WORKDIR /workspace/app

COPY . .

RUN chmod +x gradlew
RUN ./gradlew clean bootJar -x test

FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY --from=build /workspace/app/build/ /app/build
RUN mv /app/build/libs/*.jar /app/build/libs/app.jar
ENTRYPOINT ["java", "-jar", "/app/build/libs/app.jar"]