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
<style>
.board{
width:200px;
height: 100px;
border:1px solid gray;
cursor:pointer;
}

</style>




</head>
<body>

  <div>
    <button type="button" id="btn-list">목록 갱신</button>
  </div>
  
  <hr>
  
  <div id="board-list"></div>
  
  <script>
  //비동기 요청으로 에이작 
  const fnBoardList = ()=>{
	  $('#btn-list').on('click',(evt)=>{
		  $.ajax({
			  /* 요청 보드대신 에이작 */
		    type: 'GET',
		    url: '${contextPath}/ajax1/list.do',
		    /* 응답  받아올 타입 제이슨으로 */
		    dataType:'json',// 목록세개가 레ㅣ스데이터안에 있음  이놈 배열임
		    success: (resData)=>{
		      //제이쿼리 반복문
		      //인덱스와 매개변수가 들어옴 배열데이터의
		      
		      const boardList = $('#board-list');
		      boardList.empty(); // 기존목록 싹 지우고
		      
	//반복문에 의해 새로운 목록을 추가
		     $.each(resData, (i,board)=>{
	              boardList.append('<div class="board"><div class="board-no">' + board.boardNo + '</div><div>' + board.title + '</div><div>' + board.contents + '</div></div>');
		    	                                                                                                // es6 템플 머시기 문법
		    	         
		      })
		    	/*응답 받았을때 함수가 실행되는 데 응답데이터가 석세스의 매개변수로 전달됨 괄호안에 응답데이터 */
		    }
		  })
		  
	  })
	    
  }
  
  fnBoardList();
  
  
  
  
  </script>
  <script>
   var detailWindow = null; // 너비 /높이/top-left
   
  
  const fnBoardDetail = ()=>{// 이벤트에 의해 만들어진 동적루트 원래 없음 
	 $(document).on('click','.board',(evt)=>{
//evt.target        : .board 내부 요소 중 실제로 클릭한 ㅎ요소
//evt.currentTarget : .board 자체
 		 // 보드의 자식중 보드넘버를 가진 자식의 
		const boardNo = $(evt.currentTarget).find('.board-no').text();
 		 //요청은 항상 신경쓰기
 		
 		$.ajax({
 			/* 요청 */
 			type: 'GET',
          url: '${contextPath}/ajax1/detail.do',
          data: 'boardNo=' + boardNo, // 데이터 요청 파라미터이다.
 			/* 응답 */
 			dataType: 'json',
          success: (resData)=>{
            if(detailWindow === null || detailWindow.closed) { 
 					
 			    detailWindow = window.open('','','width=300,height=200,top=100,left=100');	
 					detailWindow.document.write('<div>'+resData.boardNo +'</div>');
 					detailWindow.document.write('<div>'+resData.title +'</div>');
 					detailWindow.document.write('<div>'+resData.contents +'</div>');
 				}else{
 					alert('먼저 기존 창을 닫으세여.')
 					detailWindow.focus();
 				}
 				
 			}
 		})
	 })  
  }
  fnBoardDetail(); //호출
  
  
  
  </script>
  
  
  
  
</body>
</html>