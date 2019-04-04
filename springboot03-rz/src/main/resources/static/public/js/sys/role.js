$(function(){
    var option = {
        url: '../sys/role/list',
        pagination: true,	//显示分页条
        sidePagination: 'server',//服务器端分页
        showRefresh: true,  //显示刷新按钮
        search: true,//是否显示搜索框
        toolbar: '#toolbar',
        striped : true,     //设置为true会有隔行变色效果
        columns: [
            {
                field: 'roleId',//field： json的key对应
                title: '序号',
                width: 40,
                formatter: function(value, row, index) {
                    var pageSize = $('#table').bootstrapTable('getOptions').pageSize;
                    var pageNumber = $('#table').bootstrapTable('getOptions').pageNumber;
                    return pageSize * (pageNumber - 1) + index + 1;
                }
            },
            {checkbox:true},
            { title: '角色ID', field: 'roleId',sortable:true},
            {field:'roleName', title:'角色名'},
            { title: '备注', field: 'remark'},
            { title: '创建用户ID', field: 'createUserId'},
            { title: '创建时间', field: 'createTime'},
        ]};
    $('#table').bootstrapTable(option);
});


var vm = new Vue({
    el:'#dtapp',
    data:{
        showList: true,
        title: null,
        role:{}
    },
    methods:{
        del: function(){

            //var rows = getSelectedRows();
            /**
             * getSelections
             参数： undefined
             详情：
             返回选定的行，如果未选择任何记录，则返回一个空数组。
             */
            var rows = $("#table").bootstrapTable("getSelections")
            //[]
            if(rows == null||rows.length==0){
                alert('请选择您要删除的行');
                return ;
            }
            var id = 'roleId';
            //提示确认框  layer huozhe sweetalert
            layer.confirm('您确定要删除所选数据吗？', {
                btn: ['确定', '取消'] //可以无限个按钮
            }, function(index, layero){
                var ids = new Array();
                //遍历所有选择的行数据，取每条数据对应的ID
                $.each(rows, function(i, row) {
                    console.log(row[id]);
                    ids[i] = row[id];
                });

                $.ajax({
                    type: "POST",
                    url: "/sys/role/del",
                    data: JSON.stringify(ids),
                    success : function(r) {
                        if(r.code === 0){
                            layer.alert('删除成功');
                            $('#table').bootstrapTable('refresh');
                        }else{
                            layer.alert(r.msg);
                        }
                    },
                    error : function() {
                        layer.alert('服务器没有返回数据，可能服务器忙，请重试');
                    }
                });
            });
        },
        add: function(){
            vm.showList = false;
            vm.title = "新增";
            vm.role = {parentName:null,parentId:0,type:1,orderNum:0};
            vm.getMenu();
        },
        update: function (event) {
            var id = 'roleId';
            var roleId = getSelectedRow()[id];//common.js
            alert(roleId);
            if(roleId == null){
                return ;
            }
            //sys/menu/info/1
            $.get("../sys/role/info/"+roleId, function(r){
                vm.showList = false;
                vm.title = "修改";
                vm.role = r.role;
                vm.getRole();
            });
        },
        saveOrUpdate: function (event) {
            var url = vm.role.roleId == null ? "../sys/role/save" : "../sys/role/update";
            $.ajax({
                type: "POST",
                url: url,
                data: JSON.stringify(vm.role),
                success: function(r){
                    if(r.code === 0){
                        layer.alert('操作成功', function(index){
                            layer.close(index);
                            vm.reload();
                        });
                    }else{
                        layer.alert(r.msg);
                    }
                }
            });
        },
        reload: function (event) {
            vm.showList = true;
            $("#table").bootstrapTable('refresh');
        },
        getMenu: function(roleId) {
            var setting = {
                data: {
                    simpleData: {
                        enable: true,
                        idKey: "roleId",
                        pIdKey: "parentId",
                        rootPId: -1
                    },
                    key: {
                        url: "nourl"
                    }
                }
            };
        }
    }
});