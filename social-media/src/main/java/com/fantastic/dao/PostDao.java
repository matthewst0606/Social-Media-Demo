package com.fantastic.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fantastic.model.Post;

public class PostDao {

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
            "jdbc:postgresql://localhost:0000/example",
            "example",
            "example"
        );
    }

    public int insertPost(Post post) throws SQLException {
        Connection db = getConnection();
        String sql = "INSERT INTO post (user_id, format, content, is_public) " +
                "VALUES (?, ?, ?, ?) RETURNING post_id, created_at";

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, post.getUserID());
        ps.setString(2, post.getFormat());
        ps.setString(3, post.getContent());
        ps.setBoolean(4, post.getIsPublic());

        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            post.setPostID(rs.getInt("post_id"));
            post.setCreatedAt(rs.getString("created_at"));
        }

        rs.close();
        ps.close();
        db.close();
        return 1;
    }

    public List<Post> getAllPosts() throws SQLException {
        // Implementation to retrieve all posts from the database
        Connection db = getConnection();

        List<Post> posts = new ArrayList<>();
        String sql = "SELECT post_id, created_at, user_id, format, content, is_public " + 
                     "FROM post ORDER BY created_at DESC";

        PreparedStatement ps = db.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int post_id = rs.getInt("post_id");
            String created_at = rs.getString("created_at");
            int user_id = rs.getInt("user_id");
            String format = rs.getString("format");
            String content = rs.getString("content");
            Boolean is_public = rs.getBoolean("is_public");

            Post post = new Post(user_id, format, content, is_public);
            post.setPostID(post_id);
            post.setCreatedAt(created_at);
            posts.add(post);
        }
        rs.close();
        ps.close();
        db.close();
        return posts;
    }

    public List<Post> getPostsByUserID(Integer userID) throws SQLException {
        // Implementation to retrieve posts by a specific user from the database
        List<Post> posts = new ArrayList<>();

        Connection db = getConnection();
        String sql = "SELECT * FROM post WHERE user_id = ? ORDER BY created_at DESC";

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, userID);
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            int post_id = rs.getInt("post_id");
            String created_at = rs.getString("created_at");
            int user_id = rs.getInt("user_id");
            String format = rs.getString("format");
            String content = rs.getString("content");
            Boolean is_public = rs.getBoolean("is_public");
            Post post = new Post(user_id, format, content, is_public);
            post.setPostID(post_id);
            post.setCreatedAt(created_at);
            posts.add(post);
        }
        rs.close();
        ps.close();
        db.close();
        return posts;
    }

    public boolean updatePost(int postId, int userId, String newContent) throws SQLException {
        Connection db = getConnection();

            
        String sql = "UPDATE post SET content = ? WHERE post_id = ? AND user_id = ?";

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, newContent);
        ps.setInt(2, postId);
        ps.setInt(3, userId);

        int rowsAffected = ps.executeUpdate();

        ps.close();
        db.close();
        return rowsAffected > 0;
    }

}
