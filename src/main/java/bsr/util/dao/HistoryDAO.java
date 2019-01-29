package bsr.util.dao;

import bsr.model.History;
import bsr.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.Date;
import java.util.List;

public class HistoryDAO {

    public static void insertHistory(History history){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();

        try{
            session.beginTransaction();
            history.setDate(new Date());
            session.persist(history);
            session.getTransaction().commit();

        }catch(HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }

    }

    public static List<History> searchHistory(int id){

        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
        Session session = sessionFactory.openSession();
        List<History> history = null;

        try{
            session.beginTransaction();

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<History> criteriaQuery = builder.createQuery(History.class);

            Root<History> root = criteriaQuery.from(History.class);
            criteriaQuery.select(root).where(builder.equal(root.get("accountId"), id));

            history = session.createQuery(criteriaQuery).getResultList();
            session.getTransaction().commit();

        }catch(HibernateException e){
            session.getTransaction().rollback();
            e.printStackTrace();

        }finally {
            session.close();
        }

        return history;

    }

}
