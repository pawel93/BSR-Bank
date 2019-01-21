package bsr.Exceptions;


public class AccountNotFound extends Exception
{
    private String faultMessage;

    public AccountNotFound(String faultMessage){
        super(faultMessage);
        this.faultMessage = faultMessage;
    }


}
