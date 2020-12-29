package Task2;

public class Factory {
    private Shop shop;
    private static final int DEVELOPMENT_TIME = 3000;

    public Factory(Shop shop) {
        this.shop = shop;
    }

    public void addCar() {
        try {
            Thread.sleep(DEVELOPMENT_TIME);
            shop.getCars().add(new Car());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
