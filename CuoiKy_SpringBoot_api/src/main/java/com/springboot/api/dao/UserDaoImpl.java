package com.springboot.api.dao;

import com.springboot.api.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private static final Logger logger = LoggerFactory.getLogger(UserDaoImpl.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery("from User", User.class);
        return query.getResultList();
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public User findByUsername(String username) {
        logger.debug("===== TÌM USER THEO USERNAME: {} =====", username);
        
        TypedQuery<User> query = entityManager.createQuery(
            "from User where username = :username", User.class);
        query.setParameter("username", username);
        
        try {
            User user = query.getSingleResult();
            logger.debug("TÌM THẤY: User(id={}, username={}, password={})", 
                         user.getId(), user.getUsername(), user.getPassword());
            return user;
        } catch (NoResultException e) {
            logger.debug("KHÔNG TÌM THẤY USER với username: {}", username);
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        TypedQuery<User> query = entityManager.createQuery(
            "from User where email = :email", User.class);
        query.setParameter("email", email);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        TypedQuery<User> query = entityManager.createQuery(
            "from User where phoneNumber = :phoneNumber", User.class);
        query.setParameter("phoneNumber", phoneNumber);
        try {
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        TypedQuery<Long> query = entityManager.createQuery(
            "select count(u) from User u where u.username = :username", Long.class);
        query.setParameter("username", username);
        return query.getSingleResult() > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        TypedQuery<Long> query = entityManager.createQuery(
            "select count(u) from User u where u.email = :email", Long.class);
        query.setParameter("email", email);
        return query.getSingleResult() > 0;
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        TypedQuery<Long> query = entityManager.createQuery(
            "select count(u) from User u where u.phoneNumber = :phoneNumber", Long.class);
        query.setParameter("phoneNumber", phoneNumber);
        return query.getSingleResult() > 0;
    }

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
            return user;
        } else {
            return entityManager.merge(user);
        }
    }

    @Override
    public void deleteById(Long id) {
        User user = findById(id);
        if (user != null) {
            entityManager.remove(user);
        }
    }
} 