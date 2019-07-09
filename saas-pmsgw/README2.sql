
DELETE FROM nodejs_customer_message_pigowner;
INSERT INTO pmsgw_weixin.nodejs_customer_message_pigowner
            (openid_coteid,
             game_cote_id,
             game_cote_name,
             game_cote_state,
           
             game_pigowner,
             game_rank_top,
             game_master_id_count,
             game_ringnum_count,
             game_receiver_openid,
             game_receiver_nickname,
             game_receiver_headimgurl)
SELECT
  CONCAT(game_receiver_openid,'->',game_cote_id) openid_coteid,
  game_cote_id,
  game_cote_name,
  game_cote_state,
  game_pigowner,
  MIN(game_rank) game_rank_top,
  COUNT(DISTINCT game_master_id) game_master_id_count,
  COUNT(DISTINCT game_ringnum) game_ringnum_count,
  game_receiver_openid,
  game_receiver_nickname,
  game_receiver_headimgurl
FROM nodejs_customer_message_all
GROUP BY game_receiver_openid,game_cote_id

 SELECT 
game_ringnum, 
 GROUP_CONCAT(game_master_text) game_master_text,
 
 GROUP_CONCAT(game_rank)  game_rank 
 
 FROM nodejs_customer_message_pigowner_detail 
 
 WHERE 
 (game_cote_id = '27176')
 
  AND 
  (game_receiver_openid = 'ocSsDwg-giK_9vvidNLsl4vKkB8c') 
  GROUP BY game_ringnum