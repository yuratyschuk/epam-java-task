package com.demo.service;

import com.demo.dao.DAO;
import com.demo.dao.WorkerDao;
import com.demo.dao.impl.WorkerDaoImpl;
import com.demo.model.Worker;

import java.util.List;

public class WorkerService {
    private WorkerDao workerDao;

    public WorkerService(WorkerDao workerDao) {
        this.workerDao = workerDao;
    }

    public Worker getById(Integer id) {
        return workerDao.getById(id);
    }

    public Worker getByName(String lastName) {
        return workerDao.getByLastName(lastName);
    }

    public List<Worker> getAll() {
        return workerDao.getAll();
    }

    public boolean create(Worker worker) {
        return workerDao.save(worker);
    }

    public boolean deleteByLastName(Worker worker) {
        return workerDao.delete(worker);
    }

    public boolean deleteById(Integer id) {
        return workerDao.deleteById(id);
    }

    public boolean update(Worker worker) {
        return workerDao.update(worker);
    }
}
