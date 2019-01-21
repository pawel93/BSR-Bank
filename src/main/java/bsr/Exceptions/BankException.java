package bsr.Exceptions;


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
