package com.demo;

import com.demo.dao.impl.*;
import com.demo.model.*;

import java.util.List;

public class Application {

    public static void main(String[] args) {

        WorkerDaoImpl workerDao = new WorkerDaoImpl();
        List<Worker> worker = workerDao.getAll();
        System.out.println(worker);
    }
}
