package org.kiosk.food;

public abstract class IngridientDecorator implements IFood{
    protected IFood food;

    public IngridientDecorator(IFood food) {
        this.food = food;
    }
}
