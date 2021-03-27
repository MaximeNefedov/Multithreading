package Task2_Lock;

public class Main {
    public static void main(String[] args) {
        final var carShop = new CarShop("Магазин автомобилей");
        carShop.start();
    }
}
