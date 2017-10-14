function ajaxFunction(){
	var xmlHttp;
	try{
		//现代浏览器（IE7+、Firefox、Chrome、Safari 和 Opera）都有内建的 XMLHttpRequest 对象
		xmlHttp = new XMLHttpRequest();
	}catch(e){
		try{
			//IE6.0
			xmlHttp = new ActiveXObject("Msxml2.XMLHTTP");
		}catch(e){
			try{
				//IE5.0及更早版本
				xmlHttp = new ActiveXObject("Microsoft.XMLHTTP");
			}catch(e){
				alert("...");
				throw e;
			}
		}
				}
				return xmlHttp;
}




/* 前台获取短信验证码，按钮效果 */
var InterVarobj;//timer变量，控制时间
var count = 60;//间隔函数，1秒执行
var curCount;//当前剩余秒数
var code = "";//验证码
var codeLength = 6;//验证码长度


function sendMessage1(){
	curCount = count;
	var phone = $("input[name='phonenumber']").val().trim();
	var span = $("input[name='phonenumber']").next("span");
	if(phone !=''){
		//产生验证码
    	//每次调用生成一次6位数的随机数
       /* for(var i=1;i<=6;i++){
        	code += (int)(Math.random()*9);
        }*/
        
        
		// 设置button效果，开始计时  
        $("input[type='button']").attr("disabled", "true");  
        //$("input[type='button']").val("请在" + curCount + "秒内输入验证码");  
        $("input[type='button']").val(curCount + "秒后再获取"); 
        InterValObj = window.setInterval(SetRemainTime, 1000); // 启动计时器，1秒执行一次
        
        //向后台发送处理数据：ajax
        /*$.ajax({
        	type:"POST",//用post方式传输
        	dataType:"text",//数据格式：json
        	url:"<%= request.getContextPath()%>/servlet/AjaxCheckSmsServlet",//目标地址
        	data:"phonenumber="+phone,
        	success:function (){
        		data = parseInt(data,10);
        		if(data == 1){
        			$("input[name='phonenumber']").next("span").html("<font color='#339933'>√ 短信验证码已发到您的手机,请查收</font>");  
        		}else if(data == 0){
        			$("input[name='phonenumber']").next("span").html("<font color='red'>×  短信验证码发送失败，请重新发送</font>");  
        		}else if(data == 2){
        			$("input[name='phonenumber']").next("span").html("<font color='red'>× 该手机号码今天发送验证码过多</font>");
        		}
        	}
        });*/
        
        
		
		//1.获取XMLHttpRequest对象
		//var xmlHttp = ajaxFunction();
		
		//2.建立连接
		/*
		 * post: 请求方式
		 * url: 请求资源的路径
		 * async: 是否异步传输
		 */
		//xmlHttp.open("POST", "/servlet/AjaxCheckServlet", true);
		//xmlHttp.open("GET", "/servlet/AjaxCheckServlet?phonenumber="+phone, true);
		
		
		//3.发送请求
		//通知服务器发送的数据是请求参数
		/*xmlHttp.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
		xmlHttp.send("phonenumber="+phone);*/
	
		//4.注册监听
		/*xmlHttp.onreadystatechange = function(){
			//时刻监听服务器处理请求的过程(状态), 但是我们只关心为4的状态
			if(xmlHttp.readyState == 4){
				//如果请求处理成功了才获取响应结果
				if(xmlHttp.status == 200){
					//获取响应结果
					var result = xmlHttp.responseText;
					if(result == "true"){
						$("input[name='phonenumber']").next("span").html("<font color='red'>×  短信验证码发送失败，请重新发送</font>");  
					}else{
						$("input[name='phonenumber']").next("span").html("<font color='#339933'>√ 短信验证码已发到您的手机,请查收</font>");  
					}
				}
			}
		}*/
        
        $("#sms_msg").load(
				"<%= request.getContextPath()%>/servlet/AjaxCheckSmsServlet",
				{"phonenumber":phone}
			);
        
        console.log(2);
        
        
	}else{  
        $("input[name='phonenumber']").next("span").html("<font color='red'>× 手机号码不能为空</font>");  
    } 
}

//timer处理函数  
function SetRemainTime() {  
  if (curCount == 0) {                  
      window.clearInterval(InterValObj);// 停止计时器  
      $("input[type='button']").removeAttr("disabled");// 启用按钮  
      $("input[type='button']").val("重新发送验证码");  
      code = ""; // 清除验证码。如果不清除，过时间后，输入收到的验证码依然有效  
  }else {  
      curCount--;  
      //$("input[type='button']").val("请在" + curCount + "秒内输入验证码");  
      $("input[type='button']").val(curCount + "秒后再获取");
  }  
}  