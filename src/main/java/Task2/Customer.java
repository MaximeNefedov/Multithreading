package Task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Customer {
    private Shop shop;
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();
//    private static final int PURCHASE_TIME = 1500;

    public Customer(Shop shop) {
        this.shop = shop;
    }

    public void buy() {
        try {
            lock.lock();
            System.out.println(Thread.currentThread().getName() + " зашел в магазин");
            while (shop.getCars().isEmpty()) {
                System.out.println("Машин нет");
                condition.await();
            }
            shop.getCars().remove(0);
            System.out.println(Thread.currentThread().getName() + " уехал домой на новом авто");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void receive() {
        try {
            lock.lock();
            Thread.sleep(200);
            condition.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
