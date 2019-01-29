package bsr.model;



public class Payment
{

    private String amount;
    private String billNumber;

    public Payment() {

    }

    public Payment(String amount, String billNumber) {
        this.amount = amount;
        this.billNumber = billNumber;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getBillNumber() {
        return billNumber;
    }

    public void setBillNumber(String billNumber) {
        this.billNumber = billNumber;
    }



}
