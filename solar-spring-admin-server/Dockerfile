# BUILD AND PACKAGE STAGE     
FROM alpine/java:21-jre

# Argument from compose
ARG service_name
#ARG project_name=admin-server
ARG workdir_env
ARG sas_version

# Copy arg to env
ENV SERVICE_NAME=$service_name
#ENV PROJECT_NAME=$project_name
ENV WORKDIR_ENV=$workdir_env
ENV SAS_VERSION=$sas_version

# Set workdir
WORKDIR $WORKDIR_ENV

# Copy jar file
COPY target/$SERVICE_NAME-$SAS_VERSION.jar .

# RUN jar file
ENTRYPOINT java -jar $WORKDIR_ENV/$SERVICE_NAME-$SAS_VERSION.jar