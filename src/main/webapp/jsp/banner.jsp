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
        $("#bannerlist").jqGrid({
           url: "${pageContext.request.contextPath}/banner/getbannerlist" ,
            editurl:"${pageContext.request.contextPath}/banner/editbanner",
            datatype:"json",
            colNames:["id","标题","状态","创建日期","描述","图片"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"status",editable:true,edittype:"select",editoptions:{value:"on:展示;off:不展示"},
                    formatter:function (cellValue) {
                        if(cellValue=="on"){
                            return "展示";
                        }else if(cellValue=="off"){
                            return "不展示";
                        }
                    }
                },
                {name:"createDate"},
                {name:"description",editable:true},
                {name:"picPath",editable:true,edittype:"file",
                    formatter:function (cellValue) {
                        return "<img style='height:100px;width:100px' src='${pageContext.request.contextPath}/bannerImg/"+cellValue+"' />";
                    }
                },
            ],
            pager:"#bannerpager",
            rowNum:4,
            rowList:[1,2,3,4,5,6,7,8,9,10],
            viewrecords:true,
            height:"60%",
            width:"100%",
            multiselect:true,
            rownumbers:true,
            styleUI:"Bootstrap",
            autowidth:true,



        }).jqGrid("navGrid","#bannerpager",
            {addtext:"添加",edittext:"编辑",deltext:"删除",search:false},
            {
                closeAfterAdd:true,
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var bannerid=response.responseJSON.bannerid;

                    if(bannerid!=""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/banner/upload",
                            type:"post",
                            fileElementId:"picPath",
                            data:{bannerid:bannerid},
                            success:function () {
                                $("#bannerlist").trigger("reloadGrid");
                            }

                        });
                    }

                    return response;
                }
            }, {
                closeAfterAdd:true,
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var bannerid=response.responseJSON.bannerid;

                    if(bannerid!=""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/banner/upload",
                            type:"post",
                            fileElementId:"picPath",
                            data:{bannerid:bannerid},
                            success:function () {
                                $("#bannerlist").trigger("reloadGrid");
                            }

                        });
                    }else {
                        $("#bannerlist").trigger("reloadGrid");
                    }

                    return response;
                }
            }
        );
    })
</script>
<head>
</head>
<body>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">轮播图信息</a></li>
    </ul>
</div>
    <table id="bannerlist" class="table table-striped" ></table>
    <div id="bannerpager" style="height: 50px"></div>
</body>
</html>
