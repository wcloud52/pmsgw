<!DOCTYPE html>
<html class="no-js">
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<title>Amaze UI Widget</title>
<meta name="description" content="">
<meta name="keywords" content="">
<meta name="viewport" content="width=device-width, initial-scale=1">
<meta name="renderer" content="webkit">
<meta http-equiv="Cache-Control" content="no-siteapp" />
<link rel="icon" type="image/png" href="../plugins/AmazeUI/assets/i/favicon.png">
<link rel="stylesheet" href="../plugins/AmazeUI/assets/css/amazeui.min.css">
<link rel="stylesheet" href="../plugins/AmazeUI/assets/css/app.css">
<link rel="stylesheet" href="../plugins/theme/public/css/bootstrap.css">
<link rel="stylesheet" href="../plugins/theme/public/css/dataTables_bootstrap.css">


<script type="text/javascript">
	/**
	 * 系统全局变量
	 * */
	var GlobalParam = {
		context : "/", //系统路径
		loadAnimate : false, //页面加载动画
		defaultTargetID : "content",
		language : "zh_CN"//语言
	};
	GlobalParam.context = "../plugins/";
	var contextPath = "../plugins/";
</script>
<style type="text/css">
p {
	display: block;
	-webkit-margin-before: 1em;
	-webkit-margin-after: 1em;
	-webkit-margin-start: 0px;
	-webkit-margin-end: 0px;
	margin-top: 1px;
}

.icon {
	width: 110px;
	vertical-align: top;
}

.groupby-img-panle {
	width: 38%;
	float: left;
	margin-top: 12px
}

.groupby-info-panle {
	margin-top: 12px
}

.groupby-info-panle h3 {
	font-size: 13px;
	color: #333;
	margin-bottom: 0.3rem
}

.groupby-info-panle p {
	font-size: 12px;
	color: #888888;
	margin: 0.2rem
}

.withdrawals-panel {
	border: solid 1px #ccc;
	border-radius: 5px;
	background-color: #fff;
	padding: 0.8rem 0.6rem;
	margin-bottom: 1rem;
	overflow: hidden
}
.bottom{
 margin-bottom: 50px;
}

