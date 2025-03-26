# BUILD AND PACKAGE STAGE     
#FROM openjdk:11-jre-slim
FROM alpine/java:21-jre

# Argument from compose
#ARG project_name=registry-api
ARG service_name
ARG workdir_env
ARG registry_version

# Copy arg to env
#ENV PROJECT_NAME=$project_name
ENV SERVICE_NAME=$service_name
ENV WORKDIR_ENV=$workdir_env
ENV REGISTRY_VERSION=$registry_version

# Set workdir
WORKDIR $WORKDIR_ENV

# Copy jar file
COPY ../service-registry/target/$SERVICE_NAME-$REGISTRY_VERSION.jar .

# RUN jar file
ENTRYPOINT java -jar $WORKDIR_ENV/$SERVICE_NAME-$REGISTRY_VERSION.jar

##
## Build stage
##
#FROM maven:3.8.4-jdk-11-slim as DEPS
#WORKDIR /opt/app
#
## COPY MODULES
#COPY service-registry/pom.xml service-registry/pom.xml
#
## COPY POM ROOT
#COPY pom.xml .
#
#RUN mvn -B -e -C org.apache.maven.plugins:maven-dependency-plugin:3.2.0:go-offline
#
#FROM maven:3.8.4-jdk-11-slim as BUILDER
#WORKDIR /opt/app
#COPY --from=deps /root/.m2 /root/.m2
#COPY --from=deps /opt/app/ .
#COPY service-registry/src service-registry/src
#RUN mvn -B -e clean install -DskipTests=true
#
##
## Package stage
##
#FROM openjdk:11-jre-slim
#
## Set workdir
#WORKDIR /opt/app
#
## Argument from compose
#ARG registry_version
#
## Copy arg to env
#ENV REGISTRY_VERSION=$registry_version
#
## Copy jar file
#COPY --from=builder /opt/app/service-registry/target/registry-api-$REGISTRY_VERSION.jar .
#
## RUN jar file
#ENTRYPOINT java -jar /opt/app/registry-api-$REGISTRY_VERSION.jar