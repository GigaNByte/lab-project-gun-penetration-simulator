module com.giga {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.giga to javafx.fxml;
    opens com.giga.controllers to javafx.fxml;

    exports com.giga.controllers;
    exports com.giga;
}
