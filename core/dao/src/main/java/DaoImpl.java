package com.exist.ecc.dao;

// import org.hibernate.HibernateException; 
// import org.hibernate.Session; 
// import org.hibernate.SessionFactory;
// import org.hibernate.cfg.Configuration;
// import org.hibernate.Query;
// import java.util.List;
// import org.springframework.stereotype.Repository;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.transaction.annotation.Transactional;

// @Repository("dao")
// @Transactional
// public class DaoImpl implements Dao{

//     @Autowired
//     private SessionFactory sessionFactory;

//     @Override
//     public <T> void create(T object) {
//       sessionFactory.getCurrentSession().save(object);
//     }

//     @Override
//     public Object getById(long id, String object) {
//         return sessionFactory.getCurrentSession().get(object, id); 
//     }
    
//     @Override
//     public <T> void update(T object) {
//       sessionFactory.getCurrentSession().update(object);
//     }

//     @Override
//     public <T> void delete(long id, String object) {
//       Session session = sessionFactory.getCurrentSession();
//         Object resultObject = session.get(object, id);
//         if (resultObject != null) {
//             session.delete(resultObject);
//         }
//     }

//     @Override
//     public List getList(String object) {
//       return (List<Object>) sessionFactory.getCurrentSession().createQuery("from " + object).setCacheable(true).list();
//     }
// }

import org.hibernate.HibernateException; 
import org.hibernate.Session; 
import org.hibernate.Criteria;
import static org.hibernate.Criteria.DISTINCT_ROOT_ENTITY;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.Query;
import java.util.List;
import org.springframework.stereotype.Repository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Repository("dao")
@Transactional
public class DaoImpl implements Dao{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public <T> void create(T object) {
        sessionFactory.getCurrentSession().save(object);
    }

    @Override
    public Object getById(long id, String object) {
        return sessionFactory.getCurrentSession().get(object, id); 
    }
    
    @Override
    public <T> void update(T object) {
        sessionFactory.getCurrentSession().update(object);
    }

    @Override
    public <T> void delete(long id, String object) {
        Session session = sessionFactory.getCurrentSession();
        Object resultObject = session.get(object, id);
        if (resultObject != null) {
            session.delete(resultObject);
        }
    }

    @Override
    public List getList(String object) {
        Session session = sessionFactory.getCurrentSession();
        List results = null;
        try {
            Criteria criteria = session.createCriteria(object);
            criteria.setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
            results = criteria.list();
        } catch (HibernateException e) {
            e.printStackTrace(); 
        }
        return results;
    }
}