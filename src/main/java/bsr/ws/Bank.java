package bsr.ws;

import bsr.Exceptions.AccountNotFound;
import bsr.Exceptions.BankException;
import bsr.model.*;
import bsr.util.AccountDAO;
import bsr.util.Validator;

import javax.annotation.Resource;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;
import javax.xml.ws.handler.MessageContext;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;





@WebService(endpointInterface = "bsr.ws.IBank")
public class Bank implements IBank
{
    //numer id banku
    private static final String BankId = "00112272";

    //generuje numer banku
    public String generateBankAccountNumber(String clientId)
    {
        //dodanie kodów liter PL i pomnożenie przez 100
        clientId = BankId + clientId + "252100";
        BigInteger bankAccountId = new BigInteger(clientId);
        BigInteger checkSum = new BigInteger("98").subtract(bankAccountId.mod(new BigInteger("97")));
        return checkSum.toString() + clientId.substring(0, 24);
    }


    @Resource
    private WebServiceContext WSContext;

    public Bank() {

    }


    //utworzenie nowego rachunku bankowego
    public BankAccount createBankAccount(int id)
    {
        Account account = getAccount(id);
        List<BankAccount> lista = account.getBills();

        String num = lista.get(lista.size()-1).getNumber();
        long num1 = Long.valueOf(num.substring(10, 26)) + 1;
        String accountNumber = String.format("%016d", num1);
        accountNumber = generateBankAccountNumber(accountNumber);

        AccountDAO.insertBankAccount(account.getId(), accountNumber, 0);

        BankAccount created = AccountDAO.searchBankAccount(accountNumber);
        return created;

    }

    public void deleteBankAccount(String accountNumber){
        AccountDAO.deleteBankAccount(accountNumber);
    }

    public BankAccount getBankAccount(String accountNumber){

        return AccountDAO.searchBankAccount(accountNumber);
    }



    //wpłata na rachunek bankowy
    public double payin(Payment payment)throws BankException
    {
        if(!Validator.validatePayment(payment)){
            throw new BankException("invalid sum");
        }
        else
        {
            BankAccount bankAccount = AccountDAO.searchBankAccount(payment.getBillNumber());
            double amount = Double.valueOf(payment.getAmount());
            double sum = bankAccount.getBalance() + amount;
            AccountDAO.updateBankAccount(payment.getBillNumber(), sum);

            History history = new History(bankAccount.getId(),
                    payment.getBillNumber(),
                    "wplata" ,
                    amount,
                    0,
                    "wplata",
                    sum);
            AccountDAO.insertHistory(history);
            return sum;
        }

    }

    //wypłata z rachunku bankowego
    public double payout(Payment payment)throws BankException
    {
        if(!Validator.validatePayment(payment)) {
            throw new BankException("invalid sum");
        }
        else
        {
            BankAccount bankAccount = AccountDAO.searchBankAccount(payment.getBillNumber());
            double amount = Double.valueOf(payment.getAmount());
            double sum = bankAccount.getBalance() - amount;
            if(sum < 0)
                throw new BankException("lack of money");
            else{
                AccountDAO.updateBankAccount(payment.getBillNumber(), sum);
                History history = new History(bankAccount.getId(),
                        payment.getBillNumber(),
                        "wyplata" ,
                        0,
                        amount,
                        "wyplata",
                        sum);
                AccountDAO.insertHistory(history);
                return sum;
            }

        }

    }



    //dodanie do bazy nowego konta z loginem i haslem
    public void createAccount(Account account) {

        String accountNumber;
        AccountDAO.insertAccount(account.getName(), account.getSurname(), account.getLogin(), account.getPassword());

        account = AccountDAO.searchAccount(account.getLogin(), account.getPassword());

        accountNumber = String.format("%016d", (account.getId() * 10 + 5));
        accountNumber = generateBankAccountNumber(accountNumber);

        AccountDAO.insertBankAccount(account.getId(), accountNumber, 0);

    }

    //pobranie historii dla konta
    public ArrayList<History> getAccountHistory(int id)
    {
        ArrayList<History> historyList;
        historyList = AccountDAO.searchHistory(id);

        return historyList;
    }

    //get account by id
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
