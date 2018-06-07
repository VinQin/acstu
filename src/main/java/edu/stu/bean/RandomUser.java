package edu.stu.bean;

public class RandomUser {
    int id;
    String username;
    String password;
    boolean tag;
    String comments;

    public int getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public boolean isTag() {
        return tag;
    }

    public String getComments() {
        return comments;
    }

    public RandomUser(int id, String username, String password, boolean tag, String comments) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.tag = tag;
        this.comments = comments;
    }
}
