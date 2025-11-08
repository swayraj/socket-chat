import java.io.IOException;
import java.net.Socket;
import java.net.ServerSocket;

class ChatServer
{
    public static void main(String[] args)
    {
        int port = 4000;

        try(ServerSocket serverSocket = new ServerSocket(port))
        {
            System.out.println("Listening on Port:" + port);

            while(true)
            {
                Socket clientSocket = serverSocket.accept();

                System.out.println("A new client has connected: " + clientSocket);

            }
        }
        catch (IOException e)
        {
            System.out.println("Server Exception:" + e.getMessage());
            e.printStackTrace();
        }
    }
}