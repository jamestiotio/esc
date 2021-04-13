import java.io.*;
import java.net.*;

public class ChatRoomClient2 {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        int portNumber = 4321;
        Socket echoSocket = new Socket(hostName, portNumber);

        System.out.println("Connected to chat room. Please talk within 5 seconds...");

        // The following is a pattern of busy-waiting.

        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);
        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
        String userInput;
        do {
            userInput = stdIn.readLine();
            out.println(userInput);
            out.flush();
        } while (!userInput.equals("Bye!"));
        System.out.println("Done chatting.");
        echoSocket.close();
        out.close();
        stdIn.close();
    }
}

