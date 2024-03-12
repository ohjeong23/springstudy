package com.gdu.prj01.xml01;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
@AllArgsConstructor

public class Computer {
  
  
  private String model;
  private int price;
  private Calculator calculator;

}
