package com.springboot.api.dao;

import com.springboot.api.entity.Authorities;
import com.springboot.api.entity.AuthoritiesId;
import java.util.List;

public interface AuthoritiesDao {
    
    List<Authorities> findAll();
    
    Authorities findById(AuthoritiesId id);
    
    List<Authorities> findByUsername(String username);
    
    Authorities save(Authorities authorities);
    
    void deleteById(AuthoritiesId id);
    
    void deleteByUsername(String username);
} 