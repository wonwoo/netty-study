package me.wonwoo.chat;

import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by wonwoo on 2016. 3. 27..
 */
@ChannelHandler.Sharable
public class ChatNettyServiceHandler extends SimpleChannelInboundHandler<Message> {

  private final ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
  final AttributeKey<Integer> id = AttributeKey.newInstance("id");
  private static final AtomicInteger count = new AtomicInteger(0);

  @Override
  public void channelActive(ChannelHandlerContext ctx) throws Exception {
    int value = count.incrementAndGet();
    ctx.channel().attr(id).set(value);
    ctx.writeAndFlush("your id : "+ String.valueOf(value) + "\n");
    channels.writeAndFlush(String.valueOf(value) + " join \n");
    channels.add(ctx.channel());
  }

  @Override
  public void channelInactive(ChannelHandlerContext ctx) throws Exception {
    ctx.channel().attr(id).remove();
    channels.remove(ctx.channel());
  }

  @Override
  public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
    ctx.flush();
  }

  protected void channelRead0(ChannelHandlerContext ctx, Message msg) throws Exception {

    if ("10".equals(msg.getHerder().getCommand())) {
      channels.writeAndFlush(ctx.channel().attr(id).get() + " :" + msg.getText());;
    } else if ("20".equals(msg.getHerder().getCommand())) {

      String text = msg.getText();
      String substring = text.substring(0, 2).trim();
      String message = text.substring(2, text.length());

      channels.stream().filter(i -> i.attr(id).get() == Integer.parseInt(substring))
        .forEach(i -> i.writeAndFlush(i.attr(id).get() + " : " +message));

      ctx.writeAndFlush(ctx.channel().attr(id).get() + " : " + message);

    } else if("30".equals(msg.getHerder().getCommand())){
      ChannelFuture channelFuture = ctx.writeAndFlush(ctx.channel().attr(id).get() + " : " + " bye!");
      channelFuture.addListener(ChannelFutureListener.CLOSE);
    }
  }

  @Override
  public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
    cause.printStackTrace();
    ctx.close();
  }
}
