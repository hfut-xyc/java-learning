package bio;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;

public class Server {

    private static final int PORT = 8080;

    private static final String EXIT = "exit";

    private ServerSocket serverSocket;

    // use thread-safe container because multiple threads modify it
    private volatile ConcurrentHashMap<Integer, Writer> clients;

    public Server() {
        this.clients = new ConcurrentHashMap<Integer, Writer>(10);
        try {
            this.serverSocket = new ServerSocket(PORT);
            System.out.println("Server has started, listening to port:" + PORT);

            while (true) {
                // wait for a client to connect
                Socket socket = serverSocket.accept();

                // create a new thread to interact with the client
                new Thread(new ForwardHandler(this, socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (serverSocket != null) {
                try {
                    serverSocket.close();
                    System.out.println("Server has shut down");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void addClient(Socket socket) throws IOException {
        if (socket == null) {
            return;
        }
        int port = socket.getPort();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
        );
        clients.put(port, writer);
        System.out.println("Client[" + port + "] has connected");
    }

    public void removeClient(Socket socket) throws IOException {
        if (socket == null) {
            return;
        }
        int port = socket.getPort();
        if (clients.containsKey(port)) {
            clients.get(port).close();
            clients.remove(port);
            System.out.println("Client[" + port + "] has disconnected");
        }
    }

    public void forwardMessage(Socket socket, String message) throws IOException {
        for (Integer port : clients.keySet()) {
            if (!port.equals(socket.getPort())) {
                Writer writer = clients.get(port);
                writer.write(message);
                writer.flush();
            }
        }
    }

    static class ForwardHandler implements Runnable {

        private Server server;

        private Socket socket;

        public ForwardHandler(Server server, Socket socket) {
            this.server = server;
            this.socket = socket;
        }

        @Override
        public void run() {
            try {
                server.addClient(socket);
                BufferedReader reader = new BufferedReader(
                        new InputStreamReader(socket.getInputStream())
                );
                String msg;
                while ((msg = reader.readLine()) != null) {
                    String forwardMsg = "Client[" + socket.getPort() + "]: " + msg + "\n";
                    server.forwardMessage(socket, forwardMsg);
                    System.out.print(forwardMsg);
                    if (EXIT.equals(msg)) {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                try {
                    server.removeClient(socket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Server server = new Server();
    }
}
