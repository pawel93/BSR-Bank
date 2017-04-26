package bsr.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 2017-01-12.
 */
public class Account
{

    private int id;
    private String name;
    private String surname;
    private String login;
    private String password;
    private List<BankAccount> bills = new ArrayList<>();

    public Account() {

    }

    public Account(String name, String surname, String login, String password) {
        this.name = name;
        this.surname = surname;
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }



    public List<BankAccount> getBills() {
        return bills;
    }

    public void setBills(List<BankAccount> bills) {
        this.bills = bills;
    }

    public void addBill(BankAccount bill) {
        bills.add(bill);
    }




}
