package restautant;

public class WaitPerson extends Thread {
    private final Restaurant restaurant;

    public WaitPerson(String name, Restaurant restaurant) {
        super(name);
        this.restaurant = restaurant;
    }

    public void placeOrder(Order order) {
        synchronized (this) {
            restaurant.getOrders().add(order);
            notify();
        }
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " на работе");
            while (!isInterrupted()) {
                synchronized (this) {
                    while (restaurant.getOrders().isEmpty()) {
                        System.out.println(Thread.currentThread().getName() + " ожидает, заказов нет");
                        wait();
                    }
                }

                setBusyStatus(this);
                System.out.println(Thread.currentThread().getName() + " принял заказ");
                synchronized (restaurant.getChief()) {
                    restaurant.getChief().notify();
                }

                synchronized (this) {
                    while (restaurant.getFilledOrders().isEmpty()) {
                        wait();
                    }
                    final var cookedOrder = restaurant.getFilledOrders().removeFirst();
                    synchronized (cookedOrder.getCustomer()) {
                        cookedOrder.getCustomer().deliverDish(cookedOrder.getDish());
                        System.out.println(Thread.currentThread().getName() + " отнес заказ " + cookedOrder.getCustomer());
                        cookedOrder.getCustomer().notify();
                    }
                }
                setAbleToWorkStatus(this);
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " закончил работу");
        }
    }

    private void setBusyStatus(WaitPerson waitPerson) {
        restaurant.getWaiters().remove(waitPerson);
    }

    private void setAbleToWorkStatus(WaitPerson waitPerson) {
        synchronized (restaurant.getWaitPersonsLock()) {
            restaurant.getWaiters().add(waitPerson);
            restaurant.getWaitPersonsLock().notify();
        }

    }

    public String[] getMenu() {
        return restaurant.getMenu();
    }
}
