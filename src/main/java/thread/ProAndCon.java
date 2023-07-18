package thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class ProAndCon {
}

class Producer implements Runnable{
    private ResNormal resource;
    Producer(ResNormal resource){
        this.resource=resource;
    }
    public void run(){
        //while(true)
        resource.set("面包");
    }
}

class Consumer implements Runnable{
    private ResNormal resource;
    Consumer(ResNormal resource){
        this.resource=resource;
    }
    public void run(){
        //while(true)
        resource.get();
    }
}

class ResNormal{
    //描述资源类
    private String name;
    private int count=1;
    private boolean flag;
    public synchronized void set(String name){
        //if变while
        while(flag)
            try {
                wait();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        this.name=name+"--"+count;
        count++;
        System.out.println(Thread.currentThread().getName()+"...生产者..."+this.name);
        flag=true;
        notify();
    }

    public synchronized void get(){
        //if变while
        while(!flag)
            try {
                wait();
            } catch (InterruptedException e) {

                e.printStackTrace();
            }
        System.out.println(Thread.currentThread().getName()+"...消费者..."+this.name);
        flag=false;
        notify();
    }
}

//类似于阻塞队列的源码实现

class ResLock{
    //描述资源类
    private String name;
    private int count=1;
    private boolean flag;
    private Lock lock = new ReentrantLock();
    private Condition producer_con = lock.newCondition();
    private Condition consumer_con = lock.newCondition();

    public void set(String name) {
        lock.lock();
        try {
            while (flag)
                try {
                    //把该线程加入到生产者线程池
                    producer_con.await();
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            this.name = name + "--" + count;
            count++;
            System.out.println(Thread.currentThread().getName() + "...生产者..." + this.name);
            flag = true;
            //只唤醒一个消费者
            consumer_con.signal();
        } finally {
            lock.unlock();
        }
    }


    public void get() {
        lock.lock();
        try {
            while (!flag)
                try {
                    //把该线程加入到消费者线程池
                    consumer_con.await();
                } catch (InterruptedException e) {

                    e.printStackTrace();
                }
            System.out.println(Thread.currentThread().getName() + "...消费者..." + this.name);
            flag = false;
            //只唤醒一个生产者
            producer_con.signal();
        } finally {
            lock.unlock();
        }
    }
}

