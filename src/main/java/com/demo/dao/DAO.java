package com.demo.dao;

import java.util.List;
import java.util.Optional;

public interface DAO<T> {

    boolean update(T t);

    boolean delete(T t);

    List<T> getAll();

    T save(T t);

    T getById(Integer id);

    boolean deleteById(Integer id);

}
