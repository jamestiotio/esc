import java.io.*;
import java.net.*;

public class TicTacToeClient {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        int portNumber = 4321;

        Socket echoSocket = new Socket(hostName, portNumber);
        echoSocket.setSoTimeout(500000);

        BufferedReader in = new BufferedReader(new InputStreamReader(echoSocket.getInputStream()));
        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String playerInput;

        System.out.println("Welcome to Tic Tac Toe!");

        while (true) {
            try {
                System.out.println(in.readLine());
                playerInput = stdIn.readLine();
                out.println(playerInput);
                out.flush();

            } catch (java.net.SocketTimeoutException e) {
                System.out.println("Timed out.");
                break;
            }
        }

        System.out.println("Client connection terminated.");
        in.close();
        out.close();
        echoSocket.close();
    }
}
