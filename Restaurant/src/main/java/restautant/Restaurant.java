package restautant;

import java.util.*;

public class Restaurant extends Thread {
    private final Object waitPersonsLock = new Object();
    private final Chief chief;
    private final Queue<Order> orders;
    private final LinkedList<Order> filledOrders;
    private final LinkedList<WaitPerson> waiters;
    private final String[] menu = new String[]{"Рыба", "Мясо", "Тушеные овощи", "Вино", "Курица", "Штрудель"};
    private final int customersLimit;
    private int peopleServed = 0;
    private final Object administrator;
    private final int closingTime = 500;

    public Restaurant(String name, int nOfWaitPersons, int customersLimit) {
        super(name);
        this.customersLimit = customersLimit;
        waiters = new LinkedList<>();
        for (int i = 0; i < nOfWaitPersons; i++) {
            waiters.add(new WaitPerson("Официант " + (i + 1), this));
        }
        filledOrders = new LinkedList<>();
        chief = new Chief("Повар", this);
        orders = new ArrayDeque<>();
        administrator = new Object();
    }

    public void exit() {
        synchronized (administrator) {
            peopleServed++;
            if (peopleServed == customersLimit) {
                administrator.notify();
            }
        }
    }

    @Override
    public void run() {
        try {
            for (WaitPerson waiter : waiters) {
                waiter.start();
            }
            chief.start();
            for (int i = 0; i < customersLimit; i++) {
                new Customer("Клиент " + (i + 1), this).start();
                Thread.sleep(400);
            }

            synchronized (administrator) {
                while (peopleServed != customersLimit) {
                    administrator.wait();
                }
            }

            for (WaitPerson waiter : waiters) {
                waiter.interrupt();
            }

            chief.interrupt();

            Thread.sleep(closingTime);
            System.out.println(Thread.currentThread().getName() + " закончил работу");

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    public Object getWaitPersonsLock() {
        return waitPersonsLock;
    }

    public WaitPerson getWaitPerson() {
        // если официант не занят, то он назначается посетителю
        WaitPerson waitPerson = null;
        try {
            synchronized (waitPersonsLock) {
                while (waiters.isEmpty()) {
                    System.out.println(Thread.currentThread().getName() + " ожидает, свободных официантов нет");
                    waitPersonsLock.wait();
                }
                waitPerson = waiters.removeFirst();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return waitPerson;
    }

    public LinkedList<WaitPerson> getWaiters() {
        return waiters;
    }

    public String[] getMenu() {
        return menu;
    }

    public Chief getChief() {
        return chief;
    }

    public Queue<Order> getOrders() {
        return orders;
    }

    public LinkedList<Order> getFilledOrders() {
        return filledOrders;
    }
}
