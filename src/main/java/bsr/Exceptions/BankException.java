package bsr.Exceptions;

/**
 * Created by Paweł on 2017-01-28.
 */
public class BankException extends Exception
{
    private String faultMessage;
    public BankException(String faultMessage)
    {
        super(faultMessage);
        this.faultMessage = faultMessage;
    }

    public String getFaultMessage() {
        return faultMessage;
    }

    public void setFaultMessage(String faultMessage) {
        this.faultMessage = faultMessage;
    }
}
