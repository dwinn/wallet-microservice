FROM adoptopenjdk/openjdk11:jdk-11.0.11_9-alpine
VOLUME /tmp
ARG JAR_FILE=/build/libs/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT exec java $JAVA_OPTS  -jar /app.jar
