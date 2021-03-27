package Task;

import java.util.Arrays;
import java.util.LinkedList;

public class CarShop extends Thread {
    private final CarSupplier carSupplier;
    private final Object carSeller;
    private final LinkedList<Car> cars;
    private final int sellingTime = 2000;
    private final int workingDay = 20000;
    private final int limit = 10;

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
            synchronized (carSeller) {
                while (cars.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + " обратился к продавцу, но машин пока нет");
                    carSeller.wait();
                }
                Thread.sleep(sellingTime);
                cars.removeFirst();
                System.out.println(Thread.currentThread().getName() + " купил новый автомобиль");
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    public LinkedList<Car> getCars() {
        return cars;
    }

    public CarSupplier getCarSupplier() {
        return carSupplier;
    }

    public Object getCarSeller() {
        return carSeller;
    }
}
