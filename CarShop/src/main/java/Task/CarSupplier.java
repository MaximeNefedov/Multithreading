package Task;

public class CarSupplier extends Thread {
    private final CarShop carShop;
    private final int developmentTime = 3000;

    public CarSupplier(CarShop carShop, String name) {
        super(name);
        this.carShop = carShop;
    }

    @Override
    public void run() {
        try {
            System.out.println(Thread.currentThread().getName() + " на работе");
            while (!isInterrupted()) {
                Thread.sleep(developmentTime);
                carShop.getCars().add(new Car());
                System.out.println(Thread.currentThread().getName() + " выпустил новый автомобиль");
                synchronized (carShop.getCarSeller()) {
                    carShop.getCarSeller().notify();
                }
            }
        } catch (InterruptedException e) {
            System.out.println(Thread.currentThread().getName() + " закончил работу");
        }
    }
}
