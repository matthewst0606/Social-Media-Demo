package com.fantastic.dao;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.fantastic.model.User;

public class UserDao {

    private Connection getConnection() throws SQLException{
        return DriverManager.getConnection(
            "jdbc:postgresql://localhost:0000/example",
            "example",
            "example"
        );
    }

    public int insertUser(User user) throws SQLException {
        Connection db = getConnection();
        String sql = "INSERT INTO users (email, username, password) " + 
                     "VALUES (?, ?, ?) RETURNING user_id, created_at";

        PreparedStatement ps = db.prepareStatement(sql);
        ResultSet rs;
        int result = 0;

        ps.setString(1, user.getEmail());
        ps.setString(2, user.getUsername());
        ps.setString(3, user.getPassword());
        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("user_id");
            user.setUserID(id);
            user.setCreatedAt(rs.getDate("created_at").toString());
            result = 1;
        

            PreparedStatement ps2 = db.prepareStatement(
                "INSERT INTO userProfile (user_id) VALUES (?)"
            );

            ps2.setInt(1, id);
            ps2.executeUpdate();
            ps2.close();
        }

        rs.close();
        ps.close();
        db.close();
        return result;
    }


    public User findUser(String loginUser) throws SQLException {
        Connection db = getConnection();
        User user = null;
        
        String sql = "SELECT user_id, email, username, password, created_at " + 
                     "FROM users " + 
                     "WHERE email = ? OR username = ?";

        PreparedStatement ps = db.prepareStatement(sql);
        ResultSet rs;

        ps.setString(1, loginUser);
        ps.setString(2, loginUser);

        rs = ps.executeQuery();

        if (rs.next()) {
            int id = rs.getInt("user_id");
            String email = rs.getString("email");
            String username = rs.getString("username");
            String password = rs.getString("password");
            String created_at = rs.getString("created_at");
        
            user = new User(email, username, password);
            user.setUserID(id);
            user.setCreatedAt(created_at);
        }


        rs.close();
        ps.close();
        db.close();
        return user;
    }


    public List<User> getAllUsers() throws SQLException {
        Connection db = getConnection();
        String sql = "SELECT user_id, username FROM users";
        List<User> users = new ArrayList<>();
        
        PreparedStatement ps = db.prepareStatement(sql);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            int user_id = rs.getInt("user_id");
            String username = rs.getString("username");

            User user = new User(null, username, null);
            user.setUserID(user_id);
            users.add(user);
        }
        rs.close();
        ps.close();
        db.close();
        return users;
    }

    public int updatePfp(int userID, String newPfp) throws SQLException {
        Connection db = getConnection();
        String sql = "UPDATE userProfile SET pfp = ? WHERE user_id = ?";

        PreparedStatement ps = db.prepareStatement(sql);

        ps.setString(1, newPfp);
        ps.setInt(2, userID);
        int rows = ps.executeUpdate();

        ps.close();
        db.close();
        return rows;
    }


    public int updateBio(int userID, String newBio) throws SQLException {
        Connection db = getConnection();
        String sql = "UPDATE userProfile SET bio = ? WHERE user_id = ?";

        PreparedStatement ps = db.prepareStatement(sql);

        ps.setString(1, newBio);
        ps.setInt(2, userID);
        int rows = ps.executeUpdate();

        ps.close();
        db.close();
        return rows;
    }
    
    public int updateEmail(int userID, String email) throws SQLException {
        Connection db = getConnection();
        String sql = "UPDATE users SET email = ? WHERE user_id = ?";

        PreparedStatement ps = db.prepareStatement(sql);

        ps.setString(1, email);
        ps.setInt(2, userID);
        int rows = ps.executeUpdate();

        ps.close();
        db.close();
        return rows;
    }
    public int updateUsername(int userID, String username) throws SQLException {
        Connection db = getConnection();
        String sql = "UPDATE users SET username = ? WHERE user_id = ?";

        PreparedStatement ps = db.prepareStatement(sql);

        ps.setString(1, username);
        ps.setInt(2, userID);
        int rows = ps.executeUpdate();

        ps.close();
        db.close();
        return rows;
    }
    public int updatePassword(int userID, String password) throws SQLException {
        Connection db = getConnection();
        String sql = "UPDATE users SET password = ? WHERE user_id = ?";

        PreparedStatement ps = db.prepareStatement(sql);

        ps.setString(1, password);
        ps.setInt(2, userID);
        int rows = ps.executeUpdate();

        ps.close();
        db.close();
        return rows;
    }

    public int deleteAccount(int userID) throws SQLException {
        Connection db = getConnection();

        String sql1 = "DELETE FROM userProfile WHERE user_id = ?";
        PreparedStatement ps1 = db.prepareStatement(sql1);

        ps1.setInt(1, userID);
        ps1.executeUpdate();
        ps1.close();

        String sql2 = "DELETE FROM users WHERE user_id = ?";
        PreparedStatement ps2 = db.prepareStatement(sql2);

        ps2.setInt(1, userID);
        int rows = ps2.executeUpdate();
        ps2.close();

        db.close();
        return rows;
    }
}
