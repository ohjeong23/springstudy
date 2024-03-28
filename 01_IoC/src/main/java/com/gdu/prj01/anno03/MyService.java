package com.gdu.prj01.anno03;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data

public class MyService {
  
  private MyDao myDao;
  
  public void add() {
    myDao.add();
    System.out.println("MyService add()호출");
  }
  //서비스를 호출하는건 컨트롤러
  

}
