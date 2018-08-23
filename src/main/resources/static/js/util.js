
/*******************************************************************************************************
 * 日期操作
*******************************************************************************************************/
//获得指定格式的当前日期yyyy-mm-dd
function getCurrentDate(){
	var tmpDate  = new Date();
	var date    =    tmpDate.getDate();
	date =(date<10 ? "0"+date:date);
	var month   =    tmpDate.getMonth()+1;
	month =(month<10 ? "0"+month:month);
	var year    =    tmpDate.getFullYear();
	var currentDay=year+"-"+month+"-"+date;
	return currentDay;
}

//获得指定格式的当前日期yyyymmdd
function getCurrentDateTime(){
	var tmpDate  = new Date();
	var date    =    tmpDate.getDate();
	date =(date<10 ? "0"+date:date);
	var month   =    tmpDate.getMonth()+1;
	month =(month<10 ? "0"+month:month);
	var year    =    tmpDate.getFullYear();
	var currentDay=year+""+month+""+date;
	return currentDay;
}

//获取180天之前的日期
function getTimeBeforSixMonth(){
	var now = new Date();
	var beforTime = new Date(now.getTime() - 1000 * 60 * 60 * 24 * 30 * 12-(1000 * 60 * 60 * 24*6));
	var date    =    beforTime.getDate();
	date =(date<10 ? "0"+date:date);
	var month   =    beforTime.getMonth()+1;
	month =(month<10 ? "0"+month:month);
	var year    =    beforTime.getFullYear();
	var sixMonth=year+""+month+""+date;
	return sixMonth;
}

//比较当前时间与输入时间的差是否大于两周年
function getNumberByNowAndTime(strDate){
	var tmpDate  = new Date();
	var date    =    tmpDate.getDate();
	date =(date<10 ? "0"+date:date);
	var month   =    tmpDate.getMonth()+1;
	month =(month<10 ? "0"+month:month);
	var year    =    tmpDate.getFullYear();
	var currentDay=year+""+month+""+date;
	if(strDate.indexOf("/")!=-1)
	{
	strDate=strDate.replace("/","");
	strDate=strDate.replace("/","");
	}
	if((currentDay - strDate)>=20000){
		return true;
	}else{
		return false;
	}
}

//比较时间是否大于12个月
function checkTimeInSixMonth(startDate, endDate){  
	var startTime = getTimeByDateStr(startDate);  
    var endTime = getTimeByDateStr(endDate);  
    if((endTime - startTime) > (12*30*24*60*60*1000)+(1000 * 60 * 60 * 24*6)){  
        return false;  
    }  
    return true;  
}  
	  
	  
//根据日期字符串取得其时间  
function getTimeByDateStr(dateStr){ 
    var year = parseInt(dateStr.substring(0,4));
    var month = parseInt(dateStr.substr(4,2))-1;  
    var day = parseInt(dateStr.substr(6,2));  
    return new Date(year, month, day).getTime();  
}  



//获得当前星期几的中文描述
function getCurrentWeekDay(){
	myArray = new Array(6);
	myArray[0]    =    "星期日";
	myArray[1]    =    "星期一";
	myArray[2]    =    "星期二";
	myArray[3]    =    "星期三";
	myArray[4]    =    "星期四";
	myArray[5]    =    "星期五";
	myArray[6]    =    "星期六";
	
	weekday    =    tmpDate.getDay();
	return weekday;
}
/*******************************************************************************************************
 * option对象操作
*******************************************************************************************************/
function removealloption(obj){
   	var size=obj.options.length;
	for(i=0;i<size;i++){
		obj.remove(0);
	}
}

function createOption(label,value)
{
	var varOption=document.createElement("OPTION");
	varOption.text=label;
	varOption.value=value;
	return varOption;
}

/*******************************************************************************************************
 * 数组操作
*******************************************************************************************************/
//删除指定位置的对象	
Array.prototype.removeAt=function(Index)
{
	if(isNaN(Index)||Index>this.length){return false;}
	for(var i=0,n=0;i<this.length;i++)
	{
		if(this[i]!=this[Index])
		{
			this[n++]=this[i]
		}
	}
	this.length-=1
 }

//从数组中删除指定对象
Array.prototype.remove=function(obj)
{
	if(null==obj){return;}
	var tmp = new Array(this.length);
	for(var i=0,n=0;i<this.length;i++)
	{
		if(this[i]!=obj)
		{
			tmp[n++]=this[i];
		}
	} 
	return tmp
}

