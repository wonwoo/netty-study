package me.wonwoo.chat;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

/**
 * Created by wonwoo on 2016. 3. 27..
 */
public class ChatNettyMessageCodec extends MessageToMessageDecoder<String> {

  @Override
  protected void decode(ChannelHandlerContext ctx, String msg, List<Object> out) throws Exception {
    String command = msg.substring(0, 2);
    String message = msg.substring(2, msg.length() - 1) + "\n";
    Herder herder = new Herder();
    herder.setCommand(command);
    out.add(new Message(herder, message));
  }
}
