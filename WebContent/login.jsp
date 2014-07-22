<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>Login</title>
    <style type="text/css"></style>
    <link rel="stylesheet" href="style.css">
   

</head>

<body>
<div id="warp">
<div id= "title">
    <h1> Login </h1> 
<h2>Dumpling System</h2>

</div>
<div id ="main">
    <div id="log_form">
    <form action="doLogin" method=post>
            <p>
            
            <input type="text" name="email" size="25" class="s_input" placeholder="Email">
            <br />
         
            <input type="password" size="15" name="password" class="s_input" placeholder="Password">
            </p>
            <p>
            <input type="submit" value="Login" class="login_button">
          
            </p>
        </form>
          
    </div>
    <div class="new_account">
      <a href="register.jsp">New user? Need Account?</a>
    </div>
</div>
</div>


</body>
</html>