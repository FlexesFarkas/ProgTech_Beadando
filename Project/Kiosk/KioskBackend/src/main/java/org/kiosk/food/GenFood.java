package org.kiosk.food;

import org.kiosk.Database;
import org.kiosk.FoodType;
import org.kiosk.food.decorators.Cheese;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;

public class GenFood {
    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    public int[] getIngredientAmounts() {
        return ingredientAmounts;
    }

    public void setIngredientAmounts(int[] ingredientAmounts) {
        this.ingredientAmounts = ingredientAmounts;
    }

    private String foodtype;
    private int[] ingredientAmounts;

    public GenFood(String foodtype, int[] ingredientAmounts) {
        this.foodtype = foodtype;
        this.ingredientAmounts = ingredientAmounts;
    }

    public IFood convertToIFood() throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        IFood food = new Food(FoodType.valueOf(foodtype));
        for (int i = 0; i < ingredientAmounts.length; i++) {
            String ingredient = Database.returnIndredientNameByFoodtype(foodtype, i);
            food = addDecorator(food, ingredient);
        }
        return food;
    }

    private IFood addDecorator(IFood food, String ingredient) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
        ArrayList<Class<?>> decorators = getListOfDecorators();
        IFood result = null;
        for (Class<?> decorator : decorators) {
            if (decorator.getName().equals(ingredient)) {
                Object decoratorInstance = decorator.newInstance();
                if (decoratorInstance instanceof IFood) {
                    IngridientDecorator ingridientDecorator = (IngridientDecorator) decoratorInstance;
                    ingridientDecorator.food = food;
                    result = ingridientDecorator;
                }
            }
        }
        return result;
    }
    
    private ArrayList<Class<?>> getListOfDecorators() throws ClassNotFoundException {
        ArrayList<Class<?>> decorators = new ArrayList<>();
        String packagePath = IFood.class.getResource("") + "decorators";
        File directory = new File(packagePath.substring(5));
        for (File file : directory.listFiles()) {
            if (file.isFile() && file.getName().endsWith(".class")) {
                String[] fileNames = file.getName().split(" \\.");
                String className = fileNames[fileNames.length - 1].replace(".class", "");
                Class<?> clazz = Class.forName(className);
                decorators.add(clazz);
            }
        }
        return decorators;
    }

    @Override
    public String toString() {
        return foodtype + "," + ingredientAmounts[0] + "," +  ingredientAmounts[1] + "," +  ingredientAmounts[2] + "," +  ingredientAmounts[3] + "," + ingredientAmounts[4];
    }



}
