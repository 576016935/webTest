<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="shiro" uri="http://shiro.apache.org/tags" %>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>路漫漫其修远兮</title>
    <!--IE以最高内核渲染-->
    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
    <!--优先选用急速模式webkit内核-->
    <meta name="renderer" content="webkit">
    <link rel="stylesheet" href="./css/bootstrap.css">
    <link rel="stylesheet" href="./css/ele.css">
    <link rel="stylesheet" href="./css/eleicon.css">
    <link rel="stylesheet" href="./css/cytpurchase.css">
    <script src="./js/vue.min.js"></script>
    <script src="./js/jquery.js"></script>
    <script src="./js/bootstrap.js"></script>
    <script src="./js/ele.js"></script>
    <%--<script type="text/javascript" src="../../js/Invitation.js"></script>--%>
    <style>
        .el-form-item {
            margin-bottom: 0;
        }
        .row{
            margin: 0;
        }
        .el-table th, .el-table tr{
            cursor: pointer;
        }
        .el-button {
            padding: 8px 26px;
        }
        .el-button--danger, .el-button--danger:focus, .el-button--danger:hover {
            background: #F04848;
        }

        element.style {
        }
        .el-button {
            padding: 8px 26px;
        }
        .el-button--small, .el-button--small.is-round {
            padding: 9px 15px;
        }
        .el-button--mini, .el-button--small {
            font-size: 12px;
            border-radius: 3px;
        }
        .el-button--warning {
            color: #fff;
            background-color: #f56c6c;
            border-color: #f56c6c;
        }
        .headerbtn {
            padding: 6px 0;
            margin-right: 20px;
            background: #FFEEF0;
            color: #D7666D;
            border: 1px solid #D4AAA7;
            border-radius: 4px;
            width: 82px;
        }
        .el-table--enable-row-transition .el-table__body td {
            -webkit-transition: background-color .25s ease;
            transition: background-color .25s ease;
            color: #858689;
            font-size: 14px;
        }
    </style>
</head>
<body style="background: #F4F4F5;">
<div id="app" v-cloak >

    <div class="line"></div>
    <!-- 顶部导航栏 -->
    <el-menu
            :default-active="activeIndex2"
            class="el-menu-demo"
            mode="horizontal"
            @select="handleSelect"
            background-color="#545c64"
            text-color="#fff"
            active-text-color="#ffd04b">
        <el-menu-item index="1-personal" @click="dialogVisible = true">个人中心</el-menu-item>
        <el-submenu index="2">
            <template slot="title">我的工作台</template>
            <el-menu-item index="2-1-user">用户管理</el-menu-item>
            <el-menu-item index="2-2-role">角色管理</el-menu-item>
            <el-menu-item index="2-3-resc">权限管理</el-menu-item>
            <%--<el-submenu index="2-4">
                <template slot="title">选项4</template>
                <el-menu-item index="2-4-1">选项1</el-menu-item>
                <el-menu-item index="2-4-2">选项2</el-menu-item>
                <el-menu-item index="2-4-3">选项3</el-menu-item>
            </el-submenu>--%>
        </el-submenu>
        <el-menu-item index="3" disabled>消息中心</el-menu-item>
        <el-menu-item index="5" style="right: 20px"><a href="/logout">退出登录</a></el-menu-item>
    </el-menu>

    <!-- 个人信息弹窗 -->
    <el-dialog
            title="个人信息"
            :visible.sync="dialogVisible"
            width="30%"
            :before-close="handleClose">
        <el-form ref="form" :model="user" label-width="80px">
            <el-form-item label="用户名">{{user.name}}</el-form-item>
            <el-form-item label="手机">{{user.phone}}</el-form-item>
            <el-form-item label="邮箱">{{user.email}}</el-form-item>
            <el-form-item label="性别">{{user.sex}}</el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <%--<el-button type="primary" @click="dialogVisible = false">确 定</el-button>--%>
  </span>
    </el-dialog>


    <div class="panel panel-default" style="border: 0;box-shadow: none">
        <div class="panel-body">
            <p style="font-size: 18px;font-weight: 600;">路漫漫其修远兮</p>
            <p>您好：
                <shiro:user>
                    <shiro:principal property="name"></shiro:principal>
                </shiro:user>
                <%--<shiro:user>
                    <shiro:principal property="rolename"></shiro:principal>
                </shiro:user>--%>
            </p>
        </div>
    </div>

    <!-- 列表页 -->
    <div class="panel panel-default" style="border: 0;box-shadow: none;margin: 0 10px">
        <div class="panel-body" >
            <el-form :label-position="labelPosition" label-width="80px" :model="formLabelAlign" :visible.sync="dialogVisible">
                <div class="row">
                    <div class="col-xs-3 col-sm-3 col-md-4">
                        <el-form-item label="地域名称">
                            <el-input v-model="formLabelAlign.areaName"></el-input>
                        </el-form-item>
                    </div>
                    <shiro:hasPermission name="/user/query">
                    <div class="col-xs-1 col-sm-1 col-md-1">
                        <el-form-item >
                            <el-button type="danger"  id="chaxun_s" onclick="query()">查询</el-button>

                        </el-form-item>
                    </div>
                    </shiro:hasPermission>
                </div>
            </el-form>

        </div>
    </div>
    <div class="panel panel-default" style="border: 0;box-shadow: none;margin: 20px">
        <%--缓存数据--%>
        <div class="panel-body">
            <div class="row">
                <el-table  ref="multipleTable" :data="tableData3"
                           tooltip-effect="dark"  @row-click="rowclick" @cell-click='qwer'

                           :header-cell-style="{background:'#FFF1F4',color: '#111111',fontSize: '16px',fontWeight: '600'}"
                           @selection-change="handleSelectionChange" empty-text = '暂无数据'>
                    <el-table-column type="selection" @click="found(tableData3,index)" >
                    </el-table-column>
                    <el-table-column type="index" label="序号" width="70px"  show-overflow-tooltip></el-table-column>
                    <el-table-column  label="项目名称" show-overflow-tooltip>
                        <template slot-scope="scope">
                            <p v-html="scope.row.projectName"></p>
                        </template>
                    </el-table-column>
                    <el-table-column prop="createtime" label="创建时间" show-overflow-tooltip></el-table-column>
                    <el-table-column prop="faddress" label="文件地址" show-overflow-tooltip></el-table-column>
                </el-table>
                <div class="modal-footer">
                    <span class="demonstration">显示总数</span>
                        <el-pagination
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange"
                                :current-page.sync="currentPage1"
                                :page-size="pageSize"
                                layout="total, prev, pager, next"
                                :total="total">
                        </el-pagination>
                </div>
            </div>
        </div>
    </div>
    <div class="foooter"></div>
