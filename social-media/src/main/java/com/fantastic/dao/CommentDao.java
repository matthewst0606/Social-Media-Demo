package com.fantastic.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fantastic.model.Comment;

public class CommentDao {
    
    // method to establish database connection
    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
            "jdbc:postgresql://localhost:0000/example",
            "example",
            "example"
        );
    }

    //  method to add comment
    public int addComment(int postID, int userID, String content) throws SQLException {
        Connection db = getConnection();

        String sql = "INSERT INTO comment (content, post_id, user_id) VALUES (?, ?, ?)";

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setString(1, content);
        ps.setInt(2, postID);
        ps.setInt(3, userID);

        int result = ps.executeUpdate();

        ps.close();
        db.close();

        return result;
    }

    // method to get comments by post ID   
    public List<Comment> getCommentsByPostID(int postID) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        Connection db = getConnection();


        String sql = "SELECT comment_id, post_id, user_id, content, created_at " + 
                     "FROM comment WHERE post_id = ? ORDER BY created_at";

        PreparedStatement ps = db.prepareStatement(sql);
        ps.setInt(1, postID);

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            Comment c = new Comment(
                rs.getInt("comment_id"),
                rs.getInt("post_id"),
                rs.getInt("user_id"),
                rs.getString("content")
            );
            c.setCreatedAt(rs.getString("created_at"));
            comments.add(c);
        }

        rs.close();
        ps.close();
        db.close();

        return comments;

    }
}
