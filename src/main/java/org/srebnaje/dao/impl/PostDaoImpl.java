package org.srebnaje.dao.impl;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.hibernate.HibernateException;
import org.srebnaje.dao.PostDao;
import org.srebnaje.dao.DaoException;
import org.srebnaje.model.Post;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Collections;

@Repository
public class PostDaoImpl implements PostDao {

	@Autowired
	private SessionFactory sessionFactory;

	private Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}

	@Override
	public Post createPost(Post post) throws DaoException {
		try {
			if (post == null) {
				throw new DaoException("Null Post object provided");
			}
			getCurrentSession().persist(post);
			return post;
		} catch (HibernateException e) {
			throw new DaoException("Error creating post", e);
		}
	}

	@Override
	public List<Post> getAllPosts() throws DaoException {
	    try {
	        Query<Post> query = getCurrentSession().createQuery(
	            "SELECT p FROM Post p JOIN FETCH p.author ORDER BY p.id DESC", Post.class
	        );
	        List<Post> posts = query.list();
	        return posts != null ? posts : Collections.emptyList();
	    } catch (HibernateException e) {
	        throw new DaoException("Error retrieving post list", e);
	    }
	}


	@Override
	public List<Post> findPostsByAuthor(String username) throws DaoException {
		try {

			Query<Post> query = getCurrentSession()
					.createQuery("SELECT p FROM Post p WHERE p.author.username = :username", Post.class);

			query.setParameter("username", username);

			List<Post> posts = query.list();

			return posts != null ? posts : Collections.emptyList();
		} catch (HibernateException e) {
			throw new DaoException("Error retrieving posts by author: " + username, e);
		}
	}

}
