package net.discussions.core.business;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.discussions.core.dao.UserDAO;
import net.discussions.core.exception.SystemException;
import net.discussions.core.model.User;

@Service
public class UserBusiness {
	
	@Autowired
	private UserDAO userDAO;
	
	@Transactional
	public User saveOrUpdateUser(final User user) throws SystemException {
		return userDAO.saveOrUpdate(user);
	}
	
	public User getUser(final String email) throws SystemException {
		return userDAO.get(email);
	}
	
	public List<User> listUsers() throws SystemException {
		return (List<User>) userDAO.list();
	}
	
}
