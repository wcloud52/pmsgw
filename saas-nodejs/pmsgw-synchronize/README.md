node app.js
pm2 start app.js --watch --name speedpigeoncncom_crawler


http://localhost:9990/app_crawler/flashInsert




DELETE FROM nodejs_crawler_master_game;
DELETE FROM nodejs_crawler_detail_game;


pm2 restart .\run.json
pm2 delete speedpigeoncncom_crawler


-- delete from  pmsgw_weixin.nodejs_crawler_master_game where `master_website`='pmsgw_benzinglive';
       INSERT INTO pmsgw_weixin.nodejs_crawler_master_game(master_id,cote_id,cote_name,master_text,master_href,master_date,master_type,master_website,detail_href,detail_crawler_total,detail_crawler_href,create_time,modify_time)
       SELECT master_id,cote_id,cote_name,master_text,master_href,master_date,master_type,master_website,detail_href,detail_crawler_total,detail_crawler_href,create_time,modify_time
        FROM nodejs_crawler_master_game WHERE DATE_FORMAT(create_time, '%Y%m%d')=DATE_FORMAT(NOW(), '%Y%m%d')
          AND      cote_id IN 
          (SELECT cote_id FROM z8.nodejs_crawler_cote WHERE cote_state='1')   
        ;
  
  
  -- DELETE FROM  pmsgw_weixin.nodejs_crawler_detail_game WHERE `master_website`='pmsgw_benzinglive';
        INSERT INTO pmsgw_weixin.nodejs_crawler_detail_game(detail_id,cote_id,cote_name,master_id,master_text,master_type,master_website,master_href,detail_page,detail_page_index,detail_state,distence,ringnum,pigowner,cometime,cotenum,speed,rank,create_time,modify_time)
        SELECT detail_id,cote_id,cote_name,master_id,master_text,master_type,master_website,master_href,detail_page,detail_page_index,detail_state,distence,ringnum,pigowner,cometime,cotenum,speed,rank,create_time,modify_time
        FROM nodejs_crawler_detail_game WHERE DATE_FORMAT(create_time, '%Y%m%d')=DATE_FORMAT(NOW(), '%Y%m%d')
        AND      cote_id IN 
          (SELECT cote_id FROM z8.nodejs_crawler_cote WHERE cote_state='1')         
        ;