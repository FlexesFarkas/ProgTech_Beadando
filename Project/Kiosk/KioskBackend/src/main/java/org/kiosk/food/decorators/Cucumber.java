package org.kiosk.food.decorators;

import org.kiosk.FoodType;
import org.kiosk.food.IFood;
import org.kiosk.food.IngridientDecorator;

import java.util.ArrayList;

public class Cucumber extends IngridientDecorator {
    private static String ingredientName;
    private static int ingredientPrice;
    private static ArrayList<FoodType> foodTypes;
    public Cucumber(IFood food) throws ClassNotFoundException {
        super(food);
        initialize();
    }

    private static void initialize() throws ClassNotFoundException {
        ingredientName = (Class.forName("org.kiosk.food.decorators.Cucumber")).getName().split("\\.")[4];
        ingredientPrice = 150;
        foodTypes = new ArrayList<FoodType>();
        foodTypes.add(FoodType.Burger);
    }

    @Override
    public String getDescription() {
        return food.getDescription() + "," + ingredientName;
    }

    @Override
    public double getCost() {
        return food.getCost() + ingredientPrice;
    }

    @Override
    public FoodType getFoodType() {
        return food.getFoodType();
    }

    @Override
    public String getName() {
        return ingredientName;
    }

    public static FoodType getIngredientTypes(int number) throws ClassNotFoundException {
        initialize();
        return foodTypes.get(number);
    }

    public static int getIngredientTypesLength() throws ClassNotFoundException {
        initialize();
        return foodTypes.size();
    }

    public static String getIngredientName() throws ClassNotFoundException {
        initialize();
        return ingredientName;
    }

    public static int getIngredientPrice() throws ClassNotFoundException {
        initialize();
        return ingredientPrice;
    }
}
