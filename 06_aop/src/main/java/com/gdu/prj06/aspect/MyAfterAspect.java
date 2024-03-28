package com.gdu.prj06.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect

public class MyAfterAspect {

  // PointCut
  @Pointcut("execution (* com.gdu.prj06.controller.*Controller.*(..))")
  public void setPointCut() {}
  
  
  /*
   * Before Advice 메소드 작성 방법
   * 1. 반환타입 : void
   * 2. 메소드명 : 마음대로
   * 3. 매개변수 : JoinPoint 타입 객체
   */
  
  
  
  //Advice
  @After("setPointCut()")
  public void myAfterAdvice(JoinPoint joinPoint) {
    
    log.info("{}\n",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").format(new Date()));
    
  }

}
