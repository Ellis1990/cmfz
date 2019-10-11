<%--
  Created by IntelliJ IDEA.
  User: ellis
  Date: 2019-05-22
  Time: 16:50
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>

<script type="text/javascript">
    $(function () {
        $("#userlist").jqGrid({
            url: "${pageContext.request.contextPath}/user/getuserlist" ,
            editurl:"${pageContext.request.contextPath}/user/edituser",
            datatype:"json",
            colNames:["id","用户名","密码","电话号码","法号","姓名","性别","省","市","自我简介","注册日期","状态","头像"],
            colModel:[
                {name:"id"},
                {name:"username",editable:true},
                {name:"password",editable:true},
                {name:"phone",editable:true},
                {name:"darma",editable:true},
                {name:"name",editable:true},
                {name:"gender",editable:true,edittype:"select",editoptions:{value:"M:男性;F:女性"},
                    formatter:function (cellValue) {
                        if(cellValue=="M"){
                            return "男性";
                        }else if(cellValue=="F"){
                            return "女性";
                        }
                    }
                },
                {name:"state",editable:true},
                {name:"city",editable:true},
                {name:"bio",editable:true},
                {name:"signupDate"},
                {name:"status",editable:true,edittype:"select",editoptions:{value:"on:正常;off:封禁"},
                    formatter:function (cellValue) {
                        if(cellValue=="on"){
                            return "正常";
                        }else if(cellValue=="off"){
                            return "封禁";
                        }
                    }
                },
                {name:"profilePic",editable:true,edittype:"file",
                    formatter:function (cellValue) {
                        return "<img style='height:100px;width:100px' src='${pageContext.request.contextPath}/userProfilePic/"+cellValue+"' />";
                    }
                },
            ],
            pager:"#userpager",
            rowNum:4,
            rowList:[1,2,3,4,5,6,7,8,9,10],
            viewrecords:true,
            height:"60%",
            width:"100%",
            multiselect:true,
            rownumbers:true,
            styleUI:"Bootstrap",
            autowidth:true,



        }).jqGrid("navGrid","#userpager",
            {addtext:"添加",edittext:"编辑",deltext:"删除",search:false},
            {
                closeAfterAdd:true,
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var userid=response.responseJSON.userid;

                    if(userid!=""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/user/upload",
                            type:"post",
                            fileElementId:"profilePic",
                            data:{userid:userid},
                            success:function () {
                                $("#userlist").trigger("reloadGrid");
                            }

                        });
                    }

                    return response;
                }
            }, {
                closeAfterAdd:true,
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var userid=response.responseJSON.userid;

                    if(userid!=""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/user/upload",
                            type:"post",
                            fileElementId:"profilePic",
                            data:{userid:userid},
                            success:function () {
                                $("#userlist").trigger("reloadGrid");
                            }

                        });
                    }else {
                        $("#userlist").trigger("reloadGrid");
                    }

                    return response;
                }
            }
        );
    })

    function showmap() {

        var myChart = echarts.init(document.getElementById('map'));
        option = {
            title : {
                text: '用户地区分布图',
                subtext: '纯属虚构',
                left: 'center'
            },
            tooltip : {
                trigger: 'item'
            },
            legend: {
                orient: 'vertical',
                left: 'left',
                data:['iphone3']
            },
            visualMap: {
                min: 0,
                max: 2500,
                left: 'left',
                top: 'bottom',
                text:['高','低'],           // 文本，默认为数值文本
                calculable : true
            },
            toolbox: {
                show: true,
                orient : 'vertical',
                left: 'right',
                top: 'center',
                feature : {
                    mark : {show: true},
                    dataView : {show: true, readOnly: false},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            series : [
                {
                    name: '用户',
                    type: 'map',
                    mapType: 'china',
                    roam: false,
                    label: {
                        normal: {
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    }
                }
            ]
        };

        // 使用刚指定的配置项和数据显示图表。
        myChart.setOption(option);
        $.ajax({
            url:"${pageContext.request.contextPath}/user/getmap",
            type:"get",
            datatype:"json",
            success:function (data) {
                myChart.setOption({
                    series:[
                        {
                            data:data
                        }
                    ]
                })
            }
        })

    }
</script>
<head>
    <meta charset="utf-8">
    <!-- 引入 ECharts 文件 -->
    <script src="${pageContext.request.contextPath}/boot/js/echarts.min.js"></script>
    <script src="${pageContext.request.contextPath}/boot/js/china.js"></script>
</head>
<body>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">用户信息</a></li>
        <li><a href="javascript:void(0)" onclick="showmap()"><b>用户分布地图</b></a></li>
    </ul>
</div>
<table id="userlist" class="table table-striped" ></table>
<div id="map" style="width: 1000px;height:400px;"></div>

<div id="userpager" style="height: 50px"></div>

</body>
</html>
