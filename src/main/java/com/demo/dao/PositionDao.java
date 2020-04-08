package com.demo.dao;

import com.demo.model.Position;

public interface PositionDao extends DAO<Position> {


    Position getByName(String name);

}
