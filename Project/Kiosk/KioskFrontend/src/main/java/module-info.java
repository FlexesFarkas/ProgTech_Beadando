module org.kiosk.kioskfrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires KioskBackend;


    opens org.kiosk.kioskfrontend to javafx.fxml;
    exports org.kiosk.kioskfrontend;
}