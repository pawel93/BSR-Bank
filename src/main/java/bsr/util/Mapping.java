package bsr.util;

import bsr.model.BankAccount;
import bsr.model.History;
import bsr.model.Account;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Paweł on 2017-01-25.
 */
public class Mapping
{

    public static Account getAccountFromResultSet(ResultSet rs)
    {
        Account account = null;
        try {
            if(rs.next())
            {
                account = new Account();
                account.setId(rs.getInt("id"));
                account.setName(rs.getString("name"));
                account.setSurname(rs.getString("surname"));
                account.setLogin(rs.getString("login"));
                account.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return account;
    }

    public static List<BankAccount> getBankAccounts(ResultSet rs)
    {
        List<BankAccount> bankAccounts = new ArrayList<>();
        try {
            while(rs.next())
            {
                BankAccount bankAccount = new BankAccount();
                bankAccount.setId(rs.getInt("id"));
                bankAccount.setNumber(rs.getString("number"));
                bankAccount.setBalance(Double.valueOf(rs.getString("saldo")));
                bankAccounts.add(bankAccount);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return bankAccounts;

    }

    public static ArrayList<History> getAccountHistory(ResultSet rs)
    {
        ArrayList<History> historyList = new ArrayList<>();
        try {
            while(rs.next())
            {
                History history = new History();
                history.setId(rs.getInt("id"));
                history.setAccount(rs.getString("account"));
                history.setTitle(rs.getString("title"));
                history.setIncome(rs.getString("income"));
                history.setOutcome(rs.getString("outcome"));
                history.setSource(rs.getString("source"));
                history.setSaldo(rs.getString("saldo"));
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }



}
