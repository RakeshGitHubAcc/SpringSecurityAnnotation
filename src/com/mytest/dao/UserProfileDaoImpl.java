package com.mytest.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mytest.model.UserProfile;

@Repository("userProfileDao")
public class UserProfileDaoImpl extends AbstractDao<Integer, UserProfile>
		implements UserProfileDao {

	@SuppressWarnings("unchecked")
	@Override
	public List<UserProfile> findAll() {
		Criteria crit = createEntityCriteria();
		crit.addOrder(Order.asc("type"));
		return (List<UserProfile>) crit.list();
	}

	@Override
	public UserProfile findByType(String type) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("type", type));
		return (UserProfile) crit.uniqueResult();
	}

	@Override
	public UserProfile findById(int id) {
		return getByKey(id);
	}

}
