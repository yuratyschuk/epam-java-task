package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Worker;

import java.util.Optional;

public interface WorkerDao extends DAO<Worker> {

    Worker getByLastName(String lastName);
}