</div>
</body>
</html>
<script type="text/javascript">
    var user = '<%=request.getAttribute("user")%>'
    console.log(user)
    var app = new Vue({
        el:'#app',
        data:{
            textarea:'',
            labelPosition: 'left',
            formLabelAlign: {
            },
            tableData3: [],
            total:0,
            pageSize:0,
            currentPage1:1,
            activeIndex: '1',//顶部导航栏属性
            activeIndex2: '1',//顶部导航栏属性
            dialogVisible: false,//个人信息弹窗
            user:[],//用户对象
        },
        mounted :function (){
            var pageNum=0;
            var pageSize=10;
            queryList(pageNum,pageSize);
        },

        methods:{
            handleSizeChange(val) {
                console.log(val)
            },
            handleCurrentChange(val) {
                queryList(val,10);
            },
            ahref:function () {},
            change: function (currentPage) {
                this.currentPage1 = currentPage
            },
            //  反选
            handleSelectionChange:function(val) {
                var id = [];
                for (var i=0; i<val.length; i++){
                    id.push(val[i].id);
                }
                this.ids = id;
            },
            //  点击每行数据操作
            rowclick:function (rest) {
            },
            qwer:function(rest, column, cell, event){
            },
            //alert提示
            callbackInfo: function callbackInfo(param1,param2,path,flag) {
                //param1(提示消息),param2(当前提示框),path（点击确定后路径）,flag（true需要跳转页面；false不需要跳转页面）
                this.$alert(param1, param2, {
                    confirmButtonText: '确定',
                    callback: function(action) {
                        this.dialogVisible = false;
                        if (flag){
                            window.location.href = path;
                        }
                    }
                });
            },
            handleSelect(key, keyPath) {
                console.log(key, keyPath);
            },
            handleClose(done) {
                this.$confirm('确认关闭？')
                    .then(_ => {
                    done();
            })
            .catch(_ => {});
            }
        }
    })

function queryList(pageNum,pageSize) {
    $.ajax({
        //url:"./notice/query",
        url:"./notice/queryAll",
        type:"post",
        dataType:"json",
        data:{pageNum:pageNum,pageSize:pageSize,keyword:"拿到"},
        success:function (date) {
            app.tableData3 = date.list;
            app.total = date.total;//总条数
            app.pageSize = date.pageSize;
        },
        error:function (e) {
            console.log(e)
        }
    })
}
//solr高亮测试
   function query(pageNum,pageSize) {
       $.ajax({
           url:"./notice/query",
           type:"post",
           dataType:"json",
           data:{pageNum:pageNum,pageSize:pageSize,keyword:app.formLabelAlign.areaName},
           success:function (date) {
               app.tableData3 = date.datas;
               app.total = date.total;//总条数
               app.pageSize = date.pageSize;
           },
           error:function (e) {
               console.log(e)
           }
       })
   }

   $.ajax({
       url:"./sessionController/getUser",
       type:"post",
       dataType:"json",
       success:function(date){
           if(date!=null){
               date.sex = date.sex == 1 ? '男':'女'
           }
           console.log(date)
           app.user=date;
       }
   })
</script>