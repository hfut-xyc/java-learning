package io;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;


public class NioClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    private static final Charset charset = StandardCharsets.UTF_8;
    private static final ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private static final ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            socketChannel.register(selector, SelectionKey.OP_CONNECT);
            socketChannel.connect(new InetSocketAddress(HOST, PORT));

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    // when clients connect to server
                    if (key.isConnectable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        if (client.isConnectionPending()) {
                            client.finishConnect();
                            new Thread(() -> {
                                BufferedReader inputReader = new BufferedReader(
                                        new InputStreamReader(System.in)
                                );
                                try {
                                    while (true) {
                                        String input = inputReader.readLine();
                                        send(client, input);
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }).start();
                        }
                        client.register(selector, SelectionKey.OP_READ);
                    }
                    // when clients receive messages from server
                    else if (key.isReadable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        String msg = receive(client);
                        System.out.println(msg);
                    }
                }
                keys.clear();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String receive(SocketChannel client) throws IOException {
        readBuffer.clear();
        while (client.read(readBuffer) > 0) ;
        readBuffer.flip();
        return String.valueOf(charset.decode(readBuffer));
    }

    private static void send(SocketChannel client, String input) throws IOException {
        writeBuffer.clear();
        writeBuffer.put(charset.encode(input));
        writeBuffer.flip();
        while (writeBuffer.hasRemaining()) {
            client.write(writeBuffer);
        }
    }
}
