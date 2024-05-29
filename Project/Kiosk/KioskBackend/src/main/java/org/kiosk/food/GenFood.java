package org.kiosk.food;

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





    @Override
    public String toString() {
        return foodtype + "," + ingredientAmounts[0] + "," +  ingredientAmounts[1] + "," +  ingredientAmounts[2] + "," +  ingredientAmounts[3] + "," + ingredientAmounts[4];
    }



}
