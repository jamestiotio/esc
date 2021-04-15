import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

// Future <=> Thread, Callable <=> Runnable
public class FutureTaskExample {
    public static void main(String[] args) {
        FutureTask<String> future = new FutureTask<>(new CallableTask());
        // Cancelling code before run
        /*
         * boolean b = future.cancel(true); System.out.println("Cancelled="+b);
         */
        future.run(); // Just like thread.start()
        // Cancelling code after run
        /*
         * boolean b = future.cancel(true); System.out.println("Cancelled="+b);
         */
        System.out.println("Result=");
        try {
            String result = future.get(1, TimeUnit.MILLISECONDS); // Similar to thread.join()
            System.out.println(result);
        } catch (InterruptedException | ExecutionException | TimeoutException e) {
            System.out.println("EXCEPTION!!!");
            e.printStackTrace();
        }
    }
}

