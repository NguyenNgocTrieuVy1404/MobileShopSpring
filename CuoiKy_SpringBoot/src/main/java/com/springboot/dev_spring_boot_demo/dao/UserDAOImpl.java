package com.springboot.dev_spring_boot_demo.dao;

import com.springboot.dev_spring_boot_demo.entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class UserDAOImpl implements UserDAO {

    private EntityManager entityManager;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDAOImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public void save(User user) {
        if (user.getId() == null) {
            entityManager.persist(user);
        } else {
            entityManager.merge(user);
        }
    }

    @Override
    public User findByUsername(String username) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                "FROM User WHERE username = :username", User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByEmail(String email) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                "FROM User WHERE email = :email", User.class);
            query.setParameter("email", email);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        try {
            TypedQuery<User> query = entityManager.createQuery(
                "FROM User WHERE phoneNumber = :phoneNumber", User.class);
            query.setParameter("phoneNumber", phoneNumber);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public User findById(Long id) {
        return entityManager.find(User.class, id);
    }

    @Override
    public List<User> findAll() {
        TypedQuery<User> query = entityManager.createQuery(
            "FROM User ORDER BY username", User.class);
        return query.getResultList();
    }

    @Override
    public void deleteById(Long id) {
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public boolean existsByUsername(String username) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class)
                .setParameter("username", username)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean existsByEmail(String email) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.email = :email", Long.class)
                .setParameter("email", email)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public boolean existsByPhoneNumber(String phoneNumber) {
        Long count = entityManager.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.phoneNumber = :phoneNumber", Long.class)
                .setParameter("phoneNumber", phoneNumber)
                .getSingleResult();
        return count > 0;
    }

    @Override
    public void deleteAuthoritiesByUsername(String username) {
        jdbcTemplate.update("DELETE FROM authorities WHERE username = ?", username);
    }

    @Override
    public void deleteOrdersByUserId(Long userId) {
        jdbcTemplate.update("DELETE FROM orders WHERE user_id = ?", userId);
    }
}