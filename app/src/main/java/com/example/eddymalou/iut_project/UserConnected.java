package com.example.eddymalou.iut_project;

/**
 * Created by EddyMalou on 04/05/2017.
 */
public final class UserConnected {

    public static User user;

    public  UserConnected()
    {
    }

    public boolean isConnected()
    {
        if (this.user == null)
            return false;
        else
            return true;
    }

    public static User getUser() {
        return user;
    }

    public static void setUser(User user) {
        UserConnected.user = user;
    }
}
