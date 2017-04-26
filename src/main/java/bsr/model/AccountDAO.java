package bsr.model;

import bsr.util.DBUtil;
import bsr.util.Mapping;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 2017-01-25.
 */
public class AccountDAO
{

    public static Account searchAccount(String login, String password)
    {
        Account account = null;
        String selectStmt = "SELECT * FROM accounts WHERE login='" + login + "'" + " AND password='" + password + "'";
        account = Mapping.getAccountFromResultSet(DBUtil.dbExecuteQuery(selectStmt));
        return account;
    }


    public static Account searchAccount(int id)
    {
        Account account = null;
        String selectStmt = "SELECT * FROM accounts WHERE id='" + id + "'";
        account = Mapping.getAccountFromResultSet(DBUtil.dbExecuteQuery(selectStmt));
        return account;
    }


    public static void insertAccount(String name, String surname, String login, String password)
    {
        String updateStmt = "INSERT INTO accounts(name, surname, login, password) VALUES('" + name + "','"
                + surname + "','" + login + "','" + password + "' )";
        DBUtil.dbExecuteUpdate(updateStmt);
    }

    public static void insertBankAccount(int id, String number, String saldo)
    {
        String updateStmt = "INSERT INTO bills(id, number, saldo) VALUES('" + id + "','" + number + "','" + saldo + "')";
        DBUtil.dbExecuteUpdate(updateStmt);
    }

    public static List<BankAccount> searchBankAccount(int id)
    {
        List<BankAccount> bankAccounts;
        String selectStmt = "SELECT * FROM bills WHERE id='" + id + "'";
        bankAccounts = Mapping.getBankAccounts(DBUtil.dbExecuteQuery(selectStmt));
        return bankAccounts;
    }

    public static BankAccount searchBankAccount(String number)
    {
        List<BankAccount> bankAccounts;
        String selectStmt = "SELECT * FROM bills WHERE number='" + number + "'";
        bankAccounts = Mapping.getBankAccounts(DBUtil.dbExecuteQuery(selectStmt));
        if(bankAccounts.size() == 0)
            return null;

        return bankAccounts.get(0);

    }

    public static void updateBankAccount(String number, String amount)
    {
        String selectStmt = "UPDATE bills SET saldo='" + amount + "'WHERE number='" + number + "'";
        DBUtil.dbExecuteUpdate(selectStmt);
    }

    public static void insertHistory(int id,String account, String title, String income, String outcome, String source, String saldo)
    {
        String updateStmt = "INSERT INTO history VALUES('" + id + "','" + account + "','" + title + "','" + income + "','" + outcome + "','" +
                source + "','" + saldo + "')";
        DBUtil.dbExecuteUpdate(updateStmt);

    }

    public static ArrayList<History> searchHistory(int id)
    {
        String selectStmt = "SELECT * FROM history WHERE id='" + id + "'";
        ArrayList<History> historyList = Mapping.getAccountHistory(DBUtil.dbExecuteQuery(selectStmt));

        return historyList;
    }



}
