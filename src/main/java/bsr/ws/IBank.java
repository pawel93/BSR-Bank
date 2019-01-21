package bsr.ws;

import bsr.Exceptions.AccountNotFound;
import bsr.Exceptions.BankException;
import bsr.model.Account;
import bsr.model.BankAccount;
import bsr.model.History;
import bsr.model.Payment;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;
import java.util.ArrayList;




@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public interface IBank
{

    @WebMethod
    BankAccount createBankAccount(int id);

    @WebMethod
    void deleteBankAccount(String accountNumber);

    @WebMethod
    BankAccount getBankAccount(String accountNumber);


    @WebMethod
    double payin(Payment payment)throws BankException;

    @WebMethod
    double payout(Payment payment)throws BankException;


    @WebMethod
    ArrayList<History> getAccountHistory(int id);

    @WebMethod
    void createAccount(Account account);

    @WebMethod
    @WebResult(partName = "accountResponse")
    Account getAccount(@WebParam(name = "accountId") int id);

    @WebMethod
    Account confirmLoginAndPassword()throws AccountNotFound;


}


