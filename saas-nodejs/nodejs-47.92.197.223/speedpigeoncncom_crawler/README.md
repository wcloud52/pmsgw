node app.js
pm2 start app.js --watch --name speedpigeoncncom_crawler


http://localhost:1000/app_crawler/master
http://localhost:1000/app_crawler/detail
http://localhost:1000/app_crawler/sendMessage



DELETE FROM nodejs_crawler_master;
DELETE FROM nodejs_crawler_detail;
DELETE FROM nodejs_crawler_master_game;
DELETE FROM nodejs_crawler_detail_game;


pm2 restart .\run.json
pm2 delete speedpigeoncncom_crawler