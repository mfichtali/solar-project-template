#export SOLAR_ROOT_PROJECT='C:\mfichtali\projects\solar-depot'
export SOLAR_ROOT_PROJECT='/home/mfichtali/workspace/depôt'
export COMPOSE_DIR='startup-compose'

export DOCKER_COMPOSE_DIR='./docker/tools'
export DOCKER_COMPOSE_FILE_NAME="${DOCKER_COMPOSE_DIR}/elk-dc.yml"

cd $SOLAR_ROOT_PROJECT
cd $COMPOSE_DIR

arg="$1" 
case ${arg} in 
   "run") 

        echo ''
        echo '#################################'
        echo '###     ELK build services    ###'
        echo '#################################'
        echo ''
        docker-compose --env-file ./environment/.env -f $DOCKER_COMPOSE_FILE_NAME build --no-cache
        echo ''

        echo '###################################'
        echo '### Create and start containers ###'
        echo '###################################'
        echo ''
        docker-compose --env-file ./environment/.env -f $DOCKER_COMPOSE_FILE_NAME up -d
        echo '' 

   ;;
   "start") 

        echo ''
        echo '#############################################'
        echo '###          ELK Start containers        ###'
        echo '#############################################'
        echo ''
        docker-compose --env-file ./environment/.env -f $DOCKER_COMPOSE_FILE_NAME start
        echo '' 

   ;;
   "stop") 
    
        echo ''
        echo '#############################################'
        echo '###          ELK Stop containers         ###'
        echo '#############################################'
        echo ''
        docker-compose --env-file ./environment/.env -f $DOCKER_COMPOSE_FILE_NAME stop
        echo ''

   ;;
   "down") 
    
        echo ''
        echo '#############################################'
        echo '###          ELK Down containers         ###'
        echo '#############################################'
        echo ''
        docker-compose --env-file ./environment/.env -f $DOCKER_COMPOSE_FILE_NAME down
        echo ''
     
   ;;
   *)

        echo 'Argument mistak ...'
   ;;
esac
read -p "Press any key to continue" x