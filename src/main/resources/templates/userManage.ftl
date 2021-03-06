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

    <#--bootstrap-table-->
    <!-- Latest compiled and minified CSS -->
    <#--<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.2/bootstrap-table.min.css">-->
    <#--<!-- Latest compiled and minified JavaScript &ndash;&gt;-->
    <#--<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.2/bootstrap-table.min.js"></script>-->
    <#--<!-- Latest compiled and minified Locales &ndash;&gt;-->
    <#--<script src="//cdnjs.cloudflare.com/ajax/libs/bootstrap-table/1.12.2/locale/bootstrap-table-zh-CN.min.js"></script>-->
    <title>用户管理</title>
</head>

<body>
    <#if msg??>
    <div><label>${msg!""}</label></div>
    </#if>

    <div class="container">
        <h1>用户管理</h1>
        <#--<p class="toolbar">-->
            <#--<a class="create btn btn-default" href="javascript:">新增</a>-->
            <#--<span class="alert"></span>-->
        <#--</p>-->

        <br><br>
        <div class="panel panel-default">
            <div class="panel-heading">
                查询面板
            </div>
            <div class="panel-body form-group toolbar" style="margin-bottom:0px;">
                <label class="col-md-1 control-label" style="text-align: right; margin-top:5px">姓名：</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="name" id="search_name"/>
                </div>
                <label class="col-md-1 control-label" style="text-align: right; margin-top:5px">部门：</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="department" id="search_department"/>
                </div>
                <label class="col-md-1 control-label" style="text-align: right; margin-top:5px">手机号：</label>
                <div class="col-md-2">
                    <input type="text" class="form-control" name="phone" id="search_phone"/>
                </div>
                <select class="col-md-1 combobox" name="role" id="search_role">
                    <option value="">角色</option>
                    <option value="false">普通</option>
                    <option value="true">管理员</option>
                </select>
                <div class="col-md-1">
                    <button class="btn btn-default" id="search_btn">查询</button>
                </div>
                <div class="col-md-1">
                    <button class="btn btn-primary create" href="javascript:">新增</button>
                </div>
                <span class="alert"></span>
            </div>
        </div>


        <table id="table"
               data-show-refresh="true"
               data-show-columns="true"
               <#--data-search="true"-->
               data-query-params="queryParams"
               data-toolbar=".toolbar">
            <thead>
            <tr>
                <th data-field="id">用户id</th>
                <th data-field="name">姓名</th>
                <th data-field="department">部门</th>
                <th data-field="phone">手机号</th>
                <th data-field="role" data-formatter="roleFormatter">角色</th>
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
                        <span class="input-group-addon">姓名<span style="color:red">*</span></span>
                        <input class="form-control" type="text" name="name" id="name" mustwrite="true" >
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">密码<span style="color:red">*</span></span>
                        <input class="form-control" type="password" name="password" id="password" mustwrite="true" >
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">部门</span>
                        <input class="form-control" type="text" name="department" id="department">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">手机号</span>
                        <input class="form-control" type="text" name="phone" id="phone">
                    </div>
                    <div class="input-group">
                        <span class="input-group-addon">角色(管理员填1)</span>
                        <input class="form-control" type="text" name="role" id="role">
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
        var $table = $('#table').bootstrapTable({
                    url: "/admin/getUsers",
                    toolbar: '#toolbar', //工具按钮用哪个容器
                    striped: true, //是否显示行间隔色
                    cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true, //是否显示分页
                    sortable: true, //是否启用排序
                    sortOrder: "asc", //排序方式
                    queryParams: queryParams,//传递参数（*）
                    sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
                    pageSize: 10, //每页的记录行数（*）
                    pageList: [5,10, 20, 30], //可供选择的每页的行数（*）
                    strictSearch: true,
    //            height: table_h, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度,设置了行高后编辑时标头宽度不会随着下面的行改变，且颜色也不会改变？？？？
                    uniqueId: "id", //每一行的唯一标识，一般为主键列
                    cardView: false, //是否显示详细视图
                    detailView: false, //是否显示父子表
                    paginationHAlign: "left",
                    singleSelect: true,
                    // search: true,               //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                    //strictSearch: true,
                    showColumns: true, //是否显示所有的列
                    showRefresh: true, //是否显示刷新按钮
                    clickToSelect: true, //是否启用点击选中行
                    paginationPreText: "<<",
                    locale:'zh-CN',//中文支持,
                    paginationNextText: ">>"
                }),
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
                $('#table').bootstrapTable('refresh', {url: '/admin/getUsers'});
            });

            $modal.find('.submit').click(function () {
                if(!check()){
                    alert("请填写所有必填项！");
                    return;
                }
                var row = {};
                $modal.find('input[name]').each(function () {
                    row[$(this).attr('name')] = $(this).val();
                });
                row["role"]==false;
                if(row["role"]==1){
                    row["role"]==true;
                }
                var url="/add";
                if($modal.data('id')){
                    //密码置空,不变
                    row["password"]=null;
                    url="/update";
                    row["id"]=$modal.data('id');
                }
                $.ajax({
                    url: "/admin"+url,
                    type: "post",
                    data: row,
                    success: function (data) {
                        $modal.modal('hide');
                        $table.bootstrapTable('refresh');
                        if(data.result=="success"){
                            showAlert(($modal.data('id') ? '更新' : '新增') + '用户成功!', 'success');
                        }else{
                            showAlert(($modal.data('id') ? '更新' : '新增') + '用户失败!'+data.desc, 'danger');
                        }
                    },
                    error: function () {
                        $modal.modal('hide');
                        showAlert(($modal.data('id') ? '更新' : '新增') + '用户失败!', 'danger');
                    }
                });
            });
        });

        //批量校验input必填项
        function check() {
            var item=$modal.find('input[mustwrite="true"]');
            // var item = $("input[mustwrite='true']", document.forms[0]);
            for (var i = 0; i < item.length; i++) {
                if ($.trim(item[i].value) == "") {
                    item[i].focus();    //光标定位到未填input框中
                    return false;
                }
            }
            return true;
        }

        function queryParams(params) {
            var user = { //这里的键的名字和控制器的变量名必须一直，这边改动，控制器也需要改成一样的
                limit: params.limit, //页面大小
                offset: params.offset, //页码
                name: $("#search_name").val(),
                department: $("#search_department").val(),
                phone: $("#search_phone").val(),
                role: $("#search_role").val()
            };
            return user;
        }

        function actionFormatter(value) {
            return [
                '<a class="update" href="javascript:" title="更新"><i class="glyphicon glyphicon-edit"></i></a>',
                '<a class="remove" href="javascript:" title="删除"><i class="glyphicon glyphicon-remove-circle"></i></a>',
            ].join('');
        }

        function roleFormatter(value){
            if(value==1){
                return "管理员";
            }else {
                return "普通";
            }
        }

        // update and delete events
        window.actionEvents = {
            'click .update': function (e, value, row) {
                showModal($(this).attr('title'), row);
            },
            'click .remove': function (e, value, row) {
                if (confirm('确定删除此用户 ？')) {
                    if(row.role==true){
                        showAlert('禁止删除管理员!', 'danger');
                        return;
                    }
                    $.ajax({
                        url: "/admin/del?id=" + row.id,
                        type: 'delete',
                        success: function (data) {
                            $table.bootstrapTable('refresh');
                            if(data.result=="success"){
                                showAlert('删除用户成功!', 'success');
                            }else {
                                showAlert('删除用户失败!'+data.desc, 'danger');
                            }
                        },
                        error: function () {
                            showAlert('删除用户失败!', 'danger');
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

            $modal.find('input[name=password]').attr("readOnly",false);
            //更新时控件传值，显示role为0/1
            if(title=="更新"){
                for (var name in row) {
                    $modal.find('input[name="' + name + '"]').val(row[name]);
                }
                if(row.role==true){
                    $modal.find('input[name=role]').val(1);
                }else{
                    $modal.find('input[name=role]').val(0);
                }
                //禁用更新password
                $modal.find('input[name=password]').attr("readOnly",true);
            }
            $modal.modal('show');
        }

        function showAlert(title, type) {
            $alert.attr('class', 'alert alert-' + type || 'success')
                    .html('<i class="glyphicon glyphicon-check"></i> ' + title).show();
            setTimeout(function () {
                $alert.hide();
            }, 3000);
        }

        //清除弹窗原数据
        $("#modal").on("hidden.bs.modal", function() {
            // $(this).data("id",)
            $(this).removeData("bs.modal");
        });
    </script>
</body>
</html>