import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.math.BigInteger;
import java.net.Socket;

/**
 * Question for Cohort Exercise 2.
 */
public class MultipleClient {
    public static void main(String[] args) throws Exception {
        int numberOfClients = 5; // Vary this number here (5, 10, 100 and 1000)
        long startTime = System.currentTimeMillis();
        // BigInteger n = new BigInteger("4294967297");
        BigInteger n = new BigInteger("239839672845043");
        Thread[] clients = new Thread[numberOfClients];

        for (int i = 0; i < numberOfClients; i++) {
            clients[i] = new Thread(new Client(n));
            clients[i].start();
        }

        for (int i = 0; i < numberOfClients; i++) {
            clients[i].join();
        }
        long timeSpent = System.currentTimeMillis() - startTime;
        double throughput = (double) numberOfClients / (double) timeSpent;
        System.out.println("Total spent time: " + timeSpent + " ms"); // Total time spent
        System.out.println("Throughput: " + throughput); // Throughput
    }
}


class Client implements Runnable {
    private final BigInteger n;

    public Client(BigInteger n) {
        this.n = n;
    }

    public void run() {
        String hostName = "localhost";
        int portNumber = 54321;

        // Local firewall or network settings might block this socket connection
        try (Socket socket = new Socket(hostName, portNumber)) {
            long startTime = System.currentTimeMillis();
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            out.println(n.toString());
            out.flush();
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // Waiting for results from server, which might temporarily fix the connection issue.
            // However, this busy-waiting will spend and consume a lot of CPU resources. For larger
            // numberOfClients, it might even cause connection issues to reappear. You have been
            // warned! Run without this line with only one ServerSocket for a more comfortable
            // experience. Please do not use this as this fix is unsustainable and not fit for
            // scalability purposes.
            // while (!in.ready());
            in.readLine();
            System.out.println("Spent time: " + (System.currentTimeMillis() - startTime) + " ms"); // Latency
            out.close();
            in.close();
            socket.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Server got problem");
        }
    }
}
