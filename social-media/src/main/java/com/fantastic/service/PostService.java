package com.fantastic.service;

import java.sql.SQLException;
import java.util.List;

import com.fantastic.dao.PostDao;
import com.fantastic.model.Post;
import com.fantastic.model.User;


public class PostService {

    private PostDao pDao = new PostDao();

    public Post createPost(Integer postUserID, String format, String content, Boolean is_public) {
        Post post = new Post(postUserID, format, content, is_public);

        try {
            int result = pDao.insertPost(post);

            if (result == 1) return post;
            else return null;

        } catch (SQLException e) { return null; }
    }

    // method gets all posts
    public List<Post> getAllPosts() {
        try {
            return pDao.getAllPosts();
        } catch (SQLException e) { return java.util.Collections.emptyList(); }
    }

    public List<Post> getPostsByUserID(Integer userID) {
        try {
            return pDao.getPostsByUserID(userID);
        } catch (SQLException e) { return java.util.Collections.emptyList(); }
    }


    public boolean updatePost(User loggedUser, int postId, String newContent) {
        try {
            return pDao.updatePost(postId, loggedUser.getUserID(), newContent);
        } catch (SQLException e) { return false; }
    }

}