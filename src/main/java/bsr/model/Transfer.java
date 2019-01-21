package bsr.model;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;



@XmlRootElement
//@XmlAccessorType(XmlAccessType.FIELD)
public class Transfer
{

    @XmlElement(name = "title")
    private String title;

    @XmlElement(name = "amount")
    private String amount;

    @XmlElement(name = "sender")
    private String sender_account;

    @XmlElement(name = "receiver")
    private String receiver_account;

    public Transfer() {
    }

    public Transfer(String title, String amount, String sender, String receiver) {
        this.title = title;
        this.amount = amount;
        this.sender_account = sender;
        this.receiver_account = receiver;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getSender() {
        return sender_account;
    }

    public void setSender(String sender) {
        this.sender_account = sender;
    }

    public String getReceiver() {
        return receiver_account;
    }

    public void setReceiver(String receiver) {
        this.receiver_account = receiver;
    }
}
