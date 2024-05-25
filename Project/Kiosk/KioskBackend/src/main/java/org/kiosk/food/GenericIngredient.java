package org.kiosk.food;

public class GenericIngredient {
    private String name;
    private double price;
    private  int amount;

    public GenericIngredient(String name, double price) {
        this.name = name;
        this.price = price;
    }
    public GenericIngredient(String name, double price,int amount) {
        this.name = name;
        this.price = price;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }
    public double getPrice() {
        return price;
    }
    public  int getAmount(){return amount;}

    @Override
    public String toString() {
        return "Ingredient [name=" + name + ", price=" + price + "]";
    }
}
