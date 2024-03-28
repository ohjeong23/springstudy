<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>

</head>
<body>

  <div>
    <button type="button" onclick="fnBoardList()">목록 갱신</button>
  </div>
  
 
  
  <hr>
  
  <div id="board-list"></div>
  
  
  <script>
  
    const fnBoardList = ()=>{
    	const options = {
    		//객체로 만드는거
    		method : 'GET',
    		cache  : 'no-cache'
    			
    	}
  
    	//함수 선언,함수 표현식 2가지 방법
    	// 자바 스트립으로 비동기 연습 전역함수
    	//const myPromise = fetch('${contextPath}/ajax3/list.do', options);
    	//myPromise.then()
    	//둘이 같음.위에꺼랑 밑에꺼랑 
    	fetch('${contextPath}/ajax3/list.do', options)
    	     .then(response=>response.json())
    	     .then(resData=>{
    	    	 //console.log(resData); //받아쓰던 데이터 //제이슨 데이터오면 그냥 댄 호출 두번 
    	       const boardList = document.getElementById('board-list');
    	    	 boardList.innerHTML='';
    	         let result = '<div class="board-wrap">';

    	       resData.forEach(board=>{
    	           result += '<div class="board" data-board-no="' + board.boardNo + '"><div>' + board.boardNo + '</div><div>' + board.title + '</div><div>' + board.contents + '</div></div>';
  
    	    	   
    	       })
    	        result += '</div>';// success 안쓰는 방법 에이작이 끝나고 뒤에 done으로 바로 붙여 버리기
    	        boardList.innerHTML = result;
    	     
    	     
    	     })
    	     //매개변수의 괄호 지울수있음.응답이 응답형태로 온다
    	//프라미스가 반환된다. 전체 결과가 또 프라미스가 됨 
    }
  
  </script>
  
  
  
  <script>
  
  const fnBoardDetail =()=>{
	   
	  const boardList = document.getElementsByClassName('board');  //클래스 3개 우리의
	  console.log(boardList.length);
	  for(let i =0; i< boardList.length; i++){
		  boardList[i].addEventListener('click',(evt)=>{
			  const boardNo = evt.currentTarget.dataset.boardNo;
		    fetch('${contextPath}/ajax3/detail.do?boardNo='+boardNo,{method:'GET',cache:'no-cache'})
		       .then(response => response.jsopn())//제이슨 빠진곳에 또 프라미스 빠져서 두번써야함
		       .then(resData=>{
		              console.log(resData);
		            })
		        })
		      }
		      
		    }
  
  </script>
  
  
  
  
  
</body>
</html>