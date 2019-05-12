package sample;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.BorderPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.layout.VBox;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private Label error_text;

    @FXML
    private PasswordField password;

    @FXML
    private Button login;

    @FXML
    private ComboBox<String> user_type;

    private String dev_user = "Developer";
    private String dev_pass = "develop";
    private String city_user = "City Planner";
    private String city_pass = "plan";


    @FXML
    void handleLoginSubmit(ActionEvent event) throws IOException {
        Parent newDevScene = null;
        Stage stage = null;

        String option = user_type.getSelectionModel().getSelectedItem();


        if(option == null || username == null || password == null) {
            error_text.setVisible(true);
            error_text.setText("Please enter all fields");
        }else if(option.equals("Developer") && username.getText().equals(dev_user) && password.getText().equals(dev_pass)){
            error_text.setVisible(false);
            error_text.setText("Error");

            stage = (Stage) error_text.getScene().getWindow();
            BorderPane pane = FXMLLoader.load(
                    LoginController.class.getResource("developerPage.fxml"));

            stage.setTitle("Dev Page");
            stage.setScene(new Scene(pane, 800, 600));
            stage.show();

        }else if(option.equals("City Planner") && username.equals(city_user) && username.equals(city_pass)){
            error_text.setVisible(false);
            error_text.setText("Error");
        }else{
            error_text.setVisible(true);
            error_text.setText("The username or password you entered is incorrect");
        }

    }

    @Override // and this
    public void initialize(URL url, ResourceBundle rb) {
        user_type.getItems().removeAll(user_type.getItems());
        user_type.getItems().addAll("Developer", "City Planner");
    }
}
