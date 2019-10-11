<%--
  Created by IntelliJ IDEA.
  User: ellis
  Date: 2019-05-24
  Time: 09:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" isELIgnored="false" %>
<html>
<script type="text/javascript">
    $(function () {
        $("#albumlist").jqGrid({
            url: "${pageContext.request.contextPath}/album/getalbumlist" ,
            editurl:"${pageContext.request.contextPath}/album/editalbum",
            datatype:"json",
            colNames:["id","标题","星级","作者","播音员","集数","内容简介","创建时间","状态","封面"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"score",editable:true,edittype:"select",editoptions:{value:"1:一星;2:二星;3:三星;4:四星;5:五星"},
                    formatter:function (cellValue) {
                        if(cellValue=="1"){
                            return "一星";
                        }else if(cellValue=="2"){
                            return "二星";
                        }else if(cellValue=="3"){
                            return "三星";
                        }else if (cellValue == "4") {
                            return "四星";
                        }else if (cellValue == "5") {
                            return "五星";
                        }
                    }
                },
                {name:"author",editable:true},
                {name:"broadcaster",editable:true},
                {name:"count"},
                {name:"bio",editable:true},
                {name:"createDate"},
                {name:"status",editable:true,edittype:"select",editoptions:{value:"on:展示;off:不展示"},
                    formatter:function (cellValue) {
                        if(cellValue=="on"){
                            return "展示";
                        }else if(cellValue=="off"){
                            return "不展示";
                        }
                    }
                },
                {name:"picPath",editable:true,edittype:"file",
                    formatter:function (cellValue) {
                        return "<img style='height:100px;width:100px' src='${pageContext.request.contextPath}/albumImg/"+cellValue+"' />";
                    }
                },
            ],
            pager:"#albumpager",
            rowNum:4,
            rowList:[1,2,3,4,5,6,7,8,9,10],
            viewrecords:true,
            height:"60%",
            width:"100%",
            multiselect:true,
            rownumbers:true,
            styleUI:"Bootstrap",
            autowidth:true,
            subGrid:true,
            subGridRowExpanded:function (subGridId,albumid) {
                addSubGrid(subGridId,albumid);
            }
        }).jqGrid("navGrid","#albumpager",
            {addtext:"添加",edittext:"编辑",deltext:"删除",search:false},
            {
                closeAfterAdd:true,
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var albumid=response.responseJSON.albumid;
                    if(albumid!=""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/album/upload",
                            type:"post",
                            fileElementId:"picPath",
                            data:{albumid:albumid},
                            success:function () {
                                $("#albumlist").trigger("reloadGrid")
                            }
                        });
                    }
                    return response;
                }

            },
            {
                closeAfterAdd:true,
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var albumid=response.responseJSON.albumid;
                    if(albumid!=""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/album/upload",
                            type:"post",
                            fileElementId:"picPath",
                            data:{albumid:albumid},
                            success:function () {
                                $("#albumlist").trigger("reloadGrid")
                            }
                        });
                    }
                    return response;
                }
            }
        )
    })

    function addSubGrid(subGridId, albumid) {

        var subGridTableId= subGridId+"table";
        var subGridPagerId=subGridId+"pager";
        $("#"+subGridId).html(
            "<table id='"+subGridTableId+"' class='table table-striped' ></table>"+
                "<div id='"+subGridPagerId+"' style=\"height: 50px\"></div>"
        )

        $("#"+subGridTableId).jqGrid({
            url: "${pageContext.request.contextPath}/chapter/getchapterlist?albumid="+albumid ,
            editurl:"${pageContext.request.contextPath}/chapter/editchapter?albumid="+albumid,
            datatype:"json",
            colNames:["id","标题","音频大小","音频时长","音频"],
            colModel:[
                {name:"id"},
                {name:"title",editable:true},
                {name:"size"},
                {name:"duration"},
                {name:"audioPath",editable:true,edittype:"file",
                    formatter:function (cellValue,options,value) {
                        return "<a href='#' onclick=\"playAudio('"+cellValue+"') \"><span class='glyphicon glyphicon-play-circle'></span></a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;"+
                                "<a href='#' onclick=\"downloadAudio('"+cellValue+"') \" ><span class='glyphicon glyphicon-download'></span></a>";
                    }
                },
            ],
            pager:"#"+subGridPagerId,
            rowNum:4,
            rowList:[1,2,3,4,5,6,7,8,9,10],
            viewrecords:true,
            height:"60%",
            width:"100%",
            multiselect:true,
            rownumbers:true,
            styleUI:"Bootstrap",
            autowidth:true,
        }).jqGrid("navGrid","#"+subGridPagerId,
            {addtext:"添加",edittext:"编辑",deltext:"删除",search:false},
            {
                closeAfterAdd:true,
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var chapterid=response.responseJSON.chapterid;
                    if(chapterid!=""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chapter/upload",
                            type:"post",
                            fileElementId:"audioPath",
                            data:{chapterid:chapterid},
                            success:function () {
                                $("#"+subGridTableId).trigger("reloadGrid")
                    //            $("#albumlist").trigger("reloadGrid")
                            }
                        });
                    }
                    return response;
                }
            },
            {
                closeAfterAdd:true,
                closeAfterEdit:true,
                afterSubmit:function (response) {
                    var chapterid=response.responseJSON.chapterid;
                    if(chapterid!=""){
                        $.ajaxFileUpload({
                            url:"${pageContext.request.contextPath}/chapter/upload",
                            type:"post",
                            fileElementId:"audioPath",
                            data:{chapterid:chapterid},
                            success:function () {
                             //   $("#albumlist").trigger("reloadGrid")
                                $("#"+subGridTableId).trigger("reloadGrid")

                            }
                        });
                    }
                    return response;
                }
            }
        )
    }
    function playAudio(cellValue) {
        $("#playaudio").modal("show");
        $("#audioid").prop("src","${pageContext.request.contextPath}/chapterAudio/"+cellValue);
    }
    function downloadAudio(cellValue) {

        location.href="${pageContext.request.contextPath}/chapter/downloadaudio?audioPath="+cellValue+""
    }
</script>
<head>
</head>
<body>
    <div>
        <!-- Nav tabs -->
        <ul class="nav nav-tabs" role="tablist">
            <li role="presentation" class="active"><a href="#home" aria-controls="home" role="tab" data-toggle="tab">专辑信息</a></li>
        </ul>
    </div>

    <table id="albumlist" class="table table-striped" ></table>
    <div class="modal fade" tabindex="-1" role="dialog" id="playaudio">
        <div class="modal-dialog" role="document">
            <audio src="" id="audioid" controls></audio>
        </div>
    </div>
    <div id="albumpager" style="height: 50px"></div>
</body>
</html>
