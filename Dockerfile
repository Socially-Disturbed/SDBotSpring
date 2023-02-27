FROM eclipse-temurin:17-alpine

# Set timezone to Oslo
RUN apk add --no-cache tzdata
ENV TZ=Europe/Oslo

EXPOSE 8080
RUN mkdir /sd-bot
COPY target/*.jar /sd-bot/app.jar
VOLUME /tmp
ENTRYPOINT ["java", "-jar", "/sd-bot/app.jar"]