package com.springboot.api.rest;

import com.springboot.api.entity.Authorities;
import com.springboot.api.entity.AuthoritiesId;
import com.springboot.api.service.AuthoritiesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authorities")
public class AuthoritiesRestController {

    private final AuthoritiesService authoritiesService;

    @Autowired
    public AuthoritiesRestController(AuthoritiesService authoritiesService) {
        this.authoritiesService = authoritiesService;
    }

    @GetMapping("")
    public List<Authorities> getAllAuthorities() {
        return authoritiesService.getAllAuthorities();
    }

    @GetMapping("/user/{username}")
    public ResponseEntity<List<Authorities>> getAuthoritiesByUsername(@PathVariable String username) {
        List<Authorities> authorities = authoritiesService.getAuthoritiesByUsername(username);
        if (authorities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(authorities);
    }

    @PostMapping("")
    public ResponseEntity<Authorities> addAuthority(@RequestBody Authorities authority) {
        Authorities savedAuthority = authoritiesService.saveAuthority(authority);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedAuthority);
    }

    @DeleteMapping("/{username}/{authority}")
    public ResponseEntity<String> deleteAuthority(@PathVariable String username, @PathVariable String authority) {
        AuthoritiesId id = new AuthoritiesId(username, authority);
        Authorities existingAuthority = authoritiesService.getAuthorityById(id);
        
        if (existingAuthority == null) {
            return ResponseEntity.notFound().build();
        }
        
        authoritiesService.deleteAuthority(id);
        return ResponseEntity.ok("Xóa quyền thành công: " + username + " - " + authority);
    }

    @DeleteMapping("/user/{username}")
    public ResponseEntity<String> deleteAuthoritiesByUsername(@PathVariable String username) {
        List<Authorities> authorities = authoritiesService.getAuthoritiesByUsername(username);
        
        if (authorities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        authoritiesService.deleteAuthoritiesByUsername(username);
        return ResponseEntity.ok("Xóa tất cả quyền của người dùng thành công: " + username);
    }

    @PutMapping("/{username}")
    public ResponseEntity<?> updateAuthority(@PathVariable String username, 
                                            @RequestBody Authorities newAuthority) {
        List<Authorities> existingAuthorities = authoritiesService.getAuthoritiesByUsername(username);
        
        if (existingAuthorities.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        
        // Xóa quyền cũ
        authoritiesService.deleteAuthoritiesByUsername(username);
        
        // Thêm quyền mới
        Authorities savedAuthority = authoritiesService.saveAuthority(newAuthority);
        
        return ResponseEntity.ok(savedAuthority);
    }
} 