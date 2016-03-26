package me.wonwoo.evnethandler;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * Created by wonwoo on 2016. 3. 26..
 */
public class NettyEventHandler {
  public static void main(String[] args) {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          protected void initChannel(SocketChannel ch) throws Exception {
            ChannelPipeline p = ch.pipeline();
//            p.addLast(new LoggingHandler(LogLevel.INFO));
            p.addLast(new ServerHandler());
          }
        });

      Channel ch = b.bind(8080).sync().channel();
      ch.closeFuture().sync();

    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      workerGroup.shutdownGracefully();
      workerGroup.shutdownGracefully();
    }
  }
}
