package com.mytest.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import com.mytest.model.User;

@Repository("userDao")
public class UserDaoImpl extends AbstractDao<Integer, User> implements UserDao {

	@Override
	public User findById(int id) {
		return getByKey(id);
	}

	@Override
	public User findByUserName(String username) {
		Criteria crit = createEntityCriteria();
		crit.add(Restrictions.eq("username", username));
		return (User) crit.uniqueResult();
	}

	@Override
	public void save(User user) {
		persist(user);
	}

	@Override
	public void deleteUser(int id) {
		User user = findById(id);
		delete(user);
	}

	@Override
	public List<User> findAllUsers() {
		return list();
	}

	@Override
	public void updateUser(User user) {
		 System.out.println("Only an Admin can Update a User");
	        User u = findById(user.getId());
	        u.setFirstName(user.getFirstName());
	        u.setLastName(user.getLastName());
	        update(user);
	}

}
