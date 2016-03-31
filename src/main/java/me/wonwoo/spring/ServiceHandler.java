package me.wonwoo.spring;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;

/**
 * Created by wonwoo on 2016. 3. 28..
 */
@ChannelHandler.Sharable
public class ServiceHandler extends ChannelInboundHandlerAdapter {

  Logger logger = LoggerFactory.getLogger(this.getClass());

  private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    channels.add(ctx.channel());
  }

  @Override
  public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    ByteBuf byteBuf = (ByteBuf) msg;
    logger.debug("message : {} ",byteBuf.toString(Charset.defaultCharset()));
    channels.writeAndFlush(msg);
  }
}
