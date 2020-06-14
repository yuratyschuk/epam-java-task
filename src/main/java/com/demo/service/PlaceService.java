package com.demo.service;

import com.demo.dao.impl.PlacesDaoImpl;
import com.demo.dao.interfaces.PlacesDao;
import com.demo.model.Places;

import java.util.List;


public class PlaceService {

    private final PlacesDao placesDao;

    public PlaceService(PlacesDao placesDao) {
        this.placesDao = placesDao;
    }

    public List<Places> getAll() {
        return placesDao.getAll();
    }

    public boolean update(Places places) {
        return placesDao.update(places);
    }

    public boolean delete(Places places) {
        return placesDao.delete(places);
    }

    public Places save(Places places) {
         return placesDao.save(places);
    }

    public Places getById(int id) {
        return placesDao.getById(id);
    }

    public boolean deleteById(int id) {
        return  placesDao.deleteById(id);
    }

}
