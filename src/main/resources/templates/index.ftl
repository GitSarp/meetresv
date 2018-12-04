<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- 上述3个meta标签*必须*放在最前面，任何其他内容都*必须*跟随其后！ -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="https://v3.bootcss.com/favicon.ico">


    <!-- Bootstrap core CSS -->
    <link href="/admin/bootstrap.min.css" rel="stylesheet">

    <!--主要样式控制-->
    <link href="/admin/main.css" rel="stylesheet">

    <title>bootstrap后台模板设计</title>

    <!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
    <link href="/admin/ie10-viewport-bug-workaround.css" rel="stylesheet">

    <!-- Custom styles for this template -->
    <!--    <link href="./1for Bootstrap_files/starter-template.css" rel="stylesheet">-->

    <!-- Just for debugging purposes. Don't actually copy these 2 lines! -->
    <#--<!--[if lt IE 9]><script src="../../assets/js/ie8-responsive-file-warning.js"></script><![endif]&ndash;&gt;-->
    <#--<script src="./1for Bootstrap_files/ie-emulation-modes-warning.js"></script>-->

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!--[if lt IE 9]>
    <script src="https://cdn.bootcss.com/html5shiv/3.7.3/html5shiv.min.js"></script>
    <script src="https://cdn.bootcss.com/respond.js/1.4.2/respond.min.js"></script>
    <![endif]-->
    <#--<script type="text/javascript">//================================================-->

    <#--(ytCinema = {-->
        <#--players: {objs: [], active: 0},-->
        <#--messageEvent: new Event('ytCinemaMessage'),-->
        <#--playerStateChange: function (stateId) {-->
            <#--var message = document.getElementById("ytCinemaMessage"),-->
                    <#--stateIO = "playerStateChange:".concat(stateId);-->
            <#--// console.log("Debug " + message.textContent + " " +stateIO);-->
            <#--if (message && message.textContent !== stateIO) {-->
                <#--message.textContent = stateIO;-->
                <#--message.dispatchEvent(ytCinema.messageEvent);-->
            <#--}-->
        <#--},-->
        <#--initialize: function () {-->
            <#--this.messageEvent;-->
            <#--window.addEventListener("load", initvideoinject, false);-->
            <#--document.addEventListener("DOMContentLoaded", initvideoinject, false);-->
            <#--initvideoinject();-->

            <#--// New Mutation Summary API Reference-->
            <#--var MutationObserver = window.MutationObserver || window.WebKitMutationObserver || window.MozMutationObserver;-->
            <#--if(MutationObserver) {-->
                <#--// setup MutationSummary observer-->
                <#--var videolist = document.querySelector('body');-->
                <#--var observer = new MutationObserver(function (mutations, observer) {-->
                    <#--mutations.forEach(function (mutation) {-->
                        <#--if(mutation.target.tagName == "VIDEO") {-->
                            <#--if (mutation.attributeName === "src") {-->
                                <#--initvideoinject();-->
                            <#--}-->
                        <#--}-->
                        <#--if(typeof mutation.addedNodes == "VIDEO" || typeof mutation.removedNodes == "VIDEO") {-->
                            <#--initvideoinject();-->
                        <#--}-->
                    <#--});-->
                <#--});-->

                <#--observer.observe(videolist, {-->
                    <#--subtree: true,       // observe the subtree rooted at ...videolist...-->
                    <#--childList: true,     // include childNode insertion/removals-->
                    <#--characterData: false, // include textContent changes-->
                    <#--attributes: true     // include changes to attributes within the subtree-->
                <#--});-->
            <#--} else {-->
                <#--// setup DOM event listeners-->
                <#--document.addEventListener("DOMNodeRemoved", initvideoinject, false);-->
                <#--document.addEventListener("DOMNodeInserted", initvideoinject, false);-->
            <#--}-->

            <#--function initvideoinject(e) {-->
                <#--var youtubeplayer = document.getElementById("movie_player") || null;-->
                <#--var htmlplayer = document.getElementsByTagName("video") || false;-->

                <#--if(youtubeplayer !== null) { // YouTube video element-->
                    <#--var interval = window.setInterval(function () {-->
                        <#--if (youtubeplayer.pause || youtubeplayer.pauseVideo) {-->
                            <#--window.clearInterval(interval);-->
                            <#--if (youtubeplayer.pauseVideo) {youtubeplayer.addEventListener("onStateChange", "ytCinema.playerStateChange");}-->
                        <#--}-->
                    <#--}, 10);-->
                <#--}-->
                <#--if(htmlplayer && htmlplayer.length > 0) { // HTML5 video elements-->
                    <#--var setPlayerEvents = function(players) {-->
                        <#--for(var j=0; j<players.length; j++) {-->
                            <#--(function(o, p) {-->
                                <#--var ev = {-->
                                    <#--pause: function() {if(!p.ended) {o.players.active -= 1;} if(o.players.active < 1){o.playerStateChange(2);}},-->
                                    <#--play: function() {o.players.active += 1; o.playerStateChange(1);},-->
                                    <#--ended: function() {o.players.active -= 1; if(o.players.active < 1){o.playerStateChange(0);}}-->
                                <#--};-->
                                <#--p.removeEventListener("pause", ev.pause); p.removeEventListener("play", ev.play); p.removeEventListener("ended", ev.ended);-->

                                <#--p.addEventListener("pause", ev.pause);-->
                                <#--p.addEventListener("play", ev.play);-->
                                <#--p.addEventListener("ended", ev.ended);-->
                                <#--o.players.objs.push(p);-->
                            <#--}(this.ytCinema, htmlplayer[j]));-->
                        <#--}-->
                    <#--};-->

                    <#--setPlayerEvents(htmlplayer);-->

                    <#--(function(o) {-->
                        <#--var triggerDOMChanges = function() {-->
                            <#--var htmlplayer = document.getElementsByTagName("video") || null;-->

                            <#--if(htmlplayer == null || htmlplayer.length === 0) {o.players.active = 0; if(o.players.active < 1){o.playerStateChange(0);} return;}-->

                            <#--o.players.active = 0;-->

                            <#--for(var j=0; j<htmlplayer.length; j++) {-->
                                <#--if(!htmlplayer[j].paused && !htmlplayer[j].ended) {-->
                                    <#--o.players.active += 1;-->
                                <#--}-->
                            <#--}-->
                            <#--if(o.players.active < 1){o.playerStateChange(0);}-->

                            <#--setPlayerEvents(htmlplayer);-->
                        <#--};-->

                    <#--}(this.ytCinema));-->
                <#--}-->
            <#--}-->
        <#--}-->
    <#--}).initialize();</script>-->
