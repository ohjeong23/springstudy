package com.gdu.prj09.utils;

import lombok.Data;

@Data
public class MyPageUtils {

  private int total; //전체 멤버의 개수 
  private int display; //한페이지에 몇개를 
  private int page;  // 이거 세개 서비스로 부터 받아옴 이거 세개로 맵을 만들고 리스트 처리를 해서
  private int begin; //위의 세개가 비긴하고 엔드를 구하기 위해 필요
  private int end;
   
//위는 한페이지에 들어가는거
  //아래는 페이지 목록

  private int pagePerBlock = 10;
  private int totalPage;
  private int beginPage;
  private int endPage;
  
  public void setPaging(int total, int display, int page) {
    
    this.total = total;
    this.display = display;
    this.page = page;
    
 //비긴 엔드 계산
    // begin = (page -1) * dispaly +1         1 -1 20 , 2 - 21 40 ,.....
    // end = begin + display -1;   

    begin = (page - 1) * display + 1;
    end = begin + display - 1;
    
  // total  display  total page 
    // 1000          20      1000/20 =50
    // 1001          20      1001.0/20.0 = 50.x = 50+1
// 나눠서 소수점이 나오면 그냥 올리기 .x 토탈 나누기 디스플레이 토탈 페이지를 올림처리      
     totalPage = (int)Math.ceil((double)total/display); // 만들지 말고 붙여서 써


    totalPage = (int) Math.ceil((double)total / display);
    beginPage = ((page - 1) / pagePerBlock) * pagePerBlock + 1;
    endPage = Math.min(totalPage, beginPage + pagePerBlock - 1);
    
  }
  // single page app spa 페이지 안바꾸는
  // String 반환 a 태그 간단하게 만들려함 
  // function getList 자바스크립트가 목록을 가져오게 비동기 ajax 처리 
  //클릭할때마다 자바스크립트의 펑션이 가져오게  a 태그가 같은 페이지의 스크립트를 동작시켜줘서 페이지를 바꾸게 ㅎ함
  //a 태그를 덕지덕지 붙이는게 아니라 페이지 안에서 돌아다니려면 스크립트로 동작 시킴 페이지 정보 변경
  
  public String getAsyncPaging() {
   
    StringBuilder builder = new StringBuilder("");
    
    // < 
    
    if(beginPage ==1) {
      builder.append("<a>&lt;</a>");
      
    }else {
      builder.append("<a href=\"javascript:fnPaging(" + (beginPage - 1) + ")\">&lt;</a>");
     
    }    
    // 1 2 3 4 5 6 7 8 9 10
    for(int p = beginPage; p <= endPage; p++){
     if(p==page) {
      builder.append("<a>"+ p + "</a>");       
     }else {
       
       builder.append("<a href=\"javascript:fnPaging("+p+")\">" + p +"</a>");
       
     }
    }
    
    // >
    
    
    if(endPage == totalPage) {
      builder.append("<a>&gt;</a>");
      
    }else {
      
      builder.append("<a href=\"javascript:fnPaging(" + (endPage + 1) + ")\">&gt;</a>");
    }

    
    
    return builder.toString();
    
  }
  
}







