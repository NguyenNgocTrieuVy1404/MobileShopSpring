package com.springboot.api.dao;

import com.springboot.api.entity.Authorities;
import com.springboot.api.entity.AuthoritiesId;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class AuthoritiesDaoImpl implements AuthoritiesDao {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Authorities> findAll() {
        TypedQuery<Authorities> query = entityManager.createQuery("from Authorities", Authorities.class);
        return query.getResultList();
    }

    @Override
    public Authorities findById(AuthoritiesId id) {
        return entityManager.find(Authorities.class, id);
    }

    @Override
    public List<Authorities> findByUsername(String username) {
        TypedQuery<Authorities> query = entityManager.createQuery(
            "from Authorities where username = :username", Authorities.class);
        query.setParameter("username", username);
        return query.getResultList();
    }

    @Override
    public Authorities save(Authorities authorities) {
        AuthoritiesId id = new AuthoritiesId(authorities.getUsername(), authorities.getAuthority());
        if (findById(id) == null) {
            entityManager.persist(authorities);
            return authorities;
        } else {
            return entityManager.merge(authorities);
        }
    }

    @Override
    public void deleteById(AuthoritiesId id) {
        Authorities authorities = findById(id);
        if (authorities != null) {
            entityManager.remove(authorities);
        }
    }

    @Override
    public void deleteByUsername(String username) {
        List<Authorities> authorities = findByUsername(username);
        for (Authorities auth : authorities) {
            entityManager.remove(auth);
        }
    }
} 