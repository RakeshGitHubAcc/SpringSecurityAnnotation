package com.mytest.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mytest.dao.UserDao;
import com.mytest.model.User;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService{
	@Autowired
    private UserDao dao;
	
	@Autowired
    private PasswordEncoder passwordEncoder;

	@Override
	public User findById(int id) {
		 return dao.findById(id);
	}

	@Override
	public User findByUserName(String username) {
		 return dao.findByUserName(username);
	}
	
	public void save(User user){
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        dao.save(user);
    }

	@Override
	public void deleteUser(int id) {
		dao.deleteUser(id);
	}

	@Override
	public List<User> findAllUsers() {
		return dao.findAllUsers();
	}

	@Override
	public void updateUser(User user) {
		dao.updateUser(user);
	}

}
