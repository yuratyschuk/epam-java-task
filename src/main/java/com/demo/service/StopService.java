package com.demo.service;

import com.demo.dao.impl.StopDaoImpl;
import com.demo.model.Stop;

import java.util.List;

public class StopService {

    StopDaoImpl stopDao = new StopDaoImpl();

    public boolean save(Stop stop) {
        return stopDao.save(stop);
    }

    public boolean update(Stop stop) {
        return stopDao.update(stop);
    }

    public List<Stop> getAll() {
        return stopDao.getAll();
    }

    public Stop getAllByName(String stopName) {
        return stopDao.getByName(stopName);
    }

    public Stop getById(int id) {
        return stopDao.getById(id);
    }

    public boolean delete(Stop stop) {
        return stopDao.delete(stop);
    }

    public boolean deleteById(int id) {
        return stopDao.deleteById(id);
    }
}