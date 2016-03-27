package me.wonwoo;

import org.junit.Test;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.IntBuffer;

import static org.junit.Assert.assertEquals;

/**
 * Created by wonwoo on 2016. 3. 27..
 */
public class CreateByteBufferTest {

  @Test
  public void createTest() {
    CharBuffer charBuffer = CharBuffer.allocate(11);
    assertEquals(11, charBuffer.capacity());
    assertEquals(false, charBuffer.isDirect());

    ByteBuffer directBuffer = ByteBuffer.allocateDirect(11);
    assertEquals(11, directBuffer.capacity());
    assertEquals(true, directBuffer.isDirect());

    int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 0, 0};
    IntBuffer intBuffer = IntBuffer.wrap(array);
    assertEquals(11, intBuffer.capacity());
    assertEquals(false, intBuffer.isDirect());
  }

  @Test
  public void byteBufferTest1() {
    ByteBuffer byteBuffer = ByteBuffer.allocate(11);
    System.out.println(String.format("바이트 버퍼 초기 값 : %s", byteBuffer));
    assertEquals(0, byteBuffer.position());

    byte[] source = "Hello world".getBytes();
    byteBuffer.put(source);
    System.out.println(String.format("11 바이트 기록 후 : %s", byteBuffer));
    assertEquals(11, byteBuffer.position());
  }

  @Test
  public void byteBufferTest2() {
    ByteBuffer byteBuffer = ByteBuffer.allocate(11);
    System.out.println(String.format("바이트 버퍼 초기 값 : %s", byteBuffer));
    assertEquals(0, byteBuffer.position());

    byte[] source = "Hello world!".getBytes();

    for (byte item : source) {
      byteBuffer.put(item);
      System.out.println(String.format("바이트 버퍼 초기 값 : %s", byteBuffer));
    }
  }

  @Test
  public void byteBufferTest3() {
    ByteBuffer byteBuffer = ByteBuffer.allocate(11);
    System.out.println(String.format("바이트 버퍼 초기 값 : %s", byteBuffer));

    byteBuffer.put((byte) 1);
    assertEquals(0, byteBuffer.get());
    assertEquals(2, byteBuffer.position());
    System.out.println(String.format("바이트 버퍼 값 : %s", byteBuffer));
  }

  @Test
  public void byteBufferTest4() {
    ByteBuffer byteBuffer = ByteBuffer.allocate(11);
    System.out.println(String.format("바이트 버퍼 초기 값 : %s", byteBuffer));

    byteBuffer.put((byte) 10);
    byteBuffer.put((byte) 20);
    assertEquals(2, byteBuffer.position());

    byteBuffer.rewind();
    assertEquals(0, byteBuffer.position());

    assertEquals(10, byteBuffer.get());
    assertEquals(1, byteBuffer.position());

    System.out.println(String.format("바이트 버퍼 값 : %s", byteBuffer));
  }

  @Test
  public void writeTest() {
    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(11);

    System.out.println(String.format("바이트 버퍼 초기 값 : %s", byteBuffer));

    assertEquals(0, byteBuffer.position());
    assertEquals(11, byteBuffer.limit());

    byteBuffer.put((byte) 10);
    byteBuffer.put((byte) 20);
    byteBuffer.put((byte) 30);
    byteBuffer.put((byte) 40);

    assertEquals(4, byteBuffer.position());
    assertEquals(11, byteBuffer.limit());

    byteBuffer.flip();

    assertEquals(0, byteBuffer.position());
    assertEquals(4, byteBuffer.limit());

    System.out.println(String.format("바이트 버퍼 값 : %s", byteBuffer));
  }

  @Test
  public void readTest() {
    byte[] tempArray = {1, 2, 3, 4, 5, 0, 0, 0, 0, 0, 0};
    ByteBuffer byteBuffer = ByteBuffer.wrap(tempArray);
    assertEquals(0, byteBuffer.position());
    assertEquals(11, byteBuffer.limit());

    assertEquals(1, byteBuffer.get());
    assertEquals(2, byteBuffer.get());
    assertEquals(3, byteBuffer.get());
    assertEquals(4, byteBuffer.get());
    assertEquals(4, byteBuffer.position());
    assertEquals(11, byteBuffer.limit());

    byteBuffer.flip();

    assertEquals(0, byteBuffer.position());
    assertEquals(4, byteBuffer.limit());

    byte firstByte = byteBuffer.get(0);
    System.out.println(firstByte);
    assertEquals(0,byteBuffer.position());

    byte getFirstByte = byteBuffer.get();
    System.out.println(getFirstByte);

    assertEquals(firstByte, getFirstByte);

    assertEquals(1,byteBuffer.position());

    System.out.println(String.format("바이트 버퍼 값 : %s", byteBuffer));
  }
}
