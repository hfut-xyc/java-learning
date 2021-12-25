package bio;

import java.io.*;
import java.net.Socket;

public class BioClient {

    private static final String HOST = "127.0.0.1";

    private static final int PORT = 8080;

    private static final String EXIT = "exit";

    private Socket socket;

    private BufferedReader reader;

    private BufferedWriter writer;

    public BioClient() {
        try {
            this.socket = new Socket(HOST, PORT);
            this.reader = new BufferedReader(
                    new InputStreamReader(socket.getInputStream())
            );
            this.writer = new BufferedWriter(
                    new OutputStreamWriter(socket.getOutputStream())
            );
            // create a new thread to receive message from server
            new Thread(new ReceiveTask(this)).start();

            // send what user inputs to server
            BufferedReader consoleReader = new BufferedReader(
                    new InputStreamReader(System.in)
            );
            while (true) {
                String input = consoleReader.readLine();
                writer.write(input + "\n");
                writer.flush();
                if (EXIT.equals(input)) {
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ReceiveTask implements Runnable {

        private BioClient bioClient;

        public ReceiveTask(BioClient bioClient) {
            this.bioClient = bioClient;
        }

        @Override
        public void run() {
            try {
                String msg;
                while ((msg = bioClient.reader.readLine()) != null) {
                    System.out.println(msg);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        BioClient bioClient = new BioClient();
    }
}