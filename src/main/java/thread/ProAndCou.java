package thread;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

//使用非阻塞队列模式实现消费者-生产者模式:
public class ProAndCou {
    public static void main(String[] args)  {

            Queue<String> queue = new LinkedList<>();
            int maxTask = 3;
            for(int i=0;i<3;i++){
                new Thread(new Productor(queue, maxTask)).start();
            }
            for(int i=0;i<3;i++){
                new Thread(new Customer(queue, maxTask)).start();
            }
    }
}
class Customer implements Runnable {

    private Queue<String> queue; // 生产队列
    @SuppressWarnings("unused")
    private Integer maxTask; // 最大产量

    public Customer(Queue<String> queue, Integer maxTask) {
        super();
        this.queue = queue;
        this.maxTask = maxTask;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.size() == 0) {
                    try {
                        queue.wait(); // 线程进入阻塞状态
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000); // 模拟消费用时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("消费 " + queue.remove());
                queue.notify(); // 唤醒阻塞线程
            }
        }
    }
}
class Productor implements Runnable {

    private Queue<String> queue; // 生产队列
    private Integer maxTask; // 最大产量
    int i = 1; // 生产计数

    public Productor(Queue<String> queue, Integer maxTask) {
        super();
        this.queue = queue;
        this.maxTask = maxTask;
    }

    @Override
    public void run() {
        while (true) {
            synchronized (queue) {
                while (queue.size() >= maxTask) {
                    try {
                        queue.wait(); // 线程进入阻塞状态
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                try {
                    Thread.sleep(1000); // 模拟生产用时
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                String msg = "" + (i++);
                queue.add(msg);
                System.out.println("生产 " + msg);
                queue.notify(); // 唤醒阻塞线程
            }
        }
    }
}



