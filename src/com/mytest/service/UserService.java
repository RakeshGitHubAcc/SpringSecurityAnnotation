package com.mytest.service;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;

import com.mytest.model.User;

public interface UserService {
	User findById(int id);
    User findByUserName(String username);
    void save(User user);
    List<User> findAllUsers();
    @PreAuthorize("hasRole('ADMIN') AND hasRole('DBA')")
    void deleteUser(int id);
    @PreAuthorize("hasRole('ADMIN')")
    void updateUser(User user);
}
