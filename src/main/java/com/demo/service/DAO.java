package com.demo.service;

import com.demo.model.Worker;

import java.util.List;

public interface DAO<T> {

    boolean update(T t);

    boolean delete(T t);

    boolean deleteById(Integer id);

    List<T> getAll();

    T getById(Integer id);

    boolean create(T t);
}