.my-search-title-panel{ background:#fff; border-top: solid 1px #eaeaea; border-bottom:solid 1px #eaeaea; padding:8px; margin-top:9px;}
.my-search-titp-p{ margin-bottom:0.8rem}
</style>
</head>
<body>
	<!-- Header -->
	<header data-am-widget="header" class="am-header am-header-default">
	<div class="am-header-left am-header-nav">
        <a href="../plugins//wapIndex" class="">
          <i class="am-header-icon am-icon-chevron-left"></i>
        </a>
      </div>
		<h1 class="am-header-title">
			<a href="#title-link">detail-Amaze UI</a>
		</h1>
		<div class="am-header-right am-header-nav">
        <a href="#right-link" class="">
          <i class="am-header-icon  am-icon-home"></i>
        </a>
      </div>
	</header>




<div class="my-search-title-panel">
<!-- <div class="groupby-info-panle"> -->
<h3><a href="#">商都龙轩500公里决赛</a></h3>
<p>司放时间：<span class="am-text-success">2016/10/28 7:15:03</span>   
司放地点：<span class="am-text-success">西乌旗</span></p>
<p>集鸽羽数：<span class="am-text-success">/</span>   司放天气：<span class="am-text-success">晴</span></p>
<p>司放地坐标：<span class="am-text-success">118.12/ 44.30</span>   参考空距：<span class="am-text-success">498.28</span></p>
<p>当前归巢：<span class="am-text-success">/</span></p><p>比赛状态:<span class="am-text-success">比赛中</span></p>
<!-- </div> -->
</div>
	<!-- List -->
	<div data-am-widget="list_news" class="am-list-news am-list-news-default">
		<input type="hidden" id="displayStart" value="{param.displayStart}" />
		<div class="am-list-news-bd">
			<!-- <table id="tableList" class="am-table tpl-table">
                                <thead>
                                    <tr class="tpl-table-uppercase">
                                        <th>人员</th>
                                        <th>余额</th>
                                        <th>次数</th>
                                        <th>效率</th>
                                    </tr>
                                </thead>
                                <tbody>
                                    <tr>
                                        <td>
                                            <img src="assets/img/user01.png" alt="" class="user-pic">
                                            <a class="user-name" href="###">禁言小张</a>
                                        </td>
                                        <td>￥3213</td>
                                        <td>65</td>
                                        <td class="font-green bold">26%</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <img src="assets/img/user02.png" alt="" class="user-pic">
                                            <a class="user-name" href="###">Alex.</a>
                                        </td>
                                        <td>￥2635</td>
                                        <td>52</td>
                                        <td class="font-green bold">32%</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <img src="assets/img/user03.png" alt="" class="user-pic">
                                            <a class="user-name" href="###">Tinker404</a>
                                        </td>
                                        <td>￥1267</td>
                                        <td>65</td>
                                        <td class="font-green bold">51%</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <img src="assets/img/user04.png" alt="" class="user-pic">
                                            <a class="user-name" href="###">Arron.y</a>
                                        </td>
                                        <td>￥657</td>
                                        <td>65</td>
                                        <td class="font-green bold">73%</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <img src="assets/img/user05.png" alt="" class="user-pic">
                                            <a class="user-name" href="###">Yves</a>
                                        </td>
                                        <td>￥3907</td>
                                        <td>65</td>
                                        <td class="font-green bold">12%</td>
                                    </tr>
                                    <tr>
                                        <td>
                                            <img src="assets/img/user06.png" alt="" class="user-pic">
                                            <a class="user-name" href="###">小黄鸡</a>
                                        </td>
                                        <td>￥900</td>
                                        <td>65</td>
                                        <td class="font-green bold">10%</td>
                                    </tr>
                                </tbody>
                            </table>
    -->
			<table class="table table-striped table-bordered" id="tableList" width="100%">
				<!-- <thead>
				<tr>
					<th width="5%">序号</th>
													
					  	                   <th width="100%">比赛项目</th>
                      	                   <th width="10%">司放时间</th>
                      	                   <th width="10%">司放地点</th>
                      	                    <th width="10%">集鸽羽数</th>
                      	                   <th width="10%">司放天气</th>
                      	                   <th width="10%">司放地坐标</th>
                      	                    <th width="10%">参考空距</th>
                      	                     <th width="10%">当前归巢</th>
                      	                      <th width="10%">比赛状态</th>
                      					
				</tr>
			</thead> -->
			</table>
		</div>
		
	</div>

	<!-- Navbar -->
	<!-- <div data-am-widget="navbar" class="am-navbar am-cf am-navbar-default " id="">
		<ul class="am-navbar-nav am-cf am-avg-sm-4">
			<li><a href="tel:123456789"> <span class="am-icon-phone"></span> <span class="am-navbar-label">呼叫</span>
			</a></li>
			<li data-am-navbar-share><a href="###"> <span class="am-icon-share-square-o"></span> <span class="am-navbar-label">分享</span>
			</a></li>
			<li data-am-navbar-qrcode><a href="###"> <span class="am-icon-qrcode"></span> <span class="am-navbar-label">二维码</span>
			</a></li>
			<li><a href="https://github.com/allmobilize/amazeui"> <span class="am-icon-github"></span> <span class="am-navbar-label">GitHub</span>
			</a></li>
			<li><a href="http://amazeui.org/getting-started"> <span class="am-icon-download"></span> <span class="am-navbar-label">下载使用</span>
			</a></li>
			<li><a href="https://github.com/allmobilize/amazeui/issues"> <span class="am-icon-location-arrow"></span> <span class="am-navbar-label">Bug 反馈</span>
			</a></li>
		</ul>
	</div> -->

	<script src="../plugins//AmazeUI/assets/js/jquery.min.js"></script>
	<script src="../plugins//AmazeUI/assets/js/amazeui.min.js"></script>

	<script src="../plugins//js/easybcp/plugins/extend/jquery-ui-1.10.3.custom.min.js" type="text/javascript"></script>
	<script src="../plugins//js/easybcp/plugins/extend/bootstrap.min.js" type="text/javascript"></script>
	<script src="../plugins//js/easybcp/plugins/extend/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>

	<script src="../plugins//js/easybcp/plugins/jquery.dataTables.js" type="text/javascript"></script>
	<script src="../plugins//js/easybcp/plugins/dataTables_bootstrap.js" type="text/javascript"></script>
	<script src="../plugins//js/easybcp/plugins/TableTools.js" type="text/javascript"></script>
	<script>
		$(function() {
			var oTableTools = {
					//"sRowSelect" : "single",
					"aButtons" : []
				};
			$('#tableList').dataTable({
				"oTableTools" : oTableTools,
				"bProcessing" : true,
				"bServerSide" : true,
				"bRetrieve" : true,
				"bFilter" : false,
				"iDisplayStart" : $("#displayStart").val() == "" ? 0 : parseInt($("#displayStart").val(), 10),
				"sAjaxSource" : GlobalParam.context + "/PmsgwUser/tableData",
				"aaSorting" : [ [ 1, "asc" ] ],
				"bSortClasses" : false,
				"bPaginate": true, //翻页功能
				"bLengthChange": false, //改变每页显示数据数量
				//"sDom": "<'row-fluid'<'span6'l><'span6'f>r>t<'row-fluid'<'span6'i><'span6'p>>",
				//"sPaginationType": "bootstrap",
				//"sDom": '<"top"f>Trt<"top"lip><"clear">',
				//"sDom": "<row-fluid<'span6 center'p>>",
				"aoColumns" : [ {
					"sName" : "id",
					"mData" : "id",
					"bSearchable" : false,
					"bSortable" : false,
					"bVisible" : true
				}
				/*  ,{
				"sName" : "loginName",
				"mData" : "loginName",
				"bSearchable" : false,
				"sDefaultContent":"",
				"bVisible" : true
				} */

				/*  ,{
				"sName" : "userName",
				"mData" : "userName",
				"bSearchable" : true,
				"sDefaultContent":"",
				"bVisible" : true
				}
				,{
				"sName" : "modifyTime",
				"mData" : "modifyTime",
				"bSearchable" : false,
				"bVisible" : true
				} ,{
				"sName" : "loginName",
				"mData" : "loginName",
				"bSearchable" : false,
				"sDefaultContent":"",
				"bVisible" : true
				}
				,{
				"sName" : "userName",
				"mData" : "userName",
				"bSearchable" : true,
				"sDefaultContent":"",
				"bVisible" : true
				}
				,{
				"sName" : "modifyTime",
				"mData" : "modifyTime",
				"bSearchable" : false,
				"bVisible" : true
				},{
				"sName" : "loginName",
				"mData" : "loginName",
				"bSearchable" : false,
				"sDefaultContent":"",
				"bVisible" : true
				}
				,{
				"sName" : "userName",
				"mData" : "userName",
				"bSearchable" : true,
				"sDefaultContent":"",
				"bVisible" : true
				} */
				/* 		 ,{
				"sName" : "modifyTime",
				"mData" : "modifyTime",
				"bSearchable" : false,
				"bVisible" : true
				} */
				],
				"fnRowCallback" : function(nRow, aData, iDisplayIndex, iDisplayIndexFull) {

					var id = $("td:first", nRow).html();
					nRow.id = id;
					var oSettings = this.fnSettings();
					if (oSettings.oFeatures.bServerSide) {
						$("td:first", nRow).html(oSettings._iDisplayStart + iDisplayIndex + 1);
					} else {
						$("td:first", nRow).html(iDisplayIndexFull + 1);
					}
					//比赛项目	司放时间	司放地点	集鸽羽数	司放天气	司放地坐标	参考空距	当前归巢	比赛状
					//$("td:eq(0)", nRow).addClass("icon");
					//$("td:eq(0)", nRow).html('<img src="http://file.saige.com/smallImage.aspx?url=%2fupload%2f2017%2f2017-01-11%2fac3a8097-6aeb-433e-80d2-12bebf5a4362.jpg&amp;width=300&amp;height=300" width="100" alt="">');
					//$("td:eq(0)", nRow).html("<h3><a href='#'>广州XXX店</a></h3><p>司放时间:<span class='c4'>2016/10/28 7:15:03</span></p><p>司放地点:<span class='c4'>西乌旗</span></p><p>集鸽羽数:<span class='c4'></span></p><p>司放天气:<span class='c4'></span></p><p>司放地坐标:<span class='c4'></span></p><p>参考空距:<span class='c4'></span></p><p>当前归巢:<span class='c4'></span></p><p>比赛状:<span class='c4'></span></p>");
					var v = '<div class="withdrawals-panel">';
					//v+=' <div class="groupby-img-panle"><a href="#"><img src="default/img3.jpg" class="am-img-responsive"></a></div>';
					v += '<div class="groupby-info-panle">';
					v += '<h3><a href="#">商都龙轩500公里决赛</a></h3>';
					v += '<p>司放时间：<span class="am-text-success">2016/10/28 7:15:03</span>   司放地点：<span class="am-text-success">西乌旗</span></p>';
					v += '<p>集鸽羽数：<span class="am-text-success">/</span>   司放天气：<span class="am-text-success">晴</span></p>';
					v += '<p>司放地坐标：<span class="am-text-success">118.12/ 44.30</span>   参考空距：<span class="am-text-success">498.28</span></p>';
					
					v += '<p>当前归巢：<span class="am-text-success">/</span>';
					v += '<p>比赛状态:<span class="am-text-success">比赛中</span></p>';
					v += '</div>';
					v += '</div>';
					$("td:eq(0)", nRow).html(v);
				}
			});

		});
	</script>
</body>
</html>
