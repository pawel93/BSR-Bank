package bsr.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;



@Entity
@Table(name = "history")
public class History implements Serializable
{
    @Id
    @GeneratedValue(generator = "increment")
    private int id;

    private int accountId;
    private String account;
    private String title;
    private double income;
    private double outcome;
    private String source;
    private double saldo;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    public History(){

    }

    public History(int accountId, String account, String title, double income, double outcome, String source, double saldo) {
        this.accountId = accountId;
        this.account = account;
        this.title = title;
        this.income = income;
        this.outcome = outcome;
        this.source = source;
        this.saldo = saldo;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public double getIncome() {
        return income;
    }

    public void setIncome(double income) {
        this.income = income;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getOutcome() {
        return outcome;
    }

    public void setOutcome(double outcome) {
        this.outcome = outcome;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

}
