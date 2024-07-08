package com.fu.springbootweb.dao;

abstract public interface LoginDao {
    abstract public boolean create(String account, String password);
    abstract public boolean delete(String account);
    abstract public boolean update(String account, String password);
    abstract public int query(String account, String password);
}