//判断数组中是否包含指定对象
Array.prototype.Contains=function(obj)
{
	if(null==obj){return;}
	for(var i=0,n=0;i<this.length;i++)
	{
		if(this[i]!=obj)
		{
			return true;
		}
	}
	return false;
}


//获得对象的索引号
Array.prototype.IndexOf=function(obj)
{
	if(null==obj){return;}
	{
		for(var i=0,n=0;i<this.length;i++)
		{
			if(this[i]==obj)
			{
				return i;
			}
		}
	}
	return -1;
}

//清空数组
Array.prototype.Clear=function()
{
	this.length=0;
}

/*******************************************************************************************************
 * 字符串处理
*******************************************************************************************************/
//将输入串转换成大写
function toUpper(objID){
	if(document.getElementById(objID)==undefined){
		alert("对象：'+objID+'未定义");
		return false;
	}
	var oldValue=document.getElementById(objID).value;
	document.getElementById(objID).value=oldValue.toUpperCase();
}


//去左空格;
function ltrim(s){ 
	return s.replace(/(^\s*)/g, "");
} 
 
//去右空格;
function rtrim(s){ 
	return s.replace(/(\s*$)/g, "");
} 
  
//去左右空格;
function trim(s){ 
   return rtrim(ltrim(s)); 
}

/*******************************************************************************************************
 * 数字处理
*******************************************************************************************************/
//处理整数
function filterInteger(obj){
	obj.value = obj.value.replace(/[^\d;]/g,"");
	//obj.value = obj.value.replace(/(^0[\d;])/g,"");
}

//处理小数
function filterDecimal(obj) {
	// 先把非数字的都替换掉，除了数字和.
	obj.value = obj.value.replace(/[^\d.]/g,"");
	//必须保证第一个为数字而不是.
	obj.value = obj.value.replace(/^\./g,"");
	//保证只有出现一个.而没有多个.
	obj.value = obj.value.replace(/\.{2,}/g,".");
	// 保证.只出现一次，而不能出现两次以上
	obj.value = obj.value.replace(".","$#$").replace(/\./g,"").replace("$#$",".");
}

//按指定精度格式化输出
function  formatDigit(digit,precision){     
	digit  =  Math.round (digit*Math.pow(10,precision))/Math.pow(10,precision);     
	return  digit;     
}
/*******************************************************************************************************
 * 常用校验函数
*******************************************************************************************************/
function validateASCII(){
  var activeObj=document.activeElement;
  if(activeObj.value!=''){
	   var regu = /^[a-zA-Z0-9]*$/;
	   if (regu.test(activeObj.value)){
	       return true;
	   } else {
	       alert('只允许输入数字和字母');
	       return false;
	   }
   }
}

function validateNumber(number){
  if(number!=''){
	   var regu = /^[0-9]*$/;
	   if (regu.test(number)){
	       return true;
	   } else {
	       return false;
	   }
   }else{
	return false;  
   }
}

// 验证手机号码
function checkIsMobile(mobile){
	if(isNaN(mobile)||(mobile.length!=11)){
  	    return false;
  	}
  	var reg =/^13\d{9}$|^15\d{9}$|^17\d{9}$|^18\d{9}$|^145\d{8}$|^147\d{8}$|^199\d{8}$|^198\d{8}$|^166\d{8}$/;
  	if(!reg.test(mobile)){
  	    return false;
  	}
  	return true;
}
  
  
//验证电话号码
function checkIsPhone(phone){
	if(isNaN(phone)||(phone.length<7)){
  	    return false;
  	}
  	var reg =/(([0]{2}|\+)?[1-9]{1,4}([-,\s]+))?(0[1-9]\d{1,2}[-,\s]+)?[2-9]\d{6,7}([-,\s]+\d{1,6})?$/;
  	if(!reg.test(phone)){
  	    return false;
  	}
  	return true;
}
  
//验证金额是否合法  
function isMoney(s){ 
	var patrn=/^-?\d+\.{0,}\d{0,}$/; 
	if (!patrn.exec(s)) {
		return false;
	}else{
		return true;
	}
}

//验证email地址是否合法
function isValidMail(sText) {
	var reMail = /^(?:[a-zA-Z0-9]+[_\-\+\.]?)*[a-zA-Z0-9]+@(?:([a-zA-Z0-9]+[_\-]?)*[a-zA-Z0-9]+\.)+([a-zA-Z]{2,})+$/;
	return reMail.test(sText);
}

