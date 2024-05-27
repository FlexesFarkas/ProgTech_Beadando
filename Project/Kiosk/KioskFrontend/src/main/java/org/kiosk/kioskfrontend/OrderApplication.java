package org.kiosk.kioskfrontend;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import org.kiosk.Database.*;
import org.kiosk.FoodType.*;


public class OrderApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader1 = new FXMLLoader(OrderApplication.class.getResource("SelectOrderType.fxml"));
        Scene scene1 = new Scene(fxmlLoader1.load());
        FXMLLoader fxmlLoader2 = new FXMLLoader(OrderApplication.class.getResource("OrderPayment.fxml"));
        Scene scene2 = new Scene(fxmlLoader2.load());

        stage.setScene(scene1);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}