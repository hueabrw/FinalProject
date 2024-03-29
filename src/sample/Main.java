package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        APIConsumer api = new APIConsumer();
        api.Consume();

        Parent root = FXMLLoader.load(getClass().getResource("login.fxml"));
        primaryStage.setTitle("Developers Playground");
        primaryStage.setScene(new Scene(root, 630, 580));
        primaryStage.setMaximized(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
