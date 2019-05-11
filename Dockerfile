FROM openjdk:11-slim as build-env

WORKDIR /src
COPY . .

RUN apt-get update && apt-get install -y restic
RUN ./gradlew --no-daemon build

FROM openjdk:11-jre-slim

RUN apt-get update && apt-get install -y restic
COPY --from=build-env /src/build/libs/restic-agent-cd.jar /restic-agent.jar

ENV AGENT_COMMAND_QUEUE=""
ENV RESTIC_WORKING_DIRECTORY=""
ENV RESTIC_DEFAULT_BACKUP_PATH=""
ENV RESTIC_REPOSITORY=""
ENV RESTIC_PASSWORD=""
ENV AWS_DEFAULT_REGION=""
ENV AWS_ACCESS_KEY_ID=""
ENV AWS_SECRET_KEY=""

CMD java -jar /restic-agent.jar