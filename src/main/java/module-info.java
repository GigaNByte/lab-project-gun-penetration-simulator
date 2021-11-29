module com.giga {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.persistence;
    requires java.sql;
    requires org.hibernate.orm.core;
    requires java.naming;
    opens com.giga to javafx.fxml;
    opens com.giga.controllers to javafx.fxml;
    exports com.giga.controllers;
    exports com.giga.model;
    exports com.giga;
}
