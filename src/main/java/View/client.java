package View;
import java.io.*;
import java.net.Socket;

public class client {
    private String host;
    private int port;
    private boolean connected;

    public client(String host, int port) {
    }

    public void GameClient(String host, int port) {
        this.host = host;
        this.port = port;
        this.connected = false;
    }

    public void connect() {
        try {
            Socket socket = new Socket(host, port);
            connected = true;
            System.out.println("Connected to the server.");

            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);


            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            String line;
            while (connected && (line = userInput.readLine()) != null) {
                out.println(line);
                out.flush();
                String response = in.readLine();
                System.out.println("Received from server: " + response);
            }

            socket.close();
            connected = false;
            System.out.println("Disconnected from the server.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

    }
}
