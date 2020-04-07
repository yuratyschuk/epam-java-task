package com.demo.service;

import com.demo.dao.DAO;
import com.demo.dao.impl.PositionDaoImpl;
import com.demo.model.Position;
import com.demo.model.Worker;

import java.util.List;

public class PositionService {
    private DAO<Position> positionDao = new PositionDaoImpl();

    public PositionService(DAO<Position> positionDao) {
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

    public boolean create(Position position) {
        return positionDao.create(position);
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

}
