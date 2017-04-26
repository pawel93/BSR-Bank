package bsr.model;

/**
 * Created by Paweł on 2017-01-12.
 */
public class BankAccount
{

    private int id;
    private String number;
    private double saldo;

    public BankAccount() {
    }

    public BankAccount(String number, double saldo) {
        this.number = number;
        this.saldo = saldo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public double getBalance() {
        return saldo;
    }

    public void setBalance(double balance) {
        this.saldo = balance;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }



}
