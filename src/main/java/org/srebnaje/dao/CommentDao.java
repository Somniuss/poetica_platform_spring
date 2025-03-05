package org.srebnaje.dao;

import org.srebnaje.model.Comment;
import java.util.List;

public interface CommentDao {
	
	Comment addComment(Comment comment) throws DaoException;

	Comment getCommentById(Long id) throws DaoException;

	List<Comment> getAllComments() throws DaoException;

	Comment updateComment(Comment comment) throws DaoException;

	boolean deleteComment(Long id) throws DaoException;
}
