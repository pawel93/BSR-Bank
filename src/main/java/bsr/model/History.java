package bsr.model;

import java.io.Serializable;
import java.util.Date;


public class History implements Serializable
{
    private int id;
    private String account;
    private String title;
    private double income;
    private double outcome;
    private String source;
    private double saldo;
    private Date date;

    public History(){

    }

    public History(int id,String account, String title, double income, double outcome, String source, double saldo) {
        this.id = id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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
