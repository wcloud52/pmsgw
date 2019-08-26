:
http://www.aj52zx.com/racelist.aspx
:
http://gp.aj52gx.com/racelist.aspx


http://60.205.5.42/default.aspx

http://www.086019.com/live/index.html



http://www.yunfeichina.com/HomeWeb/Match/index.html

https://github.com/xuhang2015/region

http://www.benzinglive.cn/AP_M1/AP0000_list.aspx?pid=0115
:
把比赛成绩通知改成平民赛鸽网，把计分方式：最终成绩以公布为准，这几个字去掉。
:
用户绑定要弄成绿色，用户就清楚明白
:
就跟关注一样


http://59.125.72.59/msg/message.asp?ucgp=224



:
http://gdgp.chinaxinge.com/detail.asp?id=1617952&sjm=c640be31194bd644
这是华顺德500公里指定规则，300公里，200公里基本一样
http://gdgp3.chinaxinge.com/shuju2/201805/201859920561996.htm
这是报名表
http://gdgp3.chinaxinge.com/shuju2/201805/20185916130420985.htm
这是最后获奖名单



http://www.gexingwang.com/page/14.htm



UPDATE pmsgw_weixin.nodejs_weixin_user_excel a,weixin_user b
SET 
  
 
  a.create_time =b.create_time ,
  a.modify_time =b.modify_time,
  a.city = b.city,
  a.country = b.country,
  a.groupid = b.groupid,
  a.headimgurl = b.headimgurl,
  a.language = b.language,
  a.nickname = b.nickname,
  a.openid = b.openid,
  a.province = b.province,
  a.remark = b.remark,
  a.sex = b.sex,
  a.subscribe = b.subscribe,
  a.subscribe_time = b.subscribe_time,
  a.unionid = b.unionid,
  a.bind_type = b.bind_type,
  a.bind_tel = b.bind_tel,
  a.bind_name = b.bind_name,
  a.bind_loft = b.bind_loft,
  a.bind_address = b.bind_address,
  a.bind_game = b.bind_game,
  a.bind_address_prov = b.bind_address_prov,
  a.bind_address_city = b.bind_address_city,
  a.bind_address_dist = b.bind_address_dist,
  a.bind_time = b.bind_time,
  a.club_bind_tel = b.club_bind_tel,
  a.club_bind_name = b.club_bind_name,
  a.club_bind_loft = b.club_bind_loft,
  a.club_bind_address = b.club_bind_address,
  a.club_bind_game = b.club_bind_game,
  a.club_bind_address_prov = b.club_bind_address_prov,
  a.club_bind_address_city = b.club_bind_address_city,
  a.club_bind_address_dist = b.club_bind_address_dist,
  a.club_bind_time = b.club_bind_time
WHERE a.bind_tel= b.bind_tel;

UPDATE nodejs_weixin_user_excel SET bind_name=excel_bind_name;
