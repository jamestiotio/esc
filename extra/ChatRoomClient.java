import java.io.*;
import java.net.*;

public class ChatRoomClient {
    public static void main(String[] args) throws Exception {
        String hostName = "localhost";
        int portNumber = 4321;

        Socket echoSocket = new Socket(hostName, portNumber);

        PrintWriter out = new PrintWriter(echoSocket.getOutputStream(), true);

        BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));

        String inputLine;

        inputLine = stdIn.readLine();
        out.println(inputLine);
        out.flush();
        echoSocket.close();
        out.close();
    }
}
