package bsr.client.controller;

import bsr.Exceptions.BankException;
import bsr.client.Client;
import bsr.client.RootScreen;
import bsr.model.Account;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
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
    @FXML private Label alertlabel;

    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public RegisterController(){
        client = Client.getInstance();
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

    @FXML
    private void returnButton(ActionEvent actionEvent)
    {
        alertlabel.setText("");
        rootScreen.setScreen("login");
    }

    @FXML
    private void createAccount(ActionEvent actionEvent)
    {
        final Account account = new Account(nameText.getText(), surnameText.getText(), loginText.getText(), passwordText.getText());
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                try{
                    client.createAccount(account);
                }catch(BankException e){
                    Platform.runLater(new Runnable(){
                        @Override
                        public void run() {
                            alertlabel.setText("wrong login");
                        }
                    });

                }
                return null;
            }
        };
        new Thread(task).start();
        clearForm();
    }



}
