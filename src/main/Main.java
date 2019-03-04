package main;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    List<ClientThread> clients;

    public static void main(String[] args) {
	    new Main().run();
    }

    private void run() {
        clients = new ArrayList<>();
        try (ServerSocket serverSocket = new ServerSocket(12345)) {
            while (true) {
                Socket socket = serverSocket.accept();
                ClientThread clientThread = new ClientThread(socket, this);
                clients.add(clientThread);
                new Thread(clientThread).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public synchronized void sendToAll(String message) {
        for (ClientThread client : clients) {
            client.sendMessage(message);
        }
    }
}
