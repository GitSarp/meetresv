<html>
<head>

    <link rel="stylesheet" href="/admin/bootstrap.min.css"  id="bootstrap-css">
    <link rel="stylesheet" href="/login/login.css">
    <script src="/admin/bootstrap.min.js"></script>
    <script src="/admin/jquery.min.js"></script>
    <!------ Include the above in your HEAD tag ---------->

    <#--跳出iframe-->
    <script type="text/javascript">
        if(window !=top){
            top.location.href=location.href;
        }
    </script>
</head>

<body>
<div class="container">
    <div class="row">
        <div class="col-md-6 col-md-offset-3">
            <h2 align="center">阿拉丁会议室预约管理系统</h2>
            <br><br><br><br><br>

            <div class="panel panel-login">
                <div class="panel-heading">
                    <div class="row">
                        <div class="col-xs-6">
                            <a href="#" class="active" id="login-form-link">登录</a>
                        </div>
                        <div class="col-xs-6">
                            <a href="#" id="register-form-link">注册</a>
                        </div>
                    </div>
                    <hr>
                </div>
                <div class="panel-body">
                    <div class="row">
                        <div class="col-lg-12">
                            <form id="login-form" action="/admin/logindo" method="post" role="form" style="display: block;">
                                    <#if msg??>
                                        <div  class="form-group"><label>${msg!""}</label></div>
                                    </#if>
                                <div class="form-group">
                                    <input type="text" name="name" id="name" tabindex="1" class="form-control" placeholder="用户名" value="">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="密码">
                                </div>
                                <div class="form-group text-center">
                                    <input type="checkbox" tabindex="3" class="" name="remember" id="remember">
                                    <label for="remember"> 记住我</label>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="login-submit" id="login-submit" tabindex="4" class="form-control btn btn-login" value="登录">
                                        </div>
                                    </div>
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-lg-12">
                                            <div class="text-center">
                                                <a href="https://phpoll.com/recover" tabindex="5" class="forgot-password">忘记密码?</a>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </form>
                            <form id="register-form" action="#" method="post" role="form" style="display: none;">
                                <div class="form-group" hidden="hidden" id="registResp">
                                    <label>本系统不支持注册，请联系管理员！</label>
                                </div>
                                <div class="form-group">
                                    <input type="text" name="username" id="username" tabindex="1" class="form-control" placeholder="Username" value="">
                                </div>
                                <div class="form-group">
                                    <input type="email" name="email" id="email" tabindex="1" class="form-control" placeholder="Email Address" value="">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="password" id="password" tabindex="2" class="form-control" placeholder="Password">
                                </div>
                                <div class="form-group">
                                    <input type="password" name="confirm-password" id="confirm-password" tabindex="2" class="form-control" placeholder="Confirm Password">
                                </div>
                                <div class="form-group">
                                    <div class="row">
                                        <div class="col-sm-6 col-sm-offset-3">
                                            <input type="submit" name="register-submit" id="register-submit" tabindex="4" class="form-control btn btn-register" value="注册" onclick="doregist()">
                                        </div>
                                    </div>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<script type="text/javascript">
    $(function() {
        $('#login-form-link').click(function(e) {
            $("#login-form").delay(100).fadeIn(100);
            $("#register-form").fadeOut(100);
            $('#register-form-link').removeClass('active');
            $(this).addClass('active');
            e.preventDefault();
        });
        $('#register-form-link').click(function(e) {
            $("#register-form").delay(100).fadeIn(100);
            $("#login-form").fadeOut(100);
            $('#login-form-link').removeClass('active');
            $(this).addClass('active');
            e.preventDefault();
        });

    });

    function doregist(){
        $("#registResp").show();
    }
</script>

</body>
</html>