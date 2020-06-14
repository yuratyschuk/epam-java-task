package serviceLayerTest;

import com.demo.dao.interfaces.PlacesDao;
import com.demo.model.Places;
import com.demo.model.Position;
import com.demo.service.PlaceService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlaceServiceTest {

    @Mock
    PlacesDao placesDao;

    @InjectMocks
    PlaceService placeService;

    List<Places> placesList;
    
    Places places;


    @Before
    public void setup() {
        places = new Places();
        places.setId(1);
        places.setPlaceName("Place1");

        Places places1 = new Places();
        places.setId(2);
        places.setPlaceName("Place2");

        placesList = Arrays.asList(places, places1);

    }

    @Test
    public void testGetAllMethodCalled() {
        when(placesDao.getAll()).thenReturn(placesList);

        List<Places> placesListToTest = placeService.getAll();

        verify(placesDao, times(1)).getAll();
        assertEquals(placesListToTest.size(), placesList.size());
    }

    @Test
    public void testVerifySaveMethod() {
        when(placesDao.getById(anyInt())).thenReturn(places);
        Places getByIdPlaces = placeService.getById(1);
        assertEquals(places.getId(), getByIdPlaces.getId());
        assertEquals(places.getPlaceName(), getByIdPlaces.getPlaceName());
    }


    @Test
    public void testDeleteMethodCalled() {

        when(placesDao.delete(any(Places.class))).thenReturn(true);

        boolean testIfDelete = placeService.delete(new Places());

        verify(placesDao, times(1)).delete(any(Places.class));
        assertTrue(testIfDelete);
    }

    @Test
    public void testDeleteByIdMethodCalled() {

        when(placesDao.deleteById(anyInt())).thenReturn(true);
        boolean testIfDelete = placesDao.deleteById(1);

        verify(placesDao, times(1)).deleteById(anyInt());
        assertTrue(testIfDelete);
    }


    @Test
    public void testUpdateMethodCalled() {

        when(placesDao.update(any(Places.class))).thenReturn(true);
        boolean testIfUpdate = placeService.update(new Places());

        verify(placesDao, times(1)).update(any(Places.class));
        assertTrue(testIfUpdate);
    }

}
