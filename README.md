import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class Junaz {
    private static final Logger logger = Logger.getLogger(Junaz.class.getName());

    public static void main(String[] args) {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            String threadName1;
            String threadName2;

            System.out.println("============================");
            System.out.println("=    Welcome Threads       =");
            System.out.println("============================");

            System.out.print("Name your first thread: ");
            threadName1 = reader.readLine();

            System.out.print("Name your second thread: ");
            threadName2 = reader.readLine();

            Thread thread1 = new Thread(new CustomTask(threadName1), threadName1);
            Thread thread2 = new Thread(new CustomTask(threadName2), threadName2);

            displayThreadState(thread1);
            displayThreadState(thread2);

            System.out.println("===========================");
            System.out.println("= Starting the threads... =");
            System.out.println("===========================");

            thread1.start();
            thread2.start();

            displayThreadState(thread1);
            displayThreadState(thread2);

            TimeUnit.MILLISECONDS.sleep(500);

            System.out.println("============================");
            System.out.println("=      After Sleep...      =");
            System.out.println("============================");

            displayThreadState(thread1);
            displayThreadState(thread2);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading input", e);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Main thread interrupted", e);
            Thread.currentThread().interrupt();
        }
    }

    private static void displayThreadState(Thread thread) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long cpuTime = threadMXBean.getThreadCpuTime(thread.getId());
        NumberFormat currencyFormat = NumberFormat.getCurrencyInstance(Locale.getDefault());
        String formattedCpuTime = currencyFormat.format(cpuTime);
        logger.info(thread.getName() + " is " + thread.getState() + ", CPU time: " + formattedCpuTime);
    }
}