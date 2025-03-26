export SOLAR_ROOT_PROJECT='C:\mfichtali\projects\solar-factory\solar-renter-car'
#export SOLAR_ROOT_PROJECT='/home/mfichtali/workspace/depôt'  # Linux env path
export COMPOSE_DIR='startup-compose'

export DOCKER_COMPOSE_DIR='./docker/tools'
export DOCKER_COMPOSE_FILE_NAME=" -f ${DOCKER_COMPOSE_DIR}/tools-dc.yml "
export DOCKER_ENV_FILE=' --env-file ./environment/.env.shared --env-file ./environment/.env.tools '

cd $SOLAR_ROOT_PROJECT
cd $COMPOSE_DIR

arg="$1" 
case ${arg} in 
   "run") 

        echo ''
        echo '#################################'
        echo '###    TOOLS build services   ###'
        echo '#################################'
        echo ''
        docker-compose $DOCKER_ENV_FILE $DOCKER_COMPOSE_FILE_NAME  build --no-cache
        echo ''

        echo '###################################'
        echo '### Create and start containers ###'
        echo '###################################'
        echo ''
        docker-compose $DOCKER_ENV_FILE $DOCKER_COMPOSE_FILE_NAME  up -d
        echo ''

   ;;
   "start") 

        echo ''
        echo '#############################################'
        echo '###         TOOLS Start containers        ###'
        echo '#############################################'
        echo ''
        docker-compose $DOCKER_ENV_FILE $DOCKER_COMPOSE_FILE_NAME start
        echo ''

   ;;
   "stop") 
    
        echo ''
        echo '#############################################'
        echo '###          TOOLS Stop containers        ###'
        echo '#############################################'
        echo ''
        docker-compose $DOCKER_ENV_FILE $DOCKER_COMPOSE_FILE_NAME stop
        echo ''

   ;;
   "down") 
    
        echo ''
        echo '#############################################'
        echo '###         TOOLS Down containers         ###'
        echo '#############################################'
        echo ''
        docker-compose $DOCKER_ENV_FILE $DOCKER_COMPOSE_FILE_NAME down
        echo ''
     
   ;;
   "restart") 
    
        echo ''
        echo '#############################################'
        echo '###       TOOLS RESTART containers        ###'
        echo '#############################################'
        echo ''
        echo 'Stopping ...'
        docker-compose $DOCKER_ENV_FILE $DOCKER_COMPOSE_FILE_NAME stop
        echo 'Stropped'
        echo 'Starting ...'
        docker-compose $DOCKER_ENV_FILE $DOCKER_COMPOSE_FILE_NAME start
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
         docker-compose $DOCKER_ENV_FILE $DOCKER_COMPOSE_FILE_NAME down
         echo 'Stropped'
         echo 'Running ...'
         docker-compose $DOCKER_ENV_FILE $DOCKER_COMPOSE_FILE_NAME build --no-cache
         docker-compose $DOCKER_ENV_FILE $DOCKER_COMPOSE_FILE_NAME up -d
         echo 'Runned ...'
         echo ''

    ;;
   *)

        echo 'Argument mistak ...'
   ;;
esac
read -p "Press any key to continue" x