package com.springboot.api.dao;

import com.springboot.api.entity.Order;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderDaoImpl implements OrderDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Order> findAll() {
        TypedQuery<Order> query = entityManager.createQuery("from Order", Order.class);
        return query.getResultList();
    }

    @Override
    public Order findById(Long id) {
        return entityManager.find(Order.class, id);
    }

    @Override
    public List<Order> findByUserId(Long userId) {
        TypedQuery<Order> query = entityManager.createQuery(
            "from Order where user.id = :userId", Order.class);
        query.setParameter("userId", userId);
        return query.getResultList();
    }

    @Override
    public List<Order> findByStatus(String status) {
        TypedQuery<Order> query = entityManager.createQuery(
            "from Order where status = :status", Order.class);
        query.setParameter("status", status);
        return query.getResultList();
    }

    @Override
    public Order save(Order order) {
        if (order.getId() == null) {
            entityManager.persist(order);
            return order;
        } else {
            return entityManager.merge(order);
        }
    }

    @Override
    public void deleteById(Long id) {
        Order order = findById(id);
        if (order != null) {
            entityManager.remove(order);
        }
    }
} 