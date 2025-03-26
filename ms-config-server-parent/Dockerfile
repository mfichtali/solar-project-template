# the first stage of our build will extract the layers
#################################################################################################
#                                             Build                                             #
#################################################################################################
#FROM adoptopenjdk:11-jre-hotspot AS builder
#FROM openjdk:21 AS builder
#
#WORKDIR /config-server
#
#ARG JAR_FILE=config-server/target/*.jar
#COPY ${JAR_FILE} config-server.jar
#
#RUN java -Djarmode=layertools -jar config-server.jar extract

#################################################################################################
#                                               Run                                             #
#################################################################################################

# the second stage of our build will copy the extracted layers
#FROM adoptopenjdk:11-jre-hotspot
#FROM openjdk:21 AS run
#
#WORKDIR /config-server
#COPY --from=builder config-server/dependencies/ ./
#COPY --from=builder config-server/spring-boot-loader/ ./
#COPY --from=builder config-server/snapshot-dependencies/ ./
#COPY --from=builder config-server/application/ ./
#ENTRYPOINT ["java", "org.springframework.boot.loader.launch.JarLauncher"]

FROM alpine/java:21-jre

# Argument from compose
ARG service_name
ARG workdir_env
ARG config_version

# Copy arg to env
ENV SERVICE_NAME=$service_name
ENV WORKDIR_ENV=$workdir_env
ENV CONFIG_VERSION=$config_version

# Set workdir
WORKDIR $WORKDIR_ENV

# Copy jar file
COPY ../config-server/target/$SERVICE_NAME-$CONFIG_VERSION.jar .

# RUN jar file
ENTRYPOINT java -jar $WORKDIR_ENV/$SERVICE_NAME-$CONFIG_VERSION.jar