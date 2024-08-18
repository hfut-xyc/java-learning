package io;


import java.io.*;
import java.net.Socket;

public class BioClient {

    private static final String HOST = "127.0.0.1";
    private static final int PORT = 8080;

    public static void main(String[] args) {
        try {
            // wait until connected to server
            Socket socket = new Socket(HOST, PORT);
            System.out.println("connected to server successfully");

            BufferedReader bioReader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            BufferedWriter bioWriter = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            // input from keyboard
            BufferedReader inputReader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            // send message to server
            new Thread(() -> {
                try {
                    while (true) {
                        String input = inputReader.readLine();
                        bioWriter.write(input + "\n");
                        bioWriter.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();

            // read message from server
            String msg;
            while ((msg = bioReader.readLine()) != null) {
                System.out.println(msg);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
