package me.wonwoo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by wonwoo on 2016. 3. 27..
 */
public class CreateByteBufferNettyTest {

  @Test
  public void createUnpooledHeapBufferTest(){
    ByteBuf byteBuf = Unpooled.buffer(11);

    testBuffer(byteBuf,false);
  }

  @Test
  public void createUnpooledDirectBufferTest(){
    ByteBuf byteBuf = Unpooled.directBuffer(11);
    testBuffer(byteBuf,true);
  }

  @Test
  public void createPooledHeapBufferTest(){
    ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(11);
    testBuffer(byteBuf, false);
  }

  @Test
  public void createPooledDirectBufferTest(){
    ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(11);
    testBuffer(byteBuf, true);
  }

  private void testBuffer(ByteBuf buf, boolean isDirect){
    assertEquals(11, buf.capacity());

    assertEquals(isDirect, buf.isDirect());

    assertEquals(0, buf.readableBytes());
    assertEquals(11,buf.writableBytes());
  }
}
