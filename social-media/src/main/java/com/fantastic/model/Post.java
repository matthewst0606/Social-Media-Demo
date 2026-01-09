package com.fantastic.model;


public class Post {
    private String format, content, created_at;
    private int post_id, user_id;
    private boolean is_public;

    public Post(Integer postUserID, String format, String content, Boolean is_public) {
        this.user_id = postUserID;
        this.post_id = -1;
        this.format = format;
        this.content = content;
        this.is_public = is_public;
        this.created_at = null;
    }

    public int getUserID () {
         return this.user_id;
    }
    public int getPostID() {
        return this.post_id;
    }
    public String getFormat() {
         return this.format;
    }
    public String getContent() {
         return this.content;
    }
    public boolean getIsPublic() {
         return this.is_public;
    }
    public String getCreatedAt() {
         return this.created_at;
    }
    public void setPostID(int id) {
         this.post_id = id;
    }
    public void setCreatedAt(String date) {
         this.created_at = date;
    }  
}
