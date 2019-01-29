package bsr.util.dao;

import bsr.model.Account;
import bsr.model.BankAccount;
import bsr.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

public class BankAccountDAO {

    public static void insertBankAccount(int id, BankAccount bankAccount){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        try{
            session.beginTransaction();
            Account account = session.load(Account.class, id);
            account.getBills().add(bankAccount);
            session.getTransaction().commit();

        }catch (HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }

    }

    public static BankAccount searchBankAccount(String number){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        BankAccount bankAccount = null;

        try{
            session.beginTransaction();
            bankAccount = session.get(BankAccount.class, number);
            session.getTransaction().commit();

        }catch(HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }

        return bankAccount;

    }

    public static List<BankAccount> searchBankAccount(int id){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<BankAccount> bankAccounts = null;

        try{
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<BankAccount> criteriaQuery = builder.createQuery(BankAccount.class);

            Root<BankAccount> root = criteriaQuery.from(BankAccount.class);
            criteriaQuery.select(root).where(builder.equal(root.get("id"), id));

            bankAccounts = session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();

        }catch(HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }

        return bankAccounts;


    }

    public static void updateBankAccount(String number, double saldo){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            BankAccount bankAccount = session.get(BankAccount.class, number);
            bankAccount.setBalance(saldo);

            session.getTransaction().commit();

        }catch(HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }

    }

    public static void makeTransfer(String sender, String receiver, double senderAmount, double receiverAmount){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            BankAccount senderBankAccount = session.get(BankAccount.class, sender);
            senderBankAccount.setBalance(senderAmount);
            BankAccount receiverBankAccount = session.get(BankAccount.class, receiver);
            receiverBankAccount.setBalance(receiverAmount);

            session.getTransaction().commit();

        }catch(HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }

    }

    public static void deleteBankAccount(String number){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            BankAccount bankAccount = session.get(BankAccount.class, number);
            session.delete(bankAccount);

            session.getTransaction().commit();

        }catch(HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }


    }


}
