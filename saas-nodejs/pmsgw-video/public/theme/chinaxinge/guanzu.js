document.write("<script language='javascript' src='/public/jquery_zh.js' ></script>");
document.write("<script language='javascript' src='/public/dialog/dialog.js' ></script>");

function add_Attention(otp,oid,oname,other){$.ajax({type:"get",url:"http://www.chinaxinge.com/jiaoyou/AttentService.asp?callback=?",jsonpCallback:"ok",data:"act=a&o_tp="+otp+"&o_id="+oid+"&o_name="+oname+"&gourl="+document.location.href.replace('&','|'),dataType:"jsonp",success:function(data){if(data!=null){myjson=data;if(myjson.login=="1")
{if(myjson.code=="1"){open_d("提示","sc",280,150,gz_result(1,'',''));}if(myjson.code=="2"){open_d("","sc",280,150,gz_result(2,'',''));}}
else
{window.location="http://www.chinaxinge.com/jiaoyou/denglu.asp?gourl="+document.location.href.replace('&','|');}}}});}

function gz_result(code,ty,sy)
{var showurl="http://www.chinaxinge.com/jiaoyou/user_attention.asp?sid=5";	var result="您已成功添加平台关注！"
if(code==2){result="您已关注过该俱乐部平台！"}
var NewLine="";var temp="";temp+="<table width=\"250\"  cellpadding=\"0\"  cellspacing=\"0\" border=\"0\"><tr>"
temp+="<td valign=\"top\" style='padding-top:30px'>"
temp+="<table width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">"
temp+="<tr>"
temp+="<td width=\"6%\" ></td>"
temp+="<td width=\"14%\" rowspan=\"2\" align=\"center\" ><img src=\"/images/s_ok.gif\" border=\"0\" /></td>"
temp+="<td width=\"80%\" height=\"20\" style=\"padding-bottom:5px; padding-left:10px;font-size:14px;color:#404040\"><b>"+result+"</b></td></tr>"
temp+="<tr>"
temp+="<td height=\"30\" ></td>"
temp+="<td style=\"padding-left:10px\"><a href=\"javascript:void(0)\" onclick=\"window.location='"+showurl+"'\" style=\"text-decoration:none; color:#3366CC; font-size:12px\">>>查看我关注的平台</a></td></tr>"
temp+="</table>"
temp+="</td></tr></table>"
return temp;}



