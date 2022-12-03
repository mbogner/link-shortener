FROM openjdk:17-slim
WORKDIR /application

RUN apt update && apt install -y curl && rm -rf /var/lib/apt/lists/*

COPY build/libs/link-shortener.jar ./app.jar
COPY docker-run.sh ./
COPY src/main/resources/application.yml ./config.yml

CMD ["./docker-run.sh"]
EXPOSE 8080