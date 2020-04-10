package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Worker;

public interface WorkerDao extends DAO<Worker> {

    Worker getByLastName(String lastName);
}
