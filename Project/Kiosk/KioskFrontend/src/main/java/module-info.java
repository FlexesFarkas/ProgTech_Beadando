module org.kiosk.kioskfrontend {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.kiosk.kioskfrontend to javafx.fxml;
    exports org.kiosk.kioskfrontend;
}