<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Welcome</title>
</head>
<body>
  <%-- 정적 자원 확인 webapp--%>
  <img src="${contextPath}/resources/image/404.jpg"width="200px">
 <%-- MyController2 --%>
  <div>
    <a href="${contextPath}/board/list.do">board 목록</a>
  </div>

 <%-- MyController3 --%>
 <div>
   <a href="${contextPath}/article/detail1.do?article_no=10">article 상세</a>
   <a href="${contextPath}/article/detail2.do">article 상세2</a>
   <a href="${contextPath}/article/detail3.do?article_no=10">article 상세3</a>
 </div>
 
 <%--MyController4 --%>
 <div>
   <a href="${contextPath}/blog/list.do">블로그 목록</a>
 </div>
 
 <%-- MyController5 --%>
 <div>
    <a href="${contextPath}/faq/add.do">faq 등록</a>
    <a href="${contextPath}/faq/modify.do">faq 수정</a>
 </div>
 
 <%-- MyController6 --%>
 <div>
 <c:if test="${sessionScope.user == null}"> <!-- 세션에 저장된 유저 -->
     <a href="${contextPath}/user/login1.do">로그인1</a>
     <a href="${contextPath}/user/login2.do">로그인2</a>
     
 </c:if>
 <c:if test="${sessionScope.user != null}">
<span><a href="${contextPath}/user/mypage.do">${sessionScope.user.userEmail}님 반갑습니다.</span>
 <a href="${contextPath}/user/logout1.do">로그아웃1</a>
 <a href="${contextPath}/user/logout2.do">로그아웃2</a>
 </c:if>
 </div>
 
 
 

</body>
</html>