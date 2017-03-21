package me.wonwoo.client;

import java.io.*;
import java.net.Socket;

/**
 * Created by wonwoo on 2016. 3. 26..
 */
public class SocketClient {
  public static void main(String[] args) {
    socket();
  }
  private static final int port = 8080;
  private static final String host = "127.0.0.1";
  private static void socket() {
    try {

      Socket sock = new Socket(host, port);
      BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));

      OutputStream out = sock.getOutputStream();
      InputStream in = sock.getInputStream();
      PrintWriter pw = new PrintWriter(new OutputStreamWriter(out));
      BufferedReader br = new BufferedReader(new InputStreamReader(in));

      String line;

      while ((line = keyboard.readLine()) != null) {
        pw.println(line);
        pw.flush();
        String echo = br.readLine();
        System.out.println(echo);
      }

      pw.close();
      br.close();
      sock.close();
    } catch (Exception e) {
      System.out.println(e);
    }
  }
}
