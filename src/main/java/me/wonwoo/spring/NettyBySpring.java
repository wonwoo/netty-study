package me.wonwoo.spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * Created by wonwoo on 2016. 3. 28..
 */
public class NettyBySpring {

  public static void main(String[] args) {
    try(AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class)){
      context.registerShutdownHook();
      NettyServer nettyServer = context.getBean(NettyServer.class);
      nettyServer.start();
    }
  }
}
