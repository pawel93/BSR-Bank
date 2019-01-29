package bsr.Exceptions;

import javax.xml.ws.WebFault;




public class AccountNotFound extends Exception
{
    //private String faultMessage;

    public AccountNotFound(String faultMessage){
        super(faultMessage);
        //this.faultMessage = faultMessage;
    }


}
