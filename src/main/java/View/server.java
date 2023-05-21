package View;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class server extends Thread {

    @Override
    public void run() {
        super.run();
        connectServer();
    }

    public void connectServer() {
        try {
            ServerSocket serverSocket = new ServerSocket(6000);
            System.out.println("Server started...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress());

                try (
                        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
                ) {
                    out.writeObject("Hello, client!");

                    String message = (String) in.readObject();
                    System.out.println("Client sent: " + message);
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                } finally {
                    socket.close();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectClient() {
        try {
            Socket socket = new Socket("localhost", 6000);
            System.out.println("Connected to server...");

            try (
                    ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                    ObjectInputStream in = new ObjectInputStream(socket.getInputStream())
            ) {
                String message = (String) in.readObject();
                System.out.println("Server sent: " + message);

                out.writeObject("Hello, server!");
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
