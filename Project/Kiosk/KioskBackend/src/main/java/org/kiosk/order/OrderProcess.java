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
        logger.info("Order process has started.");
    }

    public void selectFood(FoodType type) {
        if (state == OrderState.START) {
            this.food = new Food(type);
            nextState();
            logger.info("Food selected: " + type);
        } else {
            logger.warning("Food can only be selected at the start of the order process.");
        }
    }

    public void addCheese() throws ClassNotFoundException {
        if (state == OrderState.FOOD_SELECTION || state == OrderState.INGREDIENT_SELECTION) {
            if (Database.checkIngredient(1, 1, food.getFoodType())) { // 1 is the id for cheese
                this.food = new Cheese(food);
                nextState();
                logger.info("Cheese added to the order.");
            } else {
                logger.warning("Not enough cheese in stock.");
            }
        } else {
            logger.warning("Ingredients can only be added after food selection.");
        }
    }

    public void addTomato() throws ClassNotFoundException {
        if (state == OrderState.FOOD_SELECTION || state == OrderState.INGREDIENT_SELECTION) {
            if (Database.checkIngredient(2, 1, food.getFoodType())) { // 2 is the id for tomato
                this.food = new Tomato(food);
                nextState();
                logger.info("Tomato added to the order.");
            } else {
                logger.warning("Not enough tomatoes in stock.");
            }
        } else {
            logger.warning("Ingredients can only be added after food selection.");
        }
    }

    public void addToCart() {
        if (state == OrderState.FOOD_SELECTION || state == OrderState.INGREDIENT_SELECTION) {
            nextState();
            logger.info("Food added to cart.");
        } else {
            logger.warning("Food can only be added to the cart after selection.");
        }
    }

    public void addAnotherItem(boolean anotherItem) {
        if (state == OrderState.CART) {
            if (anotherItem) {
                state = OrderState.FOOD_SELECTION;
                logger.info("Adding another item. Returning to food selection.");
            } else {
                nextState();
                logger.info("No more items to add. Proceeding to payment.");
            }
        } else {
            logger.warning("Can only add another item after adding to cart.");
        }
    }

    public void makePayment(String paymentDetails) {
        if (state == OrderState.CART) {
            boolean paymentAccepted = Database.processPayment(paymentDetails);
            if (paymentAccepted) {
                nextState();
                logger.info("Payment successful.");
            } else {
                state = OrderState.START;
                logger.warning("Payment failed. Returning to start.");
            }
        } else {
            logger.warning("Payment can only be made after adding items to the cart.");
        }
    }

    public void placeOrder(String orderId) {
        if (state == OrderState.PAYMENT_DONE) {
            if (Database.saveOrder((Food) food, food.getFoodType(), orderId)) {
                nextState();
                logger.info("Order placed successfully: " + food.getDescription());
            } else {
                logger.warning("Order placement failed.");
            }
        } else {
            logger.warning("Order can only be placed after payment.");
        }
    }

    private void nextState() {
        if (state != null) {
            state = state.next();
            logger.info("State transitioned to: " + state);
        } else {
            logger.warning("Cannot proceed, the order process has already ended.");
        }
    }
}
