package Task2;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Shop shop = new Shop();

        new Thread(shop::sellCar, "Производитель Toyota").start();

        for (int i = 0; i < 5; i++) {
            new Thread(shop::buyCar, "Покупатель " + (i + 1)).start();
            Thread.sleep(1000);
        }
    }
}
