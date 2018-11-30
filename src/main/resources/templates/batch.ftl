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

    <p>用户导入</p>
    <div>
        <p>选择导入用户excel</p>
        <form action="/admin/uploadUser" method="POST" enctype="multipart/form-data">
            <label>文件：</label><input type="file" name="file"/><input type="submit" value="上传"/>
        </form>
    </div>

    <p>会议室导入</p>

    <div>
        <p>选择会议室excel</p>
        <form action="#" method="POST" enctype="multipart/form-data">
            <label>文件：</label><input type="file" name="file"/><input type="submit" value="上传"/>
        </form>
    </div>

    <#--<a href="/file/download">下载test</a>-->
    <#--<p>多文件上传</p>-->
    <#--<form method="POST" enctype="multipart/form-data" action="/file/batch">-->
        <#--<p>文件1：<input type="file" name="file"/></p>-->
        <#--<p>文件2：<input type="file" name="file"/></p>-->
        <#--<p><input type="submit" value="上传"/></p>-->
    <#--</form>-->
</body>
</html>