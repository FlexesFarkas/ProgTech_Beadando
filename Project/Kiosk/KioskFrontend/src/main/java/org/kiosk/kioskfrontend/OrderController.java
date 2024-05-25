package org.kiosk.kioskfrontend;

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
import org.kiosk.order.OrderProcess;
import org.kiosk.order.OrderProcess.*;
import org.kiosk.order.OrderState;
import org.kiosk.order.OrderState.*;
import javafx.scene.control.*;


public class OrderController {

    private Food newFood;
    @FXML
    private Button food1_button;
    @FXML
    private Button food2_button;
    @FXML
    private Button food3_button;
    @FXML
    private Button food4_button;
    @FXML
    private Button Topping1_m;
    @FXML
    private Button Topping2_m;
    @FXML
    private Button Topping3_m;
    @FXML
    private Button Topping4_m;
    @FXML
    private Button Topping5_m;
    @FXML
    private Button Topping1_p;
    @FXML
    private Button Topping2_p;
    @FXML
    private Button Topping3_p;
    @FXML
    private Button Topping4_p;
    @FXML
    private Button Topping5_p;
    @FXML
    private Label ToppingName_1;
    @FXML
    private Label ToppingName_2;
    @FXML
    private Label ToppingName_3;
    @FXML
    private Label ToppingName_4;
    @FXML
    private Label ToppingName_5;
    @FXML
    private Slider ToppingSlider_1;
    @FXML
    private Slider ToppingSlider_2;
    @FXML
    private Slider ToppingSlider_3;
    @FXML
    private Slider ToppingSlider_4;
    @FXML
    private Slider ToppingSlider_5;

    private OrderState os;
    Alert a = new Alert(Alert.AlertType.WARNING);




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
        Topping1_m.setVisible(false);
        Topping2_m.setVisible(false);
        Topping3_m.setVisible(false);
        Topping4_m.setVisible(false);
        Topping5_m.setVisible(false);
        Topping1_p.setVisible(false);
        Topping2_p.setVisible(false);
        Topping3_p.setVisible(false);
        Topping4_p.setVisible(false);
        Topping5_p.setVisible(false);
        ToppingName_1.setVisible(false);
        ToppingName_2.setVisible(false);
        ToppingName_3.setVisible(false);
        ToppingName_4.setVisible(false);
        ToppingName_5.setVisible(false);
        ToppingSlider_1.setVisible(false);
        ToppingSlider_2.setVisible(false);
        ToppingSlider_3.setVisible(false);
        ToppingSlider_4.setVisible(false);
        ToppingSlider_5.setVisible(false);

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
        if (food1_button.getText()=="-"){
            TilePane r = new TilePane();
            EventHandler<ActionEvent> event3 = new
                    EventHandler<ActionEvent>() {
                        public void handle(ActionEvent e)
                        {
                            // set alert type
                            a.setAlertType(Alert.AlertType.WARNING);

                            // set content text
                            a.setContentText("Warning Dialog");

                            // show the dialog
                            a.show();
                        }
                    };
        }
        Topping1_m.setVisible(true);
        Topping2_m.setVisible(true);
        Topping3_m.setVisible(true);
        Topping4_m.setVisible(true);
        Topping5_m.setVisible(true);
        Topping1_p.setVisible(true);
        Topping2_p.setVisible(true);
        Topping3_p.setVisible(true);
        Topping4_p.setVisible(true);
        Topping5_p.setVisible(true);

        ToppingName_1.setVisible(true);
        ToppingName_1.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(0)).get(0).getName()));
        ToppingName_2.setVisible(true);
        ToppingName_2.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(0)).get(1).getName()));
        ToppingName_3.setVisible(true);
        ToppingName_3.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(0)).get(2).getName()));
        ToppingName_4.setVisible(true);
        ToppingName_4.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(0)).get(3).getName()));
        ToppingName_5.setVisible(true);
        ToppingName_5.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(0)).get(4).getName()));

    }



    public void Food2_Selected(MouseEvent mouseEvent) {
        Topping1_m.setVisible(true);
        Topping2_m.setVisible(true);
        Topping3_m.setVisible(true);
        Topping4_m.setVisible(true);
        Topping5_m.setVisible(true);
        Topping1_p.setVisible(true);
        Topping2_p.setVisible(true);
        Topping3_p.setVisible(true);
        Topping4_p.setVisible(true);
        Topping5_p.setVisible(true);

        ToppingName_1.setVisible(true);
        ToppingName_1.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(1)).get(0).getName()));
        ToppingName_2.setVisible(true);
        ToppingName_2.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(1)).get(1).getName()));
        ToppingName_3.setVisible(true);
        ToppingName_3.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(1)).get(2).getName()));
        ToppingName_4.setVisible(true);
        ToppingName_4.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(1)).get(3).getName()));
        ToppingName_5.setVisible(true);
        ToppingName_5.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(1)).get(4).getName()));
    }

    public void Food3_Selected(MouseEvent mouseEvent) {
        Topping1_m.setVisible(true);
        Topping2_m.setVisible(true);
        Topping3_m.setVisible(true);
        Topping4_m.setVisible(true);
        Topping5_m.setVisible(true);
        Topping1_p.setVisible(true);
        Topping2_p.setVisible(true);
        Topping3_p.setVisible(true);
        Topping4_p.setVisible(true);
        Topping5_p.setVisible(true);

        ToppingName_1.setVisible(true);
        ToppingName_1.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(2)).get(0).getName()));
        ToppingName_2.setVisible(true);
        ToppingName_2.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(2)).get(1).getName()));
        ToppingName_3.setVisible(true);
        ToppingName_3.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(2)).get(2).getName()));
        ToppingName_4.setVisible(true);
        ToppingName_4.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(2)).get(3).getName()));
        ToppingName_5.setVisible(true);
        ToppingName_5.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(2)).get(4).getName()));
    }

    public void Food4_Selected(MouseEvent mouseEvent) {
        if (food4_button.getText().equals("-")) {
            // Megjelenítjük a figyelmeztető ablakot
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("A jelen termék még nem elérhető!.");
            alert.show();

            // Lefuttatjuk az initialize metódust
            initialize();
        }else{
        Topping1_m.setVisible(true);
        Topping2_m.setVisible(true);
        Topping3_m.setVisible(true);
        Topping4_m.setVisible(true);
        Topping5_m.setVisible(true);
        Topping1_p.setVisible(true);
        Topping2_p.setVisible(true);
        Topping3_p.setVisible(true);
        Topping4_p.setVisible(true);
        Topping5_p.setVisible(true);

        ToppingName_1.setVisible(true);
        ToppingName_1.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(3)).get(0).getName()));
        ToppingName_2.setVisible(true);
        ToppingName_2.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(3)).get(1).getName()));
        ToppingName_3.setVisible(true);
        ToppingName_3.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(3)).get(2).getName()));
        ToppingName_4.setVisible(true);
        ToppingName_4.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(3)).get(3).getName()));
        ToppingName_5.setVisible(true);
        ToppingName_5.setText(String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(3)).get(4).getName()));}
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

