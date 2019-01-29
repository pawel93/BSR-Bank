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


public class AccountDAO {


    public static void insertAccount(Account account) throws Exception{

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            session.persist(account);
            session.getTransaction().commit();

        }catch (HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();
            throw new Exception("login exception");

        }finally {
            session.close();
        }

    }

    public static Account searchAccount(String login, String password){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Account account = null;

        try{
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Account> criteriaQuery = builder.createQuery(Account.class);

            Root<Account> root = criteriaQuery.from(Account.class);
            criteriaQuery.select(root).where(
                    builder.equal(root.get("login"), login),
                    builder.equal(root.get("password"), password));

            List<Account> accounts = session.createQuery(criteriaQuery).getResultList();
            if(accounts.size()>=1)
                account = accounts.get(0);

            session.getTransaction().commit();

        }catch (HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }

        return account;

    }

    public static Account searchAccount(int id){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        Account account = null;

        try{
            session.beginTransaction();
            account = session.get(Account.class, id);
            session.getTransaction().commit();

        }catch(HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }

        return account;

    }


}
