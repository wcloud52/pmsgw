node app.js
pm2 start app.js --watch --name aj52zxcom_crawler

http://localhost:9920/app_crawler/master/club

http://localhost:9920/app_crawler/master/loft

http://localhost:9920/app_crawler/detail?cote_state=0
http://localhost:9920/app_crawler/sendMessage


http://localhost:10092/app_test/test

http://localhost:10092/app_crawler/master/loft

http://localhost:10092/app_crawler/detail?cote_state=0


http://localhost:10092/app_crawler/validationQuery

DELETE FROM nodejs_crawler_master;
DELETE FROM nodejs_crawler_detail;
DELETE FROM nodejs_crawler_master_game;
DELETE FROM nodejs_crawler_detail_game;


pm2 restart .\run.json
pm2 delete aj52zxcom_crawler

 "mysql": "^2.17.1",
        "node-mysql": "^0.4.2",