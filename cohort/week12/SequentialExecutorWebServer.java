import java.net.*;
import java.util.concurrent.Executor;

public class SequentialExecutorWebServer {
    private static final int NTHREADS = 100;
    private static final Executor exec = new WithinThreadExecutor();

    public static void main(String[] args) throws Exception {
        try (ServerSocket socket = new ServerSocket(54321, 1000)) {
            while (true) {
                final Socket connection = socket.accept();
                Runnable task = new Runnable() {
                    public void run() {
                        handleRequest(connection);
                    }
                };

                exec.execute(task);
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("There is an issue with the socket connection.");
        }
    }

    protected static void handleRequest(Socket connection) {
        // TODO Auto-generated method stub
    }
}


// Sequential
class WithinThreadExecutor implements Executor {
    public void execute(Runnable r) {
        r.run();
    }
}
