package main;

import java.io.*;
import java.net.Socket;

public class ClientThread implements Runnable {
    private final Main main;
    private Socket socket;
    private PrintWriter out;

    public ClientThread(Socket socket, Main main) {
        this.socket = socket;
        this.main = main;
    }

    @Override
    public void run() {
        try {
            OutputStream outputStream = socket.getOutputStream();
            out = new PrintWriter(outputStream, true);

            InputStream inputStream = socket.getInputStream();
            BufferedReader in = new BufferedReader(new InputStreamReader(inputStream));
            out.println("Hello from server!\nWrite to me!");
            while (true) {
                String line = in.readLine();
                if (line.contains("quit")) {
                    break;
                }
//                out.println(">>> " + line);
                main.sendToAll(">>> " + line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
