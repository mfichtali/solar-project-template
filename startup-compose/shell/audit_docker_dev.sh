export SOLAR_ROOT_PROJECT='C:\mfichtali\projects\solar-factory\solar-renter-car'
#export SOLAR_ROOT_PROJECT='/home/mfichtali/workspace/depôt'
export COMPOSE_DIR='startup-compose'

export DB_NAME='db-audit-dev'
export DOCKER_COMPOSE_DB_DIR='./docker/databases'
export DOCKER_COMPOSE_DB_FILE_NAME=" -f ${DOCKER_COMPOSE_DB_DIR}/database-dev-dc.yml"
export DOCKER_ENV_FILE=' --env-file ./environment/.env.shared --env-file ./environment/.env.ecosystem --env-file ./environment/dev.env '


export DOCKER_COMPOSE_DIR='./docker/micro-s'
export DOCKER_COMPOSE_FILE_NAME=" -f ${DOCKER_COMPOSE_DIR}/audit-dc.yml"


cd $SOLAR_ROOT_PROJECT
cd $COMPOSE_DIR

arg="$1" 
case ${arg} in 
   "run") 

        echo ''
        echo '#################################'
        echo '###    AUDIT build services   ###'
        echo '#################################'
        echo ''
        docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_FILE_NAME build --no-cache
        echo ''

        echo '###################################'
        echo '### Create and start containers ###'
        echo '###################################'
        echo ''
        docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_DB_FILE_NAME up -d $DB_NAME
        docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_FILE_NAME up -d
        echo '' 

   ;;
   "start") 

        echo ''
        echo '#############################################'
        echo '###         AUDIT Start containers        ###'
        echo '#############################################'
        echo ''
        docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_DB_FILE_NAME start $DB_NAME
        docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_FILE_NAME start
        echo ''

   ;;
   "stop") 
    
        echo ''
        echo '#############################################'
        echo '###          AUDIT Stop containers        ###'
        echo '#############################################'
        echo ''
        docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_FILE_NAME stop
        docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_DB_FILE_NAME stop $DB_NAME
        echo ''

   ;;
   "down") 
    
        echo ''
        echo '#############################################'
        echo '###         AUDIT Down containers         ###'
        echo '#############################################'
        echo ''
        docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_FILE_NAME down
        docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_DB_FILE_NAME down $DB_NAME
        echo ''
     
   ;;
     "restart")

         echo ''
         echo '#############################################'
         echo '###       MDM  RESTART containers        ###'
         echo '#############################################'
         echo ''
         echo 'Stopping ...'
         docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_FILE_NAME stop
         docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_DB_FILE_NAME stop $DB_NAME

         echo 'Stropped'
         echo 'Starting ...'
         docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_DB_FILE_NAME start $DB_NAME
         docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_FILE_NAME start
         echo 'Started ...'
         echo ''

      ;;
       "rebuild")

          echo ''
          echo '#############################################'
          echo '###       TOOLS RESTART containers        ###'
          echo '#############################################'
          echo ''
          echo 'Down & Stopping ...'
          docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_FILE_NAME down
          docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_DB_FILE_NAME down $DB_NAME

          echo 'Stropped'
          echo 'Running ...'
          docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_FILE_NAME build --no-cache
          docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_DB_FILE_NAME up -d $DB_NAME
          docker-compose $DOCKER_ENV_FILE --profile dev $DOCKER_COMPOSE_FILE_NAME up -d
          echo 'Runned ...'
          echo ''

       ;;
   *)

        echo 'Argument mistak ...'
   ;;
esac
read -p "Press any key to continue" x