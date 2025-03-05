package org.srebnaje.dao;

import org.srebnaje.model.User;

public interface UserDao {

	User addUser(User user) throws DaoException;

	User getUserById(Long id) throws DaoException;

	User getUserByName(String username) throws DaoException;

	boolean existsByUsername(String username) throws DaoException;

}
