package bsr.rs;

import bsr.Exceptions.Error;
import bsr.util.AccountDAO;
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
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;



@Path("/transfer")
public class TransferResource
{
    private static final String BankId = "00112272";


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

    public void registerTransferForSender(BankAccount sender, Transfer transfer){
        History history = new History(sender.getId(),
                transfer.getSender(),
                transfer.getTitle(),
                0,
                Double.valueOf(transfer.getAmount()),
                transfer.getReceiver(),
                sender.getBalance());

        AccountDAO.insertHistory(history);

    }

    public void registerTransferForReceiver(BankAccount receiver, Transfer transfer){
        History history = new History(receiver.getId(),
                transfer.getReceiver(),
                transfer.getTitle(),
                Double.valueOf(transfer.getAmount()),
                0,
                transfer.getSender(),
                receiver.getBalance());

        AccountDAO.insertHistory(history);

    }

    public void registerTransfer(BankAccount sender, BankAccount receiver, Transfer transfer){
        registerTransferForSender(sender, transfer);
        registerTransferForReceiver(receiver, transfer);

    }

    public Response transferBetweenAccounts(Transfer transfer){
        BankAccount senderAccount = AccountDAO.searchBankAccount(transfer.getSender());
        BankAccount receiverAccount = AccountDAO.searchBankAccount(transfer.getReceiver());

        if(senderAccount != null && receiverAccount != null)
        {
            double amount = Double.valueOf(transfer.getAmount());
            double senderAmount = senderAccount.getBalance() - amount;
            if(senderAmount < 0)
                return Response.status(400).entity(new Error("lack of money")).build();

            double receiverAmount = receiverAccount.getBalance() + amount;

            AccountDAO.makeTransfer(transfer.getSender(), transfer.getReceiver(), senderAmount, receiverAmount);
            registerTransfer(senderAccount, receiverAccount, transfer);

            return Response.status(201).entity("transfer completed successfully").build();
        }
        else
        {
            return Response.status(404).entity(new Error("brak odbiorcy")).build();
        }

    }

    public Response sendTransferRequest(String receiverBankId, Transfer transfer){
        Client client = ClientBuilder.newClient();
        HttpAuthenticationFeature feature = HttpAuthenticationFeature.basic("admin", "admin");
        client.register(feature);

        String bankUrl = loadPropertiesFile(receiverBankId);

        WebTarget webTarget = client.target(bankUrl);
        Response receiverBankResponse = webTarget.request("application/json")
                .post(Entity.entity(transfer, MediaType.APPLICATION_JSON), Response.class);

        return receiverBankResponse;
    }

    public Response sendTransferToAnotherBank(Transfer transfer){

        BankAccount senderAccount = AccountDAO.searchBankAccount(transfer.getSender());

        double senderAmount = senderAccount.getBalance() - Double.valueOf(transfer.getAmount());
        if(senderAmount < 0)
            return Response.status(400).entity(new Error("lack of money")).build();

        String receiverBankId = transfer.getReceiver().substring(2, 10);
        Response res = sendTransferRequest(receiverBankId, transfer);

        if(res.getStatus() == 201){
            AccountDAO.updateBankAccount(transfer.getSender(), senderAmount);
            registerTransferForSender(senderAccount, transfer);

            return Response.status(201).entity("transfer completed successfully").build();
        }else{

            return Response.status(404).entity(new Error("brak odbiorcy")).build();
        }

    }

    public Response receiveTransferFromAnotherBank(Transfer transfer){
        BankAccount receiverAccount = AccountDAO.searchBankAccount(transfer.getReceiver());

        if(receiverAccount != null){
            double receiverAmount = receiverAccount.getBalance() + Double.valueOf(transfer.getAmount());

            AccountDAO.updateBankAccount(transfer.getReceiver(), receiverAmount);
            registerTransferForReceiver(receiverAccount, transfer);

            return Response.status(201).entity("transfer completed successfully").build();
        }
        else
        {
            return Response.status(404).entity(new Error("brak odbiorcy")).build();
        }


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
        String senderBankId = transfer.getSender().substring(2, 10);
        String receiverBankId = transfer.getReceiver().substring(2, 10);

        if(!Validator.validateAccountNumber(sender)){
            return Response.status(403).entity(new Error("forbidden")).build();
        }
        if(!Validator.validateAccountNumber(receiver)){
            return Response.status(403).entity(new Error("forbidden")).build();
        }


        //przelew do konta w tym samym banku
         if(senderBankId.equals(receiverBankId))
        {
            return transferBetweenAccounts(transfer);

        }//przelew do konta w innym banku
        else if(!receiverBankId.equals(BankId))
        {
            return sendTransferToAnotherBank(transfer);

        }//odbiór przelewu od konta w innym banku
        else if(!senderBankId.equals(BankId))
        {
            return receiveTransferFromAnotherBank(transfer);

        }

        return Response.status(500).entity(new Error("internal server error")).build();

    }




}

