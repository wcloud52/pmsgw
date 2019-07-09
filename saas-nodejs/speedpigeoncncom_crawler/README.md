node app.js
pm2 start app.js --watch --name speedpigeoncncom_crawler


http://localhost:9910/app_crawler/master
http://localhost:9910/app_crawler/detail?cote_state=0
http://localhost:9910/app_crawler/detail?cote_state=1
http://localhost:9910/app_crawler/sendMessage
http://115.28.48.136:9910/app_crawler/master
http://115.28.48.136:9910/app_crawler/detail?cote_state=1
http://47.92.241.46:9910/app_crawler/master
http://47.92.241.46:9910/app_crawler/detail?cote_state=0

http://47.92.241.46:9910/app_crawler/sendMessage

DELETE FROM nodejs_crawler_master;
DELETE FROM nodejs_crawler_detail;
DELETE FROM nodejs_crawler_master_game;
DELETE FROM nodejs_crawler_detail_game;
DELETE FROM nodejs_crawler_detail_game_temp;


pm2 restart .\run.json
pm2 delete speedpigeoncncom_crawler