<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>

<!-- include libraries(jquery, bootstrap) -->
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
<link href="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/css/bootstrap.min.css" rel="stylesheet">
<script src="https://stackpath.bootstrapcdn.com/bootstrap/3.4.1/js/bootstrap.min.js"></script>

<!-- include summernote css/js -->
<link rel="stylesheet" href="${contextPath}/resources/summernote-0.8.18-dist/summernote.min.css">
<script src="${contextPath}/resources/summernote-0.8.18-dist/summernote.min.js"></script>
<script src="${contextPath}/resources/summernote-0.8.18-dist/lang/summernote-ko-KR.min.js"></script>

<style>
@import url('https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css');
@import url('https://fonts.googleapis.com/css2?family=Noto+Sans+KR:wght@100..900&display=swap')
  * { 
  font-family: "Noto Sans KR", sans-serif;
  font-weight: 400;
}
  
</style>


</head>
<body>

  <h1>Sign Up</h1>
  <!-- 정규식 처리를 위해 -->
  <form method="POST"
        action="${contextPath}/user/signup.do" 
        id="frm-signup" >
    <div class="mb-3">
      <label for="email">아이디</label>
      <input type="text" id="email" name="email" placeholder="example@example.com">
      <button type="button" id="btn-code">인증코드받기</button>
      <div id="msg-email"></div>
    </div>
    <div class="mb-3">
      <input type="text" id="code" placeholder="인증코드 입력" disabled>
      <button type="button" id="btn-verify-code" class="btn btn-primary">인증하기</button>
    </div>
  
  
  </form>
  
 <script>
 
 const fnGetContextPath = ()=>{
	  const host = location.host;  /* localhost:8080 */
	  const url = location.href;   /* http://localhost:8080/mvc/getDate.do */
	  const begin = url.indexOf(host) + host.length;
	  const end = url.indexOf('/', begin + 1);
	  return url.substring(begin, end);
	}
 
 const fnCheckEmail = () => {
	 
	
	 let email = document.getElementById('email');
	 let regEmail = /^[A-Za-z0-9_]{2,}@[A-Za-z]+(\.[A-Za-z]{2,6}){1,2}$/; //시작과^ 끝$
	 if(!regEmail.test(email.value)){
		 alert('이메일 형식이 올바르지 않습니다.');  //포스트 방식으로 제이슨을 보낸다. 그러면 받는 쪽에선 애너테이션 리퀘스트 바디로 받는다. 받을때 쓸 실제 도구는 맵을 쓰기. 리퀘스트 바디와 맵으로 데이터를 받을 것 . 데이터의 흐름이 중요하다. 어떻게 보내고 어떻게 받을 거다 .이 연결고리가 중요하다.상호 연결해주는게 잭슨 라이브러리
		 return;
	 }
	 //fetch(주소,{옵션});  
	 fetch(fnGetContextPath()+'/user/checkEmail.do',{
		 method:'POST',
		 headers:{
			 'Content-Type':'application/json'
		 }, 
		 body:JSON.stringify({
			 'email':email.value
		 }) //이메일을 제이슨으로 만든거 
		 
	 })
	 .then(response=>response.json()) //받아온 응답에서 제이슨만 꺼냄
	 //((response)=> {return response.json();})
   .then((resData)=>{
	  if(resData.enableEmail){
		 fetch(fnGetContextPath()+'/user/sendCode.do',{ 
			   method:'POST',
		     headers:{
		         'Content-Type':'application/json'
		       }, 
		       body:JSON.stringify({
		         'email':email.value
		       })
		 }); // 메일 보내기 
	  } else {
		  document.getElementById('msg-email').innerHTML = '이미 사용 중인 이메일입니다.';
		  return ;
	  }
  })
 }
  
 document.getElementById('btn-code').addEventListener('click',fnCheckEmail);
 
 
 
 
 //promise 가 꼭 필요할때 
//에이작 호출이 연속해서 진행되는 경우 에이작후 전 프라미스 필요
 //단순하게 코드를 적어주는 순서대로 동작할 경우 비동기는 원래 순서대로 아니니까 요청응답요청응답 비동기 동작하게 순서대로 
 //두번이상일때 요청 응답으로 구성되게 프라미스가 필요 
 //근데 할 필요없이 패치를 그냥 쓰는게 편함. 리졸브 리젝트 
 // ex6 프라미스 문제가 많이 있다. 
 
 
 
 </script>




  
</body>
</html>