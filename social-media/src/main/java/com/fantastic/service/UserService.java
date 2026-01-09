package com.fantastic.service;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fantastic.dao.UserDao;
import com.fantastic.model.User;


public class UserService {

    private UserDao usrDao = new UserDao();

    
    public User registerUser(String email, String username, String password){
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(14);
		String encodedPswd = encoder.encode(password);

        User user = new User(email, username, encodedPswd);
        
        try {
            int result = usrDao.insertUser(user);
            if (result == 1) return user;
            return null;
        } catch (SQLException e) { return null; }
    }

    public User loginUser(String loginUser, String password) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(14);

        try {
            User found = usrDao.findUser(loginUser);
            if (found == null) return null;
            
            boolean matches = encoder.matches(password, found.getPassword());
            if (matches) return found;
            else return null;
            

        } catch (SQLException e) { return null; }
    }

    public List<User> getAllUsers() {
        try {
            return usrDao.getAllUsers();
        } catch (SQLException e) {
            return new ArrayList<>(); // safe fallback so main doesn't crash
        }
    }

    public int updatePfp(int userID, String newPfp) {
        try {
            return usrDao.updatePfp(userID, newPfp);
        } catch (SQLException e) { return 0; }
    }
    
    public int updateBio(int userID, String newBio) {
        try {
            return usrDao.updateBio(userID, newBio);
        } catch (SQLException e) { return 0; }
    }

    public int updateEmail(int userID, String newEmail) {
        try {
            return usrDao.updateEmail(userID, newEmail);
        } catch (SQLException e) { return 0; }
    }

    public int updateUsername(int userID, String newUsername) {
        try {
            return usrDao.updateUsername(userID, newUsername);
        } catch (SQLException e) { return 0; }

    }

    public int updatePassword(int userID, String newPassword) {
        try {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(14);
		    String encodedPswd = encoder.encode(newPassword);
            return usrDao.updatePassword(userID, encodedPswd);
        } catch (SQLException e) { return 0; }

    }

    public int deleteAccount(int userID) {
        try {
            return usrDao.deleteAccount(userID);
        } catch (SQLException e) { 
            return 0; 
        }

    }
}
