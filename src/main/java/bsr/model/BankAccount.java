package bsr.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;



@Entity
@Table(name = "bills")
public class BankAccount
{

    @Id
    private String number;

    private int id;
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
