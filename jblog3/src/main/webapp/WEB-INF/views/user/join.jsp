<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!doctype html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>JBlog</title>
<Link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/jblog.css">
<script
	src="${pageContext.servletContext.contextPath }/assets/js/jquery/jquery-1.9.0.js" ></script>
<script>
	$(function() {

		$("#blog-id").keydown(function() {
			$("#btn-checkemail").show();
			$("#img-checkemail").hide();
			document.getElementById('submitBtn').disabled = true;
		});
		$('#btn-checkemail')
				.click(
						function() {
							var blogId = $('#blog-id').val();
							$
									.ajax({
										url : "${pageContext.servletContext.contextPath}/user/api/checkIdDup?blogId="
												+ blogId,
										type : "get",
										dataType : "json",
										success : function(response) {
											if (response.result != 'success') {
												console.log(response);
												return;
											}
											if (response.data == true) {
												console.log(response);
												alert("아이디중복!");
												$("#blog-id").focus().val("");
												return;
											}
											$("#btn-checkemail").hide();
											$("#img-checkemail").show();
											$("#submitBtn").removeAttr(
													"disabled");

										},
										error : function(hxr, msg) {
											console.error("error:" + msg);
										}
									});
						});
	});
</script>
</head>
<body>
	<div class="center-content">
		<c:import url='/WEB-INF/views/includes/header.jsp' />
		<form class="join-form" id="join-form" method="post" action="">
			<label class="block-label" for="name">이름</label>
			<input id="name"name="name" type="text" value="">
			<spring:hasBindErrors name="userVo">
						<c:if test="${errors.hasFieldErrors('name') }">
						<p style="font-weight:bold; color:red; text-align:left; padding:0">
							<spring:message
									code="${errors.getFieldError( 'name' ).codes[0] }"
									text="${errors.getFieldError( 'name' ).defaultMessage }" />
						</p>
						</c:if>
			</spring:hasBindErrors>
			<label class="block-label" for="blog-id">아이디</label>
			<input id="blog-id" name="id" type="text"> 
			<input id="btn-checkemail" type="button" value="id 중복체크">

			<spring:hasBindErrors name="userVo">
						<c:if test="${errors.hasFieldErrors('id') }">
						<p style="font-weight:bold; color:red; text-align:left; padding:0">
							<spring:message
									code="${errors.getFieldError( 'id' ).codes[0] }"
									text="${errors.getFieldError( 'id' ).defaultMessage }" />
						</p>
						</c:if>
			</spring:hasBindErrors>
			<img id="img-checkemail" style="display: none;" src="${pageContext.request.contextPath}/assets/images/check.png">

			<label class="block-label" for="password">패스워드</label>
			<input id="password" name="password" type="password" />

			<spring:hasBindErrors name="userVo">
						<c:if test="${errors.hasFieldErrors('password') }">
						<p style="font-weight:bold; color:red; text-align:left; padding:0">
							<spring:message
									code="${errors.getFieldError( 'password' ).codes[0] }"
									text="${errors.getFieldError( 'password' ).defaultMessage }" />
						</p>
						</c:if>
			</spring:hasBindErrors>


			<input id = "submitBtn" type="submit" value="가입하기" disabled>

		</form>
	</div>
</body>
</html>
