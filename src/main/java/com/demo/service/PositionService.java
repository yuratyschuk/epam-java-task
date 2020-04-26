package com.demo.service;

import com.demo.dao.impl.PositionDaoImpl;
import com.demo.model.Position;

import java.util.List;

public class PositionService {
    private PositionDaoImpl positionDao = new PositionDaoImpl();

    public Position getById(Integer id) {
        return positionDao.getById(id);
    }

    public Position getByName(String jobName) {
        return positionDao.getByName(jobName);
    }

    public List<Position> getAll() {
        return positionDao.getAll();
    }

    public Position save(Position position) {
        return positionDao.save(position);
    }

    public boolean delete(Position position) {
        return positionDao.delete(position);
    }

    public boolean deleteById(Integer id) {
        return positionDao.deleteById(id);
    }

    public boolean update(Position position) {
        return positionDao.update(position);
    }

    public List<Position> getPositionListByActive(boolean active) {
        return positionDao.getPositionListByActive(active);
    }

}
