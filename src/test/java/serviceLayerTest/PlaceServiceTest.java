package serviceLayerTest;

import com.demo.dao.impl.PlacesDaoImpl;
import com.demo.model.Places;
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
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlaceServiceTest {

    @Mock
    PlacesDaoImpl placesDao;

    @InjectMocks
    PlaceService placeService;

    List<Places> placesList;


    @Before
    public void setup() {
        Places places = new Places();
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
}
