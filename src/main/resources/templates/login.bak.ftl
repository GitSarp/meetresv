<!doctype html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>File</title>

    <#--跳出iframe-->
    <script type="text/javascript">
        if(window !=top){
            top.location.href=location.href;
        }
    </script>
</head>

<body>
    <#if msg??>
    <div><label>${msg!""}</label></div>
    </#if>
    <p>管理员登录</p>

    <form action="/admin/logindo" method="POST">
        <label>用户名：</label>
        <input name="name" type="text"/><br>
        <label>密 &nbsp;码：</label>
        <input name="password" type="password"><br>
        <input type="submit" value="登录"/>
    </form>

    <#--<a href="/file/download">下载test</a>-->
    <#--<p>多文件上传</p>-->
    <#--<form method="POST" enctype="multipart/form-data" action="/file/batch">-->
        <#--<p>文件1：<input type="file" name="file"/></p>-->
        <#--<p>文件2：<input type="file" name="file"/></p>-->
        <#--<p><input type="submit" value="上传"/></p>-->
    <#--</form>-->
</body>
</html>