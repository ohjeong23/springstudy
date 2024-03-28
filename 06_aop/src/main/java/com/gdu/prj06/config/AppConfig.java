package com.gdu.prj06.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import com.gdu.prj06.aspect.MyAfterAspect;
import com.gdu.prj06.aspect.MyAroundAspect;
import com.gdu.prj06.aspect.MyBeforeAspect;
//여기 빈들만 되는게 아니라 그냥 들어가 잇어서 root 에 안넣어도 됨
@EnableAspectJAutoProxy
@Configuration
public class AppConfig {
  
  @Bean
  public MyAroundAspect myAroundAspect() {
    return new MyAroundAspect();
  }
  //생성자가 안만들어져 있었으니까 
  //컨포넌트를 대체해서 ,같이 들어오는 이너테이션이 필요하다.
  
  @Bean
  public MyBeforeAspect myBeforeAspect() {
    return new MyBeforeAspect();
  }
  
  @Bean
  public MyAfterAspect myAfterAspect() {
    return new MyAfterAspect();
  }
//스프링이 알게 설정하는 역할임 ioc 컨테이너가 정보를 활용해서 
}
