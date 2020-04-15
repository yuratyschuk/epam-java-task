package com.demo.service;

import com.demo.dao.impl.PlacesDaoImpl;
import com.demo.model.Places;

import java.util.List;

public class PlaceService {

    private PlacesDaoImpl placesDao = new PlacesDaoImpl();

    public List<Places> getAll() {
        return placesDao.getAll();
    }
}
