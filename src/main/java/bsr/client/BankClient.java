package bsr.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * Created by Paweł on 2017-01-20.
 */
public class BankClient extends Application
{

    public String loginScreen = "/login.fxml";
    public String registerScreen = "/register.fxml";
    public String homeScreen = "/home.fxml";
    public String historyScreen = "/history.fxml";

    public static void main(String[] args)throws Exception
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //parent screen for all screens
        ParentScreen parentScreen = new ParentScreen();
        //loading screens to map
        parentScreen.loadScreens(1, loginScreen);
        parentScreen.loadScreens(2, registerScreen);
        parentScreen.loadScreens(3, homeScreen);
        parentScreen.loadScreens(4, historyScreen);
        //set current screen
        parentScreen.setScreen(1);

//        Group group = new Group();
//        group.getChildren().addAll(parentScreen);

        Scene scene = new Scene(parentScreen);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
