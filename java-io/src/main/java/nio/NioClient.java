package nio;

import java.io.*;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedSelectorException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Set;


public class NioClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;
    private static final String EXIT = "exit";
    private static Charset charset = Charset.forName("UTF-8");

    private SocketChannel client;
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);
    private ByteBuffer writeBuffer = ByteBuffer.allocate(1024);

    public NioClient() {
        try {
            client = SocketChannel.open();
            client.configureBlocking(false);
            selector = Selector.open();
            client.register(selector, SelectionKey.OP_CONNECT);
            client.connect(new InetSocketAddress(HOST, PORT));

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
        } catch (ClosedSelectorException e) {
            //
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
        if (key.isConnectable()) {
            SocketChannel client = (SocketChannel) key.channel();
            if (client.isConnectionPending()) {
                client.finishConnect();
                new Thread(new UserInputHandler(this)).start();
            }
            client.register(selector, SelectionKey.OP_READ);
        }
        else if (key.isReadable()) {
            SocketChannel client = (SocketChannel) key.channel();
            String msg = receive(client);
            System.out.println(msg);
        }
    }

    private String receive(SocketChannel client) throws IOException {
        readBuffer.clear();
        while (client.read(readBuffer) > 0);
        readBuffer.flip();
        return String.valueOf(charset.decode(readBuffer));
    }

    private void send(String input) throws IOException {
        writeBuffer.clear();
        writeBuffer.put(charset.encode(input));
        writeBuffer.flip();
        while (writeBuffer.hasRemaining()) {
            client.write(writeBuffer);
        }
        if (EXIT.equals(input)) {
            if (selector != null) {
                try {
                    selector.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static class UserInputHandler implements Runnable {

        private NioClient nioClient;

        public UserInputHandler(NioClient nioClient) {
            this.nioClient = nioClient;
        }

        @Override
        public void run() {
            try {
                BufferedReader consoleReader = new BufferedReader(
                        new InputStreamReader(System.in)
                );
                while (true) {
                    String input = consoleReader.readLine();
                    nioClient.send(input);
                    if (EXIT.equals(input)) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        NioClient nioClient = new NioClient();
    }
}
