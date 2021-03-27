package Task2_Lock;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class CarShop extends Thread {
    private final CarSupplier carSupplier;
    private final Object carSeller;
    private final LinkedList<Car> cars;
    private final int sellingTime = 2000;
    private final int workingDay = 20000;
    private final int limit = 10;
    private final ReentrantLock sellerLock = new ReentrantLock(true);
    private final Condition condition = sellerLock.newCondition();

    public CarShop(String name) {
        super(name);
        carSupplier = new CarSupplier(this, "Поставщик машин");
        carSeller = new Object();
        cars = new LinkedList<>(Arrays.asList(new Car(), new Car()));
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " открылся");
            carSupplier.start();
            for (int i = 0; i < limit; i++) {
                new Customer(this, "Покупатель " + (i + 1)).start();
                Thread.sleep(1000);
            }
            Thread.sleep(workingDay);
            carSupplier.interrupt();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void sellCar() {
        try {
            System.out.println(Thread.currentThread().getName() + " зашел в магазин");
            sellerLock.lock();
            while (cars.isEmpty()) {
                System.out.println(Thread.currentThread().getName() + " обратился к продавцу, но машин пока нет");
                condition.await();
            }
            Thread.sleep(sellingTime);
            cars.removeFirst();
            System.out.println(Thread.currentThread().getName() + " купил новый автомобиль");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            sellerLock.unlock();
        }

    }

    public ReentrantLock getSellerLock() {
        return sellerLock;
    }

    public Condition getCondition() {
        return condition;
    }

    public LinkedList<Car> getCars() {
        return cars;
    }
}
