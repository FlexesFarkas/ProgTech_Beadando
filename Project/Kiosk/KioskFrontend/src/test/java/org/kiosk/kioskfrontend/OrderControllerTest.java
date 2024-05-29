package org.kiosk.kioskfrontend;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;
import org.kiosk.Database;
import org.kiosk.Database.*;
import org.kiosk.FoodType.*;
import org.kiosk.food.Food;
import org.kiosk.food.GenFood;
import org.kiosk.food.IFood;
import org.kiosk.order.OrderProcess;
import org.kiosk.order.OrderProcess.*;
import org.kiosk.order.OrderState;
import org.kiosk.order.OrderState.*;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.Delayed;

import static org.kiosk.Database.*;

class OrderControllerTest {


    @Test
    void removeItemsFromCartWhenCartIsNotEmpty() throws ExceptionInInitializerError{
        OrderController orderController = new OrderController();
        orderController.initialize();
        GenFood testFood = new GenFood("Burger", new int[]{1, 2, 3, 4, 5});
        orderController.cartString += testFood.getFoodtype() + Arrays.toString(testFood.getIngredientAmounts());
        orderController.cartList.add(testFood);
        orderController.RemoveItemsFromCart();
        Assertions.assertTrue(orderController.cartList.isEmpty(), "Cart is empty after items removed from cart");
    }

    @Test
    void removeItemsFromCartWhenCartIsEmpty() throws ExceptionInInitializerError{
        OrderController orderController = new OrderController();
        orderController.initialize();
        GenFood testFood = null;
        orderController.cartString = "";
        orderController.cartList = null;
        orderController.RemoveItemsFromCart();
        Assertions.assertTrue(orderController.cartList.isEmpty(), "Cart is empty after before and after running RemoveItemsFromCart");
    }
    @Test
    void AddGoodItemsToCart() throws ExceptionInInitializerError{
        OrderController orderController = new OrderController();
        orderController.initialize();
        orderController.ftype = "Burger";
        orderController.ingredientAmounts = new int[]{1,2,3,4,5};
        orderController.AddFoodToCart();
        Assertions.assertEquals("Burger",orderController.cartList.getLast().getFoodtype());
        Assertions.assertEquals(new int[]{1,2,3,4,5},orderController.cartList.getLast().getIngredientAmounts());
    }
    @Test
    void AddEmptyIngredientItemsToCart() throws ExceptionInInitializerError{
        OrderController orderController = new OrderController();
        orderController.initialize();
        orderController.ftype = "testFoodType";
        orderController.ingredientAmounts = new int[]{0,0,0,0,0};
        orderController.AddFoodToCart();
        Assertions.assertEquals("",orderController.ftype);
        Assertions.assertEquals(new int[]{1,2,3,4,5},orderController.cartList.getLast().getIngredientAmounts());
    }

    @Test
    void FoodNumber1Selected() throws ExceptionInInitializerError{
        OrderController orderController = new OrderController();
        orderController.initialize();
        orderController.ftype = "";
        orderController.FoodSelected(0);
        Assertions.assertEquals("Burger",orderController.ftype);
    }

}