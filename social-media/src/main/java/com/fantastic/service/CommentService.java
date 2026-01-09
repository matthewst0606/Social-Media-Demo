package com.fantastic.service;
import java.sql.SQLException;
import java.util.List;

import com.fantastic.dao.CommentDao;
import com.fantastic.model.Comment;

public class CommentService {

    private final CommentDao commentDao = new CommentDao();

    // method to add comment
    public int addCommentToPost(int postID, int userID, String content) {
        try {
            int result = commentDao.addComment(postID, userID, content);
            return result;
        } catch (SQLException e) { return -1; }   
    }

    // method to get comments by post ID
    public List<Comment> getCommentsByPostID(int postID) {
        try {
            return commentDao.getCommentsByPostID(postID);
        } catch (SQLException e) { return java.util.Collections.emptyList(); }
    
    }
}
