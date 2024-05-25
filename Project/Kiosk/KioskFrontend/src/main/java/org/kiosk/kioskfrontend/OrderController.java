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
    @FXML
    private Label inum_1;
    @FXML
    private Label inum_2;
    @FXML
    private Label inum_3;
    @FXML
    private Label inum_4;
    @FXML
    private Label inum_5;
    @FXML
    private Label ingredient_label;

    private OrderState os;
    Alert a = new Alert(Alert.AlertType.WARNING);
    private int i1;
    private int i2;
    private int i3;
    private  int i4;
    private int i5;
    private String ftype;




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
        inum_1.setVisible(false);
        inum_2.setVisible(false);
        inum_3.setVisible(false);
        inum_4.setVisible(false);
        inum_5.setVisible(false);
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
        ingredient_label.setText("");

    }


    public void FoodButtonColorChange() {
        food1_button.setOnMouseEntered(event -> {
            food1_button.setStyle("-fx-background-color: coral; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        food1_button.setOnMouseExited(event -> {
            food1_button.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        food2_button.setOnMouseEntered(event -> {
            food2_button.setStyle("-fx-background-color:coral; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        food2_button.setOnMouseExited(event -> {
            food2_button.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        food3_button.setOnMouseEntered(event -> {
            food3_button.setStyle("-fx-background-color: coral; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        food3_button.setOnMouseExited(event -> {
            food3_button.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        food4_button.setOnMouseEntered(event -> {
            food4_button.setStyle("-fx-background-color: coral; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        food4_button.setOnMouseExited(event -> {
            food4_button.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        Topping1_m.setOnMouseEntered(event -> {
            Topping1_m.setStyle("-fx-background-color: coral; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping1_m.setOnMouseExited(event -> {
            Topping1_m.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        Topping2_m.setOnMouseEntered(event -> {
            Topping2_m.setStyle("-fx-background-color: coral; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping2_m.setOnMouseExited(event -> {
            Topping2_m.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        Topping3_m.setOnMouseEntered(event -> {
            Topping3_m.setStyle("-fx-background-color: coral; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping3_m.setOnMouseExited(event -> {
            Topping3_m.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping4_m.setOnMouseEntered(event -> {
            Topping4_m.setStyle("-fx-background-color: coral; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping4_m.setOnMouseExited(event -> {
            Topping4_m.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });Topping5_m.setOnMouseEntered(event -> {
            Topping5_m.setStyle("-fx-background-color: coral; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping5_m.setOnMouseExited(event -> {
            Topping5_m.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });



        Topping1_p.setOnMouseEntered(event -> {
            Topping1_p.setStyle("-fx-background-color: lightgreen; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping1_p.setOnMouseExited(event -> {
            Topping1_p.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        Topping2_p.setOnMouseEntered(event -> {
            Topping2_p.setStyle("-fx-background-color: lightgreen; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping2_p.setOnMouseExited(event -> {
            Topping2_p.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });

        Topping3_p.setOnMouseEntered(event -> {
            Topping3_p.setStyle("-fx-background-color: lightgreen; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping3_p.setOnMouseExited(event -> {
            Topping3_p.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping4_p.setOnMouseEntered(event -> {
            Topping4_p.setStyle("-fx-background-color: lightgreen; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping4_p.setOnMouseExited(event -> {
            Topping4_p.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });Topping5_p.setOnMouseEntered(event -> {
            Topping5_p.setStyle("-fx-background-color: lightgreen; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
        Topping5_p.setOnMouseExited(event -> {
            Topping5_p.setStyle("-fx-background-color: #ffcccc; -fx-background-radius: 45; -fx-border-color: #b30000; -fx-border-radius: 45; -fx-border-width: 2;");
        });
    }

    public void Food1_Selected(MouseEvent mouseEvent) {
        ResetIngredients();
        if (food1_button.getText().equals("-")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("A jelen termék még nem elérhető!.");
            alert.show();
            initialize();
        }else{
            FoodSelected(0);
        }
    }



    public void Food2_Selected(MouseEvent mouseEvent) {
        ResetIngredients();
        if (food2_button.getText().equals("-")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("A jelen termék még nem elérhető!.");
            alert.show();
            initialize();
        }else {
            FoodSelected(1);
        }
    }

    public void Food3_Selected(MouseEvent mouseEvent) {
        ResetIngredients();
        if (food3_button.getText().equals("-")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("A jelen termék még nem elérhető!.");
            alert.show();
            initialize();
        }else {
            FoodSelected(2);
        }
    }

    public void Food4_Selected(MouseEvent mouseEvent) {
        ResetIngredients();
        if (food4_button.getText().equals("-")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("A jelen termék még nem elérhető!.");
            alert.show();
            initialize();
        }else{
            FoodSelected(3);
            }
    }

    public void Topping1_minus(MouseEvent mouseEvent) {
        if (i1>0){
            if (i1==1){
                Topping1_m.setVisible(false);
            }
            if (i1==5){
                Topping1_p.setVisible(true);
            }
            i1--;

            ToppingSlider_1.adjustValue(i1);
            inum_1.setText(String.valueOf(i1));

        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nem lehet kevesebb nullánál.");
            alert.show();
        }
    }

    public void Topping2_minus(MouseEvent mouseEvent) {
        if (i2>0){
            if (i2==1){
                Topping2_m.setVisible(false);
            }if (i2==5){
                Topping2_p.setVisible(true);
            }
            i2--;
            ToppingSlider_2.adjustValue(i2);
            inum_2.setText(String.valueOf(i2));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nem lehet kevesebb nullánál.");
            alert.show();
        }
    }

    public void Topping3_minus(MouseEvent mouseEvent) {
        if (i3>0){
            if (i3==1){
                Topping3_m.setVisible(false);
            }if (i3==5){
                Topping3_p.setVisible(true);
            }
            i3--;
            ToppingSlider_3.adjustValue(i3);
            inum_3.setText(String.valueOf(i3));
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nem lehet kevesebb nullánál.");
            alert.show();
        }
    }

    public void Topping4_minus(MouseEvent mouseEvent) {
        if (i4>0){
            if (i4==1){
                Topping4_m.setVisible(false);
            }if (i4==5){
                Topping4_p.setVisible(true);
            }
            i4--;
            inum_4.setText(String.valueOf(i4));
            ToppingSlider_4.adjustValue(i4);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nem lehet kevesebb nullánál.");
            alert.show();
        }
    }

    public void Topping5_minus(MouseEvent mouseEvent) {
        if (i5>0){
            if (i5==1){
                Topping5_m.setVisible(false);
            }if (i5==5){
                Topping5_p.setVisible(true);
            }
            i5--;
            inum_5.setText(String.valueOf(i5));
            ToppingSlider_5.adjustValue(i5);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nem lehet kevesebb nullánál.");
            alert.show();
        }
    }

    public void Topping1_plus(MouseEvent mouseEvent) {
        if (i1<5 && Database.returnIndredientCountByFoodtype(ftype,0)>i1){
            if (i1==0){
                Topping1_m.setVisible(true);
            }
            if (i1==4){
                Topping1_p.setVisible(false);
            }
            i1++;
            inum_1.setText(String.valueOf(i1));
            ToppingSlider_1.adjustValue(i1);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nem lehet több ötnél.");
            alert.show();
        }
    }

    public void Topping2_plus(MouseEvent mouseEvent) {
        if (i2<5&& Database.returnIndredientCountByFoodtype(ftype,0)>i2){
            if (i2==0){
                Topping2_m.setVisible(true);
            }
            if (i2==4){
                Topping2_p.setVisible(false);
            }
            i2++;
            inum_2.setText(String.valueOf(i2));
            ToppingSlider_2.adjustValue(i2);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nem lehet több ötnél.");
            alert.show();
        }
    }

    public void Topping3_plus(MouseEvent mouseEvent) {
        if (i3<5&& Database.returnIndredientCountByFoodtype(ftype,0)>i3){
            if (i3==0){
                Topping3_m.setVisible(true);
            }
            if (i3==4){
                Topping3_p.setVisible(false);
            }
            i3++;
            inum_3.setText(String.valueOf(i3));
            ToppingSlider_3.adjustValue(i3);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nem lehet több ötnél.");
            alert.show();
        }
    }

    public void Topping4_plus(MouseEvent mouseEvent) {
        if (i4<5&& Database.returnIndredientCountByFoodtype(ftype,0)>i4){
            if (i4==0){
                Topping4_m.setVisible(true);
            }
            if (i4==4){
                Topping4_p.setVisible(false);
            }
            i4++;
            inum_4.setText(String.valueOf(i4));
            ToppingSlider_4.adjustValue(i4);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nem lehet több ötnél.");
            alert.show();
        }
    }

    public void Topping5_plus(MouseEvent mouseEvent) {
        if (i5<5&& Database.returnIndredientCountByFoodtype(ftype,0)>i5){
            if (i5==0){
                Topping5_m.setVisible(true);
            }
            if (i5==4){
                Topping5_p.setVisible(false);
            }
            i5++;
            inum_5.setText(String.valueOf(i5));
            ToppingSlider_5.adjustValue(i5);
        }
        else{
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setContentText("Nem lehet több ötnél.");
            alert.show();
        }
    }

    public void  ResetIngredients(){
        ftype="";
        i1=0;
        i2=0;
        i3=0;
        i4=0;
        i5=0;
        inum_1.setVisible(false);
        inum_2.setVisible(false);
        inum_3.setVisible(false);
        inum_4.setVisible(false);
        inum_5.setVisible(false);
    }

    public void FoodSelected(int foodid){
        ftype=Database.getFoodTypes().get(foodid);
        ingredient_label.setText("Kérem válasszon összetevőket! kiválasztott étel: "+ftype);
        for (int i = 0; i < 5; i++) {
            String ingredientName = String.valueOf(Database.returnIndredientByFoodtype(Database.getFoodTypes().get(foodid)).get(i).getName());
            boolean isHidden = ingredientName.equals("-");

            switch (i) {
                case 0:
                    ToppingName_1.setVisible(!isHidden);
                    ToppingName_1.setText(ingredientName);
                    Topping1_p.setVisible(!isHidden);
                    ToppingSlider_1.setVisible(!isHidden);
                    inum_1.setVisible(!isHidden);
                    inum_1.setText(String.valueOf(i1));
                    break;
                case 1:
                    ToppingName_2.setVisible(!isHidden);
                    ToppingName_2.setText(ingredientName);
                    Topping2_p.setVisible(!isHidden);
                    ToppingSlider_2.setVisible(!isHidden);
                    inum_2.setVisible(!isHidden);
                    inum_2.setText(String.valueOf(i2));
                    break;
                case 2:
                    ToppingName_3.setVisible(!isHidden);
                    ToppingName_3.setText(ingredientName);
                    Topping3_p.setVisible(!isHidden);
                    ToppingSlider_3.setVisible(!isHidden);
                    inum_3.setVisible(!isHidden);
                    inum_3.setText(String.valueOf(i3));
                    break;
                case 3:
                    ToppingName_4.setVisible(!isHidden);
                    ToppingName_4.setText(ingredientName);
                    Topping4_p.setVisible(!isHidden);
                    ToppingSlider_4.setVisible(!isHidden);
                    inum_4.setVisible(!isHidden);
                    inum_4.setText(String.valueOf(i4));
                    break;
                case 4:
                    ToppingName_5.setVisible(!isHidden);
                    ToppingName_5.setText(ingredientName);
                    Topping5_p.setVisible(!isHidden);
                    ToppingSlider_5.setVisible(!isHidden);
                    inum_5.setVisible(!isHidden);
                    inum_5.setText(String.valueOf(i5));
                    break;
                default:
                    break;
            }
        }
    }
}

