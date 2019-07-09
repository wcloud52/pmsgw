node app.js
pm2 start app.js --watch --name 086019com_crawler



http://localhost:9930/app_crawler/master
http://localhost:9930/app_crawler/detail?cote_state=1
http://localhost:9930/app_crawler/sendMessage
http://115.28.48.136:9930/app_crawler/sendMessage


DELETE FROM nodejs_crawler_master;
DELETE FROM nodejs_crawler_detail;
DELETE FROM nodejs_crawler_master_game;
DELETE FROM nodejs_crawler_detail_game;


pm2 restart .\run.json
pm2 delete 086019com_crawler


C:\apache-tomcat-7.0.50-7099\bin\startup.bat 网站
C:\weixin\startup.bat 用户绑定及爬取数据服务
C:\weixin\speedpigeoncncom_saas-pmsgw-weixin\startup.bat 发送微信客服消息服务



2018-11-20 05:00 +08:00: TypeError: Cannot read property 'forEach' of undefined
    at /home/nodejs/speedpigeoncncom-9910/app_crawler.js:265:20
    at Query.<anonymous> (/home/nodejs/speedpigeoncncom-9910/connection.js:8:9)
    at Query.<anonymous> (/home/nodejs/speedpigeoncncom-9910/node_modules/mysql/lib/Connection.js:502:10)
    at Query._callback (/home/nodejs/speedpigeoncncom-9910/node_modules/mysql/lib/Connection.js:468:16)
    at Query.Sequence.end (/home/nodejs/speedpigeoncncom-9910/node_modules/mysql/lib/protocol/sequences/Sequence.js:83:24)
    at /home/nodejs/speedpigeoncncom-9910/node_modules/mysql/lib/protocol/Protocol.js:396:18
    at Array.forEach (native)
    at /home/nodejs/speedpigeoncncom-9910/node_modules/mysql/lib/protocol/Protocol.js:395:13
    at args.(anonymous function) (/www/node-v8.0.0-linux-x64/lib/node_modules/pm2/node_modules/event-loop-inspector/index.js:138:29)
    at _combinedTickCallback (internal/process/next_tick.js:95:7)
    at process._tickDomainCallback (internal/process/next_tick.js:198:9)
