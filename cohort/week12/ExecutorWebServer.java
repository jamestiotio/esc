import java.io.*;
import java.math.BigInteger;
import java.net.*;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

// Avoid submitting asymmetrically long-running tasks so as to not impair the responsiveness of the
// Executor-managed service. Reusing threads also create channels for communication between tasks
// (might have visibility issues).
public class ExecutorWebServer {
    // Heuristic empirical optimization
    // Optimal pool size, M = N * U * (1 + W/C)
    // N: Number of CPUs
    // U: Target CPU utilization
    // W/C: Ratio of wait time to compute time
    // Otherwise, for compute-intensive tasks: N+1 threads for a N-processor system.
    // And for tasks with I/O or other blocking operations, need a larger pool.
    // Solving this issue without manual tuning and hardcoding is much more complicated since it
    // requires mathematical optimization depending on various configurations (academic research
    // problem).
    // private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();
    private static final int NTHREADS = 3; // For our case, 3 seems to be the optimal pool size.
    // Other possible Executor types include: newCachedThreadPool, newSingleThreadExecutor and
    // newScheduledThreadPool.
    private static final Executor exec = Executors.newFixedThreadPool(NTHREADS);

    public static void main(String[] args) throws Exception {
        // If the client process is interrupted abruptly and the socket is not closed properly, this
        // will require a restart of the server to properly re-establish an open server socket again
        // available for connections (a "SocketException: Connection reset" error might be raised on
        // Windows and Mac).
        try (ServerSocket socket = new ServerSocket(54321, 100000000)) {
            while (true) {
                final Socket connection = socket.accept();
                Runnable task = new Runnable() {
                    public void run() {
                        try {
                            handleRequest(connection);
                        } catch (Exception e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                };

                // Deadlock and liveness hazard might happen if tasks are heterogeneous or dependent.
                exec.execute(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is an issue with the socket connection.");
        }
    }

    private static void handleRequest(Socket connection) throws Exception {
        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        PrintWriter out = new PrintWriter(connection.getOutputStream(), true);
        BigInteger number = new BigInteger(in.readLine());
        BigInteger result = factor(number);
        // System.out.println("sending results: " + String.valueOf(result));
        out.println(result);
        out.flush();
        in.close();
        out.close();
        connection.close();
    }

    private static BigInteger factor(BigInteger n) {
        BigInteger i = new BigInteger("2");
        BigInteger zero = new BigInteger("0");

        while (i.compareTo(n) < 0) {
            if (n.remainder(i).compareTo(zero) == 0) {
                return i;
            }

            i = i.add(new BigInteger("1"));
        }

        assert (false);
        return null;
    }
}
