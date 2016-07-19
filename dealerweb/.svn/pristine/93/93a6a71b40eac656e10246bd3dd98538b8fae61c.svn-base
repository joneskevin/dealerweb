<%@ page contentType="text/html; charset=utf-8"%>
<%@ include file="/dealer/include/jstl.jsp" %>
<%@ page import="com.ava.util.ValidationCodeUtil"%>
<%@ page import="com.ava.domain.entity.User"%>
<%@ page import="com.ava.domain.vo.FilingProposalCompanyVehcileInfo"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<%@ include file="/dealer/include/meta.jsp"%>
</head>

<script type="text/javascript">
	$(function() {
		//加载多<strong>文件</strong>上传的JS
		
		$('#multiFileId').MultiFile(
						{
							list : '#multiFileId-list',
							accept:'png|jpg|bmp|jpeg|doc|docx|xls|xlsx|ppt|pptx|txt|avi|zip', 
							max:10, 
							STRING: {
						           remove:'删除',
						           selected:'文件重复: $file',  
						           denied:'不支持上传该文件类型 $ext!',
						           duplicate:'文件重复:\n$file'
						    }
						});
	});
	
	function uploadFile(){
		var multiFileIdList=$("multiFileId-list");
		var multiFileLabels=$(".MultiFile-label");
		if(null==multiFileLabels || multiFileLabels.length<=0){
			alert("请先选择文件");
			return;
		}
		$("#myPageForm").submit();
	}
	function saveAndDelFile(){
		var allFiles=$("span[name=checkedIds]");
		if(null!=allFiles && allFiles.length>0){
			new window.parent.dialog().reset();
			//parent.location.reload();
		}else{
			alert("附件不能为空");
			return;
			/**
			if (confirm("您现在没有附件，请尽快上传附件")) {
				return true;
			} else {
				return false;
			}
			*/
		}
	}

</script>
<body>
<form id="myPageForm" action="<%=path%>/dealer/declareManager/uploadFile.vti" method="post"  enctype="multipart/form-data">
<input type="hidden" id="filingProposalId" name="filingProposalId" value="${filingProposalId}" />
<input type="hidden" id="delFiles" name="delFiles" value="" />
	<div>
		<fieldset>
			<legend>添加附件</legend>
			<!-- 注意这里的attach名字为固定，如果要控制上传的格式，则追加accept="doc|txt|jsp"  最大上传量maxlength="3" -->
			<input type="file" name="attach" id="multiFileId" class="text-input"/>
			<input type="button" name="upFileBtn" id="upFileBtnId" value="上传" onclick="uploadFile();" style="width: 74px; display:block; color: #fff;line-height: 22px;text-align: center;background: #10a0d9; margin:5px auto;"/>
		</fieldset>
		<div id="multiFileId-list"></div>
	</div>
	<div class="table_list">
				<ul class="table_hd clearfix">
            		<li class="li_280">文件名</li>
            		<li class="li_150">上传时间</li>
            		<li class="li_100">文件大小(M)</li>
            		<li class="li_100">操作</li>
				</ul>
			<c:if test="${filingProposalAttachmentList != null}" >
					<c:forEach var="filingProposalAttachment" items="${filingProposalAttachmentList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
            	<li class="li_280"><span name="checkedIds">${filingProposalAttachment.originalName}</span></li>
            	<li class="li_150"><span ><fmt:formatDate value="${filingProposalAttachment.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li class="li_100"><span>${filingProposalAttachment.size}</span></li>
            	<li class="li_100">
            		<span><a href="<%=path%>/dealer/declareManager/delDeclareAttachmentFile.vti?filingProposalId=<c:out value="${filingProposalAttachment.filingProposalId}" />&filingProposalAttachmentId=<c:out value="${filingProposalAttachment.id}" />">删除附件</a></span>
            	</li>
			</ul>
		</c:forEach>
		</c:if>
	</div><!--/table_list-->
	</br>
	<input type="button" onclick="saveAndDelFile()" value="确   定"  style="width: 74px; display:block; color: #fff;line-height: 22px;text-align: center;background: #10a0d9; margin:5px auto;"/>
</form>
</body>
</html>