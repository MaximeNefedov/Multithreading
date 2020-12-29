package Task2;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Shop {
    private List<Car> cars;
    //    private static int counter = 10;
    private Customer customer = new Customer(this);
    private Factory factory = new Factory(this);

    public Shop() {
        cars = new ArrayList<>(1);
    }

    public List<Car> getCars() {
        return cars;
    }

    public void sellCar() {
        while (true) {
            factory.addCar();
            System.out.println("Производитель Toyota выпустил 1 авто");
            customer.receive();
        }
    }

    public void buyCar() {
        customer.buy();
    }
}