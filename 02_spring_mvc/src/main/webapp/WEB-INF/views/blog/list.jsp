<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>블로그 목록</title>
<style type="text/css">
  .blog {
    width: 200px;
    cursor: pointer;
    background-color: yellow;
  }
</style>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>

  <c:forEach items="${blogList}" var="blog" varStatus="vs">
    <div class="blog">
      <span>${vs.index}</span>
      <span class="blog-no">${blog.blogNo}</span>
      <span>${blog.title}</span>
    </div>
  </c:forEach>
<script type="text/javascript">

  $('.blog').on('click',function(evt){	  
	  // 자바 스크립트 객체 this 제이쿼리로 바꿈
	  let blogNo = $(this).find('.blog-no').text(); //클릭한 디아이브이의 하위 블로그 넘버 클릭한 디아이브의
	  location.href ='${contextPath}/blog/detail.do?blogNo=' + blogNo;
	  
	  //블로그가 세개이기때문에 배열임 ('.blog') 그래서 제이쿼리로 하는거임 일반 자바면 제이쿼리 활용하면 바로 이벤트로 감  배열의 이벤트는 제이쿼리가 편함
	  // this 로 하면  눌렀을때 글씨는 스펜인데 다른 빈칸누르면 디아이브여서 디스로 바꿈 그러면 다 디아이브이
  });

</script>


</body>
</html>