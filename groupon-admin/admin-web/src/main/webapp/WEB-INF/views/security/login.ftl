<html>
<head>
    <meta charset="utf-8">
    <link href="signin.css" rel="stylesheet">
    <link href="${ctx}/css/signin.css" rel="stylesheet" type="text/css"/>
    <title>ERP登录</title>
    <link rel="icon" href="images/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="images/favicon.ico" type="image/x-icon"/>
    <link rel="bookmark" href="images/favicon.ico" type="image/x-icon"/>
</head>
<body>
<div class="container">
    <form class="form-signin" role="form" method="post" action="${ctx}/main">
        <h2 class="form-signin-heading">ERP登录</h2>
        <input type="text" class="form-control" name="name" placeholder="用户名" required autofocus>
        <input type="password" class="form-control" name="password" placeholder="密码" required>
    <#--<label class="checkbox">-->
    <#--<input type="checkbox" value="remember-me"> Remember me-->
    <#--</label>-->
        <button class="btn btn-lg btn-primary btn-block" type="submit">Sign in</button>
    </form>
</div>
</body>
</html>