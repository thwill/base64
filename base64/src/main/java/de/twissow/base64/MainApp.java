package de.twissow.base64;

import de.twissow.base64.controller.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.fxapps.cdifx.FXMLLoaderProducer;
import org.fxapps.cdifx.StartupScene;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import java.net.URL;

public class MainApp extends Application {

    private static final Logger log = LoggerFactory.getLogger(MainApp.class);

    @Inject FXMLLoaderProducer producer;

    public static void main(String[] args) throws Exception {
        launch(args);
    }

    public void start(@Observes @StartupScene Stage stage) throws Exception {

        log.info("Starting XSLExecutor Application ");

        String fxmlFile = "/fxml/main.fxml";
        URL location = getClass().getResource(fxmlFile);
        log.info("Loading FXML for main view from: {}", fxmlFile);
        FXMLLoader loader = producer.createLoader();
        loader.setLocation(location);
        Parent rootNode = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
        MainController gameController = loader.getController();
        gameController.setStage(stage);

        log.debug("Showing JFX scene");
        Scene scene = new Scene(rootNode, 450, 800);
        scene.getStylesheets().add("/styles/styles.css");

        stage.setTitle("Image - Base64 Converter");
        stage.setScene(scene);
        stage.show();
    }
}
