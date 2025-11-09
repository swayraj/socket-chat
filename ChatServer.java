import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ChatServer {

    private static Set<String> usernames = ConcurrentHashMap.newKeySet();
    private static Map<String, PrintWriter> clientWriters = new ConcurrentHashMap<>();

    public static void main(String[] args) {


        int port = 4000; // Default port

        if (args.length > 0) {
            try {
                // parse the first argument as an integer
                port = Integer.parseInt(args[0]);
            } catch (NumberFormatException e) {
                // If it's not a valid number, print an error and use the default
                System.out.println("Invalid port number: " + args[0] + ". Using default port 4000.");
            }
        }
        

        try (ServerSocket serverSocket = new ServerSocket(port)) {
            // This line now uses the (possibly new) port variable
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