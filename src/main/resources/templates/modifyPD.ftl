<!doctype html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>File</title>
</head>

<body>
    <#if msg??>
    <div><label>${msg!""}</label></div>
    </#if>
    <p>密码修改</p>

    <form action="/admin/modifyPD" method="POST">
        <label>旧密码：</label>
        <input name="oldPasswd" type="password"/><br>
        <label>新密码：</label>
        <input name="newPasswd" type="password"><br>
        <#--<label>确认密码：</label>-->
        <#--<input name="confirm" type="password"><br>-->
        <input type="submit" value="确定"/><br>
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