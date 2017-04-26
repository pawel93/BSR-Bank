package bsr.Exceptions;

/**
 * Created by Paweł on 2017-01-26.
 */
public class AccountNotFound extends Exception
{
    private String faultMessage;
    public AccountNotFound(String faultMessage){
        super(faultMessage);
        this.faultMessage = faultMessage;
    }


}
