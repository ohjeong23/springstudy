/*************************************************
 * 파일명 : member.js
 * 설  명 : 회원 관리 JavaScript
 * 수정일      수정자  Version   Function 명
 * -------------------------------------------
 * 2024.03.25  민경태  1.0       fnInit
 * 2024.03.25  민경태  1.1       fnRegisterMember
 * 2023.03.26  민경태  1.2       fnGetContextPath
 *************************************************/

// 전역변수 (vXXX)
var vPage = 1;
var vDisplay = 20;

// jQuery 객체 선언 (jqXXX)
var jqMembers = $('#members');
var jqTotal = $('#total');
var jqPaging = $('#paging');
var jqDisplay = $('#display');
var jqEmail = $('#email');
var jqName = $('#name');
var jqZonecode = $('#zonecode');
var jqAddress = $('#address');
var jqDetailAddress = $('#detailAddress');
var jqExtraAddress = $('#extraAddress');
var jqBtnInit = $('#btn-init');
var jqBtnRegister = $('#btn-register');
var jqBtnModify = $('#btn-modify');
var jqBtnRemove = $('#btn-remove');
var jqBtnSelectRemove = $('#btn-select-remove');

/*************************************************
 * 함수명 : fnInit
 * 설  명 : 입력란에 입력된 데이터를 모두 초기화
 * 인  자 : 없음
 * 사용법 : fnInit()
 * 작성일 : 2024.03.26
 * 작성자 : 이런저런 개발팀 민경태
 * 수정일     수정자  수정내용
 * --------------------------------
 * 2024.03.25 민경태  입력란 초기화
 *************************************************/
const fnInit = ()=>{
  jqEmail.val('');
  jqName.val('');
  $('#none').prop('checked', true);
  jqZonecode.val('');
  jqAddress.val('');
  jqDetailAddress.val('');
  jqExtraAddress.val('');
}

const fnGetContextPath = ()=>{
  const host = location.host;  /* localhost:8080 */
  const url = location.href;   /* http://localhost:8080/mvc/getDate.do */
  const begin = url.indexOf(host) + host.length;
  const end = url.indexOf('/', begin + 1);
  return url.substring(begin, end);
}

const fnRegisterMember = ()=>{
  $.ajax({
    // 요청
    type: 'POST',
    url: fnGetContextPath() + '/members',
    contentType: 'application/json',  // 보내는 데이터의 타입
    data: JSON.stringify({            // 보내는 데이터 (문자열 형식의 JSON 데이터)
      'email': jqEmail.val(),
      'name': jqName.val(),
      'gender': $(':radio:checked').val(),
      'zonecode': jqZonecode.val(),
      'address': jqAddress.val(),
      'detailAddress': jqDetailAddress.val(),
      'extraAddress': jqExtraAddress.val()
    }),
    // 응답
    dataType: 'json'  // 받는 데이터 타입
  }).done(resData=>{  // resData = {"insertCount": 2}
    if(resData.insertCount === 2){
      alert('정상적으로 등록되었습니다.');
      fnInit();
      fnGetMemberList();
    }
  }).fail(jqXHR=>{
    alert(jqXHR.responseText);
  })
}

const fnGetMemberList = ()=>{
  $.ajax({
    type: 'GET',
    url: fnGetContextPath() + '/members/page/' + vPage + '/display/' + vDisplay,
    dataType: 'json',
    success: (resData)=>{  /*
                              resData = {
                                "members": [
                                  {
                                    "addressNo": 1,
                                    "zonecode": '12345',
                                    "address": '서울시 구로구'
                                    "detailAddress": '디지털로',
                                    "extraAddress": '(가산동)',
                                    "member": {
                                      "memberNo": 1,
                                      "email": 'aaa@bbb',
                                      "name": 'gildong',
                                      "gender": 'none'
                                    }
                                  }, ...
                                ],
                                "total": 30,
                                "paging": '< 1 2 3 4 5 6 7 8 9 10 >'
                              }
                           */
      jqTotal.html('총 회원 ' + resData.total + '명');
      jqMembers.empty();
      $.each(resData.members, (i, member)=>{
        let str = '<tr>';
        str += '<td><input type="checkbox" class="chk-member" value="' + member.member.memberNo + '"></td>';
        str += '<td>' + member.member.email + '</td>';
        str += '<td>' + member.member.name + '</td>';
        str += '<td>' + member.member.gender + '</td>';
        str += '<td><button type="button" class="btn-detail" data-member-no="' + member.member.memberNo + '">조회</button></td>';
        str += '</tr>';
        jqMembers.append(str);
      })
      jqPaging.html(resData.paging);  //응답 데이터에 페이지가 있어야하는데 이걸 실어주는게 서비스
    },
    error: (jqXHR)=>{
      alert(jqXHR.statusText + '(' + jqXHR.status + ')');
    }
  })
}


//요청 본문 바디에 리퀘스트 바디에 요 내용이 포함되어있음 요거에 데이터를 실어낸다.
         
         //문자열 형식의 제이슨 형태의 데이터를 보냄   제이슨에서 서버로    제이슨을 문자열로 만들어주는건 JSON.stringify 얘가 제이슨 데이터를 묹다열로 만들어주는 제이에스 표준 문자열 메소드
         //우리가 알고 있는 디티오로는 이 데이터를 받을 수 없음. 디티오가 안된다면 map으로 받자.
         //디티오를 새로 만들어서하던가 맵으로 받던가 데이터를

// MyPageUtils 클래스의 getAsyncPaging() 메소드에서 만든 <a href="javascript:fnPaging()"> 에 의해서 실행되는 함수
const fnPaging = (p)=>{
  vPage = p;
  fnGetMemberList();
}

const fnChangeDisplay = ()=>{
  vDisplay = jqDisplay.val();
  fnGetMemberList();
}

const fnGetMemberByNo = (evt)=>{
  $.ajax({
    type: 'GET',
    url: fnGetContextPath() + '/members/' + evt.target.dataset.memberNo,
    dataType: 'json'
  }).done(resData=>{  /* resData = {
                           "addressList": [
                             {
                               "addressNo": 1,
                               "zonecode": "12345",
                               "address": "서울시 구로구 디지털로",
                               "detailAddress": "카카오",
                               "extraAddress": "(가산동)"
                             },
                             ...
                           ],
                           "member": {
                             "memberNo": 1,
                             "email": "email@email.com",
                             "name": "gildong",
                             "gender": "man"
                           }
                         }
                      */
    fnInit();
    if(resData.member !== null){
      jqEmail.val(resData.member.email);
      jqName.val(resData.member.name);
      $(':radio[value=' + resData.member.gender + ']').prop('checked', true);
    }
    if(resData.addressList.length !== 0){
      jqZonecode.val(resData.addressList[0].zonecode);
      jqAddress.val(resData.addressList[0].address);
      jqDetailAddress.val(resData.addressList[0].detailAddress);
      jqExtraAddress.val(resData.addressList[0].extraAddress);
    }
  }).fail(jqXHR=>{
    alert(jqXHR.statusText + '(' + jqXHR.status + ')');
  })
}

// 함수 호출 및 이벤트
fnInit();
jqBtnInit.on('click', fnInit);
jqBtnRegister.on('click', fnRegisterMember);
fnGetMemberList();
jqDisplay.on('change', fnChangeDisplay);
$(document).on('click', '.btn-detail', (evt)=>{ fnGetMemberByNo(evt); });