module KioskBackend {
    requires java.base;
    requires reflections;
    requires java.sql;
    exports org.kiosk;
    exports org.kiosk.order;
    exports org.kiosk.food;
}