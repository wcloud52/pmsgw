let sqltext = {

    query_litemall_user: 'select * from litemall_user ',
    //删除当前天之前每个子节点爬取数据
    call_p_delete_nodejs_crawler: 'call p_delete_nodejs_crawler()',
    //删除当前之前主节点发送微信客服消息
    call_p_flash_master: 'call p_flash_master()',
    //将每个子节点公棚/俱乐部同步到主节点
    call_p_nodejs_crawler_cote: 'call p_nodejs_crawler_cote()',
    //将每个子节点当天爬取数据同步到主节点
    call_p_nodejs_crawler_game: 'call p_nodejs_crawler_game()',
    //
    call_p_nodejs_flash_cote_state: 'call p_nodejs_flash_cote_state()',
    call_p_nodejs_weixin_user_cote: 'call p_nodejs_weixin_user_cote()'
};

module.exports = sqltext;