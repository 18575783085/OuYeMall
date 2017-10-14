/* 注册表单的js校验 */
function checkForm(){
	//step1.非空校验
	var res1 = checkNull("username","用户名不能为空");
	var res2 = checkNull("password","密码不能为空");
	var res3 = checkNull("password2","确认密码不能为空");
	var res4 = checkNull("nickname","昵称不能为空");
	var res5 = checkNull("email","邮箱不能为空");
	var res6 = checkNull("phonenumber","手机号码不能为空");
	var res7 = checkNull("smsvalistr","短信验证码不能为空");
	var res8 = checkNull("valistr","验证码不能为空");
	
	//step4.1.判断两次密码是否一致
	var res9 = checkPassword("password","两次密码不一致");
	
	//step5.1.判断邮箱格式是否正确
	var res10 = checkEmail("email","邮箱格式不正确");
	
	//step6.1.判断手机号码格式是否正确
	var res11 = checkNumber("phonenumber","手机号码格式不正确");
	
	
	//返回值
	return res1 && res2 && res3 && res4 && res5 && res6 && res7 && res8 && res9 && res10 && res11;
	
}

/* step2.1.检查输入项是否为空*/
function checkNull(name,msg){
	//获取input标签中该属性的选择器
	var value = $("input[name='"+name+"']").val().trim();
	
	//step3.1.清空上次的提示消息
	setMsg(name,"");
	
	//step2.2.判断输入项的value是否为空
	if(value == ""){
		setMsg(name,msg);
		return false;
	}
	return true;
}

/* step3.2在校验失败时(输入框为空)，设置错误提示消息 */
function setMsg(name,msg){
	//获取input的该属性的标签
	var $oInp = $("input[name='"+name+"']");
	//获取所有span元素
	var $oSpan = $oInp.nextAll("span");
	$oSpan.html(msg);
	$oSpan.css("color","red");
	
}

/*  step4.2.检查两次密码是否一致 */
function checkPassword(name,msg){
	//获取密码
	var psw1 = $("input[name='"+name+"']").val().trim();
	//获取确认密码
	var psw2 = $("input[name='"+name+"2']").val().trim();
	
	//step4.3.检查确认密码输入框是否为空
	checkNull("password2","确认密码不能为空");
	
	//step4.4.判断是否匹配
	if(psw1 != "" && psw2 != "" && psw1 != psw2){
		//step4.4.1.不匹配
		setMsg(name+"2",msg);
		return false;
	}
	return true;
	
}

/* step5.2.检验邮箱是否正确*/
function checkEmail(name,msg){
	//获取邮箱标签的值
	var email = $("input[name='"+name+"']").val().trim();
	//正则表达式
	var regExp = /^\w+@\w+(\.[a-z]+)+$/;
	
	//step5.3.检查邮箱是否为空
	checkNull("email","邮箱不能为空");
	
	//step5.4.判断是否匹配
	if(email != "" && !regExp.test(email)){
		//step5.4.1.不匹配
		setMsg(name,msg);
		return false;
	}
	return true;
}

/* step6.2.检验手机号码格式是否正确*/
function checkNumber(name,msg){
	//获取手机号码标签的值
	var phone = $("input[name='"+name+"']").val().trim();
	//正则表达式
	//var regExp = /^[0-9]{13}$/;
	var regExp = /(^1[3|5|8][0-9]{9}$)/;
	
	//step6.3.检查手机号码是否为空
	checkNull("phonenumber","手机号码不能为空");
	
	//step6.4.判断是否匹配
	if(phone != "" && !regExp.test(phone)){
		//step6.4.1.不匹配
		setMsg(name,msg);
		return false;
	}
	return true;
}


//文档就绪事件, 在整个html文档加载完之后立即执行
$(function(){
	
});


