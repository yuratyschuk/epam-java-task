package daoLayerTest;

import com.demo.dao.impl.PlacesDaoImpl;
import com.demo.dao.interfaces.PlacesDao;
import com.demo.dao.interfaces.PositionDao;
import com.demo.exceptions.DataInsertException;
import com.demo.exceptions.DataNotFoundException;
import com.demo.model.Places;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(JUnit4.class)
public class PlacesDaoTest {

    private PlacesDao placesDao;

    private Places places;

    @Before
    public void setup() {
        placesDao = new PlacesDaoImpl();

        places = new Places();
        places.setId(1);
        places.setPlaceName("test");

    }

    @Test
    public void testPlacesDaoGetAllMethod() {
        List<Places> placesList = placesDao.getAll();

        assertEquals(placesList.size(), 6);
    }

    @Test
    public  void testPlacesUpdateMethodDao() {
        boolean isUpdated = placesDao.update(places);

        assertTrue(isUpdated);
    }

    @Test(expected = DataInsertException.class)
    public void testPlacesUpdateMethodDaoException() {
        places.setId(2500);
        placesDao.update(places);
    }

    @Test
    public void testPlacesDeleteMethodDao() {

        placesDao.save(places);

        boolean isDeleted = placesDao.delete(places);

        assertTrue(isDeleted);
    }

    @Test(expected = DataNotFoundException.class)
    public void testPlacesDeleteMethodDaoException() {
        places.setId(2500);
        placesDao.delete(places);
    }

    @Test
    public void testPlacesSaveMethodDao() {
        Places savedPlaces = placesDao.save(places);

        assertEquals(savedPlaces.getId(), places.getId());
        assertEquals(savedPlaces.getPlaceName(), places.getPlaceName());

        placesDao.delete(savedPlaces);
    }

    @Test
    public void testPlacesGetByIdMethodDao() {
        placesDao.save(places);

        Places getByIdPlace = placesDao.getById(places.getId());

        assertEquals(getByIdPlace.getId(), places.getId());
        assertEquals(getByIdPlace.getPlaceName(), places.getPlaceName());

        placesDao.delete(places);
    }

    @Test(expected = DataNotFoundException.class)
    public void testPlacesGetByIdMethodDaoException() {
        placesDao.getById(2500);
    }

    @Test
    public void testPlacesDeleteByIdMethodDao() {
        placesDao.save(places);

        boolean isDeleted = placesDao.deleteById(places.getId());

        assertTrue(isDeleted);
    }

    @Test(expected = DataNotFoundException.class)
    public void testPlacesDeleteByIdMethodDaoException() {
        placesDao.deleteById(2500);
    }


}
