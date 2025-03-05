package org.srebnaje.service.impl;

import org.srebnaje.dao.CommentDao;
import org.srebnaje.model.Comment;
import org.srebnaje.service.CommentService;
import org.srebnaje.service.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentDao commentDao;

    @Override
    @Transactional
    public Comment addComment(Comment comment) throws ServiceException {
        try {
            return commentDao.addComment(comment);
        } catch (Exception e) {
            throw new ServiceException("Error adding comment", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public Comment getCommentById(Long id) throws ServiceException {
        try {
            return commentDao.getCommentById(id);
        } catch (Exception e) {
            throw new ServiceException("Error retrieving comment by ID", e);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public List<Comment> getAllComments() throws ServiceException {
        try {
            return commentDao.getAllComments();
        } catch (Exception e) {
            throw new ServiceException("Error retrieving comment list", e);
        }
    }

    @Override
    @Transactional
    public Comment updateComment(Comment comment) throws ServiceException {
        try {
            return commentDao.updateComment(comment);
        } catch (Exception e) {
            throw new ServiceException("Error updating comment", e);
        }
    }

    @Override
    @Transactional
    public boolean deleteComment(Long id) throws ServiceException {
        try {
            return commentDao.deleteComment(id);
        } catch (Exception e) {
            throw new ServiceException("Error deleting comment", e);
        }
    }
}
