package com.io;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class BioServer {

    private static final int PORT = 8080;

    // use thread-safe container because multiple threads modify it
    private static final Map<Integer, Writer> clients = new HashMap<>(10);

    public static void main(String[] args) {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            System.out.println("Server has started, listening to port:" + PORT);

            while (true) {
                // wait until a client connected
                Socket socket = serverSocket.accept();
                addClient(socket);

                // create a new thread to interact with the client
                new Thread(() -> {
                    try {
                        BufferedReader reader = new BufferedReader(
                                new InputStreamReader(socket.getInputStream())
                        );
                        String msg;
                        while ((msg = reader.readLine()) != null) {
                            msg = "Client[" + socket.getPort() + "]: " + msg + "\n";
                            System.out.print(msg);
                            forwardMessage(socket, msg);
                        }
                    } catch (IOException e) {
                        try {
                            removeClient(socket);
                        } catch (IOException e1) {
                            e1.printStackTrace();
                        }
                    }
                }).start();
            }
        } catch (IOException e) {
            try {
                assert serverSocket != null;
                serverSocket.close();
                System.out.println("Server has shut down");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    private static void forwardMessage(Socket socket, String message) throws IOException {
        for (Integer port : clients.keySet()) {
            if (port == socket.getPort()) {
                continue;
            }
            Writer writer = clients.get(port);
            writer.write(message);
            writer.flush();
        }
    }

    private static void addClient(Socket socket) throws IOException {
        int port = socket.getPort();
        BufferedWriter writer = new BufferedWriter(
                new OutputStreamWriter(socket.getOutputStream())
        );
        clients.put(port, writer);
        System.out.println("Client[" + port + "] has connected");
    }

    private static void removeClient(Socket socket) throws IOException {
        int port = socket.getPort();
        clients.get(port).close();
        clients.remove(port);
        System.out.println("Client[" + port + "] has disconnected");
    }
}
