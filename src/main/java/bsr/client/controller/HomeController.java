package bsr.client.controller;

import bsr.Exceptions.BankException;
import bsr.Exceptions.Error;
import bsr.client.AlertWindow;
import bsr.client.Client;
import bsr.client.RootScreen;
import bsr.model.*;
import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import org.glassfish.jersey.client.ClientConfig;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;




public class HomeController implements Initializable, IController
{

    private RootScreen rootScreen;
    private int currentAccountId;


    @FXML private Label titleLabel;
    @FXML private Label numberLabel;
    @FXML private Label saldoLabel;
    @FXML private ComboBox<String> accountIn;

    @FXML private TextField text1;
    @FXML private TextField text2;
    @FXML private TextField titleText;
    @FXML private TextField receiverText;
    @FXML private TextField senderText;
    @FXML private TextField amountText;
    @FXML private TextArea textArea;

    @FXML private ProgressIndicator progressIndicator;

    private Client client;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    public HomeController(){
        client = new Client();
    }


    public void initData(Account currentAccount){
        this.currentAccountId = currentAccount.getId();

        populateUserData(currentAccount);
        populateComobBoxData(currentAccount);
    }

    public void populateUserData(Account currentAccount){
        titleLabel.setText("Welcome " + currentAccount.getName() + " " + currentAccount.getSurname());
    }

    public void populateComobBoxData(Account currentAccount){
        accountIn.getItems().clear();

        List<BankAccount> bankAccounts = currentAccount.getBills();
        for(BankAccount bankAccount : bankAccounts)
        {
            accountIn.getItems().add(bankAccount.getNumber());
        }
        accountIn.getSelectionModel().selectFirst();
        numberLabel.setText("account number: " + accountIn.getSelectionModel().getSelectedItem());

        BankAccount bankAccount = client.getBankAccount(accountIn.getSelectionModel().getSelectedItem());
        saldoLabel.setText("saldo: " + String.valueOf(bankAccount.getBalance()));

    }

    @Override
    public void setScreenParent(RootScreen parent) {
        this.rootScreen = parent;
    }

    public void comboAction(ActionEvent actionEvent)
    {
        numberLabel.setText("account number: " + accountIn.getValue());
        senderText.setText(accountIn.getValue());

        BankAccount bankAccount = client.getBankAccount(accountIn.getValue());
        saldoLabel.setText("saldo: " + String.valueOf(bankAccount.getBalance()));
    }

    public void logout(ActionEvent actionEvent)
    {
        rootScreen.setScreen("login");
    }

    public void goToHistory(ActionEvent actionEvent)
    {
        ArrayList<History> lista = client.getAccountHistory(currentAccountId);
        HistoryController historyController = (HistoryController) rootScreen.getController("history");
        historyController.initData(lista);
        rootScreen.setScreen("history");
    }

    public void createAccount(ActionEvent actionEvent)
    {
        BankAccount created = client.createBankAccount(currentAccountId);
        accountIn.getItems().add(created.getNumber());
    }

    public void removeAccount(ActionEvent actionEvent) {

        if(accountIn.getItems().size() > 1){
            String selectedBankAccount = accountIn.getValue();
            client.deleteBankAccount(selectedBankAccount);
            accountIn.getItems().remove(selectedBankAccount);

        }
    }

    public void payin(ActionEvent actionEvent)
    {
        Payment payment = new Payment(text1.getText(), accountIn.getValue());
        try {
            double saldo = client.payin(payment);
            saldoLabel.setText("saldo: " + String.valueOf(saldo));

        } catch (BankException e) {
            textArea.setText(e.getMessage());
            AlertWindow.show(e.getMessage(), "BankException");
        }
        text1.clear();

    }

    public void payout(ActionEvent actionEvent)
    {
        Payment payment = new Payment(text2.getText(), accountIn.getValue());
        try {
            double saldo = client.payout(payment);
            saldoLabel.setText("saldo: " + String.valueOf(saldo));

        } catch (BankException e) {
            textArea.setText(e.getMessage());
        }
        text2.clear();
    }


    public void sendTransfer(ActionEvent actionEvent)
    {

        final Transfer transfer = new Transfer(titleText.getText(), amountText.getText(), senderText.getText(), receiverText.getText());

        Task<Response> backgroundTask = new Task<Response>() {
            @Override
            protected Response call() throws Exception {

                ClientConfig clientConfig = new ClientConfig();
                HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
                clientConfig.register(feature);

                javax.ws.rs.client.Client webClient = ClientBuilder.newClient(clientConfig);

                WebTarget target = webClient.target("http://localhost:8088").path("transfer");
                Response res = target.request("application/json").post(Entity.entity(transfer, MediaType.APPLICATION_JSON), Response.class);

                return res;

            }
        };

        Thread thread = new Thread(backgroundTask);
        thread.setDaemon(true);
        thread.start();


        backgroundTask.setOnScheduled(e -> progressIndicator.setVisible(true));

        backgroundTask.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
            @Override
            public void handle(WorkerStateEvent event) {

                progressIndicator.setVisible(false);
                Response res = backgroundTask.getValue();

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
                    Error error = res.readEntity(Error.class);
                    textArea.setText(error.getError());
                }
                else if(res.getStatus() == 201){
                    textArea.setText(res.readEntity(String.class));

                    String selected = accountIn.getSelectionModel().getSelectedItem();
                    BankAccount bankAccount = client.getBankAccount(selected);
                    saldoLabel.setText("saldo: " + String.valueOf(bankAccount.getBalance()));

                }

            }
        });

        //System.out.println(res.getStatus());


    }


}
