package com.lock;

import java.util.LinkedList;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: yingxu.pi@transsnet.com
 * @date: 2019/7/30 17:10
 */
public class ReentrantLockTest {

    private static void reentrant () {
        //解释可重入的概念，lock几次就要unlock几次才能释放锁
        ReentrantLock lock = new ReentrantLock();
        new Thread(()->{
            lock.lock();
            lock.lock();
            System.out.println("Thread-1 running");
            lock.unlock();
        }).start();

        new Thread(()->{
            lock.lock();
            System.out.println("Thread-2 running");
        }).start();
    }

    private static void fairLock () {
        //公平锁
        ReentrantLock lock = new ReentrantLock(true);
        for (int i=1;i<6;i++) {
            final int a = i;
            new Thread(()->{
                for(int j=0;j<2;j++){
                    lock.lock();
                    System.out.println("获得锁的线程："+ a);
                    lock.unlock();
                }
            }).start();
        }
    }

    private static void lockInterruptibly ()  {
        //可响应中断的锁
        ReentrantLock lock_1 = new ReentrantLock();
        ReentrantLock lock_2 = new ReentrantLock();

        //先获取lock_1后获取lock_2
        Thread t1 = new Thread(()->{
            try {
                lock_1.lockInterruptibly();
                System.out.println("Thread-1 running, i get lock_1 ");
                Thread.sleep(1000); //模拟死锁
                lock_2.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock_1.unlock();
                lock_2.unlock();
                System.out.println("Thread-1 正常结束!");
            }
        });

        //先获取lock_2后获取lock_1
        Thread t2 = new Thread(()->{
            try {
                lock_2.lockInterruptibly();
                System.out.println("Thread-2 running, i get lock_2");
                Thread.sleep(1000); //模拟死锁
                lock_1.lockInterruptibly();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock_1.unlock();
                lock_2.unlock();
                System.out.println("Thread-2 正常结束!");
            }
        });

        t1.start();
        t2.start();

        try {
            Thread.sleep(10000);
            //10秒后让Thread-1主动中断
            System.out.println("主动中断Thread-1");
            t1.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void tryLock () throws Exception {
        //lock.tryLock()，true获取成功，false获取失败
        ReentrantLock lock = new ReentrantLock(true);
        new Thread(()->{
            lock.lock();
            System.out.println("获得锁的线程：Thread-1");
            try {
                Thread.sleep(5000);//5秒后释放锁
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();
        }).start();

        new Thread(()->{
            while (!lock.tryLock()) {
                try {
                    Thread.sleep(1000);//1秒后再次获取
                    System.out.println("获得锁失败：Thread-2");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("获得锁成功：Thread-2");

        }).start();

        Thread.sleep(10000); //10秒后再开个线程验证tryLock后是否需要释放锁
        new Thread(()->{
            lock.lock();
            System.out.println("获得锁的线程：Thread-3");
        }).start();

        new Thread(()->{
            lock.lock();
            System.out.println("获得锁的线程：Thread-4");
        }).start();
        System.out.println(lock.tryLock(10000, TimeUnit.MILLISECONDS));
    }

    public static void condition() {
        ReentrantLock lock = new ReentrantLock();
        Condition condition_1 = lock.newCondition();
        Condition condition_2 = lock.newCondition();
        new Thread(()->{
            lock.lock();
            System.out.println("获得锁的线程：Thread-1");
            try {
                //验证condition.await()调用释放了锁，Thread-2会获得锁
                Thread.sleep(3000);
                /*
                  当前线程挂起，等待其他线程condition.signal()/condition.signalAll()
                  await()调用后释放锁是为了别的线程获得，是为了线程间的通信，是临时释放的，真正满足继续向下执行条件后，
                  被唤醒后获得了锁，做完想做的事后仍需要释放锁，也是为了别的线程能执行或使用共享资源
                */
                condition_1.await();
                System.out.println("Thread-1 被唤醒");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

        new Thread(()->{
            lock.lock();
            System.out.println("获得锁的线程：Thread-2");
            try {
                Thread.sleep(5000); //5秒后唤醒一个挂起的线程
                condition_1.signal();
                // condition_2.signal(); //condition_2 唤醒不了Thread-1 (能唤醒的线程一定是同一个Condition)
                System.out.println("Thread-2 去唤醒 Thread-1");
                lock.unlock();//--------> 切记signal/signalAll后要记得释放锁，不然被await()的线程不会被唤醒
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }


    private ReentrantLock lock_queue = new ReentrantLock();
    private LinkedList<String> link = new LinkedList();
    private int maxSize = 3;

    public static void enQueue(String element) {

    }

    public static void outQueue(String[] args) {
        System.out.println();
    }



    public static void main(String[] args) throws Exception {
        condition();
    }
}
