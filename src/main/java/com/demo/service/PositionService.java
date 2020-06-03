package com.demo.service;

import com.demo.dao.interfaces.PositionDao;
import com.demo.model.Position;

import java.util.List;

public class PositionService {

    private final PositionDao positionDao;

    public PositionService(PositionDao positionDao) {
        this.positionDao = positionDao;
    }

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
