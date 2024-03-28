package com.gdu.prj06.aspect;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect
public class MyAroundAspect {
  
  // PointCut : 언제 동작하는가?
  //모든 컨트롤러의 모든 메소드 (클래스는 모든 컨트롤러) 클래스,* 모든 메소드 모든 매개변수
  @Pointcut("execution (* com.gdu.prj06.controller.*Controller.*(..))")
  public void setPointCut() {}
  
  
  
  
  //Spring Container에 담아야함 (<bean>, @Bean)=>이미 만들어진 클래스들이 존재할때 씀 들의 이미 만들어진 클래스의 객체생성을할때 쓰고
  //@Componenet는 내가 만든 클래스를 스프링 컨테이너에 담겠다.
  // 셋중 하나로
  
  // Advice   : 무슨 동작을 하는가?
  //위의 메소드 호출, 셋포인트컷에서 동작한다. 어라운드
  
    /*
   * Around Advice 메소드 작성 방법
   * 1. 반환타입 : Object 
   * 2. 메소드명 : 마음대로
   * 3. 매개변수 : ProceedingJoinPoint 타입 객체
   * 어드바이스에서 전체를 받아오고 
   */
@Around("setPointCut()")
  public Object myAroundAdvice(ProceedingJoinPoint proceedingJoinPoint) throws Throwable{
  //결과 리턴 어드바이스 동작시점  ,   예외 외면
   
  log.info("{}","-".repeat(80));                                                     //동작 이전(@Before 이전) 
  
  Object obj = proceedingJoinPoint.proceed();                                   // 동작 시점 
    //프로시드 이후에 작성하면 이후 전에 작성하면 전에 
  log.info("{}\n","-".repeat(80));       // 동작 이후(@After 이후)
  
  return obj;
    
  }
 
}
