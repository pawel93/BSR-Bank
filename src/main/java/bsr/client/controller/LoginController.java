package bsr.client.controller;

import bsr.client.Client;
import bsr.client.RootScreen;
import bsr.model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController implements Initializable, IController
{

    private RootScreen rootScreen;

    @FXML private TextField login;
    @FXML private TextField password;
    @FXML private Label label;

    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public LoginController(){
        client = new Client();
    }

    @Override
    public void setScreenParent(RootScreen parent) {
        this.rootScreen = parent;
    }


    public void createAccountButton(ActionEvent actionEvent)
    {
        rootScreen.setScreen("register");
    }

    public void login(ActionEvent actionEvent)
    {
        Account result = client.confirmCredentials(login.getText(), password.getText());
        if(result == null){
            label.setText("invalid input");
        }
        else{
            label.setText("");
            //Stage loginStage = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
            HomeController homeController = (HomeController) rootScreen.getController("home");
            homeController.initData(result);
            rootScreen.setScreen("home");

        }

    }


    public Stage loadNextStage(String resource){
        Stage nextStage = new Stage();
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(resource));
        try {
            Parent parent = fxmlLoader.load();
            nextStage.setScene(new Scene(parent));
            nextStage.setTitle("Client");

        } catch (IOException e) {
            e.printStackTrace();
        }
        return nextStage;
    }







}
