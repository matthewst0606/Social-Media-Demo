package com.fantastic.presenter;

import java.util.List;

import com.fantastic.model.Comment;
import com.fantastic.model.Post;
import com.fantastic.model.User;
import com.fantastic.service.CommentService;
import com.fantastic.service.PostService;
import com.fantastic.service.UserService;
import com.fantastic.view.MainView;

public class MainPresenter {
    private MainView view;
    private UserService userService;
    private PostService postService;
    private CommentService commentService;

    private boolean running = true;
    private User currentUser = null;

    public MainPresenter(MainView view, UserService userService, PostService postService, CommentService commentService) {
        this.view = view;
        this.userService = userService;
        this.postService = postService;
        this.commentService = commentService;
    }

    public void run() {
        while (running) {
            while (currentUser == null && running) {
                view.printLoginMenu();
                String choice = view.readLine();

                switch (choice) {
                    case "1" -> registerUser();
                    case "2" -> currentUser = loginUser();
                    case "3" -> running = false;
                    default -> view.printError("Error: Input valid number.");
                }
            }
            if (!running) break;

            view.printMainMenu();
            String choice = view.readLine();

            switch (choice) {
                case "1" -> editProfile();
                case "2" -> createPost();
                case "3" -> displayPostsByUser();
                case "4" -> updatePost();
                case "5" -> commentOnPost();
                case "6" -> displayCommentsByPost();
                case "7" -> listAllPosts();
                case "8" -> listAllUsers();
                case "9" -> running = false;
                default -> view.printError("Error: Input valid number.");
            }
        }
    }


    /* LOGIN AND REGISTRATION FUNCTIONS
    ** ================================
    */
    private void registerUser() {
        String email = view.receive("Enter your email");
        String username = view.receive("Enter your username");
        String password = view.receive("Enter your password");

        if (email == null || username == null || password == null || 
            email.isBlank() || username.isBlank() || password.isBlank()) {
                view.printError("Registration failed: Credentials cannot be blank.");
            return;
        }
        User newUser = userService.registerUser(email, username, password);
        if (newUser == null) 
            view.printError("Registration failed: Email/Username may already be in use.");
        else {
            view.printRegistrationSuccess(newUser);   
            currentUser = newUser;
        }
    }

    // login to an existing account
    private User loginUser() {
        String loginUser = view.receive("\nEnter your email or username");
        String loginPswd = view.receive("Enter your password");

        if (loginUser == null || loginPswd == null ||
            loginUser.isBlank() || loginPswd.isBlank()) {
            view.printError("Login failed: Credentials cannot be blank.");
            return null;
        }
        
        User login = userService.loginUser(loginUser, loginPswd);
        if (login == null) {
            view.printError("Login failed: Invalid credentials.");
            return null;
        }
        
        return login;
    }


    /* USER FUNCTIONS
    ** ================================
    */

    // case 1: edit profile
    private void editProfile() {
        int userID = currentUser.getUserID();
        boolean running = true;

        while (running) {
            view.printUserSettings();

            String choice = view.readLine();
            switch(choice) {
                case "1" -> {
                    String newPfp = view.receive("\nEnter new profile photo");
                    int rows = userService.updatePfp(userID, newPfp);
                    if (rows == 1) view.printSuccess("Profile picture updated.");
                    else view.printError("Error: Profile not found.");
                }
                case "2" -> {
                    String newBio = view.receive("\nEnter a new bio");
                    int rows = userService.updateBio(userID, newBio);
                    if (rows == 1) view.printSuccess("Bio updated.");
                    else view.printError("Error: Profile not found.");

                }
                case "3" -> {
                    accountSettings();
                    if (currentUser == null) return;
                }
                case "4" -> running = false;
                default -> view.printError("Error: Input valid number.");
            }
        }
    } 

    // case 2: create a post
    private void createPost() {
        int userID = currentUser.getUserID();
        String format = view.receive("enter post format (text/image)");
        String content;

        if (format.equals("text") || format.equals("image")) {
            content = view.receive("enter post content");
            
        } else {
            view.printError("Error: Invalid format.");
            return;
        }
        Boolean is_public = Boolean.valueOf(view.receive("is the post public? (true/false)"));
        Post post = postService.createPost(userID,format, content, is_public);
        if (post == null) 
            view.printError("Error: Post creation failed.");
        else 
            view.printSuccess("\nYou have Posted successfully!\n\n");
    }

    // case 3: display all posts by a given user
    private void displayPostsByUser() {
        listAllUsers();

        try {
            int userID = Integer.parseInt(view.receive("Enter the User ID to view their posts"));
            List<Post> posts = postService.getPostsByUserID(userID);
            
            if (posts.isEmpty()) view.printError("This user has no posts.");
            else {
                view.printSuccess("Posts by User ID " + userID + ":");
                for (Post p : posts) {
                    view.printList("Post ID: " + p.getPostID() + " | Content: " + p.getContent());
                }
            }
        } catch (NumberFormatException e) {
            view.printError("Exception Error: Invalid User ID format.");
        }

    }

