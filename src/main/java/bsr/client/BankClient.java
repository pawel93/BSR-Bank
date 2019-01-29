package bsr.client;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;



public class BankClient extends Application
{

    private static final String loginScreen = "/login.fxml";
    private static final String registerScreen = "/register.fxml";
    private static final String homeScreen = "/home.fxml";
    private static final String historyScreen = "/history.fxml";

    public static void main(String[] args)throws Exception
    {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //root screen for all screens
        RootScreen rootScreen = new RootScreen();

        //loading screens to map
        rootScreen.loadScreens("login", loginScreen);
        rootScreen.loadScreens("register", registerScreen);
        rootScreen.loadScreens("home", homeScreen);
        rootScreen.loadScreens("history", historyScreen);
        //set current screen
        rootScreen.setScreen("login");


        //Parent loginScene = FXMLLoader.load(getClass().getResource("/login.fxml"));

        Scene scene = new Scene(rootScreen);
        primaryStage.setTitle("Client");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


}
