package thread;

public class philosophere_1 {
    // chopsticks
    private boolean[] used = new boolean[] { false, false, false, false, false };
    private static String LOCK = "lock";
    public static void main(String[] args) {
        //表示5双筷子
        ChopStick cs0 = new ChopStick();
        ChopStick cs1 = new ChopStick();
        ChopStick cs2 = new ChopStick();
        ChopStick cs3 = new ChopStick();
        ChopStick cs4 = new ChopStick();

        philosophere_1 philosophere1 = new philosophere_1();

        philosophere1.new Philosopher(0,cs0,cs1).start();
        philosophere1.new Philosopher(1,cs1,cs2).start();
        philosophere1.new Philosopher(2,cs2,cs3).start();
        philosophere1.new Philosopher(3,cs3,cs4).start();
        philosophere1.new Philosopher(4,cs4,cs0).start();
    }

    class Philosopher extends Thread {

        private int num;
        private ChopStick left;
        private ChopStick right;


        public Philosopher(int num,ChopStick left,ChopStick right) {
            this.num = num;
            this.left= left;
            this.right = right;


        }

        public void eating() {
            System.out.println("my num is " + num + " , I am eating...");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public void thinking() {
            System.out.println("my num is " + num + ", I am thinking...");
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

        //死锁
        public void takeChopsticks() throws InterruptedException {
            synchronized(left) {
                Thread.sleep(1000);
                synchronized (right){
                    Thread.sleep(1000);
                    System.out.println(num+" 拿到了筷子");
                }
            }
        }
        //左撇子算法
        public void takeChopsticks1() throws InterruptedException {
            if(num%2==0){
                synchronized(left) {
                    Thread.sleep(1000);
                    synchronized (right){
                        Thread.sleep(1000);
                        System.out.println(num+" 拿到了筷子");
                    }
                }
            }else{
                synchronized(right) {
                    Thread.sleep(1000);
                    synchronized (left){
                        Thread.sleep(1000);
                        System.out.println(num+" 拿到了筷子");
                    }
                }
            }

        }
        //线程粗话，同时拿到左右筷子
        public void takeChopsticks2() throws InterruptedException {

            synchronized (LOCK) {
                if(used[num]||used[(num+1)%5]){
                    LOCK.wait();
                }
                System.out.println(num + " :获取筷:" + left + ":" + right);
                used[num] = true;
                used[(num+1)%5] = true;
            }
        }

        //放下筷子时，也要申请锁，然后通知所有等待的线程
        public void putDownChopsticks() {
            synchronized(LOCK) {
                used[num] = false;
                used[(num + 1) % 5] = false;
                System.out.println("my num is " + num + " , I have finished...");
                LOCK.notifyAll();
            }
        }

        @Override
        public void run() {
            while(true) {
                thinking();
                try {
                    takeChopsticks1();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                eating();
                //putDownChopsticks();
            }
        }
    }


}
class ChopStick{

}

