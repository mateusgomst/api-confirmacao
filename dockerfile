FROM eclipse-temurin:17-jdk-alpine

WORKDIR /app

RUN apk add --no-cache bash git

COPY gradlew .
COPY gradle ./gradle
COPY build.gradle .
COPY settings.gradle .
COPY src ./src

RUN chmod +x ./gradlew

CMD ["bash"]
