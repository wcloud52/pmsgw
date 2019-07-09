node app.js
pm2 start app.js --watch --name 086019com_crawler

http://115.28.48.136:9970/app_crawler/master
http://localhost:9970/app_crawler/master

http://localhost:9970/app_crawler/detail?cote_state=0
http://localhost:9970/app_crawler/sendMessage
http://115.28.48.136:9970/app_crawler/sendMessage

http://115.28.48.136:9970/app_crawler/master
http://47.92.197.223:9970/app_crawler/master

DELETE FROM nodejs_crawler_master;
DELETE FROM nodejs_crawler_detail;
DELETE FROM nodejs_crawler_master_game;
DELETE FROM nodejs_crawler_detail_game;


pm2 restart .\run.json
pm2 delete 086019com_crawler


C:\apache-tomcat-7.0.50-7099\bin\startup.bat 网站
C:\weixin\startup.bat 用户绑定及爬取数据服务
C:\weixin\speedpigeoncncom_saas-pmsgw-weixin\startup.bat 发送微信客服消息服务

http://www.benzinglive.cn/AP_M1/AP0000.aspx




ALTER TABLE `nodejs_crawler_master` CHANGE `cote_state` `cote_state` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci DEFAULT '0' NULL COMMENT '公棚状态'; 
ALTER TABLE `nodejs_crawler_master_game` CHANGE `cote_state` `cote_state` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci DEFAULT '0' NULL COMMENT '公棚状态'; 
ALTER TABLE `nodejs_crawler_detail` CHANGE `cote_state` `cote_state` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci DEFAULT '0' NULL COMMENT '公棚状态'; 
ALTER TABLE `nodejs_crawler_detail_game` CHANGE `cote_state` `cote_state` VARCHAR(50) CHARSET utf8 COLLATE utf8_general_ci DEFAULT '0' NULL COMMENT '公棚状态'; 




UPDATE pmsgw_tangshan.nodejs_crawler_master
SET master_id=REPLACE(master_id,'20181027','20181028')
WHERE master_id='2018102709-95141c';

UPDATE pmsgw_tangshan.nodejs_crawler_master_game
SET master_id=REPLACE(master_id,'20181027','20181028')
WHERE master_id='2018102709-95141c';

UPDATE pmsgw_tangshan.nodejs_crawler_detail
SET master_id=REPLACE(master_id,'20181027','20181028'),detail_id=REPLACE(detail_id,'20181027','20181028')
WHERE master_id='2018102709-95141c';

UPDATE pmsgw_tangshan.nodejs_crawler_detail_game
SET master_id=REPLACE(master_id,'20181027','20181028'),detail_id=REPLACE(detail_id,'20181027','20181028')
WHERE master_id='2018102709-95141c';

--  xxxxxxxxxxxxxxxxxxxxxxxxxxx

UPDATE pmsgw_weixin.nodejs_crawler_master
SET master_id=REPLACE(master_id,'20181027','20181028')
WHERE master_id='2018102709-95141c';

UPDATE pmsgw_weixin.nodejs_crawler_master_game
SET master_id=REPLACE(master_id,'20181027','20181028')
WHERE master_id='2018102709-95141c';

UPDATE pmsgw_weixin.nodejs_crawler_detail
SET master_id=REPLACE(master_id,'20181027','20181028'),detail_id=REPLACE(detail_id,'20181027','20181028')
WHERE master_id='2018102709-95141c';

UPDATE pmsgw_weixin.nodejs_crawler_detail_game
SET master_id=REPLACE(master_id,'20181027','20181028'),detail_id=REPLACE(detail_id,'20181027','20181028')
WHERE master_id='2018102709-95141c';

UPDATE pmsgw_weixin.nodejs_customer_message
SET game_master_id=REPLACE(game_master_id,'20181027','20181028'),game_detail_id=REPLACE(game_detail_id,'20181027','20181028')
WHERE game_master_id='2018102709-95141c';

UPDATE pmsgw_weixin.nodejs_customer_message_all
SET game_master_id=REPLACE(game_master_id,'20181027','20181028'),game_detail_id=REPLACE(game_detail_id,'20181027','20181028')
WHERE game_master_id='2018102709-95141c';

UPDATE pmsgw_weixin.nodejs_customer_game_message
SET game_master_id=REPLACE(game_master_id,'20181027','20181028')
WHERE game_master_id='2018102709-95141c';







