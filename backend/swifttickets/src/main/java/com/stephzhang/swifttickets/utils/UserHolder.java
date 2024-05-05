package com.stephzhang.swifttickets.utils;

import com.stephzhang.swifttickets.entity.User;

public class UserHolder {
    static ThreadLocal<User> tl = new ThreadLocal<User>();

    public static void set(User user){
        tl.set(user);
    }

    public static User get(){
        return tl.get();
    }

    public static void remove(){
        tl.remove();
    }
}
