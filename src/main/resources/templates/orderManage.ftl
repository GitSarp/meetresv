<!doctype html>
<html lang="zh-cn">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">


    <link rel="stylesheet" href="/admin/bootstrap.min.css">
    <link rel="stylesheet" href="/bootstrap-table/bootstrap-table.css">

    <script src="/admin/jquery.min.js"></script>
    <script src="/admin/bootstrap.min.js"></script>
    <script src="/bootstrap-table/bootstrap-table.js"></script>
    <script src="/bootstrap-table/bootstrap-table-locale-all.js"></script>
    <title>预约管理</title>
</head>

<body>
    <#if msg??>
    <div><label>${msg!""}</label></div>
    </#if>

    <div class="container">
        <h1>预约管理</h1>
        <br><br>
        <div class="panel panel-default">
            <div class="panel-heading">
                查询面板
            </div>
            <div class="panel-body form-group toolbar" style="margin-bottom:0px;">
                <label class="col-md-1 control-label" style="text-align: right; margin-top:5px">预约人：</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="user" id="search_name"/>
                </div>
                <label class="col-md-1 control-label" style="text-align: right; margin-top:5px">会议室：</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="roomNo" id="search_roomno"/>
                </div>
                <label class="col-md-1 control-label" style="text-align: right; margin-top:5px">工作日：</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="day" id="search_day"/>
                </div>
                <div class="col-md-1 col-md-offset-1">
                    <button class="btn btn-default" id="search_btn">查询</button>
                </div>
                <div class="col-md-1">
                    <button class="btn btn-primary create" href="javascript:">新增</button>
                </div>
                <span class="alert"></span>
            </div>
        </div>


        <#--<p class="toolbar">-->
            <#--<a class="create btn btn-default" href="javascript:">新增</a>-->
            <#--<span class="alert"></span>-->
        <#--</p>-->
        <table id="table"
               data-show-refresh="true"
               data-show-columns="true"
               <#--data-search="true"不显示内置搜索-->
               data-query-params="queryParams"
               data-toolbar=".toolbar">
            <thead>
            <tr>
                <th data-field="id">预约id</th>
                <th data-field="roomNo">会议室编号</th>
                <th data-field="user">预约人</th>
                <th data-field="day">工作日</th>
                <th data-field="period">占用时段</th>
                <th data-field="purpose">用途</th>
                <th data-field="action"
                    data-align="center"
                    data-formatter="actionFormatter"
                    <#--绑定事件-->
                    data-events="actionEvents">编辑
                </th>
            </tr>
            </thead>
        </table>
    </div>

    <div id="modal" class="modal fade">
        <div class="modal-dialog">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span
                            aria-hidden="true">×</span></button>
                    <h4 class="modal-title"></h4>
                </div>
                <div class="modal-body">
                    <div class="input-group">
                        <span class="input-group-addon">会议室编号</span>
                        <input class="form-control" type="text" name="roomNo" id="roomNo">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">预约人</span>
                        <input class="form-control" type="text" name="user" id="user">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">工作日</span>
                        <input class="form-control" type="text" name="day" id="day">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">占用时段</span>
                        <input class="form-control" type="text" name="period" id="period">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">用途</span>
                        <input class="form-control" type="text" name="purpose" id="purpose">
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button type="button" class="btn btn-primary submit">确定</button>
                </div>
            </div><!-- /.modal-content -->
        </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->



    <script>
        var $table = TableInit(),
                $modal = $('#modal').modal({show: false}),
                $alert = $('.alert').hide();

        $(function () {
            // 新增
            $('.create').click(function () {
                showModal($(this).text());
            });

            //查询按钮事件
            $('#search_btn').click(function(){
                $('#table').bootstrapTable('refreshOptions',{pageNumber:1});//搜索时重置为第一页
                $('#table').bootstrapTable('refresh', {url: '/order/query'});
            });

            //提交
            $modal.find('.submit').click(function () {
                var row = {};
                $modal.find('input[name]').each(function () {
                    row[$(this).attr('name')] = $(this).val();
                });
                var url="/add";
                if($modal.data('id')){
                    url="/update";
                    row["id"]=$modal.data('id');
                }
                $.ajax({
                    url: "/order"+url,
                    type: "post",
                    data: row,
                    success: function (data) {
                        $modal.modal('hide');
                        $table.bootstrapTable('refresh');
                        if(data.result=="success"){
                            showAlert(($modal.data('id') ? '更新' : '新增') + '预约成功!', 'success');
                        }else{
                            showAlert(($modal.data('id') ? '更新' : '新增') + '预约失败!'+data.desc, 'danger');
                        }
                    },
                    error: function () {
                        $modal.modal('hide');
                        showAlert(($modal.data('id') ? '更新' : '新增') + '预约失败!', 'danger');
                    }
                });
            });

            //模态框关闭时清除数据
            $("#modal").on("hidden.bs.modal", function() {
                // $(this).data("id",)
                $(this).removeData("bs.modal");
            });
        });

        //得到查询的参数
        function queryParams(params) {
            var order = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit, //页面大小
                offset: params.offset, //页码
                user: $("#search_name").val(),
                roomNo: $("#search_roomno").val(),
                day: $("#search_day").val()
            };
            return order;
        }

        function TableInit(){//定义一个初始化方法
                return $('#table').bootstrapTable({
                    url: "/order/query",
                    toolbar: '#toolbar', //工具按钮用哪个容器
                    striped: true,//设置为 true 会有隔行变色效果
                    undefinedText: "空",//当数据为 undefined 时显示的字符
                    cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true, //是否显示分页
                    sortable: true, //是否启用排序
                    sortOrder: "asc", //排序方式
                    queryParamsType:'limit',//查询参数组织方式
                    queryParams: queryParams,//传递参数（*）
                    sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
                    pageNumber: 1,//首页页码 ;初始化加载第一页，默认第一页
                    pageSize: 10, //每页的记录行数（*）
                    pageList: [5,10, 20, 30], //可供选择的每页的行数（*）
                    // smartDisplay:false,//总是显示分页栏
                    strictSearch: true,
                    //            height: table_h, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度,设置了行高后编辑时标头宽度不会随着下面的行改变，且颜色也不会改变？？？？
                    uniqueId: "id", //每一行的唯一标识，一般为主键列
                    cardView: false, //是否显示详细视图
                    detailView: false, //是否显示父子表
                    paginationHAlign: "left",
                    singleSelect: true,
                    // search: true, //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                    //strictSearch: true,
                    showColumns: true, //是否显示所有的列
                    showRefresh: true, //是否显示刷新按钮
                    clickToSelect: true, //是否启用点击选中行
                    paginationPreText: "<<",
                    locale:'zh-CN',//中文支持,
                    paginationNextText: ">>"
                });
        }

        function actionFormatter(value) {
            return [
                '<a class="update" href="javascript:" title="更新"><i class="glyphicon glyphicon-edit"></i></a>',
                '<a class="remove" href="javascript:" title="删除"><i class="glyphicon glyphicon-remove-circle"></i></a>',
            ].join('');
        }

        // update and delete events
        window.actionEvents = {
            'click .update': function (e, value, row) {
                showModal($(this).attr('title'), row);
            },
            'click .remove': function (e, value, row) {
                if (confirm('确定删除此预约 ？')) {
                    $.ajax({
                        url: "/order/del?id=" + row.id,
                        type: 'post',
                        success: function (data) {
                            $table.bootstrapTable('refresh');
                            if(data.result=="success"){
                                showAlert('删除预约成功!', 'success');
                            }else {
                                showAlert('删除预约失败!', 'danger');
                            }
                        },
                        error: function () {
                            showAlert('删除预约失败!', 'danger');
                        }
                    })
                }
            }
        };

        function showModal(title, row) {
            row = row || {
                id: ''
            }; // 更新传row，新增没有传
            $modal.data('id', row.id);//新增没有‘id’
            $modal.find('.modal-title').text(title);
            for (var name in row) {
                var inputEl=$modal.find('input[name="' + name + '"]');
                inputEl.val(row[name]);
                //更新禁用编辑时间和会议室
                // if($modal.data('id')){
                //     if((name=="roomNo")||(name=="day")||(name=="period")){
                //         inputEl.attr("disabled",true);
                //     }
                // }
            }
            //新增变为可以编辑
            // if($modal.data('id')==""){
            //     $modal.find('input[name="roomNo"]').attr("disabled",false);
            //     $modal.find('input[name="day"]').attr("disabled",false);
            //     $modal.find('input[name="period"]').attr("disabled",false);
            // }

            $modal.modal('show');
        }

        function showAlert(title, type) {
            $alert.attr('class', 'alert alert-' + type || 'success')
                    .html('<i class="glyphicon glyphicon-check"></i> ' + title).show();
            setTimeout(function () {
                $alert.hide();
            }, 3000);
        }

    </script>
</body>
</html>