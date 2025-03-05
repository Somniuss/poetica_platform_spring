package org.srebnaje.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.srebnaje.dao.UserDao;
import org.srebnaje.model.User;
import org.srebnaje.dao.DaoException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public User addUser(User user) throws DaoException {
		try {
			if (user == null) {
				throw new DaoException("Null User object provided");
			}
			getCurrentSession().persist(user);
			return user;
		} catch (HibernateException e) {
			throw new DaoException("Error adding user", e);
		}
	}

	@Override
	public User getUserById(Long id) throws DaoException {
		try {
			User user = getCurrentSession().find(User.class, id);
			if (user == null) {
				throw new DaoException("User with ID " + id + " not found");
			}
			return user;
		} catch (HibernateException e) {
			throw new DaoException("Error retrieving user by ID", e);
		}
	}

	@Override
	public User getUserByName(String username) throws DaoException {
		try {
			Query<User> query = getCurrentSession().createQuery("FROM User u WHERE u.username = :username", User.class);
			query.setParameter("username", username);
			return (User) query.uniqueResult(); 
		} catch (HibernateException e) {
			throw new DaoException("Error retrieving user by username: " + username, e);
		}
	}

	@Override
	public boolean existsByUsername(String username) throws DaoException {
		try {
			Query<Long> query = getCurrentSession()
					.createQuery("SELECT COUNT(u) FROM User u WHERE u.username = :username", Long.class);
			query.setParameter("username", username);
			return query.getSingleResult() > 0;
		} catch (HibernateException e) {
			throw new DaoException("Error checking if user exists: " + username, e);
		}
	}
}
