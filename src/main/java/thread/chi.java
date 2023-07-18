package thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class chi {
    ExecutorService pool1 =  Executors.newFixedThreadPool(2);
    ExecutorService pool2 =  Executors.newSingleThreadExecutor();
    ExecutorService pool3 =  Executors.newCachedThreadPool();
    ExecutorService pool4 =  Executors.newScheduledThreadPool(2);
}