</head>
<body>
<nav class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
                <span class="sr-only">Toggle navigation</span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#"> 会 议 室 预 约 管 理 系 统 </a>
        </div>

        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <ul class="nav navbar-nav">
                <#--<li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>-->
                <#--<li><a href="#">Link</a></li>-->
                <#--<li class="dropdown">-->
                    <#--<a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>-->
                    <#--<ul class="dropdown-menu">-->
                        <#--<li><a href="#">Action</a></li>-->
                        <#--<li><a href="#">Another action</a></li>-->
                        <#--<li><a href="#">Something else here</a></li>-->
                        <#--<li role="separator" class="divider"></li>-->
                        <#--<li><a href="#">Separated link</a></li>-->
                        <#--<li role="separator" class="divider"></li>-->
                        <#--<li><a href="#">One more separated link</a></li>-->
                    <#--</ul>-->
                <#--</li>-->
            </ul>

            <ul class="nav navbar-nav navbar-right">
                <li>
                    <form class="navbar-form navbar-left">
                        <div class="form-group">
                            <input type="text" class="form-control" placeholder="Search">
                        </div>
                        <button type="submit" class="btn btn-default">搜索</button>
                    </form>
                </li>
                <li class="dropdown">
                    <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Settings <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="/admin/logout" target="mainFrame">Logout</a></li>
                        <#--<li><a href="#">Another action</a></li>-->
                        <#--<li><a href="#">Something else here</a></li>-->
                        <#--<li role="separator" class="divider"></li>-->
                        <#--<li><a href="#">Separated link</a></li>-->
                    </ul>
                </li>
            </ul>
        </div><!-- /.navbar-collapse -->
    </div><!-- /.container-fluid -->
</nav>


<!-- 中间主体内容部分 -->
<div class="pageContainer">
    <!-- 左侧导航栏 -->
    <div class="pageSidebar">
        <ul class="nav nav-stacked nav-pills">
            <li role="presentation">
                <a href="/admin/orders" target="mainFrame">预约管理</a>
            </li>
            <li role="presentation">
                <a href="/admin/users" target="mainFrame">用户管理</a>
            </li>
            <li role="presentation">
                <a href="/admin/rooms" target="mainFrame">会议室管理</a>
            </li>
            <li role="presentation">
                <a href="/admin/batch" target="mainFrame">批量处理</a>
            </li>
            <!-- 开始 -->
            <li class="dropdown">
                <a class="dropdown-toggle" data-toggle="dropdown" href="nav4.html" target="mainFrame">
                    个人设置<span class="caret"></span>
                </a>
                <ul class="dropdown-menu">
                    <li>
                        <a href="/admin/modifyPD" target="mainFrame">修改密码</a>
                    </li>
                    <#--<li>-->
                        <#--<a href="/admin/logout" target="mainFrame">退出系统</a>-->
                    <#--</li>-->
                    <li>
                        <a href="#" target="mainFrame">查看个人信息</a>
                    </li>
                </ul>
            </li>
            <!-- 结束 -->
            <#--<li role="presentation">-->
                <#--<a href="nav5.html" target="mainFrame">权限设置</a>-->
            <#--</li>-->
        </ul>
    </div>

    <!-- 左侧导航和正文内容的分隔线 -->
    <div class="splitter"></div>
    <!-- 正文内容部分 -->
    <div class="pageContent">
        <iframe src="/admin/orders" id="mainFrame" name="mainFrame"
                frameborder="0" width="100%"  height="100%" frameBorder="0">
        </iframe>
    </div>

</div>
<!-- 底部页脚部分 -->
<div class="footer">
    <p class="text-center">
        2018 &copy; freaxjj.
    </p>
</div>

<script type="text/javascript">
    $(".nav li").click(function() {
        $(".active").removeClass('active');
        $(this).addClass("active");
    });
</script>

<!-- Bootstrap core JavaScript
================================================== -->
<!-- Placed at the end of the document so the pages load faster -->
<script src="/admin/jquery.min.js"></script>
<script>window.jQuery || document.write('<script src="../../assets/js/vendor/jquery.min.js"><\/script>')</script>
<script src="/admin/bootstrap.min.js"></script>
<!-- IE10 viewport hack for Surface/desktop Windows 8 bug -->
<script src="/admin/ie10-viewport-bug-workaround.js"></script>


<div id="ytCinemaMessage" style="display: none;"></div></body></html>