package thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerDemo {

    private static int THREAD_COUNT = 100;

    public static void main(String[] args) throws InterruptedException {


        NormalCounter normalCounter = new NormalCounter("normalCounter",0);
        SafeCounter safeCounter = new SafeCounter("safeCounter",0);
        List<Thread> threadList = new ArrayList<>();

        for (int i = 0; i < THREAD_COUNT ; i++) {
            Thread thread = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int j = 0; j < 10000; j++) {
                        normalCounter.add(1);
                        safeCounter.add(1);
                    }
                }
            });
            threadList.add(thread);
        }

        for (Thread thread : threadList) {
            thread.start();
        }
        for (Thread thread : threadList) {
            thread.join();
        }
        System.out.println("normalCounter:"+normalCounter.getCount());
        System.out.println("safeCounter:"+safeCounter.getCount());
    }


    public static class NormalCounter{
        private String name;
        private Integer count;

        public NormalCounter(String name, Integer count) {
            this.name = name;
            this.count = count;
        }

        public void add(int delta){
            this.count = count+delta;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getCount() {
            return count;
        }

        public void setCount(Integer count) {
            this.count = count;
        }
    }

    public static class SafeCounter{
        private String name;
        private AtomicInteger count;

        public SafeCounter(String name, Integer count) {
            this.name = name;
            this.count = new AtomicInteger(count);
        }

        public void add(int delta){
            count.addAndGet(delta);
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getCount() {
            return count.get();
        }

        public void setCount(Integer count) {
            this.count.set(count);
        }
    }

}
