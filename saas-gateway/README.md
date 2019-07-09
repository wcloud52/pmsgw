clean spring-boot:run
clean spring-boot:run -Dspring.profiles.active=dev
http://localhost:5000/swagger-ui.html



port=8080
#根据端口号查询对应的pid
pid=$(netstat -nlp | grep :$port | awk '{print $7}' | awk -F"/" '{ print $1 }');

#杀掉对应的进程，如果pid不存在，则不执行
if [  -n  "$pid"  ];  then
    kill  -9  $pid;
fi