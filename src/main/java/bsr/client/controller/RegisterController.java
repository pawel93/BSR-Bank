package bsr.client.controller;

import bsr.client.Client;
import bsr.client.RootScreen;
import bsr.model.Account;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;



public class RegisterController implements Initializable, IController
{

    private RootScreen rootScreen;

    @FXML private TextField nameText;
    @FXML private TextField surnameText;
    @FXML private TextField loginText;
    @FXML private TextField passwordText;

    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new Client();
    }

    public RegisterController(){

    }

    @Override
    public void setScreenParent(RootScreen parent)
    {
        this.rootScreen = parent;
    }

    public void clearForm()
    {
        nameText.clear();
        surnameText.clear();
        loginText.clear();
        passwordText.clear();
    }

    public void returnButton(ActionEvent actionEvent)
    {
        rootScreen.setScreen("login");
    }

    public void createAccount(ActionEvent actionEvent)
    {
        final Account account = new Account(nameText.getText(), surnameText.getText(), loginText.getText(), passwordText.getText());
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                client.createAccount(account);
                return null;
            }
        };
        new Thread(task).start();
        clearForm();
    }



}
