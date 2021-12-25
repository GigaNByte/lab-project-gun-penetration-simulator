package com.giga;

import org.flywaydb.core.Flyway;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {

        SessionFactory sessionFactory = HibernateConnection.getSessionFactory();
        Session session = sessionFactory.openSession();
        session.close();

        Flyway flyway = Flyway.configure().dataSource("jdbc:sqlite:sqlite/db/gps.db", null, null).baselineOnMigrate(true).load();
        // Start the migration
        flyway.migrate();





        scene = new Scene(loadFXML("MainView"));
        stage.setScene(scene);
        stage.show();

    }

    static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        System.out.println(App.class.getResource("view/"+fxml + ".fxml"));
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("view/"+fxml + ".fxml"));

        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}