package restautant;

import java.util.Random;

public class Customer extends Thread {
    private final Restaurant restaurant;
    private Dish dish;
    private static final Random RANDOM = new Random();
    private final int eatingTime = 2000;

    public Customer(String name, Restaurant restaurant) {
        super(name);
        this.restaurant = restaurant;
    }

    public void deliverDish(Dish dish) {
        this.dish = dish;
    }

    @Override
    public void run() {
        try {
            // Свободный официант подходит к клиенту
            final var waitPerson = restaurant.getWaitPerson();
            // и дает ему меню
            String[] menu = waitPerson.getMenu();
            System.out.println(Thread.currentThread().getName() + " получил меню");
            String dishName = menu[RANDOM.nextInt(menu.length)];
            System.out.println(Thread.currentThread().getName() + " заказал " + "\""+ dishName + "\"");
            waitPerson.placeOrder(new Order(this, waitPerson, dishName));
            synchronized (this) {
                while (dish == null) {
                    wait();
                }
                System.out.println(Thread.currentThread().getName() + " начал употребление: " + "\"" + dish + "\"");
                Thread.sleep(eatingTime);
                System.out.println(Thread.currentThread().getName() + " вышел из ресторана");
                restaurant.exit();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
