package serviceLayerTest;

import com.demo.dao.impl.TripDaoImpl;
import com.demo.exceptions.TripException;
import com.demo.model.Train;
import com.demo.model.Trip;
import com.demo.model.utils.TrainType;
import com.demo.service.TripService;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.JUnitCore;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class TripServiceTest {


    @Mock
    TripDaoImpl tripDao;

    @InjectMocks
    TripService tripService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    List<Trip> tripList;

    Trip trip;

    @Before
    public void setup() {
        trip = new Trip();
        trip.setId(1);
        Train train = new Train();
        train.setTrainType(TrainType.CARGO);
        trip.setTrain(train);

        Trip trip1 = new Trip();
        trip1.setId(2);
        Train train1 = new Train();
        train1.setTrainType(TrainType.MULTI);
        trip1.setTrain(train1);

        tripList = Arrays.asList(trip, trip1);
    }

    @Test
    public void testVerifySaveMethodCalled() throws SQLException {
        when(tripDao.save(any(Trip.class))).thenReturn(new Trip());
        tripService.save(new Trip());

        verify(tripDao, times(1)).save(any(Trip.class));
    }

    @Test
    public void testVerifyGetAllMethod() {
        when(tripDao.getAll()).thenReturn(tripList);
        List<Trip> testTripList = tripService.getAll();

        verify(tripDao, times(1)).getAll();
        assertEquals(tripList.size(), testTripList.size());
    }

    @Test
    public void testGetByIdMethodCalled() {
        when(tripDao.getById(anyInt())).thenReturn(trip);
        Trip tripToSave = tripService.getById(anyInt());

        verify(tripDao, times(1)).getById(anyInt());
        assertEquals(tripToSave.getId(), trip.getId());
    }

    @Test
    public void testDeleteMethodCalled() {
        when(tripDao.delete(any(Trip.class))).thenReturn(true);
        boolean testIfDelete = tripService.delete(new Trip());

        verify(tripDao, times(1)).delete(any(Trip.class));
        assertTrue(testIfDelete);
    }

    @Test
    public void testDeleteByIdMethodCalled() {
        when(tripDao.deleteById(anyInt())).thenReturn(true);
        boolean testIfDelete = tripService.deleteById(1);

        verify(tripDao, times(1)).deleteById(anyInt());
        assertTrue(testIfDelete);
    }


    @Test
    public void testUpdateMethodCalled() {
        when(tripDao.update(any(Trip.class))).thenReturn(true);
        boolean testIfUpdate = tripService.update(new Trip());

        verify(tripDao, times(1)).update(any(Trip.class));
        assertTrue(testIfUpdate);
    }

    @Test
    public void verifyGetByRouteIdMethodCalled() {
        when(tripDao.getByRouteId(anyInt())).thenReturn(tripList);

        List<Trip> tripListToTest = tripService.getByRouteId(anyInt());

        verify(tripDao, times(1)).getByRouteId(anyInt());
        assertEquals(tripListToTest.size(), tripList.size());

    }


}
