package net.discussions.core.dao;

import java.util.Collection;

import org.springframework.stereotype.Repository;

import net.discussions.core.exception.SystemException;
import net.discussions.core.model.User;

@Repository
public class UserDAO extends GenericDAO<User, String> {
	
	public User saveOrUpdate(final User user) throws SystemException {
		return user.getEmail() == null ? super.save(user) : super.update(user);
	}
	
	public User get(final String email) throws SystemException {
		return super.get(User.class, email);
	}
	
	public Collection<User> list() throws SystemException {
		return super.list(User.class);
	}
	
}
