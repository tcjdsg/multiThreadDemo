package thread;

import java.util.concurrent.locks.ReentrantLock;

public class Reentran1 {
    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock();
        Thread t1 = new Thread(()-> {
            //未设置等待时间，一旦获取失败，直接返回false
            if(!lock.tryLock()) {
                System.out.println("获取失败");
                //获取失败，不再向下执行，返回
                return;
            }
            System.out.println("得到了锁");
            lock.unlock();
        });


        lock.lock();
        try{
            t1.start();
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

}
