import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;



public class ChatServer {

    public static void main(String[] args) {
        int port = 4000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on Port:" + port);

            while (true) {
                // Wait for a new client...
                Socket clientSocket = serverSocket.accept();
                System.out.println("A new client has connected: " + clientSocket);


                ClientHandler clientHandler = new ClientHandler(clientSocket);

                // Create a Thread
                Thread clientThread = new Thread(clientHandler);

                // Start the Thread!
                clientThread.start(); // This calls the 'run()' method in ClientHandler

            }

        } catch (IOException e) {
            System.out.println("Server Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
}