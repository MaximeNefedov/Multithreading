package Task2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Shop {
    private List<Car> cars;
    private static final int DEVELOPMENT_TIME = 2000;
//    private static int counter = 10;
    private static final int PURCHASE_TIME = 1500;
    private Lock lock = new ReentrantLock(true);
    private Condition condition = lock.newCondition();

    public Shop() {
        cars = new ArrayList<>(1);
    }

    public void buyCar() {
        try {
            System.out.println(Thread.currentThread().getName() + " зашёл в автосалон");
            lock.lock();
            while (cars.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " обратился к продавцу, но машин нет");
                condition.await();
            }
            Thread.sleep(PURCHASE_TIME);
            System.out.println(Thread.currentThread().getName() + " уехал домой на новом автомобиле");
            cars.remove(0);
            condition.signal();
        }
        catch (InterruptedException e) {
                e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

        public void produceCar() {
        try {
            lock.lock();
            while (true) {
                while (cars.size() == 1) {
                    condition.await();
                }

                Thread.sleep(DEVELOPMENT_TIME);

                cars.add(new Car());
                System.out.println("Производитель Toyota выпустил 1 авто");
//                counter--;
                condition.signalAll();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }
}
