package me.rokevin.demo.model;

/**
 * Created by luokaiwen on 15/12/16.
 */
public class User {

    private String name;
    private int avatar;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", avatar=" + avatar +
                '}';
    }

    public int getAvatar() {
        return avatar;
    }

    public void setAvatar(int avatar) {
        this.avatar = avatar;
    }
}
