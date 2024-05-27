package org.kiosk.food;

public class GenFood {
    private String foodtype;
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
}
