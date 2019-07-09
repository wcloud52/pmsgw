node app.js
pm2 start app.js --watch --name 086019com_crawler


http://localhost:3000/app_crawler/master

http://localhost:3000/app_crawler/detail
http://localhost:3000/app_crawler/sendMessage



DELETE FROM nodejs_crawler_master;
DELETE FROM nodejs_crawler_detail;
DELETE FROM nodejs_crawler_master_game;
DELETE FROM nodejs_crawler_detail_game;


pm2 restart .\run.json
pm2 delete aj52zxcom_crawler