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
        <p class="toolbar">
            <a class="create btn btn-default" href="javascript:">新增</a>
            <span class="alert"></span>
        </p>
        <table id="table"
               data-show-refresh="true"
               data-show-columns="true"
               data-search="true"
               data-query-params="queryParams"
               data-toolbar=".toolbar">
            <thead>
            <tr>
                <th data-field="id">预约id</th>
                <th data-field="roomNo">会议室编号</th>
                <th data-field="user">预约人</th>
                <th data-field="day">预约人</th>
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
        var $table = $('#table').bootstrapTable({
                    url: "/order/query",
                    toolbar: '#toolbar', //工具按钮用哪个容器
                    //striped: true, //是否显示行间隔色
                    cache: false, //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
                    pagination: true, //是否显示分页
                    sortable: true, //是否启用排序
                    sortOrder: "asc", //排序方式
                    //queryParams: postQueryParams,//传递参数（*）
                    //sidePagination: "server",      //分页方式：client客户端分页，server服务端分页（*）
                    pageSize: 10, //每页的记录行数（*）
                    pageList: [10, 25, 50, 100], //可供选择的每页的行数（*）
                    strictSearch: true,
    //            height: table_h, //行高，如果没有设置height属性，表格自动根据记录条数觉得表格高度,设置了行高后编辑时标头宽度不会随着下面的行改变，且颜色也不会改变？？？？
                    uniqueId: "id", //每一行的唯一标识，一般为主键列
                    cardView: false, //是否显示详细视图
                    detailView: false, //是否显示父子表
                    paginationHAlign: "left",
                    singleSelect: true,
                    search: true,               //是否显示表格搜索，此搜索是客户端搜索，不会进服务端
                    //strictSearch: true,
                    showColumns: true, //是否显示所有的列
                    showRefresh: true, //是否显示刷新按钮
                    clickToSelect: true, //是否启用点击选中行
                    paginationPreText: "<<",
                    paginationNextText: ">>"
                }),
                $modal = $('#modal').modal({show: false}),
                $alert = $('.alert').hide();

        $(function () {
            // create event
            $('.create').click(function () {
                showModal($(this).text());
            });

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
        });

        function queryParams(params) {
            return {};
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
                $modal.find('input[name="' + name + '"]').val(row[name]);
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