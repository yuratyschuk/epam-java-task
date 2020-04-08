package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Position;
import com.demo.model.Stop;

public interface StopDao extends DAO<Stop> {
    Stop getByName(String name);
}
