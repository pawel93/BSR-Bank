package bsr.ws;

import bsr.Exceptions.AccountNotFound;
import bsr.Exceptions.BankException;
import bsr.model.History;
import bsr.model.Payment;
import bsr.model.Account;
import bsr.model.BankAccount;
import bsr.model.AccountDAO;
import bsr.util.Validator;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by Paweł on 2017-01-12.
 */

@WebService(endpointInterface = "bsr.ws.IBank")
public class Bank implements IBank
{
    //numer id banku
    private String BankId = "00112272";

    //generuje numer banku
    public String generateNumber(String number)
    {
        //dodanie kodów liter PL i pomnożenie przez 100
        number = BankId + number + "252100";
        BigInteger bigNumber = new BigInteger(number);
        bigNumber = new BigInteger("98").subtract(bigNumber.mod(new BigInteger("97")));
        return bigNumber.toString() + number.substring(0, 24);
    }


    @Resource
    private WebServiceContext WSContext;

    public Bank() {

    }

    //dodanie do bazy nowego konta z loginem i haslem
    @Override
    public boolean addAccount(Account account) {

        String accountNumber;
        AccountDAO.insertAccount(account.getName(), account.getSurname(), account.getLogin(), account.getPassword());

        account = AccountDAO.searchAccount(account.getLogin(), account.getPassword());

        accountNumber = String.format("%016d", (account.getId() * 10 + 5));
        accountNumber = generateNumber(accountNumber);

        AccountDAO.insertBankAccount(account.getId(), accountNumber, "0");
        return true;

    }

    //utworzenie nowego rachunku bankowego
    public boolean createBankAccount(Account account)
    {
        List<BankAccount> lista = account.getBills();
        String num = lista.get(lista.size()-1).getNumber();
        long num1 = Long.valueOf(num.substring(10, 26)) + 1;
        String accountNumber = String.format("%016d", num1);
        accountNumber = generateNumber(accountNumber);

        AccountDAO.insertBankAccount(account.getId(), accountNumber, "0");
        return true;

    }

    //dodanie historii do bazy
    public void createHistory(History history)
    {
        AccountDAO.insertHistory(history.getId(),history.getAccount(), history.getTitle(), history.getIncome(), history.getOutcome(), history.getSource(), history.getSaldo());
    }

    //pobranie historii z bazy
    public ArrayList<History> getHistory(int id)
    {
        ArrayList<History> historyList;
        historyList = AccountDAO.searchHistory(id);

        return historyList;
    }

    //wpłata na rachunek bankowy
    public boolean payin(Payment payment)throws BankException
    {
        if(!Validator.validatePayment(payment)){
            throw new BankException("invalid sum");
        }
        else
        {
            BankAccount bankAccount = AccountDAO.searchBankAccount(payment.getBillNumber());
            double sum = bankAccount.getBalance() + Double.valueOf(payment.getAmount());
            AccountDAO.updateBankAccount(payment.getBillNumber(), String.valueOf(sum));

            History history = new History(bankAccount.getId(),payment.getBillNumber(), "wplata" ,payment.getAmount(), "0", "wplata", String.valueOf(sum));
            createHistory(history);
            return true;
        }

    }

    //wypłata z rachunku bankowego
    public boolean payout(Payment payment)throws BankException
    {
        if(!Validator.validatePayment(payment)) {
            throw new BankException("invalid sum");
        }
        else
        {
            BankAccount bankAccount = AccountDAO.searchBankAccount(payment.getBillNumber());
            double sum = bankAccount.getBalance() - Double.valueOf(payment.getAmount());
            if(sum < 0)
                throw new BankException("lack of money");
            else{
                AccountDAO.updateBankAccount(payment.getBillNumber(), String.valueOf(sum));
                History history = new History(bankAccount.getId(),payment.getBillNumber(), "wyplata" , "0",payment.getAmount(), "wyplata", String.valueOf(sum));
                createHistory(history);
                return true;
            }

        }

    }

    //get account by id
    @Override
    public Account getAccount(int id)
    {
        Account account = AccountDAO.searchAccount(id);
        List<BankAccount> lista = AccountDAO.searchBankAccount(id);
        account.setBills(lista);

        return account;
    }

    //confirm credentials
    public Account confirmLoginAndPassword()throws AccountNotFound
    {

        MessageContext msgContext =  WSContext.getMessageContext();
        Map httpHeaders = (Map)msgContext.get(MessageContext.HTTP_REQUEST_HEADERS);
        List userList = (List)httpHeaders.get("Username");
        List passList = (List)httpHeaders.get("Password");

        String username = "";
        String password = "";
        if(userList != null)
            username = userList.get(0).toString();
        if(passList != null)
            password = passList.get(0).toString();

        Account account = AccountDAO.searchAccount(username, password);
        if(account == null){
            throw new AccountNotFound("invalid input");
        }
        else{
            account.setBills(AccountDAO.searchBankAccount(account.getId()));
            return account;
        }

    }




}
