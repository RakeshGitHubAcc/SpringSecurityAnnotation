package com.mytest.dao;

import java.util.List;

import com.mytest.model.UserProfile;

public interface UserProfileDao {
	List<UserProfile> findAll();
    UserProfile findByType(String type);
    UserProfile findById(int id);
}
