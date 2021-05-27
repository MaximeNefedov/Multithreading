package restautant;

public class Order {
    private final Customer customer;
    private final WaitPerson waitPerson;
    private Dish dish;
    private final String dishName;

    public Order(Customer customer, WaitPerson waitPerson, String dishName) {
        this.customer = customer;
        this.waitPerson = waitPerson;
        this.dishName = dishName;
    }

    @Override
    public String toString() {
        return "Заказ: + " + dish  + " на имя " + "\"" + customer + "\"";
    }

    public Dish getDish() {
        return dish;
    }

    public void setDish(Dish dish) {
        this.dish = dish;
    }

    public String getDishName() {
        return dishName;
    }

    public Customer getCustomer() {
        return customer;
    }

    public WaitPerson getWaitPerson() {
        return waitPerson;
    }
}
