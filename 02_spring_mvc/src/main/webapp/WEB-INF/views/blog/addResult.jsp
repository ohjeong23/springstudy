<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<!-- blogDto에 커맨드객체를 저장해서 안나옴 blog로 바꿔줘야함
  <div>${blogDto.blogNo}번 게시글</div>
  <div>${blogDto.title}</div> -->
  
  <div>${blogDto.blogNo}번 게시글</div>
  <div>${blogDto.title}</div>
  
  <hr>
  
  <div>${blog.blogNo}번 게시글</div>
  <div>${blog.title}</div>


</body>
</html>