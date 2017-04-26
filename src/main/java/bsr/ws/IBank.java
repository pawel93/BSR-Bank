package bsr.ws;

import bsr.Exceptions.AccountNotFound;
import bsr.Exceptions.BankException;
import bsr.model.History;
import bsr.model.Payment;
import bsr.model.Account;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.ws.BindingType;
import java.util.ArrayList;

/**
 * Created by Paweł on 2017-01-12.
 */

@WebService
@SOAPBinding(style = SOAPBinding.Style.DOCUMENT, use = SOAPBinding.Use.LITERAL, parameterStyle = SOAPBinding.ParameterStyle.WRAPPED)
@BindingType(value = javax.xml.ws.soap.SOAPBinding.SOAP12HTTP_BINDING)
public interface IBank
{

    @WebMethod
    public boolean addAccount(Account account);

    @WebMethod
    public boolean createBankAccount(Account account);

    @WebMethod
    public boolean payin(Payment payment)throws BankException;

    @WebMethod
    public boolean payout(Payment payment)throws BankException;

    @WebMethod
    public ArrayList<History> getHistory(int id);


    @WebMethod
    @WebResult(partName = "accountResponse")
    public Account getAccount(@WebParam(name = "accountId") int id);

    @WebMethod
    public Account confirmLoginAndPassword()throws AccountNotFound;


}


