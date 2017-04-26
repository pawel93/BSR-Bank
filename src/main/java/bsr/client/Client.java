package bsr.client;

import bsr.Exceptions.AccountNotFound;
import bsr.Exceptions.BankException;
import bsr.model.History;
import bsr.model.Payment;
import bsr.model.Account;
import bsr.ws.IBank;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;
import javax.xml.ws.Service;
import javax.xml.ws.handler.MessageContext;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;

/**
 * Created by Paweł on 2017-01-25.
 */
public class Client
{

    private IBank BankService;
    private String WSUrl;
    public Client()
    {
        URL url = null;
        WSUrl = "http://localhost:8888/ws/bank?wsdl";
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
        BankService.addAccount(account);
    }

    public void createBankAccount(Account currentAccount)
    {
        BankService.createBankAccount(currentAccount);
    }

    public Account getBankAccount(int id)
    {
        return BankService.getAccount(id);
    }


    public boolean payin(Payment payment)throws BankException
    {
        return BankService.payin(payment);
    }

    public boolean payout(Payment payment)throws BankException
    {
        return BankService.payout(payment);
    }


    public ArrayList<History> getHistory(int id)
    {
        ArrayList<History> hist = BankService.getHistory(id);
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