var Wi = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2, 1];    // 加权因子   
var ValideCode = [1, 0, 10, 9, 8, 7, 6, 5, 4, 3, 2];            // 身份证验证位值.10代表X   
function IdCardValidate(idCard) {
    idCard = trim(idCard.replace(/ /g, ""));               //去掉字符串头尾空格                     
    if (idCard.length == 15) {
        return isValidityBrithBy15IdCard(idCard);       //进行15位身份证的验证    
    } else if (idCard.length == 18) {
        var a_idCard = idCard.split("");                // 得到身份证数组   
        if (isValidityBrithBy18IdCard(idCard) && isTrueValidateCodeBy18IdCard(a_idCard)) {   //进行18位身份证的基本验证和第18位的验证
            return true;
        } else {
            return false;
        }
    } else {
        return false;
    }
}
/**  
 * 判断身份证号码为18位时最后的验证位是否正确  
*/
function isTrueValidateCodeBy18IdCard(a_idCard) {
    var sum = 0;                             // 声明加权求和变量   
    if (a_idCard[17].toLowerCase() == 'x') {
        a_idCard[17] = 10;                    // 将最后位为x的验证码替换为10方便后续操作   
    }
    for (var i = 0; i < 17; i++) {
        sum += Wi[i] * a_idCard[i];            // 加权求和   
    }
    valCodePosition = sum % 11;                // 得到验证码所位置   
    if (a_idCard[17] == ValideCode[valCodePosition]) {
        return true;
    } else {
        return false;
    }
}
/**  
  * 验证18位数身份证号码中的生日是否是有效生日  
*/
function isValidityBrithBy18IdCard(idCard18) {
    var year = idCard18.substring(6, 10);
    var month = idCard18.substring(10, 12);
    var day = idCard18.substring(12, 14);
    var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
    // 这里用getFullYear()获取年份，避免千年虫问题   
    if (temp_date.getFullYear() != parseFloat(year)
          || temp_date.getMonth() != parseFloat(month) - 1
          || temp_date.getDate() != parseFloat(day)) {
        return false;
    } else {
        return true;
    }
}
/**  
 * 验证15位数身份证号码中的生日是否是有效生日 
*/
function isValidityBrithBy15IdCard(idCard15) {
    var year = idCard15.substring(6, 8);
    var month = idCard15.substring(8, 10);
    var day = idCard15.substring(10, 12);
    var temp_date = new Date(year, parseFloat(month) - 1, parseFloat(day));
    // 对于老身份证中的你年龄则不需考虑千年虫问题而使用getYear()方法   
    if (temp_date.getYear() != parseFloat(year)
            || temp_date.getMonth() != parseFloat(month) - 1
            || temp_date.getDate() != parseFloat(day)) {
        return false;
    } else {
        return true;
    }
}
//去掉字符串头尾空格   
function trim(str) {
    return str.replace(/(^\s*)|(\s*$)/g, "");
}

/**  
 * 通过身份证判断是男是女  
 * @return 'female'-女、'male'-男  
 */
function maleOrFemalByIdCard(idCard) {
    idCard = trim(idCard.replace(/ /g, ""));        // 对身份证号码做处理。包括字符间有空格。   
    if (idCard.length == 15) {
        if (idCard.substring(14, 15) % 2 == 0) {
            return 'female';
        } else {
            return 'male';
        }
    } else if (idCard.length == 18) {
        if (idCard.substring(14, 17) % 2 == 0) {
            return 'female';
        } else {
            return 'male';
        }
    } else {
        return null;
    }
}
 

 //时间转换显示
function convertTimeToNewTime(datestring)
{
	var rtTime;
	if(datestring.length>10)
		{
		 datestring=datestring.split(" ");
		var spDate=datestring[0].split("/");
		rtTime=spDate[2]+"-"+spDate[0]+"-"+spDate[1]+" "+datestring[1];
		} else  if(datestring.length==10)
		{
			var spDate=datestring.split("/");
			rtTime=spDate[2]+"-"+spDate[0]+"-"+spDate[1];
		}else
			{
			rtTime=datestring;
			}
	return rtTime;
	}


//简单密码
var simplyPas=new Array("000000",
		"111111",
		"222222",
		"333333",
		"444444",
		"555555",
		"666666",
		"777777",
		"888888",
		"999999",
		"123456",
		"112233",
		"778899",
		"100000",
		"200000",
		"300000",
		"400000",
		"500000",
		"600000",
		"700000",
		"800000",
		"900000",
		"181818",
		"121212",
		"888666",
		"666888",
		"654321",
		"123321",
		"123123",
		"111222",
		"168168");
//验证是都简单密码
function validPayPas(pas) {
	for (x in simplyPas) {
		if (simplyPas[x] == pas) {
			return false;
		}
	}
	return true;
}