package bsr.util.dao;

import bsr.model.Account;
import bsr.model.BankAccount;
import bsr.model.History;

import java.util.ArrayList;
import java.util.List;



public class DAO
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



    public static void insertBankAccount(int id, String number, double saldo)
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

    public static void updateBankAccount(String number, double amount)
    {
        String selectStmt = "UPDATE bills SET saldo='" + amount + "'WHERE number='" + number + "'";
        DBUtil.dbExecuteUpdate(selectStmt);
    }

    public static void makeTransfer(String sender, String receiver, double senderAmount, double receiverAmount){
        String updateSenderStmt = "UPDATE bills SET saldo='" + senderAmount + "'WHERE number='" + sender + "'";
        String updateReceiverStmt = "UPDATE bills SET saldo='" + receiverAmount + "'WHERE number='" + receiver + "'";
        DBUtil.dbExecuteTransaction(updateSenderStmt, updateReceiverStmt);

    }

    public static void deleteBankAccount(String accountNumber){
        String deleteStmt = "DELETE FROM bills WHERE number = '" + accountNumber + "'";
        DBUtil.dbExecuteUpdate(deleteStmt);
    }


    public static void insertHistory(History history)
    {
        String updateStmt = "INSERT INTO history VALUES('" + history.getAccountId() + "','"
                + history.getAccount() + "','"
                + history.getTitle() + "','"
                + history.getIncome() + "','"
                + history.getOutcome() + "','"
                + history.getSource() + "','"
                + history.getSaldo() + "',"
                + " datetime('now') )";

        DBUtil.dbExecuteUpdate(updateStmt);

    }

    public static ArrayList<History> searchHistory(int id)
    {
        String selectStmt = "SELECT * FROM history WHERE id='" + id + "'";
        ArrayList<History> historyList = Mapping.getAccountHistory(DBUtil.dbExecuteQuery(selectStmt));

        return historyList;
    }



}
