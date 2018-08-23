  var projectURL="/FFPClub";
// 点击上边菜单
  function ckMenu(url, id) {

	var isWap = false;
	if (url == "/member/user/main") {
		if ((navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i))) {
			isWap = true;
		}
	}
	if(id!="jp_cjwt"){	
		if (isWap) {
			if(id==""){id="wdzh";}
			window.location.href = "/FFPWap" + menu_WAP_ID[id];
		} else {
			window.location.href = projectURL + url + "?" + id;
		}
	}
	
}
  
  
  var menu_WAP_ID = {
			"wdzh" : "/member/user/accountInfo",
			"jbxx" : "/member/user/memberInfo",
			"mmgl" : "/member/user/updpasswordview.do",
			"syrgl" : "/beneficiaries/user/main.do",
			"jtzh":"/familyAccount/user/searchView",
			"jfmx" : "/mileage/user/searchForMileManage",
			"jfbd" : "/mileage/user/addview.do",
			"dzj" : "/reward/user/loadList'",
			"cyhd" : "/activity/user/form/userActivity.do",
			"mfsc" : "/privilege/user/upgrade",
			"jftz" : "/privilege/user/overdraft",
			"zsgbk" : "/privilege/user/vipcard"
		}


//菜单显示隐藏
  function lanrenzhijia() {
		$(".mj_menu_pro_main").hide();
		var theMenu = $(".mj_menu_pro_main");
		var tarHeight = theMenu.height();
		theMenu.css({
			height : 0
		});
		$(".nav li").each(function() {
			var index = $(this).index();
			if (index != 0) {
				$(this).hover(function() {
					//判断是否是当前栏目
					if(typeof(main_sp_nav_name)!="undefined"){
					if($(this).attr("id")=='nav_li_sd_'+main_sp_nav_name)
					{
					$(this).find("a span").removeClass("nav_active");
					}}
					
					$(this).css({
						"background-color" : "#ffffff"
					});
					$(this).find("a span").css({
						"color" : "#e91a2c"
					});
					theMenu.show();
					if (index < 1) {
						return false
					}
					tarHeight=theMenu.children('div').eq(index-1).height();
					theMenu.children('div').eq(index - 1).show().siblings().hide();
					theMenu.stop().animate({
						height : tarHeight
					}, 300);
				}, function(e) {
					
					var evt = $(e.relatedTarget);
					var emClassName = evt.children().attr('class');
					if (emClassName != 'mj_menu_div') {
						
						if(typeof(main_sp_nav_name)!="undefined"){
							if($(this).attr("id")=='nav_li_sd_'+main_sp_nav_name)
							{
							$(this).find("a span").addClass("nav_active");
							}}
						
						$(this).css({
							"background-color" : "#ee1b24"
						});
						$(this).find("a span").css({
							"color" : "#ffffff"
						});
						theMenu.stop().animate({
							height : 0
						}, 300, function() {
							theMenu.hide();
						});
					}
				});
			}
		});

		$(".mj_menu_pro_main").bind({
			'mouseleave' : outShowDiv
		})
		$(".mj_menu_li_txt").find("p").hover(function() {
			$(this).attr("class", "mj_menu_3active");
		}, function() {
			$(this).attr("class", "mj_menu_3");
		});
	}
  
  
  function outShowDiv(e) {
		$(".nav li").css({
			"background-color" : "#ee1b24"
		});
		$(".nav li").find("a span").css({
			"color" : "#ffffff"
		});
		var evt = $(e.relatedTarget);
		var emClassName = evt.parents().attr('class');
		if (emClassName != 'nav' && emClassName != 'nav_li_sd'
				&& emClassName != 'nav_li_a_sd') {
			
			if(typeof(main_sp_nav_name)!="undefined"){
				$("#nav_li_sd_"+main_sp_nav_name).find("a span").addClass("nav_active");
				}
			
			$(".mj_menu_pro_main").stop().animate({
				height : 0
			}, 300, function() {
				$(this).hide();
			});
		}

	}
  
  
