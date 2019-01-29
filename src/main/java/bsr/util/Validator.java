package bsr.util;

import bsr.model.Payment;
import bsr.model.Transfer;

import java.math.BigInteger;


public class Validator
{

    public static boolean validatePayment(Payment payment)
    {
        double value;
        String[] splitter = payment.getAmount().split("\\.");
        try{
            value = Double.valueOf(payment.getAmount());
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        if(splitter.length > 1)
            if(splitter[1].length() > 2)
                return false;

        if(value <= 0.0)
            return false;
        if(payment.getBillNumber() == null || payment.getAmount() == null){
            System.out.println("val payment");
            return false;

        }

        return true;

    }

    public static boolean validateTransfer(Transfer transfer)
    {

        double value;
        String[] splitter = transfer.getAmount().split("\\.");
        try{
            value = Double.valueOf(transfer.getAmount());
        }
        catch (NumberFormatException e)
        {
            return false;
        }
        if(transfer.getAmount().length() == 0 || transfer.getReceiver().length() == 0 || transfer.getSender().length() == 0 || transfer.getTitle().length() == 0){
            return false;
        }
        if(value <= 0.0)
            return false;
        if(splitter.length > 1)
            if(splitter[1].length() > 2)
                return false;

        return true;
    }

    public static boolean validateAccountNumber(String number)
    {
        String PLcode = "2521";
        number = number.substring(2, 26) + PLcode + number.substring(0, 2);
        BigInteger result = new BigInteger(number).mod(new BigInteger("97"));
        if(result.intValue() == 1){
            return true;
        }
        else
            return false;

    }

}
