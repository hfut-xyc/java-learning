package nio;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.nio.charset.Charset;
import java.util.Set;

public class Server {

    private static final int PORT = 8080;
    private static final String EXIT = "exit";
    private static Charset charset = Charset.forName("UTF-8");

    private Selector selector;
    private ServerSocketChannel server;
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    public Server() {
        try {
            server = ServerSocketChannel.open();
            server.configureBlocking(false);

            selector = Selector.open();
            server.register(selector, SelectionKey.OP_ACCEPT);
            server.socket().bind(new InetSocketAddress(PORT));
            System.out.println("Server has started, listening to port:" + PORT);
            while (true) {
                selector.select();
                Set<SelectionKey> keys = selector.selectedKeys();
                for (SelectionKey key : keys) {
                    handleEvent(key);
                }
                keys.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void handleEvent(SelectionKey key) throws IOException {
        // the event when clients connect to server
        if (key.isAcceptable()) {
            SocketChannel client = server.accept();
            client.configureBlocking(false);
            client.register(selector, SelectionKey.OP_READ);
            System.out.println(getClientName(client) + " has connected");
        }
        // the event when clients send messages to server
        else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            String msg = receive(client);
            System.out.println(getClientName(client) + ": " + msg);
            forwardMessage(client, msg);
            if (EXIT.equals(msg)) {
                key.cancel();
                selector.wakeup();
                System.out.println(getClientName(client) + " has exited");
            }
        }
    }

    private String receive(SocketChannel client) throws IOException {
        readBuffer.clear();
        while (client.read(readBuffer) > 0) {}
        readBuffer.flip();
        return String.valueOf(charset.decode(readBuffer));
    }

    private void forwardMessage(SocketChannel client, String message) throws IOException {
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

    private String getClientName(SocketChannel client) {
        return "Client[" + client.socket().getPort() + "]";
    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}

