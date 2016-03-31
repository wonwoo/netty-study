package me.wonwoo.spring;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

import java.net.InetSocketAddress;

/**
 * Created by wonwoo on 2016. 3. 28..
 */
@Configuration
@ComponentScan("me.wonwoo.spring")
@PropertySource("classpath:server.properties")
@Getter
public class SpringConfig {

  @Value("${tcp.port}")
  private int port;

  @Bean
  public InetSocketAddress tcpPort() {
    return new InetSocketAddress(port);
  }

  @Bean
  public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
    return new PropertySourcesPlaceholderConfigurer();
  }
}
