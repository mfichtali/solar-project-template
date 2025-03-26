#export SOLAR_ROOT_PROJECT='C:\mfichtali\projects\solar-depot'
export SOLAR_ROOT_PROJECT='C:\Users\mfichtali\Desktop\mfichtali\solar-perso\depots'
#export SOLAR_ROOT_PROJECT='/home/mfichtali/workspace/depôt'
export COMPOSE_DIR='startup-compose'

export DOCKER_COMPOSE_DIR='./docker/ecosystem'
export DOCKER_COMPOSE_FILE_NAME=" -f ${DOCKER_COMPOSE_DIR}/gateway-dc.yml "

cd $SOLAR_ROOT_PROJECT
cd $COMPOSE_DIR

arg="$1" 
case ${arg} in 
   "run") 

      echo ''
      echo '#################################'
      echo '###     INIT build services   ###'
      echo '#################################'
      echo ''
      docker-compose --env-file ./environment/dev.env --profile dev $DOCKER_COMPOSE_FILE_NAME build --no-cache
      echo ''

      echo '###################################'
      echo '### Create and start containers ###'
      echo '###################################'
      echo ''
      docker-compose --env-file ./environment/dev.env --profile dev $DOCKER_COMPOSE_FILE_NAME up -d

   ;;
   "start") 

      echo ''
      echo '#############################################'
      echo '###          INIT Start containers        ###'
      echo '#############################################'
      echo ''
      docker-compose --env-file ./environment/dev.env --profile dev $DOCKER_COMPOSE_FILE_NAME start
      echo '' 

   ;;
   "stop") 
    
      echo ''
      echo '#############################################'
      echo '###          INIT Stop containers         ###'
      echo '#############################################'
      echo ''
      docker-compose --env-file ./environment/dev.env --profile dev $DOCKER_COMPOSE_FILE_NAME stop
      echo ''

   ;;
   "down") 
    
      echo ''
      echo '#############################################'
      echo '###          INIT Down containers         ###'
      echo '#############################################'
      echo ''
      docker-compose --env-file ./environment/dev.env --profile dev $DOCKER_COMPOSE_FILE_NAME down
      echo ''
     
   ;;
    "restart")

         echo ''
         echo '#############################################'
         echo '###       TOOLS RESTART containers        ###'
         echo '#############################################'
         echo ''
         echo 'Stopping ...'
         docker-compose --env-file ./environment/dev.env --profile dev $DOCKER_COMPOSE_FILE_NAME stop
         echo 'Stropped'
         echo 'Starting ...'
         docker-compose --env-file ./environment/dev.env --profile dev $DOCKER_COMPOSE_FILE_NAME start
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
          docker-compose --env-file ./environment/dev.env --profile dev $DOCKER_COMPOSE_FILE_NAME down
          echo 'Stropped'
          echo 'Running ...'
          docker-compose --env-file ./environment/dev.env --profile dev $DOCKER_COMPOSE_FILE_NAME build --no-cache
          docker-compose --env-file ./environment/dev.env --profile dev $DOCKER_COMPOSE_FILE_NAME up -d
          echo 'Runned ...'
          echo ''

     ;;
   *)

      echo 'Argument mistak ...'
   ;;
esac
read -p "Press any key to continue" x