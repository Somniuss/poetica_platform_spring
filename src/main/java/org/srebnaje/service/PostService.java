package org.srebnaje.service;

import java.util.List;

import org.srebnaje.model.Post;

public interface PostService {

	Post createPost(Post post) throws ServiceException;

	List<Post> getAllPosts() throws ServiceException;

	List<Post> getPostsByAuthor(String username) throws ServiceException;

	
}
