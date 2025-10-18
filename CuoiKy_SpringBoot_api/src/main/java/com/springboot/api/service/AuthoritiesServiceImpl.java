package com.springboot.api.service;

import com.springboot.api.dao.AuthoritiesDao;
import com.springboot.api.entity.Authorities;
import com.springboot.api.entity.AuthoritiesId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class AuthoritiesServiceImpl implements AuthoritiesService {

    private final AuthoritiesDao authoritiesDao;

    @Autowired
    public AuthoritiesServiceImpl(AuthoritiesDao authoritiesDao) {
        this.authoritiesDao = authoritiesDao;
    }

    @Override
    public List<Authorities> getAllAuthorities() {
        return authoritiesDao.findAll();
    }

    @Override
    public Authorities getAuthorityById(AuthoritiesId id) {
        return authoritiesDao.findById(id);
    }

    @Override
    public List<Authorities> getAuthoritiesByUsername(String username) {
        return authoritiesDao.findByUsername(username);
    }

    @Override
    @Transactional
    public Authorities saveAuthority(Authorities authority) {
        return authoritiesDao.save(authority);
    }

    @Override
    @Transactional
    public void deleteAuthority(AuthoritiesId id) {
        authoritiesDao.deleteById(id);
    }

    @Override
    @Transactional
    public void deleteAuthoritiesByUsername(String username) {
        authoritiesDao.deleteByUsername(username);
    }
} 