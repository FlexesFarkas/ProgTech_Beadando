package org.kiosk.food;

public class GenericIngredient {
    private String name;
    private double price;

    public GenericIngredient(String name, double price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return "Ingredient [name=" + name + ", price=" + price + "]";
    }
}
