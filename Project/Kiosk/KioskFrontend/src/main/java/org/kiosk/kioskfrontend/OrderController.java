package org.kiosk.kioskfrontend;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseDragEvent;
import javafx.scene.input.MouseEvent;
import org.kiosk.Database;
import org.kiosk.Database.*;
import org.kiosk.FoodType.*;
import org.kiosk.order.OrderProcess.*;
import org.kiosk.order.OrderState;
import org.kiosk.order.OrderState.*;

public class OrderController {
    @FXML
    private Button food1_button;
    @FXML
    private Button food2_button;
    @FXML
    private Button food3_button;
    @FXML
    private Button food4_button;
    private OrderState orderState;



    @FXML
    public void initialize() {
        FoodButtonColorChange();
        foodtypeInit();
    }
    public void foodtypeInit(){
        food1_button.setText(Database.getFoodTypes().get(0));
        food2_button.setText(Database.getFoodTypes().get(1));
        food3_button.setText(Database.getFoodTypes().get(2));
        food4_button.setText(Database.getFoodTypes().get(3));
    }


    public void FoodButtonColorChange() {
        food1_button.setOnMouseEntered(event -> {
            food1_button.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        food1_button.setOnMouseExited(event -> {
            food1_button.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        food2_button.setOnMouseEntered(event -> {
            food2_button.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        food2_button.setOnMouseExited(event -> {
            food2_button.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        food3_button.setOnMouseEntered(event -> {
            food3_button.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        food3_button.setOnMouseExited(event -> {
            food3_button.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        food4_button.setOnMouseEntered(event -> {
            food4_button.setStyle("-fx-background-color: #ff0000; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        food4_button.setOnMouseExited(event -> {
            food4_button.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
    }
    public void Food1_Selected(MouseEvent mouseEvent) {
        
    }

    public void Food2_Selected(MouseEvent mouseEvent) {
    }

    public void Food3_Selected(MouseEvent mouseEvent) {
    }

    public void Food4_Selected(MouseEvent mouseEvent) {
    }

    public void Topping1_minus(MouseEvent mouseEvent) {
    }

    public void Topping2_minus(MouseEvent mouseEvent) {
    }

    public void Topping3_minus(MouseEvent mouseEvent) {
    }

    public void Topping4_minus(MouseEvent mouseEvent) {
    }

    public void Topping5_minus(MouseEvent mouseEvent) {
    }

    public void Topping1_plus(MouseEvent mouseEvent) {
    }

    public void Topping2_plus(MouseEvent mouseEvent) {
    }

    public void Topping3_plus(MouseEvent mouseEvent) {
    }

    public void Topping4_plus(MouseEvent mouseEvent) {
    }

    public void Topping5_plus(MouseEvent mouseEvent) {
    }
}
