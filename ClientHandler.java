import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;


public class ClientHandler implements Runnable {

    // These are the "tools" each receptionist gets.
    private Socket clientSocket;
    private String username;


    public ClientHandler(Socket socket) {
        this.clientSocket = socket;
    }


    @Override
    public void run() {
        try {

            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));

            String inputLine = in.readLine();

            System.out.println("Received from client: " + inputLine); // For server debugging

            System.out.println("You said: " + inputLine);


            // When the 'try' block finishes, we close everything.
            clientSocket.close();

        } catch (IOException e) {
            System.out.println("Error handling client: " + e.getMessage());
        }
    }
}