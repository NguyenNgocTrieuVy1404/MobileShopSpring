package com.springboot.api.dao;

import com.springboot.api.entity.OrderItem;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class OrderItemDaoImpl implements OrderItemDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<OrderItem> findAll() {
        TypedQuery<OrderItem> query = entityManager.createQuery("from OrderItem", OrderItem.class);
        return query.getResultList();
    }

    @Override
    public OrderItem findById(Long id) {
        return entityManager.find(OrderItem.class, id);
    }

    @Override
    public List<OrderItem> findByOrderId(Long orderId) {
        TypedQuery<OrderItem> query = entityManager.createQuery(
            "from OrderItem where order.id = :orderId", OrderItem.class);
        query.setParameter("orderId", orderId);
        return query.getResultList();
    }

    @Override
    public List<OrderItem> findByProductId(Long productId) {
        TypedQuery<OrderItem> query = entityManager.createQuery(
            "from OrderItem where product.id = :productId", OrderItem.class);
        query.setParameter("productId", productId);
        return query.getResultList();
    }

    @Override
    public OrderItem save(OrderItem orderItem) {
        if (orderItem.getId() == null) {
            entityManager.persist(orderItem);
            return orderItem;
        } else {
            return entityManager.merge(orderItem);
        }
    }

    @Override
    public void deleteById(Long id) {
        OrderItem orderItem = findById(id);
        if (orderItem != null) {
            entityManager.remove(orderItem);
        }
    }

    @Override
    public void deleteByOrderId(Long orderId) {
        List<OrderItem> orderItems = findByOrderId(orderId);
        for (OrderItem item : orderItems) {
            entityManager.remove(item);
        }
    }
} 