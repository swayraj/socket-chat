import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.PrintWriter;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;


public class ChatServer {

    // A thread-safe set to store all unique usernames.
    private static Set<String> usernames = ConcurrentHashMap.newKeySet();

    // A thread-safe map to store [username -> PrintWriter]
    private static Map<String, PrintWriter> clientWriters = new ConcurrentHashMap<>();

    public static void main(String[] args) {
        int port = 4000;

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            System.out.println("Listening on Port:" + port);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("A new client has connected: " + clientSocket);


                ClientHandler clientHandler = new ClientHandler(clientSocket, usernames, clientWriters);


                Thread clientThread = new Thread(clientHandler);
                clientThread.start();
            }

        } catch (IOException e) {
            System.out.println("Server Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
}