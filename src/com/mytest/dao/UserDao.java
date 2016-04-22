package com.mytest.dao;

import java.util.List;

import com.mytest.model.User;

public interface UserDao {
	 User findById(int id);
	 User findByUserName(String username);
	 void save(User user);
	 List<User> findAllUsers();
	 void deleteUser(int id);
	 void updateUser(User user);
}
