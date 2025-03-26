#################################################################################################
#                                               Run                                             #
#################################################################################################
FROM alpine/java:21-jre

# Argument from compose
ARG service_name
ARG workdir_env
ARG mdm_version
ARG profile_env

# Copy arg to env
ENV SERVICE_NAME=$service_name
ENV WORKDIR_ENV=$workdir_env
ENV MDM_VERSION=$mdm_version

# Set workdir
WORKDIR $WORKDIR_ENV

# copy script database by profile
COPY ./script/$profile_env-pg ./script

# Copy jar file
COPY mdm-api/target/$SERVICE_NAME-$MDM_VERSION.jar .

# RUN jar file
ENTRYPOINT java -jar $WORKDIR_ENV/$SERVICE_NAME-$MDM_VERSION.jar