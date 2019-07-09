node app.js
pm2 start app.js --watch --name aj52zxcom_crawler


http://localhost:9920/app_crawler/master/club
http://localhost:9920/app_crawler/master/loft

http://localhost:9920/app_crawler/detail
http://localhost:9920/app_crawler/sendMessage



DELETE FROM nodejs_crawler_master;
DELETE FROM nodejs_crawler_detail;
DELETE FROM nodejs_crawler_master_game;
DELETE FROM nodejs_crawler_detail_game;


pm2 restart .\run.json
pm2 delete aj52zxcom_crawler