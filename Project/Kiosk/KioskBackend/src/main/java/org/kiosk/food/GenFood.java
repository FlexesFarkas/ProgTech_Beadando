package org.kiosk.food;

public class GenFood {
    public String getFoodtype() {
        return foodtype;
    }

    public void setFoodtype(String foodtype) {
        this.foodtype = foodtype;
    }

    private String foodtype;

    public int getIng5() {
        return ing5;
    }

    public void setIng5(int ing5) {
        this.ing5 = ing5;
    }

    public int getIng4() {
        return ing4;
    }

    public void setIng4(int ing4) {
        this.ing4 = ing4;
    }

    public int getIng3() {
        return ing3;
    }

    public void setIng3(int ing3) {
        this.ing3 = ing3;
    }

    public int getIng2() {
        return ing2;
    }

    public void setIng2(int ing2) {
        this.ing2 = ing2;
    }

    public int getIng1() {
        return ing1;
    }

    public void setIng1(int ing1) {
        this.ing1 = ing1;
    }

    private int ing1;
    private int ing2;
    private int ing3;
    private int ing4;
    private int ing5;


    public GenFood(String foodtype, int ing1, int ing2, int ing3, int ing4, int ing5) {
        this.foodtype = foodtype;
        this.ing1 = ing1;
        this.ing2 = ing2;
        this.ing3 = ing3;
        this.ing4 = ing4;
        this.ing5 = ing5;
    }

    @Override
    public String toString() {
        return foodtype + "," + ing1 + "," + ing2 + "," + ing3 + "," + ing4 + "," + ing5;
    }

    public int returnIngredientAmount(int index){
        int result = switch (index) {
            case 0 -> getIng1();
            case 1 -> getIng2();
            case 2 -> getIng3();
            case 3 -> getIng4();
            case 4 -> getIng5();
            default -> -1;
        };
        return result;
    }

}
