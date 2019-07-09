 document.write("<script language='javascript' src='/public/dialog/lhgcore.min.js' ></script>");
 document.write("<script language='javascript' src='/public/dialog/lhgdialog.min.js' ></script>");
 function openlink(_title,_id,_url,_width,_height)
{
    var dg = new J.dialog({id:_id,iconTitle:false,title:_title,page:_url, link:true, width:_width, height:_height});
	dg.ShowDialog();
}
 function open_d(_title,_id,_width,_height,_html)
{
    var dg = new J.dialog({id:_id,title:_title,iconTitle:false,btnBar:false,resize:false,width:_width, height:_height,html:_html});
	dg.ShowDialog();
}