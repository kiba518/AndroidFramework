package com.kiba.framework.comm.db_local_table;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;


@Table(name = "Cache_User", onCreated = "CREATE UNIQUE INDEX index_name ON Cache_User(id)")
public class Cache_User {

    @Column(name = "id", isId = true)
    public int id;

    @Column(name = "realId")
    public int realId;

    @Column(name = "name")
    public String name;

    @Column(name = "loginName")
    public String loginName;

    @Column(name = "password")
    public String password;


    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRealId() {
        return realId;
    }

    public void setRealId(int realId) {
        this.realId = realId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
