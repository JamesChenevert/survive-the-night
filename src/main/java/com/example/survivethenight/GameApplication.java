package com.example.survivethenight;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameApplication extends Application {

    public static ImageView mapView = new ImageView();

    @Override
    public void start(Stage stage) {

        mapView.setFitWidth(400);
        mapView.setPreserveRatio(true);

        StackPane root = new StackPane();
        root.getChildren().add(mapView);

        Scene scene = new Scene(root, 400, 400);

        stage.setTitle("Building Layout");
        stage.setScene(scene);
        stage.show();

        new GameController().startGame();
    }

    public static void updateImage(String path) {
        Image img = new Image(GameApplication.class.getResourceAsStream(path));
        mapView.setImage(img);
    }

    public static void main(String[] args) {
        launch();
    }
}
