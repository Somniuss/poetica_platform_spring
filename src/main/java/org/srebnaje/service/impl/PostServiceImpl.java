package org.srebnaje.service.impl;

import org.srebnaje.dao.PostDao;
import org.srebnaje.model.Post;
import org.srebnaje.service.PostService;
import org.srebnaje.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostDao postDao;

	@Override
	@Transactional
	public Post createPost(Post post) throws ServiceException {
		try {
			return postDao.createPost(post);
		} catch (Exception e) {
			throw new ServiceException("Error creating post", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Post> getAllPosts() throws ServiceException {
		try {
			return postDao.getAllPosts();
		} catch (Exception e) {
			throw new ServiceException("Error retrieving post list", e);
		}
	}

	@Override
	@Transactional(readOnly = true)
	public List<Post> getPostsByAuthor(String username) throws ServiceException {
		try {
			return postDao.findPostsByAuthor(username);
		} catch (Exception e) {
			throw new ServiceException("Error retrieving posts by author: " + username, e);
		}
	}
}
