FROM amazoncorretto:17-alpine AS build
WORKDIR /app
COPY . /app/
RUN ./gradlew build --no-daemon -x test

FROM amazoncorretto:17-alpine
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
