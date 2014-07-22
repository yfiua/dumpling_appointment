<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Register</title>
<style type="text/css"></style>
<link rel="stylesheet" href="style.css">
</head>
<body>
	<div id="warp">
		<div id="title">
			<h1>Register</h1>
			<h2>Dumpling System</h2>
		</div>
		<div id="main">
			<div id="log_form">
				<form id='register' action='doRegister' method='post'
					accept-charset='UTF-8'>
					<p>
						<input type='hidden' name='submitted' id='submitted' value='1'/ >
						<input type='text' name='username' id='username' maxlength="50"
							placeholder="UserName" class="s_input" />
						<input type='text'
							name='email' id='email' maxlength="50" placeholder="Email"
							class="s_input" />
						<input type='password' name='password'
							id='password' maxlength="50" placeholder="Password"
							class="s_input" />
						<input type='password' name='re_password'
							id='re_password' maxlength="50" placeholder="Repeat Password"
							class="s_input" />
					<p>
						<input type='submit' name='Submit' value='Sign Up' class="signup_button" />
						<input type='reset' name='Reset' value='Reset' class="reset_button" />
					</p>
				</form>
			</div>
			<div class="new_account">
				<a href="login.jsp">Login?</a>
			</div>
		</div>
	</div>
</body>
</html>