<%--
  Created by IntelliJ IDEA.
  User: ellis
  Date: 2019-05-29
  Time: 09:05
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<script type="text/javascript">
    $(function () {
        $("#articlelist").jqGrid({
            url: "${pageContext.request.contextPath}/article/getarticlelist" ,
            editurl:"${pageContext.request.contextPath}/article/editarticle",
            datatype:"json",
            colNames:["id","标题","作者","创建日期","内容","操作"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"author",editable:true},
                {name:"createDate"},
                {name:"content",hidden:true},
                {name:"",
                    formatter: function (cellValue, opo, value) {

                        return "<a href='#' onclick=\"checkModal('" + value.id + "')\">查看详情</a>";
                    }
                }
            ],
            pager:"#articlepager",
            rowNum:4,
            rowList:[1,2,3,4,5,6,7,8,9,10],
            viewrecords:true,
            height:"60%",
            width:"100%",
            multiselect:true,
            rownumbers:true,
            styleUI:"Bootstrap",
            autowidth:true,
        });
    })
    
    function showModal() {
        $("#myModal").modal("show")
        $("#addArticleFrom")[0].reset();
        KindEditor.html("#editor", "");
        $("#modal_footer").html(
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"addModal() \">保存</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>")
        KindEditor.create('#editor', {
            uploadJson: "${pageContext.request.contextPath}/article/uploadpic",
            filePostName: "articlePic",//默认是imgFile
            fileManagerJson: "${pageContext.request.contextPath}/article/getarticlecontent",
            allowFileManager: true,
            resizeType: 1,
            afterBlur: function () {
                this.sync()
            }
        });
    }
    function addModal() {

        $.ajax({
            url: "${pageContext.request.contextPath}/article/addarticle",
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),
            success: function () {
                $("#myModal").modal("hide")
                $("#articlelist").trigger("reloadGrid")
            }
        })
    }
    function checkModal(id) {

        var value = $("#articlelist").jqGrid("getRowData", id);

        // value.push($("#articlelist").jqGrid('getRowData', value[value.length-1]));

        $("#myModal").modal("show");
        $("#title").val(value.title);
        $("#author").val(value.author);
        $("#id").val(value.id);
        $("#createDate").val(value.createDate);
        $("#modal_footer").html(
            "<button type=\"button\" class=\"btn btn-primary\" onclick=\"saveModal()\">保存</button>\n" +
            "<button type=\"button\" class=\"btn btn-danger\" data-dismiss=\"modal\">取消</button>")

        KindEditor.create('#editor', {
            uploadJson: "${pageContext.request.contextPath}/article/uploadpic",
            filePostName: "articlePic",//默认是imgFile
            fileManagerJson: "${pageContext.request.contextPath}/article/getarticlecontent",
            allowFileManager: true,
            resizeType: 1,
            afterBlur: function () {
                this.sync()
            }
        });
        $("#editor").val(value.content)
        KindEditor.html("#editor",value.content);
    }
    function saveModal() {
        $.ajax({
            url: "${pageContext.request.contextPath}/article/editarticle?",
            datatype: "json",
            type: "post",
            data: $("#addArticleFrom").serialize(),
            success: function () {
                $("#myModal").modal("hide")
                $("#articlelist").trigger("reloadGrid")
            }
        })
    }
</script>
<head>
</head>
<body>
<div>
    <!-- Nav tabs -->
    <ul class="nav nav-tabs" role="tablist">
        <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">文章信息</a></li>
        <li><a href="javascript:void(0)" onclick="showModal()"><b>添加文章</b></a></li>
    </ul>
</div>
<table id="articlelist" class="table table-striped" ></table>
<div id="articlepager" style="height: 50px"></div>
<div class="modal fade" id="myModal">
    <div class="modal-dialog">
        <div class="modal-content" style="width:750px">
            <!--模态框标题-->
            <div class="modal-header">
                <!--
                    用来关闭模态框的属性:data-dismiss="modal"
                -->
                <button type="button" class="close" data-dismiss="modal"><span>&times;</span></button>
                <h4 class="modal-title">编辑用户信息</h4>
            </div>

            <!--模态框内容体-->
            <div class="modal-body">
                <form action="" class="form-horizontal"
                      id="addArticleFrom">
                    <div class="form-group">
                        <div class="col-sm-5">
                            <input type="text" name="id" id="id" class="form-control" style="display:none" >
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">标题</label>
                        <div class="col-sm-5">
                            <input type="text" name="title" id="title" placeholder="请输入标题" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <label class="col-sm-1 control-label">作者</label>
                        <div class="col-sm-5">
                            <input type="text" name="author" id="author" placeholder="请输入作者名" class="form-control">
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <textarea id="editor" name="content" style="width:700px;height:300px;"></textarea>
                        </div>
                    </div>
                    <input id="addInsertImg" name="insertImg" hidden>
                </form>
            </div>
            <!--模态页脚-->
            <div class="modal-footer" id="modal_footer">

            </div>
        </div>
    </div>
</div>

</body>

</html>
