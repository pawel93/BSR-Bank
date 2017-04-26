package bsr.client.controller;

import bsr.Exceptions.BankException;
import bsr.Exceptions.Error;
import bsr.model.Transfer;
import bsr.model.BankAccount;
import bsr.model.History;
import bsr.model.Payment;
import bsr.client.Client;
import bsr.client.ParentScreen;
import bsr.model.Account;
import javafx.event.ActionEvent;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

/**
 * Created by Paweł on 2017-01-26.
 */
public class HomeController implements Initializable, IController
{
    private ParentScreen parentScreen;
    private Controller controller;
    public Account currentAccount;
    public Label titleLabel;
    public Label numberLabel;
    public ComboBox<String> accountIn;

    public TextField text1;
    public TextField text2;
    public TextField titleText;
    public TextField receiverText;
    public TextField senderText;
    public TextField amountText;
    public TextArea textArea;

    Client client;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        client = new Client();
    }

    public void initData(){
        currentAccount = controller.getAccount();
        titleLabel.setText("Welcome " + currentAccount.getName() + " " + currentAccount.getSurname());
        accountIn.getItems().clear();
        for(BankAccount account1 : currentAccount.getBills())
        {
            accountIn.getItems().add(account1.getNumber());
        }
    }

    public void comboAction(ActionEvent actionEvent)
    {
        numberLabel.setText("account number: " + accountIn.getValue());
    }


    @Override
    public void setScreenParent(ParentScreen parent) {
        this.parentScreen = parent;
    }

    public void setMainController(Controller controller)
    {
        this.controller = controller;
    }

    public void logout(ActionEvent actionEvent)
    {
        parentScreen.setScreen(1);
    }

    public void goToHistory(ActionEvent actionEvent)
    {
        ArrayList<History> lista = client.getHistory(currentAccount.getId());
        controller.setHistoryList(lista);
        parentScreen.setScreen(4);
    }

    public void createAccount(ActionEvent actionEvent)
    {
        client.createBankAccount(currentAccount);
        currentAccount = client.getBankAccount(currentAccount.getId());
        controller.setAccount(currentAccount);
        initData();

    }

    public void payin(ActionEvent actionEvent)
    {
        Payment payment = new Payment(text1.getText(), accountIn.getValue());
        try {
            client.payin(payment);
        } catch (BankException e) {
            textArea.setText(e.getMessage());
        }
        text1.clear();

    }

    public void payout(ActionEvent actionEvent)
    {
        Payment payment = new Payment(text2.getText(), accountIn.getValue());
        try {
            client.payout(payment);
        } catch (BankException e) {
            textArea.setText(e.getMessage());
        }
        text2.clear();
    }

    public void sendTransfer(ActionEvent actionEvent)
    {
        Transfer transfer = new Transfer(titleText.getText(), amountText.getText(), senderText.getText(), receiverText.getText());
        ClientConfig clientConfig = new ClientConfig();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        clientConfig.register(feature);

        javax.ws.rs.client.Client client = ClientBuilder.newClient(clientConfig);

        WebTarget target = client.target("http://localhost:8088").path("transfer");
        Response res = target.request("application/json").post(Entity.entity(transfer, MediaType.APPLICATION_JSON), Response.class);

        if(res.getStatus() == 404){
            Error error = res.readEntity(Error.class);
            textArea.setText(error.getError());
        }
        else if(res.getStatus() == 401){
            textArea.setText("Unauthorized");
        }
        else if(res.getStatus() == 400){
            Error error = res.readEntity(Error.class);
            textArea.setText(error.getError());
        }
        else if(res.getStatus() == 403){
            Error error = res.readEntity(Error.class);
            textArea.setText(error.getError());
        }
        else if(res.getStatus() == 500){
           // Error error = res.readEntity(Error.class);
           // textArea.setText(error.getError());
        }
        else if(res.getStatus() == 201){
            textArea.setText(res.readEntity(String.class));
        }


        System.out.println(res.getStatus());


    }


}
