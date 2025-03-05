package org.srebnaje.dao;

import org.srebnaje.model.Post;
import java.util.List;

public interface PostDao {

	Post createPost(Post post) throws DaoException;

	List<Post> getAllPosts() throws DaoException;

	List<Post> findPostsByAuthor(String username) throws DaoException;
}
