package restautant;

public class Dish {
    private final String name;

    public Dish(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
