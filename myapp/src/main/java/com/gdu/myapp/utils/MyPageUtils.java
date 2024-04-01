package com.gdu.myapp.utils;

import lombok.Data;

@Data
public class MyPageUtils {

  private int total;     // 전체 게시글 개수                      (DB에서 구한다.)
  private int display;   // 한 페이지에 표시할 게시글 개수        (요청 파라미터로 받는다.)
  private int page;      // 현재 페이지 번호                      (요청 파라미터로 받는다.)
  private int begin;     // 한 페이지에 표시할 게시글의 시작 번호 (계산한다.)
  private int end;       // 한 페이지에 표시할 게시글의 종료 번호 (계산한다.)

  private int pagePerBlock = 10;  // 한 블록에 표시할 페이지 링크의 개수      (임의로 결정한다.)
  private int totalPage;          // 전체 페이지 개수                         (계산한다.)
  private int beginPage;          // 한 블록에 표시할 페이지 링크의 시작 번호 (계산한다.)
  private int endPage;            // 한 블록에 표시할 페이지 링크의 종료 번호 (계산한다.)
  
  
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
  
  
  
 public String getPaging(String requestURI, String sort, int display) {
    
    StringBuilder builder = new StringBuilder();
    
    // <
    if(beginPage == 1) {
      builder.append("<div class=\"dont-click\">&lt;</div>");
    } else {
      builder.append("<div><a href=\"" + requestURI + "?page=" + (beginPage - 1) + "&sort=" + sort + "&display=" + display + "\">&lt;</a></div>");
    }
    
    // 1 2 3 4 5 6 7 8 9 10
    for(int p = beginPage; p <= endPage; p++) {
      if(p == page) {
        builder.append("<div><a class=\"current-page\" href=\"" + requestURI + "?page=" + p + "&sort=" + sort + "&display=" + display + "\">" + p + "</a></div>");
      } else {
        builder.append("<div><a href=\"" + requestURI + "?page=" + p + "&sort=" + sort + "&display=" + display + "\">" + p + "</a></div>");
      }
    }
    
    // >
    if(endPage == totalPage) {
      builder.append("<div class=\"dont-click\">&gt;</div>");
    } else {
      builder.append("<div><a href=\"" + requestURI + "?page=" + (endPage + 1) + "&sort=" + sort + "&display=" + display + "\">&gt;</a></div>");
    }
    
    return builder.toString();
    
  }
  
  
  
  
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







