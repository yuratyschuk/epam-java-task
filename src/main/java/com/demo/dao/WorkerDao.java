package com.demo.dao;

import com.demo.model.Position;
import com.demo.model.Worker;

public interface WorkerDao extends DAO<Worker> {

    Worker getByLastName(String lastName);
}
