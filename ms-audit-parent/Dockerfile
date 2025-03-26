#################################################################################################
#                                               Run                                             #
#################################################################################################
FROM alpine/java:21-jre

# Argument from compose
ARG service_name
ARG workdir_env
ARG audit_version
ARG profile_env

# Copy arg to env
ENV SERVICE_NAME=$service_name
ENV WORKDIR_ENV=$workdir_env
ENV AUDIT_VERSION=$audit_version

# Set workdir
WORKDIR $WORKDIR_ENV

# copy script database by profile
COPY ./script/$profile_env-pg ./script

# Copy jar file
COPY audit-api/target/$SERVICE_NAME-$AUDIT_VERSION.jar .

# RUN jar file
ENTRYPOINT java -jar $WORKDIR_ENV/$SERVICE_NAME-$AUDIT_VERSION.jar