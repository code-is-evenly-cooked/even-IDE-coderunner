FROM openjdk:21-jdk-slim

RUN apt-get update && apt-get install -y coreutils && apt-get clean

WORKDIR /app

CMD ["sh"]