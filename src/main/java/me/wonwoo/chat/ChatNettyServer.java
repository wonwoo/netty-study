package me.wonwoo.chat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.CharsetUtil;

/**
 * Created by wonwoo on 2016. 3. 27..
 */
public class ChatNettyServer {

  private static final ChatNettyServiceHandler SHARED = new ChatNettyServiceHandler();

  public static void main(String[] args) {
    EventLoopGroup bossGroup = new NioEventLoopGroup(1);
    EventLoopGroup workerGroup = new NioEventLoopGroup();

    try {
      ServerBootstrap b = new ServerBootstrap();
      b.group(bossGroup, workerGroup)
        .channel(NioServerSocketChannel.class)
        .childHandler(new ChannelInitializer<SocketChannel>() {
          protected void initChannel(SocketChannel ch) throws Exception {
            ch.pipeline()
              .addLast(new StringDecoder(CharsetUtil.UTF_8), new StringEncoder(CharsetUtil.UTF_8))
              .addLast(new ChatNettyMessageCodec(), new LoggingHandler(LogLevel.INFO))
              .addLast(SHARED);
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
