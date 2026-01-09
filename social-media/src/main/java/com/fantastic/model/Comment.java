package com.fantastic.model;

public class Comment {
    private int commentID;
    private int postID;
    private int userID;
    private String content;
    private String created_at;

    // Constructor used when reading comments from the DB
    public Comment(int commentID, int postID, int userID, String content) {
        this.commentID = commentID;
        this.postID = postID;
        this.userID = userID;
        this.content = content;
    }

    // Constructor used when creating a new comment (comment_id auto-generated)
    public Comment(int postID, int userID, String content) {
        this.postID = postID;
        this.userID = userID;
        this.content = content;
    }

    // Getters
    public int getCommentID() {
        return commentID;
    }

    public int getPostID() {
        return postID;
    }

    public int getUserID() {
        return userID;
    }

    public String getContent() {
        return content;
    }

    public String getCreatedAt() {
        return created_at;
    }


    public void setCreatedAt(String created_at) {
        this.created_at = created_at;
    }

}
