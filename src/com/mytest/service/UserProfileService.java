package com.mytest.service;

import java.util.List;

import com.mytest.model.UserProfile;

public interface UserProfileService {
	List<UserProfile> findAll();
    UserProfile findByType(String type);
    UserProfile findById(int id);
}
