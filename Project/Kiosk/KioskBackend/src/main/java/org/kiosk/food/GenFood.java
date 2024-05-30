package org.kiosk.food;

import org.kiosk.Database;
import org.kiosk.FoodType;
import org.kiosk.food.decorators.Cheese;

import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Objects;
import java.util.logging.Logger;

public class GenFood {
    private static final Logger logger = Logger.getLogger(GenFood.class.getName());

    private String foodtype;
    private int[] ingredientAmounts;

    public GenFood(String foodtype, int[] ingredientAmounts) {
        this.foodtype = foodtype;
        this.ingredientAmounts = ingredientAmounts;
    }

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

    public IFood convertToIFood() throws ClassNotFoundException, InstantiationException, IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        IFood food = new Food(FoodType.valueOf(foodtype));
        ArrayList<GenericIngredient> ingredients = Database.returnIndredientByFoodtype(foodtype);
        for (int i = 0; i < ingredientAmounts.length; i++) {
            if (i < ingredients.size()) {
                GenericIngredient ingredient = ingredients.get(i);
                System.out.println(ingredient);
                int j = 0;
                while (j < ingredientAmounts[i]) {
                    food = addDecorator(food, ingredient.getName());
                    j++;
                }
            } else {
                logger.severe("Index " + i + " out of bounds for ingredients list of size " + ingredients.size());
            }
        }
        return food;
    }
    private IFood addDecorator(IFood food, String ingredient) throws ClassNotFoundException, InstantiationException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
        ArrayList<Class<?>> decorators = getListOfDecorators();
        IFood result = food;
        for (Class<?> decorator : decorators) {
            if (decorator.getSimpleName().equals(ingredient)) {
                Constructor<?> constructor = decorator.getConstructor(IFood.class);
                Object decoratorInstance = constructor.newInstance(food);
                if (decoratorInstance instanceof IFood) {
                    result = (IFood) decoratorInstance;
                }
            }
        }
        return result;
    }
    private ArrayList<Class<?>> getListOfDecorators() throws ClassNotFoundException {
        ArrayList<Class<?>> decorators = new ArrayList<>();
        String packageName = "org.kiosk.food.decorators";
        String packagePath = IFood.class.getResource("").getPath() + "decorators";
        File directory = new File(packagePath);

        if (directory.exists() && directory.isDirectory()) {
            for (File file : directory.listFiles()) {
                if (file.isFile() && file.getName().endsWith(".class")) {
                    String className = file.getName().substring(0, file.getName().length() - 6);
                    String fullyQualifiedClassName = packageName + "." + className;
                    Class<?> clazz = Class.forName(fullyQualifiedClassName);
                    decorators.add(clazz);
                }
            }
        }

        return decorators;
    }

    @Override
    public String toString() {
        return foodtype + "," + ingredientAmounts[0] + "," +  ingredientAmounts[1] + "," +  ingredientAmounts[2] + "," +  ingredientAmounts[3] + "," + ingredientAmounts[4];
    }
}
