package org.kiosk.order;

import org.kiosk.Database;
import org.kiosk.FoodType;
import org.kiosk.food.Food;
import org.kiosk.food.IFood;
import org.kiosk.food.decorators.Cheese;
import org.kiosk.food.decorators.Tomato;

import java.util.logging.Logger;

public class OrderProcess {
    private OrderState state;
    private IFood food;
    private static final Logger logger = Logger.getLogger(OrderProcess.class.getName());

    public OrderProcess() {
        state = OrderState.START;
        logger.info("A rendelési folyamat elkezdődött.");
    }

    public void selectFood(FoodType type) {
        if (state == OrderState.START) {
            this.food = new Food(type);
            nextState();
            logger.info("Az étel kiválasztva: " + type);
        } else {
            logger.warning("Az ételt csak a rendelési folyamat kezdetén lehet kiválasztani.");
        }
    }

    public void addCheese() {
        if (state == OrderState.FOOD_SELECTION || state == OrderState.INGREDIENT_SELECTION) {
            if (Database.checkIngredient(1, 1, food.getFoodType())) { // 1 is the id for cheese
                this.food = new Cheese(food);
                nextState();
                logger.info("Sajt hozzáadva a rendeléshez.");
            } else {
                logger.warning("Nincs elég sajt a raktárban.");
            }
        } else {
            logger.warning("Hozzávalót csak az étel kiválasztása után lehet hozzáadni.");
        }
    }

    public void addTomato() {
        if (state == OrderState.FOOD_SELECTION || state == OrderState.INGREDIENT_SELECTION) {
            if (Database.checkIngredient(2, 1, food.getFoodType())) { // 2 is the id for tomato
                this.food = new Tomato(food);
                nextState();
                logger.info("Paradicsom hozzáadva a rendeléshez.");
            } else {
                logger.warning("Nincs elég paradicsom a raktárban.");
            }
        } else {
            logger.warning("Hozzávalót csak az étel kiválasztása után lehet hozzáadni.");
        }
    }

    public void placeOrder(String orderId) {
        if (state == OrderState.INGREDIENT_SELECTION) {
            if (Database.saveOrder((Food) food, food.getFoodType(), orderId)) {
                nextState();
                logger.info("A rendelés sikeresen leadva: " + food.getDescription());
            } else {
                logger.warning("A rendelés nem sikerült.");
            }
        } else {
            logger.warning("A rendelést csak a hozzávalók kiválasztása után lehet leadni.");
        }
    }

    private void nextState() {
        if (state != null) {
            state = state.next();
            logger.info("Az állapot átváltva: " + state);
        } else {
            logger.warning("Nem lehet továbblépni, a rendelési folyamat már befejeződött.");
        }
    }
}
