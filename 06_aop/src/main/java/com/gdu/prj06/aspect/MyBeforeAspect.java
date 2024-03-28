package com.gdu.prj06.aspect;

import java.util.Arrays;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Aspect

public class MyBeforeAspect {

  //PointCut
  @Pointcut("execution (* com.gdu.prj06.controller.*Controller.*(..))")
  public void setPointCut() {}
  
  
  //Advice
  /*
   * Before Advice 메소드 작성 방법
   * 1. 반환타입 : void
   * 2. 메소드명 : 마음대로
   * 3. 매개변수 : JoinPoint 타입 객체
   */
  @Before("setPointCut()")
  public void myBeforeAdvice(JoinPoint joinPoint) {
    
     //요청 메소드/ 주소/ 파라미터 로그 남기기
    
    ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
    //요청에 관련된 애트리뷰트들이 쫙 나오는데 이중에서 리퀘스트가 필요
    HttpServletRequest request =  servletRequestAttributes.getRequest();
  // 파라미터 맵의 반환타입은 스트링 배열
    Map<String, String[]> params = request.getParameterMap();
    
    String str = "";
    if(params.isEmpty()) {
      str += "No Parameter";
      
    }else {
      for(Entry<String, String[]> entry : params.entrySet()) {
        str += entry.getKey() + ":" + Arrays.toString(entry.getValue()) + " ";  //겟벨류 배열임 그래서 어레이로
      }
    }
      
    log.info("{} | {}",request.getMethod(),request.getRequestURI());
    log.info("{}",str);
    
  
  }
}