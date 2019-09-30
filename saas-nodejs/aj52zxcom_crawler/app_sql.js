let sqltext = {

    insertCrawlerMaster: [
        'INSERT INTO nodejs_crawler_master',
        '(master_id,cote_id,cote_name,master_text,master_href,master_date,master_type,master_website,create_time,modify_time) ',
        'select master_id,cote_id,cote_name,master_text,master_href,master_date,master_type,master_website,create_time,modify_time from ',
        '(SELECT ? master_id,? cote_id,? cote_name,? master_text,? master_href,? master_date,? master_type,? master_website,? create_time,? modify_time)a ',
        'where a.master_href NOT IN (select master_href from nodejs_crawler_master)'
    ].join(' '),
    updateCrawlerMasterWith: ['update nodejs_crawler_master set detail_href = ?,detail_crawler_total = ?,detail_crawler_href = ?,modify_time = ? where master_href = ? ; '
    ].join(' '),
    queryCrawlerMaster: "SELECT master_id,cote_id,cote_name,cote_state,master_text,master_href,master_date,master_type,master_website,detail_href,detail_crawler_total,detail_crawler_href,create_time,modify_time FROM nodejs_crawler_master ",

    //nodejs_crawler_detail
    insertCrawlerDetail: [
        'INSERT INTO nodejs_crawler_detail ',
        '(detail_id,master_id,master_type,master_website,cote_id,cote_name,master_text, master_href,detail_state,detail_crawler_page,detail_crawler_href,create_time,modify_time)  ',
        'select detail_id,master_id,master_type,master_website,cote_id,cote_name,master_text, master_href,detail_state,detail_crawler_page,detail_crawler_href,create_time,modify_time from  ',
        '(SELECT  ? detail_id,? master_id,? master_type,? master_website,? cote_id,? cote_name,? master_text, ? master_href,? detail_state,? detail_crawler_page,? detail_crawler_href,? create_time,? modify_time)a  ',
        'where a.detail_crawler_href NOT IN (select detail_crawler_href from nodejs_crawler_detail) '
    ].join(' '),
    updateCrawlerDetail: 'update nodejs_crawler_detail set detail_state = ?,detail_josn=?, modify_time = ? where detail_crawler_href = ? ; ',
    queryCrawlerDetail: [
        'SELECT detail_id,master_id,master_type,master_website,cote_id,cote_name,cote_state,master_text,master_href,detail_state,',
        'detail_crawler_page,detail_crawler_href,create_time,modify_time FROM nodejs_crawler_detail aa  WHERE aa.detail_state=\'0\' and aa.master_id=? order by detail_crawler_page '
    ].join(' '),

    //nodejs_crawler_master_game
    insertGameMaster: [
      
        'DELETE FROM nodejs_crawler_master_game;',
        ' insert into nodejs_crawler_master_game',
        '(master_id,cote_id,cote_name, master_text, master_href, master_date, master_type, master_website, detail_href, detail_crawler_total, detail_crawler_href, create_time, modify_time) ',
        'select master_id,cote_id,cote_name,master_text,master_href,master_date,master_type,master_website,detail_href,detail_crawler_total,detail_crawler_href,create_time,modify_time from nodejs_crawler_master; '

    ].join(' '),

    //nodejs_crawler_detail_game
    insertGameDetail: [
        'INSERT INTO nodejs_crawler_detail_game',
        '(detail_id,master_id,master_type,master_website,master_href,cote_id,cote_name,cote_state,master_text,detail_page,detail_page_index,detail_state,distence,ringnum,pigowner,cometime,cotenum,speed,rank,create_time,modify_time)',
        'VALUES ? '
    ].join(' '),
    queryGameDetail: [
        'SELECT detail_id,master_id,master_type,master_website,master_href,cote_id,cote_name,cote_state,master_text,detail_page,detail_page_index,detail_state,distence,ringnum,pigowner,cometime,cotenum,speed,rank,create_time,modify_time,weixin_pigowner,weixin_mobile FROM nodejs_crawler_detail_game',
        'WHERE detail_state=\'0\'  and cote_state=\'1\'  ORDER BY master_id,rank'
    ].join(' '),
    changeGameDetailState: 'update nodejs_crawler_detail_game set detail_state = ? where detail_id in(?) ; ',
    deleteGameDetail: 'DELETE FROM nodejs_crawler_detail_game WHERE master_id=? AND detail_page=?',
    insertGameDetailFromTemp: [
        'INSERT INTO nodejs_crawler_detail_game(detail_id,cote_id,cote_name,cote_state,master_id,master_text,master_href,master_type,master_website,detail_page,detail_page_index,detail_state,distence,ringnum,pigowner,cometime,cotenum,speed,rank,receiver_openid,receiver_nickname,receiver_headimgurl,create_time,modify_time)',
        'SELECT detail_id,cote_id,cote_name,cote_state,master_id,master_text,master_href,master_type,master_website,detail_page,detail_page_index,detail_state,distence,ringnum,pigowner,cometime,cotenum,speed,rank,receiver_openid,receiver_nickname,receiver_headimgurl,create_time,modify_time FROM nodejs_crawler_detail_game_temp',
        'WHERE master_id=? ',
        ' AND detail_id NOT IN',
        '(SELECT detail_id FROM nodejs_crawler_detail_game)'
    ].join(' '),
    //nodejs_crawler_detail_game_temp
    deleteGameDetail_temp: 'DELETE FROM nodejs_crawler_detail_game_temp WHERE master_id=? ',
    insertGameDetail_temp: [
        'INSERT INTO nodejs_crawler_detail_game_temp',
        '(detail_id,master_id,master_type,master_website,master_href,cote_id,cote_name,cote_state,master_text,detail_page,detail_page_index,detail_state,distence,ringnum,pigowner,cometime,cotenum,speed,rank,create_time,modify_time)',
        'VALUES ? '
    ].join(' '),
    //存储过程
    flashCoteState: 'call p_nodejs_flash_cote_state() ',
    validationQuery: 'SELECT master_id,cote_id,cote_name,cote_state,master_text,master_href,master_date,master_type,master_website,detail_href,detail_crawler_total,detail_crawler_href,create_time,modify_time FROM nodejs_crawler_master '
};

module.exports = sqltext;