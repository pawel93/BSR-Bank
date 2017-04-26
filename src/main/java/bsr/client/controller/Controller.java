package bsr.client.controller;

import bsr.model.History;
import bsr.model.Account;

import java.util.List;

/**
 * Created by Paweł on 2017-01-26.
 */
public class Controller
{
    Account account;
    List<History> historyList;

    public Controller()
    {

    }

    public void setAccount(Account account)
    {
        this.account = account;
    }

    public Account getAccount()
    {
        return account;
    }

    public List<History> getHistoryList() {
        return historyList;
    }

    public void setHistoryList(List<History> historyList) {
        this.historyList = historyList;
    }
}
