package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Position;

public interface PositionDao extends DAO<Position> {


    Position getByName(String name);

}
