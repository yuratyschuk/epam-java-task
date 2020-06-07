package daoLayerTest;

import com.demo.dao.impl.PlacesDaoImpl;
import com.demo.dao.interfaces.PlacesDao;
import com.demo.dao.interfaces.PositionDao;
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


    @Before
    public void setup() {
        placesDao = new PlacesDaoImpl();


    }

    @Test
    public void testPlacesDaoGetAllMethod() {
        List<Places> placesList = placesDao.getAll();

        assertEquals(placesList.size(), 6);
    }

}
