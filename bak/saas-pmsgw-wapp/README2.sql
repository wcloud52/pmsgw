DELETE FROM pmsgw_game_master WHERE website='pmsgw.com';
INSERT INTO pmsgw_game_master
            (  
            id,main_text,
   title_name,
   title,
  tq,
   data_fengli,
 data_fengxiang,
 data_address,
  jd,
   wd,
  qh,
  data_time,
  website,
  create_time,
  modify_time)
SELECT
   DATE_FORMAT(RELEASETIME,'%m%d%H%i%S') id, 
  GAMENAME main_text,
  GAMENAME title_name,
  GAMENAME title,
  
  WEATHER tq,
  WINDPOWER data_fengli,
  WINDDIRECTION data_fengxiang,
  RPNAME data_address,
  RPLONGITUDED +RPLONGITUDEM+RPLONGITUDES jd,
  RPLATITUDED+RPLATITUDEM+RPLATITUDES wd,
  DISTANCE qh,
  RELEASETIME data_time,
  'pmsgw.com' website,
  RELEASETIME create_time,
  RELEASETIME modify_time
FROM server.game;

DELETE FROM pmsgw_game_detail
WHERE masterId IN (SELECT id FROM pmsgw_game_master WHERE website='pmsgw.com');
INSERT INTO pmsgw_game_detail
            (masterId,
rank ,
  pigowner,
   cotenum,
   ringnum,
   cometime,
  speed,
   raceid,
  create_time,
   modify_time)
SELECT
 
   DATE_FORMAT(RELEASETIME,'%m%d%H%i%S') masterId,
  TOTALRANK rank ,
  OWNERNAME pigowner,
  OWNERADDRESS cotenum,
  LEGRINGNUMBER ringnum,
  RETURNTIME cometime,
  SPEED speed,
  GROUPNUMBER raceid,
  NOW() create_time,
  NOW() modify_time
FROM server.result
























SELECT * FROM pmsgw_game_master
WHERE `main_text` LIKE '%2018春300公里比赛%'


SELECT * FROM pmsgw_game_detail
WHERE masterid=20180310060971


SELECT * FROM `pmsgw_game_detail`
WHERE `pigowner` LIKE '%张志刚%'



SELECT * FROM `weixin_user`
WHERE `bind_name` LIKE '%王金国%'


北京华顺德赛鸽公棚-张志刚
华顺德赛鸽公棚-张志刚

SELECT * FROM `pmsgw_game_detail`
WHERE `pigowner` LIKE '%王金国%'


SELECT * FROM `pmsgw_game_detail`
WHERE `pigowner` LIKE '%王金国%'

SELECT * FROM `weixin_message`
-- order by `CreatedDatetime` desc
WHERE `ReceiverName`='华顺德赛鸽公棚-张志刚'



SELECT 
		*
		FROM pmsgw_game_detail
		   WHERE pigowner LIKE '%王金国%'

	         AND IFNULL(flag,'')!='1'
			ORDER BY create_time ASC,rank ASC


DELETE FROM 
pmsgw_game_detail
WHERE create_time<'2018-03-28'

DELETE FROM 
`pmsgw_game_master`
WHERE create_time<'2018-03-28'



SELECT id, masterId, masterText, mm, raceid, distence, flag, xmbh, qh, ringnum, pigowner, cotenum, cometime, speed, rank, create_time, 
modify_time, ReceiverId, ReceiverName FROM pmsgw_game_detail 
WHERE `masterId` IN ('20180310066895','20180310066909') ORDER BY rank ASC,create_time ASC LIMIT 0,10000
