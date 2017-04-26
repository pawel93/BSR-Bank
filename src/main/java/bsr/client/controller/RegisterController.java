package bsr.client.controller;

import bsr.client.Client;
import bsr.client.ParentScreen;
import bsr.model.Account;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Paweł on 2017-01-26.
 */
public class RegisterController implements Initializable, IController
{

    private ParentScreen parentScreen;
    Controller controller;
    public TextField nameText;
    public TextField surnameText;
    public TextField loginText;
    public TextField passwordText;

    Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new Client();
    }

    public void initData(){

    }

    @Override
    public void setScreenParent(ParentScreen parent)
    {
        this.parentScreen = parent;
    }

    public void setMainController(Controller controller)
    {
        this.controller = controller;
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
        parentScreen.setScreen(1);
    }

    public void createAccount(ActionEvent actionEvent)
    {
        Account account = new Account(nameText.getText(), surnameText.getText(), loginText.getText(), passwordText.getText());
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
