import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;
import java.util.concurrent.locks.ReentrantLock;
public class contiga {
    private static final Logger logger = Logger.getLogger(contiga.class.getName());
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        String threadName1 = null;
        String threadName2 = null;
        try {
            System.out.print("Name your first thread: ");
            threadName1 = reader.readLine();
            System.out.print("Name your second thread: ");
            threadName2 = reader.readLine();
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error reading input", e);
            return;
        }
        Thread thread1 = new Thread(new CustomTask(threadName1), threadName1);
        Thread thread2 = new Thread(new CustomTask(threadName2), threadName2);
        displayThreadState(thread1);
        displayThreadState(thread2);
        System.out.println("\nStarting the threads...");
        thread1.start();
        thread2.start();
        displayThreadState(thread1);
        displayThreadState(thread2);
        try {
            TimeUnit.MILLISECONDS.sleep(500);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, "Main thread interrupted", e);
        }
        System.out.println("\nAfter sleep...");
        displayThreadState(thread1);
        displayThreadState(thread2);
    }
    private static void displayThreadState(Thread thread) {
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        long cpuTime = threadMXBean.getThreadCpuTime(thread.getId());
        System.out.println(thread.getName() + " is " + thread.getState() + ", CPU time: " + cpuTime + " ns");
    }
}
class CustomTask implements Runnable {
    private final String threadName;
    private static final Logger logger = Logger.getLogger(CustomTask.class.getName());
    private final ReentrantLock lock = new ReentrantLock();
    public CustomTask(String threadName) {
        this.threadName = threadName;
    }
    @Override
    public void run() {
        System.out.println(threadName + " is " + Thread.currentThread().getState());
        try {
            lock.lock();
            TimeUnit.MILLISECONDS.sleep(100);
        } catch (InterruptedException e) {
            logger.log(Level.SEVERE, threadName + " was interrupted", e);
        } finally {
            lock.unlock();
        }
        System.out.println(threadName + " is " + Thread.currentThread().getState());
    }
}