//分析微博
  function shareMB(oTitle,oUrl,oContent,oPic){
  	var webURU="http://newffp.hnair.com/FFPClub/";
  	var _url       = "url="+encodeURIComponent(webURU+oUrl);//页面URL
  	var _title     = "&title="+encodeURIComponent(oTitle);
  	var _content   = "&content="+oContent;
  	var _pic       = "&pic="+encodeURIComponent(webURU+oPic);//有图片就加图片地址

  	var URL ="http://v.t.sina.com.cn/share/share.php?"+_url+_title+_content+_pic+"&appkey=689112224";

  	var OP = "width=500,height=500";
  	window.open(URL,'_blank',OP);
  }
  
  
  //关注
  function saveMyAttentionData(id) {
		$.ajax({
					url : projectURL+'/myAttention/addMyAttentionData/1',
					type : 'post',
					data : {
						id : id
					},
					success : function(data) {
						var jsondata = jQuery.parseJSON(data);
						if (jsondata.success=='1') {
							alert("关注成功");
							$("#gz_hb_" + id).html("已关注").removeAttr("onclick");//移除click;
						} else if (jsondata.success=='0') {
							alert('已关注');
							$("#gz_hb_" + id).html("已关注").removeAttr("onclick");//移除click;
						} else if (jsondata.success=='2') {
							alert('操作失败');
						} else if (jsondata.success=='3') {
							alert('请先登录');
							$("#navright_up").show();
							$("#navright_down").hide();
							$("#login_div_st").show();
						}else
							{
							alert('系统错误');
							}
					}
				});
	}

  
  
  function searchBind()
  {
  	$("#search_pt").bind("blur",function(){
  		if($("#search_pt").val()=='')
  			{
  			$("#search_pt").val("请输入关键字");
  			}
  	});
  	$("#search_pt").bind("focus",function(){
  		if($("#search_pt").val()=='请输入关键字')
  			{
  			$("#search_pt").val("");
  			}
  	});
  }

  function loginBind()
  {
  	$("#userName").bind("blur",function(){
  		if($("#userName").val()=='')
  			{
  			$("#userName").val("卡号/身份证/手机/邮箱");
  			}
  	});
  	$("#userName").bind("focus",function(){
  		if($("#userName").val()=='卡号/身份证/手机/邮箱')
  			{
  			$("#userName").val("");
  			}
  	});
  	
  	
  	$("#login_pwd").bind("blur",function(){
  		if($("#login_pwd").val()=='')
  			{
  			$("#login_pwd_p").show();
  			$("#login_pwd").hide();
  			}
  	});
  	
  	$("#login_pwd_p").bind("focus",function(){
  		$("#login_pwd_p").hide();
  		$("#login_pwd").show();
  		$("#login_pwd").focus();
  	});
  	
  	$("#validCode").bind("blur",function(){
  		if($("#validCode").val()=='')
  			{
  			$("#validCode").val("验证码");
  			}
  	});
  	
  	$("#validCode").bind("focus",function(){
  		if($("#validCode").val()=='验证码')
  			{
  			$("#validCode").val("");
  			}
  	});
  }

