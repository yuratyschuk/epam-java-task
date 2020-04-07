package com.demo.dao;

import java.util.List;

public interface DAO<T> {

    boolean update(T t);

    boolean delete(T t);

    boolean deleteById(Integer id);

    List<T> getAll();

    T getById(Integer id);

    T getByName(String name);

    boolean create(T t);
}