    // case 4: update a post
    private void updatePost() { 
        if (currentUser == null) {
            view.printError("Error: You must be logged in to update a post.");
            return;
        }

        List<Post> myPosts = postService.getPostsByUserID(currentUser.getUserID());
        if (myPosts.isEmpty()){
            view.printError("Error: no posts available.");
            return;
        }
        
        view.printSuccess("Your Posts");
        
        for (Post p : myPosts) {
            view.printList("Post ID: " + p.getPostID() + " | Content: " + p.getContent());
        }
        

        try {
            int pid = Integer.parseInt(view.receive("\nEnter Post ID to update"));
            boolean ownsPost = false;
            for (Post p : myPosts) {
                if (p.getPostID() == pid) {
                    ownsPost = true;
                    break;
                }
            }
            if (!ownsPost) {
                view.printError("Error: that Post ID does not exist in your posts.");
                return;
            }
            
            String newContent = view.receive("Enter new content");
            if (newContent.isBlank()) {
                view.printError("Error: Post content cannot be empty.");
                return;
            }

            boolean updated = postService.updatePost(currentUser, pid, newContent);
            if (updated) view.printSuccess("Post updated successfully.");
            else view.printError("Error: Failed to update post.");

        } catch (NumberFormatException e) {
            view.printError("Exception Error: Invalid Post ID.");
        }
    }

    // case 5: add a comment to a post
    private void commentOnPost() { 
        listAllPosts();
        if (currentUser == null) {
            view.printError("Error: You must be logged in to comment on a post.");
            return;
        }
        try {
            int pid = Integer.parseInt(view.receive("Enter Post ID to comment"));
            String comment = view.receive("Enter your comment");

            if (comment.isBlank()) {
                view.printError("Error: comment cannot be blank.");
                return;
            }

        int current = commentService.addCommentToPost(pid, currentUser.getUserID(), comment);
        boolean successful = current != -1;
        view.printList(successful ? "\nComment added." : "\nFailed to add comment.");
        } catch (NumberFormatException e) {
            view.printError("Exception Error: Invalid Post ID.");
        }

    }

    // case 6: display all comments on a given post
    private void displayCommentsByPost() { 
        listAllPosts();
        try {
        int pid = Integer.parseInt(view.receive("\nEnter Post ID to view comments"));
        List<Comment> comments = commentService.getCommentsByPostID(pid);

        if (comments == null || comments.isEmpty()) {
            view.printError("No comments yet.");
        } else {
            for (Comment c : comments) {
                view.printList("User " + c.getUserID() + ": " + c.getContent() +
                                " (on " + c.getCreatedAt() + ")");
            }
        }
        } catch (NumberFormatException e) {
            view.printError("Exception Error: Invalid Post ID.");
        }

    }

    // case 7: list all posts
    private void listAllPosts() {
        List<Post> posts = postService.getAllPosts();

        if (posts.isEmpty()) {
            view.printError("No posts available.");
        } else {
            view.printSuccess("\nAll Posts:\n");
            for (Post p : posts) {
                view.printList("User ID: " + p.getUserID() + " | Post ID: " + p.getPostID() + " | Post: " + p.getContent());
            }
        }
    }

    // case 8: list all users
    private void listAllUsers() {

        List<User> users = userService.getAllUsers();
        if (users.isEmpty()) {
            view.printError("Error: No users registered.");
        } else {
            view.printSuccess("Registered Users:");
            for (User u : users) {
                view.printList("User ID: " + u.getUserID() + " | Username: " + u.getUsername() + 
                               " | Email: " + u.getEmail());
            }
        }
    }


    private void accountSettings() {
        boolean running = true;
        while (running) {
        view.printAccountSettings();

        int userID = currentUser.getUserID();
        String choice = view.readLine();
        switch (choice) {
            case "1" -> {
                String newEmail = view.receive("\nEnter new email");
                if (!newEmail.isBlank()) {
                    userService.updateEmail(userID, newEmail);
                }
            }
            case "2" -> {
                String newUsername = view.receive("\nEnter new username");
                if (!newUsername.isBlank()) {
                    userService.updateUsername(userID, newUsername);
                }
            }
            case "3" -> {
                String newPassword = view.receive("\nEnter new password");
                if (!newPassword.isBlank()) {
                    userService.updatePassword(userID, newPassword);
                }
            }
            case "4" -> {
                view.printWarning();
                String confirm = view.receive("\nType DELETE to confirm");
                if ("DELETE".equals(confirm)) {
                    int deleted = userService.deleteAccount(userID);
                    if (deleted == 1) {
                    view.printSuccess("Account deleted.");
                    currentUser = null;
                    this.running = false;
                    return;
                    }
                }
                else view.printSuccess("Cancelled.\n");
            }
            case "5" -> running = false;
            default -> view.printError("Error: Input valid number.");        }
        }

        return;
    }
}
