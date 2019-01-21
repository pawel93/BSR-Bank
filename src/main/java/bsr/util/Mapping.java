package bsr.util;

import bsr.model.Account;
import bsr.model.BankAccount;
import bsr.model.History;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



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
                bankAccount.setBalance(rs.getDouble("saldo"));
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
        DateProvider provider = new DateProvider("yyyy-MM-dd hh:mm:ss");
        try {
            while(rs.next())
            {
                History history = new History();
                history.setId(rs.getInt("id"));
                history.setAccount(rs.getString("account"));
                history.setTitle(rs.getString("title"));
                history.setIncome(rs.getDouble("income"));
                history.setOutcome(rs.getDouble("outcome"));
                history.setSource(rs.getString("source"));
                history.setSaldo(rs.getDouble("saldo"));
                history.setDate(provider.fromString(rs.getString("date")));
                historyList.add(history);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return historyList;
    }



}
