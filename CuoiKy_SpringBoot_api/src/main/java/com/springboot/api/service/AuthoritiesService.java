package com.springboot.api.service;

import com.springboot.api.entity.Authorities;
import com.springboot.api.entity.AuthoritiesId;
import java.util.List;

public interface AuthoritiesService {
    
    List<Authorities> getAllAuthorities();
    
    Authorities getAuthorityById(AuthoritiesId id);
    
    List<Authorities> getAuthoritiesByUsername(String username);
    
    Authorities saveAuthority(Authorities authority);
    
    void deleteAuthority(AuthoritiesId id);
    
    void deleteAuthoritiesByUsername(String username);
} 