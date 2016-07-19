<%@ page contentType="text/html; charset=utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<%@ include file="/dealer/include/meta.jsp"%>
<style>
.side-by-side{text-align: center;}
.side-by-side input{width: 74px; height:22px; color: #fff;line-height: 22px;text-align: center;background: #10a0d9; margin:5px; border:0;}
</style>
</head>
<script type="text/javascript">
	$(function() {
		//加载多<strong>文件</strong>上传的JS
		$('#multiFileId').MultiFile(
						{
							list : '#managerMultiFileIdList',
							accept:'png|jpg|bmp|jpeg|doc|docx|xls|xlsx|ppt|pptx|pdf',
							max:10, 
							STRING: {
						           remove:'删除',
						           selected:'文件重复: $file',  
						           denied:'不支持上传该文件类型 $ext!',
						           duplicate:'文件重复:\n$file'
						    }
						});
	});
	
	function selectAll(checkeAllObj, checkboxName) {
		if ($(checkeAllObj).is('.active')) {
			$(checkeAllObj).removeClass("active");
		} else {
			$(checkeAllObj).addClass("active");
		}
		$(".checkbox").each(function(index,domEle){
			if ($(this).is('.active')) {
				$(this).removeClass("active");
			} else {
				$(this).addClass("active");
			}
		});
	}
	
	function delFile(){
		var filingProposalId=$('#filingProposalId').val();
		if(null==filingProposalId || ""==filingProposalId){
			alert("删除附件失败");
			return;
		}
		var delFile="";
		var allFiles=$("span[name=checkedIds]");
		var checkFiles=$("span[name=checkedIds].checkbox.text_over.active");
		if(null!=allFiles && null!=checkFiles && allFiles.length==checkFiles.length){
			alert("附件不能为空，请先上传附件再删除旧附件");
			return ;
		}
		if(null==checkFiles || checkFiles.length<=0 ){
			alert("请选择附件或者要删除的附件");
			return ;
		}
		for(var i=0;i<checkFiles.length;i++){
			delFile+=$(checkFiles[i]).data('value')+",";
		}
		delFile=delFile.substring(0, delFile.length-1);
		var delUrl="<%=path%>/dealer/filingProposalManager/delFilingProposalFile.vti";
		$.ajax({
			url:delUrl,
			type:"POST",
			dataType:"json",
			data:{filingProposalId:filingProposalId,delFiles:delFile},
			error: function(){
		        alert('删除失败');
		    },
			success:function(data) {
				if(null==data || null==data.message){
					alert("删除失败");
				}else if("success"==data.message){
					removeFileList(checkFiles);
				}else {
					alert(data.message);
				}
			}
		});
	}

	function saveFile(){
		var multiFileTitle=$('.MultiFile-title');
		if((undefined==multiFileTitle || null==multiFileTitle || multiFileTitle.length<=0) ){
			alert("请选择附件");
			return ;
		}else{
			$("#myPageForm").submit();
		}
	}
	
	function removeFileList(checkFiles){
		if(null!=checkFiles && checkFiles.length>=1){
			for(var i=0; i<=checkFiles.length; i++){
				$("#fileUl_"+$(checkFiles[i]).data('value')).remove();
			}
		}
	}
	function addFileList(filingProposalFileList){
		var filingProposalFileDiv=$("#filingProposalFileDiv");
		filingProposalFileDiv.html("");
		var rowText = "";
		for(var i=0;i<filingProposalFileList.length;i++){
			if(i%2==0){
				rowText += "<ul class='table_bd clearfix even' id='" + filingProposalFileList[i].id + "'>";
			}else{
				rowText += "<ul class='table_bd clearfix old' id='" + filingProposalFileList[i].id + "'>";
			}
			rowText += "<li class='li_70'><span class='checkbox text_over' name='checkedIds' value=" + filingProposalFileList[i].id + "></span></li>";
			rowText += "<li class='li_280'><span title='" + filingProposalFileList[i].originalName +"'>"+ filingProposalFileList[i].originalName + "</span> </li>";
			rowText += "<li class='li_150'><span>"+ filingProposalFileList[i].createTime + "</span> </li>";
			rowText += "<li class='li_100'><span>"+ filingProposalFileList[i].size + "</span> </li>";
			rowText += "</ul>";
		}
		filingProposalFileDiv.html(rowText);
	}
</script>
<body>
<form id="myPageForm" action="<%=path%>/dealer/filingProposalManager/uploadFilingProposalFile.vti" method="post"  enctype="multipart/form-data">
<input type="hidden" id="filingProposalId" name="filingProposalId" value="${filingProposalId}" />
<input type="hidden" id="delFiles" name="delFiles" value="" />
	<fieldset>
			<legend>添加附件</legend>
			<!-- 注意这里的attach名字为固定，如果要控制上传的格式，则追加accept="doc|txt|jsp"  最大上传量maxlength="3" -->
			<input type="file" name="attach" id="multiFileId" class="text-input"/>
			<div id="managerMultiFileIdList"></div>
	</fieldset>
	<div class="table_list">
				<ul class="table_hd clearfix" style="min-width:600px;">
				    <li class="li_50">选择</li>
            		<li class="li_280">文件名</li>
            		<li class="li_150">上传时间</li>
            		<li class="li_100">文件大小(M)</li>
				</ul>
				<div id="filingProposalFileDiv">
					<c:if test="${filingProposalAttachmentList != null}" >
						<c:forEach var="filingProposalAttachment" items="${filingProposalAttachmentList}" varStatus="statusObj">
							<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}' id="fileUl_${filingProposalAttachment.id}" style="min-width:600px;">
							    <li class="li_50"><span class="checkbox text_over" name="checkedIds" data-value="${filingProposalAttachment.id}"></span></li>
				            	<li class="li_280"><span title="${filingProposalAttachment.originalName}">${fn:substring(filingProposalAttachment.originalName,0,30)}</span> </li>
				            	<li class="li_150"><span ><fmt:formatDate value="${filingProposalAttachment.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
				            	<li class="li_100"><span>${filingProposalAttachment.size}</span></li>
							</ul>
						</c:forEach>
					</c:if>				
				</div>

	</div><!--/table_list-->
	<div class="side-by-side" >
	<input type="button" onclick="delFile()" value="删除附件"  />
	<input type="button" onclick="saveFile()" value="上传新附件" />
	</div>
	
</form>
</body>
</html>