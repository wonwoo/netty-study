package me.wonwoo.java;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.*;

/**
 * Created by wonwoo on 15. 12. 5..
 */
public class NonBlockingServer {

    private Map<SocketChannel, List<byte[]>> keepDataTrack = new HashMap<>();
    private ByteBuffer buffer = ByteBuffer.allocate(2*1024);

    private void startEchoServer() {

        try(Selector selector = Selector.open(); ServerSocketChannel serverSocketChannel = ServerSocketChannel.open()){
            if((serverSocketChannel.isOpen()) && selector.isOpen()){
                serverSocketChannel.configureBlocking(false);
                serverSocketChannel.bind(new InetSocketAddress(8888));

                serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                System.out.println("접속 대기 중");

                while(true) {
                    selector.select();
                    Iterator<SelectionKey> keys = selector.selectedKeys().iterator();
                    while (keys.hasNext()) {
                        SelectionKey key = keys.next();
                        keys.remove();

                        if (!key.isValid()) {
                            continue;
                        }
                        if (key.isAcceptable()) {
                            this.acceptOP(key, selector);
                        } else if (key.isReadable()) {
                            this.readOP(key);
                        } else if (key.isWritable()) {
                            this.write(key);

                        } else {
                            System.out.println("서버 소켓 생성 못함");
                        }
                    }
                }
            }


        } catch (Exception e){
            System.out.println("에러 ");
        }
    }

    private void write(SelectionKey key) throws IOException, InterruptedException {
        SocketChannel socketChannel = (SocketChannel) key.channel();

        List<byte[]> channelData = keepDataTrack.get(socketChannel);
        Iterator<byte[]> its = channelData.iterator();

        while(its.hasNext()){
            byte[] it = its.next();
            its.remove();
            socketChannel.write(ByteBuffer.wrap(it));
        }
        key.interestOps(SelectionKey.OP_READ);
    }

    private void readOP(SelectionKey key) {
        try{
            SocketChannel socketChannel = (SocketChannel) key.channel();
            buffer.clear();
            int numRead = -1;
            try{
                numRead = socketChannel.read(buffer);
            } catch (IOException e){
                System.out.println("데이터 읽기 에러 ");
            }
            if(numRead == -1){
                this.keepDataTrack.remove(socketChannel);
                System.out.println("클라이언트 연결 종료");
                socketChannel.close();
                key.cancel();
                return;
            }

            byte[] data = new byte[numRead];
            System.arraycopy(buffer.array(), 0 ,data, 0, numRead);
            System.out.println(new String(data, "utf-8") + " from" + socketChannel.getRemoteAddress());

            doEchoJob(key, data);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void doEchoJob(SelectionKey key, byte[] data) {
        SocketChannel socketChannel = (SocketChannel) key.channel();
        List<byte[]> channelData = keepDataTrack.get(socketChannel);
        channelData.add(data);
        key.interestOps(SelectionKey.OP_WRITE);
    }

    private void acceptOP(SelectionKey key, Selector selector) throws IOException{
        ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
        SocketChannel socketChannel = serverSocketChannel.accept();
        socketChannel.configureBlocking(false);
        System.out.println("클라이언트 연결 " + socketChannel.getRemoteAddress());
        keepDataTrack.put(socketChannel, new ArrayList<byte[]>());
        socketChannel.register(selector, SelectionKey.OP_READ);
    }

    public static void main(String[] args) {
        NonBlockingServer nonBlockingServer = new NonBlockingServer();
        nonBlockingServer.startEchoServer();
    }
}
