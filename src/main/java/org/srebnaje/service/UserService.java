package org.srebnaje.service;

import org.srebnaje.model.User;

public interface UserService {

	User addUser(User user) throws ServiceException;

	User getUserById(Long id) throws ServiceException;

	User getUserByName(String username) throws ServiceException;

	boolean authenticateUser(String username, String rawPassword) throws ServiceException;

	
}
