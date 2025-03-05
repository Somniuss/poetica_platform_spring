package org.srebnaje.service;

import org.srebnaje.model.Comment;
import java.util.List;

public interface CommentService {

    Comment addComment(Comment comment) throws ServiceException;

    Comment getCommentById(Long id) throws ServiceException;

    List<Comment> getAllComments() throws ServiceException;

    Comment updateComment(Comment comment) throws ServiceException;

    boolean deleteComment(Long id) throws ServiceException;
}
