package me.wonwoo.java;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by wonwoo on 2016. 3. 26..
 */
public class BlockingServer {
  public static void main(String[] args) throws IOException {
    new BlockingServer().run();
  }

  private void run() throws IOException {
    ServerSocket serverSocket = new ServerSocket(8080);
    System.out.println("접속 대기중");
    while (true) {
      Socket socket = serverSocket.accept();
      System.out.println("클라이언트와 연결");
      OutputStream outputStream = socket.getOutputStream();
      InputStream inputStream = socket.getInputStream();

      while (true) {
        try {
          int request = inputStream.read();
          System.out.print((char) request);
          outputStream.write(request);
        } catch (IOException e) {
          break;
        }
      }
    }
  }
}
