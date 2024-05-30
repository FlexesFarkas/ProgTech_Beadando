module org.kiosk.kioskfrontend {
    requires javafx.controls;
    requires javafx.fxml;
    requires KioskBackend;
    requires java.logging;


    opens org.kiosk.kioskfrontend to javafx.fxml;
    exports org.kiosk.kioskfrontend;
}