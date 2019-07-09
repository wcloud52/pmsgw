echo "#############################################################################" 
SpringBoot=$1
echo "abtain the java progress,print the java pid and pidnames"
var=$(ps -ef | grep java |grep $SpringBoot| awk -F "[ \t-]+" '{print $2}')
#var=$(ps -ef | grep java | head -n 8| awk -F '[ \t-]+' '{if($11 == "mongodb" ) print  pidname=$2}')
echo "the current  $SpringBoot  progress is $var"
kill -9 $var
echo "startup a new service"
nohup java -Duser.timezone=GMT+8 -Dfile.encoding=UTF-8 -Dloader.path="lib/" -jar $SpringBoot --spring.config.location=bootstrap.properties,application.properties>logs/catalina.out 2>&1 &

echo "Sucess start the $SpringBoot service"
cd logs
ls -l
echo "#############################################################################"
tail -f catalina.out

