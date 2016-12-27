package com.ak.search.model;

import com.orm.SugarRecord;

/**
 * Created by dg hdghfd on 29-11-2016.
 */

public class User extends SugarRecord {

    String name, password;

    //1-Admin, 2-Superviser, 3-User
    int type;

    public User() {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
