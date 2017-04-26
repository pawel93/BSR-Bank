package bsr.model;

import java.io.Serializable;

/**
 * Created by Paweł on 2017-01-26.
 */
public class History implements Serializable
{
    private int id;
    private String account;
    private String title;
    private String income;
    private String outcome;
    private String source;
    private String saldo;

    public History(){

    }

    public History(int id,String account, String title, String income, String outcome, String source, String saldo) {
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

    public String getIncome() {
        return income;
    }

    public void setIncome(String income) {
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

    public String getOutcome() {
        return outcome;
    }

    public void setOutcome(String outcome) {
        this.outcome = outcome;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
