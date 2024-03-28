package com.gdu.prj.dto;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class BoardDto {
  private int stu_no;
  private String name;
  private int kor;
  private int eng;
  private int math;
  private int ave;
  private String mark;
}