//登录
  function user_login() {
  	var userName = $("#userName").val();
  	var login_pwd = $("#login_pwd").val();
  	var validCode = $("#validCode").val();
  	userName=$.trim(userName);
  	login_pwd=$.trim(login_pwd);
  	validCode=$.trim(validCode);
  	if (userName== '') {
  		alert('请输入登录帐号');
  		$("#userName").focus();
  		return;
  	}
  	if(userName=='卡号/身份证/手机/邮箱')
  		{
  		$("#userName").focus();
  		return;
  		}
  	var isY=false;
  	var reg = /(^\d{6,}$)|(^\d{6,}X$)/;
  	if(!reg.test(userName))
  		{
  		reg = /^([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\-|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/gi;
  	 	if(reg.test(userName))
  	 		{
  	 		isY=true;
  	 		}
  		}else
  		  {
  			isY=true;
  			}
  	if(!isY)
  		{
  		alert("登录账号格式错误");
  		return;
  		}
  	
  	if (login_pwd == '') {
  		alert('请输入密码');
  		$("#login_pwd").focus();
  		return;
  	}
  	var pwdReg = /^[0-9]{6}$/;
  	if(!pwdReg.test(login_pwd))
  	{
  		alert('密码格式错误,请输入6位数字');
  		$("#login_pwd").focus();
  		return;
  	}
  	
  	if (validCode == '') {
  		alert('请输入验证码');
  		$("#validCode").focus();
  		return;
  	}
  	
  	if (validCode.length!=4) {
  		alert('验证码格式错误');
  		$("#validCode").focus();
  		return;
  	}
  	
  	
  	var vdCode=checkValidCode();
  	if(!vdCode)
  		{
  		alert("验证码错误");
  		return;
  		}
  	
  	login_pwd=EncryptPWD(login_pwd);
  	$("#ctl_signIn").html("正在登录");
  	$("#ctl_signIn").removeAttr('onclick');
  	jQuery.ajax({
  		type : 'POST',
  		url : projectURL + '/member/loginIdx',
  		data : {
  			userName : userName,
  			login_pwd : login_pwd,
  			validCode_login:$("#validCode").val()
  		},
  		dataType : 'json',
  		success : function(data) {
  			if (data.state == 1) {
  				var member = data.member;
  				member = jQuery.parseJSON(member);
  				
  				var lc_href = window.location.href;
  				if(lc_href.indexOf("activity/info")==-1)
  					{
  				// 刷新页面
  				if (data.is_initpwd) {
  					//alert('您的密码为初始密码');
  					window.location.href = projectURL
  					+ "/member/user/main?pw_d=1";
  				}
  				if (data.is_complement) {
  					window.location.href = projectURL
  					+ "/member/user/main?cp_d=1";
  				}
  					}
  				$("#navright_contenter").hide();
  				$("#navright_contenter_after").show();
  				var show_name='';
				 if((/[\u4e00-\u9fa5]+/).test( member.firstName )){  
					  show_name= member.firstName + member.lastName
				  }else
					  {
					  show_name=member.firstName 
					  }
  				$("#navright_bg_p").html(
  						"欢迎登录：&nbsp;" + show_name
  								+ member.title);
  				$("#m_card").html(member.CID);
  				$("#m_grade").html(member.grade);
  				$("#m_kylc").html(member.points);
  				$("#jfyxq").html(member.expDate);
  			} else {
  				if (data.errNum) {
  					data.msg = data.errNum == 1 ? '用户名或密码错误，今日还可登录2次'
  							: data.errNum == 2 ? '用户名或密码错误，今日还可登录1次'
  									: '对不起，您登录失败超过3次，账户已锁定，当天24：00可解锁';
  				}
  				alert(data.msg);
  				refreshCode();
  				$("#login_pwd").val("");
  				$("#ctl_signIn").html("登录");
  				$("#ctl_signIn").attr("onclick","user_login()");
  				$("#validCode").val("");
  			}
  		},
  		error : function(data) {
  			//alert("系统错误");
  			$("#ctl_signIn").html("登录");
  			$("#ctl_signIn").attr("onclick","user_login()");
  		}
  	});

  }

  //判断是否登录
  function load_userLogin() {
  	jQuery.ajax({
  		type : 'POST',
  		url : projectURL + '/calculator/loadUserLogin',
  		data : {},
  		dataType : 'json',
  		success : function(data) {
  			if (data.state == 1) {
  				var member = data.member;
  				member = jQuery.parseJSON(member);
  				// 刷新页面
  				$("#navright_contenter").hide();
  				$("#navright_contenter_after").show();
  				var show_name='';
				 if((/[\u4e00-\u9fa5]+/).test( member.firstName )){  
					  show_name= member.firstName + member.lastName
				  }else
					  {
					  show_name=member.firstName 
					  }
  				$("#navright_bg_p").html(
  						"欢迎登录：&nbsp;" + show_name
  								+ member.title);
  				$("#m_card").html(member.CID);
  				$("#m_grade").html(member.grade);
  				$("#m_kylc").html(member.points);
  				$("#jfyxq").html(member.expDate);
  			} 
  		},
  		error : function(data) {
  			//alert("系统错误");
  		}
  	});

  }

  

//登录框
function login_set()
{
	$("#navright_down").show();
	$("#navright_up").hide();
	$(".navright_bg").click(function() {
		$("#login_div_st").toggle();
		if($("#login_div_st").css("display")=='none')
		{
			$("#navright_down").show();
			$("#navright_up").hide();
		}else
			{
			$("#navright_up").show();
			$("#navright_down").hide();
			
			}
	});
	}

//语言选择
function ch_lang()
{
	if($("#ch_lang").css("display")=='none')
		{
		$("#ch_lang").show();
		$(".select_language_war_h").show();
		$(".select_language_war").hide();
		}else
			{
			$("#ch_lang").hide();
			$(".select_language_war_h").hide();
			$(".select_language_war").show();
			}
    
}


function refreshCode(){  
    document.getElementById("rc").src=projectURL+"/imgcode.do?r"+ Math.random(); 
    $("#rc").show();
    $("#validCode_h").hide();
	 $("#validCode").val('');
} 


function EncryptPWD(word){
	var aesKey=getEncryptKey();
    var key = CryptoJS.enc.Utf8.parse(aesKey);  
    var iv  = CryptoJS.enc.Utf8.parse(aesKey); 
    var srcs = CryptoJS.enc.Utf8.parse(word);  
    var encrypted = CryptoJS.AES.encrypt(srcs, key, { iv: iv,mode:CryptoJS.mode.CBC});  
    return encrypted.toString();  
} 

function getEncryptKey()
{
	var passKey="";
	jQuery.ajax({ 
		async: false, 
		type : "POST", 
		url : projectURL+'/member/memberFindAesKey', 
		dataType : 'json',
		success : function(data) { 
		passKey=data.key;
		},
		error:function(){ 
		     alert("error"); 
		   }  
		}); 
	return passKey;
} 

//验证码
function checkValidCode(){
	var result = false;
	var validCode = $.trim($("#validCode").val());
	jQuery.ajax({ 
		type : 'POST',
		url : projectURL+'/imgcode.do?r'+ Math.random(),
		data : {vc:validCode},
		dataType : 'json',
		async:false,
		success : function(data) {
			 if(data.state==1){
				 result = true ;
			 }else{
				 refreshCode();
			 }
		},
		error : function(data) { 
		}
	}); 
	return result ;
}

//友情链接
function ch_blogroll()
{
	if($(".foot_war_h").css("display")=='none')
		{
		$(".foot_war_h").show();
		$(".foot_language").show();
		$(".foot_war").hide();
		}else
			{
			$(".foot_war_h").hide();
			$(".foot_language").hide();
			$(".foot_war").show();
			}
    
}

function  qwSearch()
{
 var search=document.getElementById("search_pt").value;
 if(search.replace(/^\s+|\s+$/g, "")==""||search=='请输入关键字')
 {
 alert("请输入有效关键字");
 return false;
 }
 var vdSearch = /^[\u4E00-\u9FA5a-zA-Z0-9_ ]{1,20}$/;
	if(!vdSearch.test(search))
		{
		return false;
		}
	var searchb= encodeURI(search);
	 window.open('https://ffp.hnair.com/SmartSearch/s?search='+searchb);
}


function sub_login_key(e) {
	var k = window.event ? window.event.keyCode : e.which;
	if (k == 13) {
		$("#ctl_signIn").click();
	}
}

function sub_search_key(e) {
	var k = window.event ? window.event.keyCode : e.which;
	if (k == 13) {
		qwSearch();
	}
}

//点击分享
function ck_share()
{
	$(".win-close").click(
			function() {
				$(".winxin-show").hide();
			}
	);
	
	$(".weixin").click(
			function() {
				$(".winxin-show").show();
			}
	);
}


function togoIndex()
{
	    if ((navigator.userAgent.match(/(iPhone|iPod|Android|ios|iPad)/i))) {
	    	window.location.href = "/FFPWap/cn/index.html";
	    }else
	    	{
	    	window.location.href = "/FFPClub/cn/index.html";
	    	}
	}


$(function() {
	load_userLogin();
	lanrenzhijia();
	loginBind();
	searchBind();
	// 登录框
	login_set();
	ck_share();
	
	if(typeof(main_sp_nav_name)!="undefined")
		{
		$("#sp_nav_"+main_sp_nav_name).addClass("nav_active");
		}


	$.ajaxSetup({
		contentType : "application/x-www-form-urlencoded;charset=utf-8",
		complete : function(XMLHttpRequest, textStatus) {
			var sessionstatus = XMLHttpRequest.getResponseHeader("sessionstatus"); // 通过XMLHttpRequest取得响应头，sessionstatus， 
			if (sessionstatus == "timeout")
			{
				window.top.location = projectURL+"/member/login";
			}else if (sessionstatus == "filterKey")
			{
				window.top.location = projectURL+"/cn/error_f.html";
			}
		}
	});
	
});



