package me.wonwoo;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.buffer.Unpooled;
import org.junit.Test;

import java.nio.ByteOrder;
import java.nio.charset.Charset;

import static org.junit.Assert.assertEquals;

/**
 * Created by wonwoo on 2016. 3. 27..
 */
public class ReadWriteByteBufferByNettyTest {

  @Test
  public void createUnpooledHeapBufferTest() {
    ByteBuf byteBuf = Unpooled.buffer(11);
//    testBuffer(byteBuf, false);
    testBuffer1(byteBuf, false);
  }

  @Test
  public void createUnpooledDirectBufferTest() {
    ByteBuf byteBuf = Unpooled.directBuffer(11);
//    testBuffer(byteBuf, true);
    testBuffer1(byteBuf, true);
  }

  @Test
  public void createPooledHeapBufferTest() {
    ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.heapBuffer(11);
//    testBuffer(byteBuf, false);
    testBuffer1(byteBuf, false);
  }

  @Test
  public void createPooledDirectBufferTest() {
    ByteBuf byteBuf = PooledByteBufAllocator.DEFAULT.directBuffer(11);
//    testBuffer(byteBuf, true);
    testBuffer1(byteBuf, true);
  }

  private void testBuffer(ByteBuf buf, boolean isDirect) {
    assertEquals(11, buf.capacity());

    assertEquals(isDirect, buf.isDirect());

    buf.writeInt(65537);
    assertEquals(4, buf.readableBytes());
    assertEquals(7, buf.writableBytes());

    assertEquals(1, buf.readShort());
    assertEquals(2, buf.readableBytes());
    assertEquals(7, buf.writableBytes());

    assertEquals(true, buf.isReadable());
    buf.clear();

    assertEquals(0, buf.readableBytes());
    assertEquals(11, buf.writableBytes());
  }

  private void testBuffer1(ByteBuf buf, boolean isDirect) {
    assertEquals(11, buf.capacity());
    assertEquals(isDirect, buf.isDirect());

    String sourceData = "hello world";
    buf.writeBytes(sourceData.getBytes());
    assertEquals(11, buf.readableBytes());
    assertEquals(0, buf.writableBytes());

    assertEquals(sourceData, buf.toString(Charset.defaultCharset()));

    buf.capacity(6);
    assertEquals("hello ", buf.toString(Charset.defaultCharset()));
    assertEquals(6, buf.capacity());

    buf.capacity(13);
    assertEquals("hello ", buf.toString(Charset.defaultCharset()));

    buf.writeBytes("world".getBytes());
    assertEquals(sourceData, buf.toString(Charset.defaultCharset()));

    assertEquals(13, buf.capacity());
    assertEquals(2, buf.writableBytes());
  }

  @Test
  public void pooledHeapBufferTest(){
    ByteBuf byteBuf = Unpooled.buffer();
    assertEquals(ByteOrder.BIG_ENDIAN, byteBuf.order());

    byteBuf.writeShort(1);
    byteBuf.markReaderIndex();

    assertEquals(1,byteBuf.readShort());

    byteBuf.resetReaderIndex();

    ByteBuf lettleEndianBuf = byteBuf.order(ByteOrder.LITTLE_ENDIAN);
    assertEquals(256,lettleEndianBuf.readShort());
  }
}
