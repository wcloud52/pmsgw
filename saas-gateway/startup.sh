echo "#############################################################################" 
pname=$1
port=$2
echo "start the java progress,print the java pid and pname"
pid=$(netstat -nlp | grep :$port | awk '{print $7}' | awk -F"/" '{ print $1 }')
echo "the current  $pname  progress is $pid:$port"
kill -9 $pid
echo "startup a new service"
nohup java -Duser.timezone=GMT+8 -Dfile.encoding=UTF-8 -Dloader.path="lib/" --server.port=$port -jar $pname --spring.config.location=bootstrap.properties,application.properties>logs/catalina.out 2>&1 &
echo "Sucess start the $pname service"
cd logs
ls -l
echo "#############################################################################"
tail -f catalina.out

