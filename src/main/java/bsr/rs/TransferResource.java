package bsr.rs;

import bsr.Exceptions.BankException;
import bsr.Exceptions.Error;
import bsr.model.AccountDAO;
import bsr.model.BankAccount;
import bsr.model.History;
import bsr.model.Transfer;
import bsr.util.Validator;
import org.glassfish.jersey.client.authentication.HttpAuthenticationFeature;

import javax.annotation.security.RolesAllowed;
import javax.ws.rs.*;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by Paweł on 2017-01-12.
 */

@Path("/transfer")
public class TransferResource
{
    BankAccount senderAccount;
    BankAccount receiverAccount;
    String BankId = "00112272";


    public void writeHistory(Transfer transfer)
    {
        BankAccount bankAccount = AccountDAO.searchBankAccount(transfer.getSender());
        BankAccount bankAccount2 = AccountDAO.searchBankAccount(transfer.getReceiver());
        if(bankAccount != null && bankAccount2 != null){
            History history = new History(bankAccount.getId(),transfer.getSender(), transfer.getTitle(),"0",transfer.getAmount(),transfer.getReceiver(),String.valueOf(bankAccount.getBalance()));
            AccountDAO.insertHistory(history.getId(),history.getAccount(), history.getTitle(), history.getIncome(), history.getOutcome(),history.getSource(), history.getSaldo());
            if(bankAccount.getId() == bankAccount2.getId()){
                History history1 = new History(bankAccount2.getId(),transfer.getReceiver(), transfer.getTitle(),transfer.getAmount(),"0",transfer.getSender(),String.valueOf(bankAccount2.getBalance()));
                AccountDAO.insertHistory(history1.getId(),history1.getAccount(), history1.getTitle(), history1.getIncome(), history1.getOutcome(),history1.getSource(), history1.getSaldo());
            }

        }
        else if(bankAccount != null){
            History history = new History(bankAccount.getId(),transfer.getSender(), transfer.getTitle(),"0",transfer.getAmount(),transfer.getReceiver(),String.valueOf(bankAccount.getBalance()));
            AccountDAO.insertHistory(history.getId(),history.getAccount(), history.getTitle(), history.getIncome(), history.getOutcome(),history.getSource(), history.getSaldo());
        }
        else{
            BankAccount bankAccount1 = AccountDAO.searchBankAccount(transfer.getReceiver());
            if(bankAccount1 != null){
                History history = new History(bankAccount1.getId(),transfer.getReceiver(), transfer.getTitle(),transfer.getAmount(), "0",transfer.getSender(),String.valueOf(bankAccount1.getBalance()));
                AccountDAO.insertHistory(history.getId(),history.getAccount(), history.getTitle(), history.getIncome(), history.getOutcome(),history.getSource(), history.getSaldo());
            }

        }

    }

    public String loadPropertiesFile(String BankId)
    {
        Properties prop = new Properties();
        InputStream input = null;
        String result = "";
        try {
            input = new FileInputStream("bank.properties");
            prop.load(input);
            result = prop.getProperty(BankId);
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(input != null){
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

    @RolesAllowed("ADMIN")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendTransfer(Transfer transfer)
    {

        if(!Validator.validateTransfer(transfer)){
            return Response.status(400).entity(new Error("invalid input")).build();
        }

        String sender = transfer.getSender();
        String receiver = transfer.getReceiver();
        String bankSender = transfer.getSender().substring(2, 10);
        String bankReceiver = transfer.getReceiver().substring(2, 10);

        if(!Validator.validateAccountNumber(sender)){
            return Response.status(403).entity(new Error("forbidden")).build();
        }
        if(!Validator.validateAccountNumber(receiver)){
            return Response.status(403).entity(new Error("forbidden")).build();
        }

        //przelew do konta w tym samym banku
         if(bankSender.equals(bankReceiver))
        {
            senderAccount = AccountDAO.searchBankAccount(sender);
            receiverAccount = AccountDAO.searchBankAccount(receiver);

            if(senderAccount != null && receiverAccount != null)
            {
                double sum = senderAccount.getBalance() - Double.valueOf(transfer.getAmount());
                if(sum < 0)
                    return Response.status(400).entity(new Error("lack of money")).build();
                double sum1 = receiverAccount.getBalance() + Double.valueOf(transfer.getAmount());
                AccountDAO.updateBankAccount(sender, String.valueOf(sum));
                AccountDAO.updateBankAccount(receiver, String.valueOf(sum1));
                writeHistory(transfer);
                return Response.status(201).entity("transfer completed successfully").build();
            }
            else
            {
                return Response.status(404).entity(new Error("brak odbiorcy")).build();
            }
        }//przelew do konta w innym banku
        else if(!bankReceiver.equals(BankId))
        {
            senderAccount = AccountDAO.searchBankAccount(sender);
            double sum = senderAccount.getBalance() - Double.valueOf(transfer.getAmount());
            if(sum < 0)
                return Response.status(400).entity(new Error("lack of money")).build();
            Client client = ClientBuilder.newClient();
            HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
            client.register(feature);
            String bankUrl = loadPropertiesFile(bankReceiver);
            WebTarget webTarget = client.target(bankUrl);
            Response res = webTarget.request("application/json").post(Entity.entity(transfer, MediaType.APPLICATION_JSON), Response.class);
            if(res.getStatus() == 201){
                AccountDAO.updateBankAccount(sender, String.valueOf(sum));
                writeHistory(transfer);
                return Response.status(201).entity("transfer completed successfully").build();
            }

        }//odbiór przelewu ok konta w innym banku
        else if(!bankSender.equals(BankId))
        {
            receiverAccount = AccountDAO.searchBankAccount(receiver);
            if(receiverAccount != null){
                double sum = receiverAccount.getBalance() + Double.valueOf(transfer.getAmount());
                AccountDAO.updateBankAccount(receiver, String.valueOf(sum));
                writeHistory(transfer);
                return Response.status(201).entity("transfer completed successfully").build();
            }
            else
            {
                return Response.status(404).entity(new Error("brak odbiorcy")).build();
            }

        }

        return Response.status(500).entity(new Error("internal server error")).build();

    }




}

