import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map;
import java.util.Set;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private String username;

    private Set<String> usernames;
    private Map<String, PrintWriter> clientWriters;

    private PrintWriter out;

    public ClientHandler(Socket socket, Set<String> usernames, Map<String, PrintWriter> clientWriters) {
        this.clientSocket = socket;
        this.usernames = usernames;
        this.clientWriters = clientWriters;
    }

    @Override
    public void run() {
        try {
            this.out = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            // --- 2. THE LOGIN FLOW ---
            while (true) {
                String inputLine = in.readLine();
                if (inputLine == null || !inputLine.startsWith("LOGIN ")) {
                    return;
                }

                String potentialUsername = inputLine.substring(6).trim();

                if (usernames.add(potentialUsername)) {
                    this.username = potentialUsername;
                    this.clientWriters.put(this.username, this.out);
                    out.println("OK");
                    break;
                } else {
                    out.println("ERR username-taken");
                }
            }

            // --- 3. THE MESSAGING LOOP ---
            String inputLine;
            while ((inputLine = in.readLine()) != null) {

                if (inputLine.startsWith("MSG ")) {
                    String messageText = inputLine.substring(4).trim();
                    String broadcastMessage = "MSG " + this.username + " " + messageText;

                    broadcast(broadcastMessage, this.username);
                }

            }

        } catch (IOException e) {
            System.out.println("Error handling client " + username + ": " + e.getMessage());
        } finally {
            // --- 4. THE DISCONNECT FLOW ---
            if (this.username != null) {
                System.out.println(this.username + " is disconnecting.");
                usernames.remove(this.username);
                clientWriters.remove(this.username);

                // Notify all users
                broadcast("INFO " + this.username + " disconnected", this.username); // <-- CHANGED
            }

            try {
                clientSocket.close();
            } catch (IOException e) {
                // Ignore
            }
        }
    }

    // --- 5. THE BROADCAST METHOD---
    private void broadcast(String message, String senderUsername) { // <-- CHANGED
        System.out.println("Broadcasting to all except " + senderUsername + ": " + message);

        for (Map.Entry<String, PrintWriter> entry : clientWriters.entrySet()) {

            String targetUsername = entry.getKey();
            PrintWriter targetWriter = entry.getValue();

            // Only send the message if the target is NOT the sender
            if (!targetUsername.equals(senderUsername)) { // <-- CHANGED
                targetWriter.println(message);
            }
        }
    }
}