package bsr.client.controller;

import bsr.client.Client;
import bsr.client.ParentScreen;
import bsr.model.Account;
import bsr.ws.IBank;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * Created by Paweł on 2017-01-24.
 */
public class LoginController implements Initializable, IController
{

    private ParentScreen parentScreen;
    Controller controller;
    public TextField login;
    public TextField password;
    public Label label;

    private Client client;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new Client();
    }

    public void initData(){

    }

    @Override
    public void setScreenParent(ParentScreen parent) {
        this.parentScreen = parent;
    }

    public void setMainController(Controller controller)
    {
        this.controller = controller;
    }

    public void createAccountButton(ActionEvent actionEvent)
    {
        parentScreen.setScreen(2);
    }

    public void login(ActionEvent actionEvent)
    {
        Account result = client.confirmCredentials(login.getText(), password.getText());
        if(result == null){
            label.setText("invalid input");
        }
        else{
            label.setText("");
            controller.setAccount(result);
            parentScreen.setScreen(3);
        }

    }







}
