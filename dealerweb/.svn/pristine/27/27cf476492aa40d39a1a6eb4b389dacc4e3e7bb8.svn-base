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
							accept:'png|jpg|bmp|jpeg|doc|docx|xls|xlsx|ppt|pptx|txt|avi', 
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
	
	function saveAndDelFile(){
		var delFile="";
		var allFiles=$("span[name=checkedIds]");
		var checkFiles=$("span[name=checkedIds].checkbox.text_over.active");
		if(null!=allFiles && null!=checkFiles && allFiles.length==checkFiles.length){
			var multiFileTitle=$('.MultiFile-title');
			if(undefined==multiFileTitle || null==multiFileTitle || multiFileTitle.length<=0){
				alert("删除已有全部附件时,同时需要上传附件");
				return ;
			}else{
				if(null!=checkFiles && checkFiles.length>=1){
					for(var i=0;i<checkFiles.length;i++){
						delFile+=$(checkFiles[i]).attr('value')+",";
					}
					delFile.substring(0, delFile.length-2);
				}
				$("#delFiles").val(delFile);
				
				$("#myPageForm").submit();
			}
		}else{
			
			var multiFileTitle=$('.MultiFile-title');
			if((undefined==multiFileTitle || null==multiFileTitle || multiFileTitle.length<=0) && (null==checkFiles || checkFiles.length<=0) ){
				alert("请选择附件或者要删除的附件");
				return ;
			}else{
				if(null!=checkFiles && checkFiles.length>=1){
					for(var i=0;i<checkFiles.length;i++){
						delFile+=$(checkFiles[i]).attr('value')+",";
					}
					delFile.substring(0, delFile.length-2);
				}
				$("#delFiles").val(delFile);
				$("#myPageForm").submit();
			}
		}
	}

</script>
<body>
<form id="myPageForm" action="<%=path%>/dealer/declareManager/uploadDelFile.vti" method="post"  enctype="multipart/form-data">
<input type="hidden" id="filingProposalId" name="filingProposalId" value="${filingProposalId}" />
<input type="hidden" id="delFiles" name="delFiles" value="" />
	<div>
		<fieldset>
			<legend>添加附件</legend>
			<!-- 注意这里的attach名字为固定，如果要控制上传的格式，则追加accept="doc|txt|jsp"  最大上传量maxlength="3" -->
			<input type="file" name="attach" id="multiFileId" class="text-input"/>
<!-- 			<input type="button" name="upFileBtn" id="upFileBtnId" class=" input_txt" value="上传" onclick="uploadFile();"/> -->
		</fieldset>
		<div id="multiFileId-list"></div>
	</div>
	<div class="table_list">
				<ul class="table_hd clearfix">
				    <li class="li_70"><span class="checkbox text_over" onclick="selectAll(this, 'checkedIds')">checkbox</span></li>
            		<li class="li_280">文件名</li>
            		<li class="li_150">上传时间</li>
            		<li class="li_100">文件大小(M)</li>
				</ul>
			<c:if test="${filingProposalAttachmentList != null}" >
					<c:forEach var="filingProposalAttachment" items="${filingProposalAttachmentList}" varStatus="statusObj">
			<ul class='table_bd clearfix ${statusObj.index%2==0?"even":"odd"}'>
			    <li class="li_70"><span class="checkbox text_over" name="checkedIds" value="${filingProposalAttachment.id}"></span></li>
            	<li class="li_280"><span>${filingProposalAttachment.originalName}</span></li>
            	<li class="li_150"><span ><fmt:formatDate value="${filingProposalAttachment.createTime}" type="both" pattern="yyyy-MM-dd HH:mm:ss"/></span></li>
            	<li class="li_100"><span>${filingProposalAttachment.size}</span></li>
            	<!--
            	<li class="li_100"><span><a href="<%=path%>/dealer/declareManager/delDeclareAttachmentFile.vti?filingProposalId=<c:out value="${filingProposalAttachment.filingProposalId}" />&filingProposalAttachmentId=<c:out value="${filingProposalAttachment.id}" />">删除附件</a></span>
            	</li>
            	-->
			</ul>
		</c:forEach>
		</c:if>
	</div><!--/table_list-->
	</br>
	<input type="button" onclick="saveAndDelFile()" value="确   定"  style="width: 74px; display:block; color: #fff;line-height: 22px;text-align: center;background: #10a0d9; margin:5px auto;"/>
</form>
</body>
</html>