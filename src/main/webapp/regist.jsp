<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML>
<html>
	<head>
		<title>欢迎注册OuYeMall</title>
		<meta http-equiv="Content-type" content="text/html; charset=UTF-8" />
		<link rel="stylesheet" href="${pageContext.request.contextPath }/css/regist.css"/>
		
		<script src="${pageContext.request.contextPath }/js/jquery-1.4.2.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/jquery.cookie.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/regist.js"></script>
		<script type="text/javascript" src="${pageContext.request.contextPath }/js/sms.js"></script>
		
		<script type="text/javascript">
		/* 使用Ajax校验用户名是否存在 */
		function checkUsername(thisobj){
			//1.先获取用户名
			var username = thisobj.value;
			
			//2.检查用户名是否为空
			if($.trim(username) == ""){//去空格
				setMsg("username","用户名不能为空");
				return;
			}
			
			setMsg("username","<img src='${pageContext.request.contextPath }/img/regist/1.gif' /> ");
			//3.检查用户名是否存在
			setTimeout(function(){
				$("#username_msg").load(
					"${pageContext.request.contextPath }/servlet/AjaxCheckUsernameServlet",
					{"username":username}
				);
				
			},1000);
			
		}
		</script>
		<script type="text/javascript">
			/* Ajax检验手机号码是否已经存在 */
			function checkPhone(){
				//1.获取手机号码参数
				var phone = $("input[name='phonenumber']").val().trim();
				var span = $("input[name='phonenumber']").next("span");
				var re= /(^1[3|5|8][0-9]{9}$)/;
				
				if(phone == ""){
					$("input[name='phonenumber']").next("span").html("<font color='red'>× 手机号码不能为空</font>");
					return false; 
				}else if(phone != ""){
					/* 校验手机号码格式 */
					if(!re.test(phone)){
						$("input[name='phonenumber']").next("span").html("<font color='red'>× 请输入有效的手机号码</font>");
						return false;
					}else{
						//$("input[name='phonenumber']").next("span").html("<font color='#339933'>√ 手机号码输入正确</font>");
						
						setMsg("phonenumber","<img src='${pageContext.request.contextPath }/img/regist/1.gif' /> ");
						/* 向后台发送处理数据（在数据库查找是否有存在该手机号码） */
						setTimeout(function(){
							$("input[name='phonenumber']").next("span").load(
								"${pageContext.request.contextPath }/servlet/AjaxCheckPhoneServlet",
								{"phonenumber":phone}
							);
					
						},1000);
						
						<%-- $.ajax({
							url:"<%= request.getContextPath()%>/servlet/AjaxCheckPhoneServlet",//目标地址
							data:{"phonenumber":phone},
							type:"POST",//用post方式传输
							dataType:"text",//数据格式：text
							success:function(data){
								 data = parseInt(data, 10);  
				                 if (data != 0) {  
									  $("input[name='phonenumber']").next("span").html("<font color='red'>× 该手机号码已经被注册，请重新输入</font>");
				                  } else {  
				                       $("input[name='phonenumber']").next("span").html("<font color='#339933'>√ 该手机号码可以注册，输入正确</font>");  
				                  }  
							}
							
						}); --%>
						return true;
					}
				}
			}
		
		</script>
		
		<script>
			/* Ajax发送验证码 ,并且带防刷新验证码页面*/
		  $(function(){
	        /*防刷新：检测是否存在cookie*/
	        if($.cookie("smscookie")){
	        	//创建Cookie
	            var count = $.cookie("smscookie");
	            //获取"发送"按钮
	            var btn = $("#btn");
	            btn.val(count+"秒后再获取").attr("disabled",true).css("cursor","not-allowed");
	            var resend = setInterval(function(){
	                count--;
	                if (count > 0){
	                    btn.val(count+"秒后再获取").attr("disabled",true).css("cursor","not-allowed");
	                    $.cookie("smscookie", count, {path: "/", expires: (1/86400)*count});
	                }else {
	                    clearInterval(resend);// 停止计时器 
	                    // 启用按钮 
	                    btn.val("重新发送验证码").removeClass("disabled").removeAttr("disabled").css("cursor","pointer");
	                    
	                }
	            }, 1000);
	         }
	        /*点击改变按钮状态*/
	        $("#btn").click(function(){
	            var btn = $(this);
	            var count = 60;//间隔函数，1秒执行
	            
	            var phone = $("input[name='phonenumber']").val().trim();//手机号码
	            var span = $("input[name='phonenumber']").next("span").text();//手机号码后的span
	            
	            if(phone != ""){
						//根据手机号码的校验来启动按钮
						if(span == "√ 该手机号码可以注册，输入正确" || span == "√ 短信验证码已发到您的手机,请查收"){
							var resend = setInterval(function(){
			                count--;
			                if (count > 0){
			                    btn.val(count+"秒后再获取");
			                    $.cookie("smscookie", count, {path: "/", expires: (1/86400)*count});
			                }else {
			                    clearInterval(resend);// 停止计时器 
			                    // 启用按钮 
			                    btn.val("重新发送验证码").removeAttr("disabled").css("cursor","pointer");
			                }
			            }, 1000);
			            btn.attr("disabled",true).css("cursor","not-allowed");
			            
			            
			            /* Ajax发送验证码 */
			            /* 调用后台短信servlet */
						$.ajax({
							    type:"POST",//用post方式传输
							    dataType:"text",//数据格式：json
							   	url:"${pageContext.request.contextPath }/servlet/AjaxCheckSmsServlet",//目标地址
							   	data:{"phonenumber":phone},
							   	success:function (data){
						      	data = parseInt(data,10);
							      		if(data == 1){
								       		$("input[name='phonenumber']").next("span").html("<font color='#339933'>√ 短信验证码已发到您的手机,请查收</font>");  
								       	}else if(data == 0){
								       		$("input[name='phonenumber']").next("span").html("<font color='red'>×  短信验证码发送失败，请重新获取</font>");  
								       	}else if(data == 2){
								       		$("input[name='phonenumber']").next("span").html("<font color='red'>× 该手机号码今天发送验证码过多</font>");
								       	}
						      	 }
							   });
						}
				}else{  
	       			$("input[name='phonenumber']").next("span").html("<font color='red'>× 手机号码不能为空</font>");  
	   			} 
	       	 });
    	});
    	</script>
		
		<script>
			/* 校验短信验证码 */
			function CheckSmsCode(){
				//获取短信验证码参数
				var smsvalistr = $("input[name='smsvalistr']").val().trim();
				//获取手机号码参数
				var phone = $("input[name='phonenumber']").val().trim();
				
				//2.检查用户名是否为空
				if($.trim(smsvalistr) == ""){//去空格
					setMsg("smsvalistr","验证码不能为空");
					return;
				}
				
				//3.判断是否匹配
				/* 调用后台校验短信servlet */
				<%-- $("#sms_msg").load(
					"<%= request.getContextPath()%>/servlet/AjaxCheckSmsCodeServlet",
						{"smsvalistr":smsvalistr}
					); --%>
					
					$.ajax({
						    type:"POST",//用post方式传输
						    dataType:"text",//数据格式：json
						    url:"${pageContext.request.contextPath }/servlet/AjaxCheckSmsCodeServlet",//目标地址
						    data:{"smsvalistr":smsvalistr,"phonenumber":phone},
						    success:function (data){
						    data = parseInt(data,10);
							    if(data == 1){
							        $("#sms_msg").html("<font color='#339933'>√ 验证码正确</font>");  
							    }else if(data == 0){
							        $("#sms_msg").html("<font color='red'>× 验证码不正确,请重新获取</font>");  
							    }else if(data == 2){
							        $("#sms_msg").html("<font color='red'>× 验证码已失效请重新获取验证码</font>");
							    }
						     }
				     });
			}
		</script>
		
		
	</head>
	<body>
		<form onsubmit="return checkForm()"
		action="${pageContext.request.contextPath }/servlet/RegistServlet" method="POST">
		<%//生成令牌
			String token = UUID.randomUUID().toString();
			//将token保存到session中
			session.setAttribute("token", token);
		 %>
		 	<!-- 将生成的令牌通过隐藏域传给Servlet -->
		 	<input type="hidden" name="token" value="<%= token%>"/>
			<h1>欢迎注册OuYeMall</h1>
			<table>
				<tr>
					<td style="color:red;text-align:center;" colspan="2" >
						<!-- 如果后台返回信息（参数为空），在此处获取提示消息提示用户
							如果后台无返回信息（参数不为空），则此处为空白  -->
						<%=
							request.getAttribute("msg") ==  null  ? "": request.getAttribute("msg") 
						%>
					</td>
				</tr>
				<tr>
					<td class="tds">用户名：</td>
					<td>
						<input type="text" name="username"
						onblur="checkUsername(this)"
						value="<%= request.getParameter("username") == null ? "" : request.getParameter("username") %>"
						/>
						<span id="username_msg"></span>
					</td>
				</tr>
				<tr>
					<td class="tds">密码：</td>
					<td>
						<input type="password" name="password"
						onblur="checkNull('password','密码不能为空')"
						value="<%= request.getParameter("password") == null ? "" : request.getParameter("password") %>"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">确认密码：</td>
					<td>
						<input type="password" name="password2"
						onblur="checkPassword('password','两次密码不一致')"
						value="<%= request.getParameter("password2") == null ? "" : request.getParameter("password2") %>"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">昵称：</td>
					<td>
						<input type="text" name="nickname"
						onblur="checkNull('nickname','呢称不能为空')"
						value="<%= request.getParameter("nickname") == null ? "" : request.getParameter("nickname") %>"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">邮箱：</td>
					<td>
						<input type="text" name="email"
						onblur="checkEmail('email','邮箱格式不正确')"
						value="<%= request.getParameter("email") == null ? "" : request.getParameter("email") %>"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">手机号码：</td>
					<td>
						<input type="text" name="phonenumber"
						onblur="checkPhone()"
						value="<%= request.getParameter("phonenumber") == null ? "" : request.getParameter("phonenumber") %>"
						/>	
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="tds">短信验证码：</td>
					<td>
						<input type="text" name="smsvalistr"
						onblur="CheckSmsCode()"
						value="<%= request.getParameter("smsvalistr") == null ? "" : request.getParameter("smsvalistr") %>"
						/>
						<input type="button" value="获取验证码" id="btn"/>	
						<span id="sms_msg"></span>
					</td>
				</tr>
				<tr>
					<td class="tds">验证码：</td>
					<td>
						<input type="text" name="valistr"
						value="<%= request.getParameter("valistr") == null ? "" : request.getParameter("valistr") %>"
						/>
						<img src="${pageContext.request.contextPath }/servlet/ValiImageServlet" width="" height="" alt="加载失败" title="看不清点击刷新验证码"
						id="verification" onclick="refreshcode(this)"
						/>
						<span></span>
					</td>
				</tr>
				<tr>
					<td class="sub_td" colspan="2" class="tds">
						<input type="submit" value="注册用户"/>
					</td>
				</tr>
			</table>
		</form>
	</body>
	<script type="text/javascript">
		/* 点击图片刷新验证码 */
		function refreshcode(thisobj){
			thisobj.src = "${pageContext.request.contextPath }/servlet/ValiImageServlet?ye="+new Date().getTime();
		}
	</script>
	
</html>

