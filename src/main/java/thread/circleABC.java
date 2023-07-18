package thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

//三个线程循环打印ABC
public class circleABC {
    private static Thread a;
    private static Thread b;
    private static Thread c;
    public static void main(String[] args) {
        ABCthread1 abc = new ABCthread1();
        a = new Thread(()->{
                try {
                    abc.printA();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);

            }
        },"线程A");
        b = new Thread(()->{
                try {
                    abc.printB();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
            }
        },"线程B");
        c = new Thread(()->{

                try {
                    abc.printC();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);

            }
        },"线程C");
        a.start();
        b.start();
        c.start();
    }

}
class ABCthread {
    private static volatile int value = 1;

    public void printA() throws InterruptedException {
        synchronized (this){
                while(value!=1){
                    wait();
                }
                System.out.println(Thread.currentThread().getName() + ": A");
                value = 2;
                notifyAll();

        }
    }
    public void printB() throws InterruptedException {
        synchronized (this){
            while(value!=2){
                wait();
            }
            System.out.println(Thread.currentThread().getName() + ": B");
            value = 3;
            notifyAll();

        }
    }
    public void printC() throws InterruptedException {
        synchronized (this){
            while(value!=3){
                wait();
            }
            System.out.println(Thread.currentThread().getName() + ": C");
            value = 1;
            notifyAll();
        }
    }
}

class ABCthread1 {
    private CountDownLatch cdl1 = new CountDownLatch(1);
    private CountDownLatch cdl2 = new CountDownLatch(1);
    private ReentrantLock lock = new ReentrantLock();
    Condition c1 = lock.newCondition();
    Condition c2 = lock.newCondition();
    Condition c3 = lock.newCondition();

    public void printA() throws InterruptedException {

        lock.lock();
        for(int i=0;i<5;i++){
            System.out.println(Thread.currentThread().getName() + ": A");
            cdl1.countDown();
            c2.signal();
            c1.await();
        }
        lock.unlock();
    }
    public void printB() throws InterruptedException {
        cdl1.await();
        lock.lock();
        for(int i=0;i<5;i++){
            System.out.println(Thread.currentThread().getName() + ": B");
            cdl2.countDown();
            c3.signal();
            c2.await();
        }
        lock.unlock();
    }
    public void printC() throws InterruptedException {
        cdl2.await();
        lock.lock();
        for(int i=0;i<5;i++){
            System.out.println(Thread.currentThread().getName() + ": C");

            c1.signal();
            c3.await();
        }
        lock.unlock();
    }
}
