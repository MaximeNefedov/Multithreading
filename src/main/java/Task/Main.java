package Task;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Shop shop = new Shop();

        new Thread(shop::produceCar, "Производитель Toyota").start();

        for (int i = 0; i < 10; i++) {
            Thread.sleep(2000);
            new Thread(shop::buyCar, "Покупатель " + (i + 1)).start();
        }
    }
}
