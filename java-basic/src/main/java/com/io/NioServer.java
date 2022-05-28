package com.io;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Set;

public class NioServer {

    private static final int PORT = 8080;
    private static final Charset charset = StandardCharsets.UTF_8;
    private static final ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private static final ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    public static void main(String[] args) {
        try {
            Selector selector = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.configureBlocking(false);
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
            serverChannel.socket().bind(new InetSocketAddress(PORT));
            System.out.println("Server has started, listening to port:" + PORT);

            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    // when clients connect to server
                    if (key.isAcceptable()) {
                        SocketChannel socketChannel = serverChannel.accept();
                        socketChannel.configureBlocking(false);
                        socketChannel.register(selector, SelectionKey.OP_READ);
                        System.out.println(getClientName(socketChannel) + " has connected");
                    }
                    // when clients send messages to server
                    else if (key.isReadable()) {
                        SocketChannel socketChannel = (SocketChannel) key.channel();
                        String msg = receive(socketChannel);
                        System.out.println(getClientName(socketChannel) + ": " + msg);
                        forwardMessage(socketChannel, selector, msg);
                    }
                }
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static String getClientName(SocketChannel client) {
        return "Client[" + client.socket().getPort() + "]";
    }

    private static String receive(SocketChannel client) throws IOException {
        readBuffer.clear();
        while (client.read(readBuffer) > 0) {}
        readBuffer.flip();
        return String.valueOf(charset.decode(readBuffer));
    }

    private static void forwardMessage(SocketChannel client, Selector selector, String message) throws IOException {
        for (SelectionKey key : selector.keys()) {
            Channel channel = key.channel();
            if (channel instanceof ServerSocketChannel) {
                continue;
            }
            if (key.isValid() && !client.equals(channel)) {
                writeBuffer.clear();
                writeBuffer.put(charset.encode(getClientName(client) + ": " + message));
                writeBuffer.flip();
                while (writeBuffer.hasRemaining()) {
                    ((SocketChannel) channel).write(writeBuffer);
                }
            }
        }
    }
}

