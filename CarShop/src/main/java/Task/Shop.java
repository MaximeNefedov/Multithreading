package Task;

import java.util.ArrayList;
import java.util.List;

public class Shop {
    private List<Car> cars;
    private static final int DEVELOPMENT_TIME = 1000;
    private static int counter = 10;
    private static final int PURCHASE_TIME = 1500;

    public Shop() {
        cars = new ArrayList<>(1);
    }

    public synchronized void buyCar() {
        System.out.println(Thread.currentThread().getName() + " зашёл в автосалон");
        while (cars.size() == 0) {
            System.out.println("Машин нет");
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            Thread.sleep(PURCHASE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + " уехал домой на новом автомобиле");
        cars.remove(0);
        notify();
    }

    public synchronized void produceCar() {

        while (counter != 0) {
            while (cars.size() == 1) {
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(DEVELOPMENT_TIME);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            cars.add(new Car());
            System.out.println("Производитель Toyota выпустил 1 авто");
            counter--;
            notify();
        }
    }
}
