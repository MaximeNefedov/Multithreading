package Task;

public class Customer extends Thread {
    private final CarShop carShop;
    public Customer(CarShop carShop, String name) {
        super(name);
        this.carShop = carShop;
    }

    @Override
    public void run() {
        carShop.sellCar();
    }
}
