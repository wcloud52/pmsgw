let sqltext = {

    insertMasterGame: [
        " DELETE FROM nodejs_crawler_master_game WHERE DATE_FORMAT(create_time, '%Y%m%d')=DATE_FORMAT(NOW(), '%Y%m%d') and master_website=?;",
        " INSERT INTO nodejs_crawler_master_game(master_id,cote_id,cote_name,master_text,master_href,master_date,master_type,master_website,detail_href,detail_crawler_total,detail_crawler_href,create_time,modify_time)",
        " SELECT master_id,cote_id,cote_name,master_text,master_href,master_date,master_type,master_website,detail_href,detail_crawler_total,detail_crawler_href,create_time,modify_time",
        " FROM ??.nodejs_crawler_master_game WHERE DATE_FORMAT(create_time, '%Y%m%d')=DATE_FORMAT(NOW(), '%Y%m%d');",
    ].join(' '),
    insertDetailGame: [
        "DELETE FROM nodejs_crawler_detail_game WHERE DATE_FORMAT(create_time, '%Y%m%d')=DATE_FORMAT(NOW(), '%Y%m%d') and master_website=?;",
        "INSERT INTO nodejs_crawler_detail_game(detail_id,cote_id,cote_name,master_id,master_text,master_type,master_website,master_href,detail_page,detail_page_index,detail_state,distence,ringnum,pigowner,cometime,cotenum,speed,rank,create_time,modify_time)",
        "SELECT detail_id,cote_id,cote_name,master_id,master_text,master_type,master_website,master_href,detail_page,detail_page_index,detail_state,distence,ringnum,pigowner,cometime,cotenum,speed,rank,create_time,modify_time",
        "FROM ??.nodejs_crawler_detail_game WHERE DATE_FORMAT(create_time, '%Y%m%d')=DATE_FORMAT(NOW(), '%Y%m%d');",

    ].join(' '),
    deleteMasterGame: [
        "DELETE FROM ??.nodejs_crawler_master WHERE DATE_FORMAT(create_time, '%Y%m%d')=DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY), '%Y%m%d');",
        "DELETE FROM ??.nodejs_crawler_master_game WHERE DATE_FORMAT(create_time, '%Y%m%d')=DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY), '%Y%m%d');"
    ].join(' '),
    deleteDetailGame: [
        "DELETE FROM ??.nodejs_crawler_detail WHERE DATE_FORMAT(create_time, '%Y%m%d')=DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY), '%Y%m%d');",
        "DELETE FROM ??.nodejs_crawler_detail_game WHERE DATE_FORMAT(create_time, '%Y%m%d')=DATE_FORMAT(DATE_SUB(NOW(),INTERVAL 1 DAY), '%Y%m%d');"
    ].join(' ')
};

module.exports = sqltext;