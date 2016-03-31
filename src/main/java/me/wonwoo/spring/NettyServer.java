package me.wonwoo.spring;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;

/**
 * Created by wonwoo on 2016. 3. 28..
 */
@Component
public class NettyServer {

  @Value("${boss.thread.count}")
  private int bossCount;

  @Value("${worker.thread.count}")
  private int workerCount;

  @Autowired
  private InetSocketAddress port;

  private static final ServiceHandler SERVICE_HANDLER = new ServiceHandler();

  public void start() {
    EventLoopGroup bossGroup = new NioEventLoopGroup(bossCount);
    EventLoopGroup workerGroup = new NioEventLoopGroup(workerCount);

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          @Override
          protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline pipeline = ch.pipeline();
            pipeline.addLast(SERVICE_HANDLER);
          }
        });

      ChannelFuture channelFuture = b.bind(port).sync();
      channelFuture.channel().closeFuture().sync();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      bossGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}
