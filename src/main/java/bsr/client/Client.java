package bsr.client;

import bsr.Exceptions.AccountNotFound;
import bsr.Exceptions.BankException;
import bsr.model.Account;
import bsr.model.BankAccount;
import bsr.model.History;
import bsr.model.Payment;
import bsr.ws.IBank;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;


public class Client
{

    private IBank BankService;
    private static final  String WSUrl = "http://localhost:8888/ws/bank?wsdl";
    public Client()
    {
        URL url = null;
        try {
            url = new URL(WSUrl);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        QName qname = new QName("http://ws.bsr/", "BankService");
        Service proxy = Service.create(url, qname);
        BankService = proxy.getPort(IBank.class);

    }

    public void createAccount(Account account)
    {
        BankService.createAccount(account);
    }

    public BankAccount createBankAccount(int id)
    {
        return BankService.createBankAccount(id);
    }

    public void deleteBankAccount(String accountNumber){
        BankService.deleteBankAccount(accountNumber);
    }

    public BankAccount getBankAccount(String accountNumber){
        return BankService.getBankAccount(accountNumber);
    }


    public Account getAccount(int id)
    {
        return BankService.getAccount(id);
    }


    public double payin(Payment payment)throws BankException
    {
        return BankService.payin(payment);
    }

    public double payout(Payment payment)throws BankException
    {
        return BankService.payout(payment);
    }


    public ArrayList<History> getAccountHistory(int id)
    {
        ArrayList<History> hist = BankService.getAccountHistory(id);
        System.out.println(hist.size());
        return hist;
    }

    public Account confirmCredentials(String login, String password)
    {
        Account account;
        Map<String, Object> reqContext = ((BindingProvider)BankService).getRequestContext();
        reqContext.put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, WSUrl);

        Map<String, List<String>> headers = new HashMap<>();
        headers.put("Username", Collections.singletonList(login));
        headers.put("Password", Collections.singletonList(password));
        reqContext.put(MessageContext.HTTP_REQUEST_HEADERS, headers);

        try {
            account = BankService.confirmLoginAndPassword();
        } catch (AccountNotFound accountNotFound) {
            //accountNotFound.printStackTrace();
            return null;
        }
        return account;


    }

}
