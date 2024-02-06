FROM eclipse-temurin:21-jre-alpine
VOLUME /tmp
COPY build/libs/*.jar app.jar
LABEL authors="orme"
ENTRYPOINT ["sh", "-c", "java ${JAVA_OPTS} -jar /app.jar ${0} ${@}"]