
# Import project in new device (Laptop)

## Create new location of volumes

Example:

- C:\mfichtali\projects\solar-volumes

## Configure Startup env location

- `export SOLAR_ROOT_PROJECT='C:\mfichtali\projects\solar-factory\solar-renter-car'`

- `BASE_PATH_VOLUMES               =C:\mfichtali\projects\solar-volumes`


# Bitbucket App Password
MFI2024 / ATBBrKERNfV2dBHYtHMEV5xPaDVeA5C5056C

# run some services from docker-compose file
docker-compose --env-file ./environement/.env -f docker-compose-tools.yml up -d  <SERVICE_NAME>

# Maven

## Compile / install with profil
mvn <goal> -P <profil_name>



# Docker CLI 

## MS AUTH
> docker-compose --env-file ./environment/dev.env --profile dev -f docker-compose-auth.yml build --no-cache

> docker-compose --env-file ./environment/dev.env --profile dev -f docker-compose-auth.yml up -d

## Create volume
> docker volume create --driver local --opt type=none --opt device=C:\mfichtali\projects\solar-volumes\elastic_data\ --opt o=bind elastic_data

## Create network
> docker network create -d bridge netix-red

## Create tag from image & Push 
> docker tag <tag_name> <tag_remote_name>
Ex:
> docker tag mdm-service:1.0.0 mfichtali/remote-mdm-srv:1.0.0
Push:
> docker push mfichtali/remote-mdm-srv:1.0.0
