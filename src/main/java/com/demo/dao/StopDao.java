package com.demo.dao;

import com.demo.model.Position;
import com.demo.model.Stop;

public interface StopDao extends DAO<Stop> {
    Stop getByName(String name);
}
