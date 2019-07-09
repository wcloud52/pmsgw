let sqltext = {
  insert: 'INSERT INTO nodejs_crawler_master(master_id,master_text,master_href,master_date,master_type,master_website,create_time,modify_time) VALUES (?,?,?,?,?,?,?,?)',
  queryAll: 'SELECT * FROM nodejs_crawler_master',
  update:'update nodejs_crawler_master set master_text = ?,master_href = ?,master_date = ?,master_type = ?,master_website = ?,create_time = ?,modify_time = ? where master_id = ?',
  updateDetailhref:'update nodejs_crawler_master set detail_href = ?,modify_time = ? where master_id = ?',
  updateDetailcrawlerhref:'update nodejs_crawler_master set detail_crawler_total = ?,detail_crawler_href = ?,modify_time = ? where master_id = ?',
  delete: 'DELETE FROM nodejs_crawler_master WHERE master_id=?',
  queryById: 'SELECT * FROM nodejs_crawler_master WHERE master_id=?',
  insert2queryBy: 'INSERT INTO nodejs_crawler_master(master_id,master_text,master_href,master_date,master_type,master_website,create_time,modify_time) select master_id,master_text,master_href,master_date,master_type,master_website,create_time,modify_time from (SELECT ? master_id,? master_text,? master_href,? master_date,? master_type,? master_website,? create_time,? modify_time)a where a.master_href NOT IN (select master_href from nodejs_crawler_master)',
   insertDetail2queryBy: 'INSERT INTO nodejs_crawler_detail(detail_id,master_id,detail_state,detail_crawler_page,detail_crawler_href,create_time,modify_time) select detail_id,master_id,detail_state,detail_crawler_page,detail_crawler_href,create_time,modify_time from (SELECT  ? detail_id,? master_id,? detail_state,? detail_crawler_page,? detail_crawler_href,? create_time,? modify_time)a where a.detail_crawler_href NOT IN (select detail_crawler_href from nodejs_crawler_detail)',
   updateCrawlerDetail:'update nodejs_crawler_detail set detail_state = ?,detail_josn=?, modify_time = ? where detail_crawler_href = ?',
   queryCrawlerDetail:"SELECT detail_id,master_id,detail_state,detail_crawler_page,detail_crawler_href,create_time,modify_time FROM nodejs_crawler_detail WHERE detail_state='0' ORDER BY master_id,detail_crawler_page ",
};

module.exports = sqltext;

