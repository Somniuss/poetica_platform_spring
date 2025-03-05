package org.srebnaje.service.impl;

import org.srebnaje.dao.UserDao;
import org.srebnaje.model.User;
import org.srebnaje.service.UserService;
import org.srebnaje.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserServiceImpl implements UserService {

	private final UserDao userDao;
	private final PasswordEncoder passwordEncoder;

	@Autowired
	public UserServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder) {
		this.userDao = userDao;
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	@Transactional
	public User addUser(User user) throws ServiceException {
		try {
			if (user == null || user.getPassword() == null) {
				throw new ServiceException("Invalid user data");
			}

			if (userDao.existsByUsername(user.getUsername())) {
				throw new ServiceException("A user with this username already exists");
			}

			user.setPassword(passwordEncoder.encode(user.getPassword()));

			return userDao.addUser(user);
		} catch (Exception e) {
			throw new ServiceException("Error adding user", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserById(Long id) throws ServiceException {
		try {
			return userDao.getUserById(id);
		} catch (Exception e) {
			throw new ServiceException("Error retrieving user by ID", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public User getUserByName(String username) throws ServiceException {
		try {
			return userDao.getUserByName(username);
		} catch (Exception e) {
			throw new ServiceException("Error retrieving user by username: " + username, e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public boolean authenticateUser(String username, String rawPassword) throws ServiceException {
		try {
			User user = userDao.getUserByName(username);
			if (user == null) {
				return false;
			}
			return passwordEncoder.matches(rawPassword, user.getPassword());
		} catch (Exception e) {
			throw new ServiceException("Error during authentication", e);
		}
	}

}
