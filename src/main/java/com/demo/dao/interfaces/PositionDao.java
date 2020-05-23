package com.demo.dao.interfaces;

import com.demo.dao.DAO;
import com.demo.model.Position;

import java.util.List;
import java.util.Optional;

public interface PositionDao extends DAO<Position> {


    Position getByName(String name);

    List<Position> getPositionListByActive(boolean active);

}
