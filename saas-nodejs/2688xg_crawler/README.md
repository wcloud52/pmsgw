node app.js
pm2 start app.js --watch --name 086019com_crawler


http://localhost:9940/app_crawler/master

http://localhost:9940/app_crawler/detail
http://localhost:9940/app_crawler/sendMessage



DELETE FROM nodejs_crawler_master;
DELETE FROM nodejs_crawler_detail;
DELETE FROM nodejs_crawler_master_game;
DELETE FROM nodejs_crawler_detail_game;


pm2 restart .\run.json
pm2 delete 086019com_crawler


C:\apache-tomcat-7.0.50-7099\bin\startup.bat 网站
C:\weixin\startup.bat 用户绑定及爬取数据服务
C:\weixin\speedpigeoncncom_saas-pmsgw-weixin\startup.bat 发送微信客服消息服务
