package restautant;

import java.util.Collections;
import java.util.Map;

public class Chief extends Thread {
    private final Restaurant restaurant;
    //    private final int cookingTime = 3000;
    private final Map<String, Long> cookingTimes = Collections.unmodifiableMap(Map.of(
            "Рыба", 3000L,
            "Мясо", 4000L,
            "Тушеные овощи", 2500L,
            "Вино", 700L,
            "Курица", 3100L,
            "Штрудель", 5000L
    ));

    public Chief(String name, Restaurant restaurant) {
        super(name);
        this.restaurant = restaurant;
    }

    private long getCookingTime(String name) {
        return cookingTimes.get(name);
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " на работе");
            while (!isInterrupted()) {
                synchronized (this) {
                    while (restaurant.getOrders().isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " ждет новых заказов");
                        wait();
                    }
                    System.out.println(Thread.currentThread().getName() + " начал готовить заказ");
                    final var order = restaurant.getOrders().poll();
                    if (order != null) {
                        Thread.sleep(getCookingTime(order.getDishName()));

                        order.setDish(new Dish(order.getDishName()));

                        restaurant.getFilledOrders().add(order);
                        synchronized (order.getWaitPerson()) {
                            System.out.println(Thread.currentThread().getName() + " приготовил " + "\"" + order.getDish() + "\"");
                            order.getWaitPerson().notify();
                        }
                    }
                }
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " закончил работу");
        }
    }
}
