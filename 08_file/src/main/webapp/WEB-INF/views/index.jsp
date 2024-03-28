<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<script src="https://code.jquery.com/jquery-3.7.1.min.js" integrity="sha256-/JqT3SQfawRcv/BIHPThkBvs0OEvtFFmqPF/lYI/Cxo=" crossorigin="anonymous"></script>
</head>
<body>
<!-- 1. submit 
        post랑 multipart
     2.ajax -->
  <div>
    <form action="${contextPath}/upload1.do"
          method="post"
          enctype="multipart/form-data"> <!--여긴 파일 첨부할때만 씀 멀티파트리졸버 객체가 필요한데 이걸 만들어야함  본문에 파일을 실어서 보내겠다. 스프링은 멀티파크 리졸버라는 빈을 찾으러감 멀티파트 담당-->
      <div>
        <input type="file" name="files" class="files" accept="image/*" multiple>
      </div>
      <div>
        <input type="text" name="writer" placeholder="작성자">
      </div>
      
      <div>
        <button type="submit">전송</button>
      </div>
    </form> <!--  hTtpSErvletRequest에서 input type="file" 을 빼는건 불가능하다. -->
  </div>
  
  <h3>첨부 파일 목록</h3>
  <div id="file-list"></div>
  
  <hr>
  
  <div>
  
    <!-- newFormData() newFormData(form) form 없이 
         new FormData().append() 
         비어있는  폼을 만들어서 어팬드로 넣어준다.-->
  
    <div>
      <input type="file" id="input-files" class="files" multiple>
    </div>
    <div>
      <input type="text" id="input-writer" placeholder="작성자">      
    </div>
    <div>
      <button type="button" id="btn-upload">전송</button>
    </div>
  
  </div>
  
  <script type="text/javascript">
  
    const fnFileCheck = ()=>{
      $('.files').on('change', (evt)=>{//j쿼리는 변수처럼  새로운 첨부가 들어오면 체인지, 이벤트 리스너
        const limitPerSize = 1024 * 1024 * 10; //10 메가 크기 제한
        const limitTotalSize = 1024 * 1024 * 100;
        let totalSize = 0;
        const files = evt.target.files; //evt.target.files 을 데이터 파일즈로 쓰겠다
        const fileList = document.getElementById('file-list');
        fileList.innerHTML ='';
        for(let i = 0; i < files.length; i++){
          if(files[i].size > limitPerSize){
            alert('각 첨부 파일의 최대 크기는 10MB입니다.'); y
            evt.target.value = '';
            fileList.innerHTML = '';
            return; // 이벤틑 함수 종료
          }
          totalSize += files[i].size;
          if(totalSize > limitTotalSize){
            alert('전체 첨부 파일의 최대 크기는 100MB입니다.');
            evt.target.value = '';
            fileList.innerHTML = '';
            return;
          }
          fileList.innerHTML += '<div>' + files[i].name + '</div>';
        }
      })
    }
    
    const fnAfterInsertCheck = ()=>{
        const insertCount = '${insertCount}';
        if(insertCount !== ''){
          if(insertCount === '1'){
            alert('저장되었습니다.');
          } else {
            alert('저장실패했습니다.');
          }
        }
      }
      //비동기 처리
      const fnAsyncUpload = () => {
    	  const inputFiles = document.getElementById('input-files');
    	  const inputWriter = document.getElementById('input-writer');
    	  let formData = new FormData();
    	  for(let i=0;i<inputFiles.files;i++){
    		  formData.append('files',inputFiles.files[i]);
    	  } //폼 데이터 실어
    	  formData.append('writer',inputWriter);
    	  //비동기 처리 가능한 자바스크립트 함수
    	  fetch('${contextPath}/upload2.do', {
    		  method:'POST',
    		  body: formData
    		  
    	  })
    	    .then(response=>response.json())
    	    //json 받은 리스판스데이터
    	    .then(resData=>{ /* resData = {"success": 1 } 또는 {"success": 0}*/
             if(resData.success === 1){
            	 alert('저장되었습니다.');
             } else{
            	 alert('저장실패했습니다.');
             }
    	    	
    	    })
      }
    
      fnFileCheck();
      fnAfterInsertCheck();
      document.getElementById('btn-upload').addEventListener('click',fnAsyncUpload);
    
    </script>
  <!-- 어떤식으로 데이터를 받고 어떻게 바뀌는지가 더 중요하다. 데이터 흐름이나 받는거 그런게 중요함. -->
  
</body>
</html>