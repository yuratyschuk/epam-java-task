package daoLayerTest;

import com.demo.dao.impl.TripDaoImpl;
import com.demo.dao.interfaces.TripDao;
import com.demo.exceptions.DataInsertException;
import com.demo.exceptions.DataNotFoundException;
import com.demo.model.Route;
import com.demo.model.Train;
import com.demo.model.Trip;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class TripDaoTest {

    TripDao tripDao;

    Trip trip;

    @Before
    public void setup() {
        tripDao = new TripDaoImpl();
        trip = new Trip();

        trip.setNumberOfPlaces(25);
        trip.setNumberOfCarriages(20);
        trip.setTicketPrice(BigDecimal.ZERO);
        trip.setArrivalTime(new Date());
        trip.setDepartureTime(new Date());

        Train train = new Train();
        train.setId(2);

        Route route = new Route();
        route.setId(2);

        trip.setTrain(train);
        trip.setRoute(route);
        trip.setStopSet(Collections.emptySet());

        trip = tripDao.save(trip);
    }

    @Test
    public void testUpdateMethodDao() {
        trip.setTicketPrice(BigDecimal.TEN);
        trip.setNumberOfCarriages(200);
        trip.setNumberOfPlaces(10);

        tripDao.update(trip);

        Trip updatedTrip = tripDao.getById(trip.getId());

        assertEquals(trip.getId(), updatedTrip.getId());
        assertEquals(trip.getTicketPrice(), updatedTrip.getTicketPrice());
        assertEquals(trip.getNumberOfPlaces(), updatedTrip.getNumberOfPlaces());
        assertEquals(trip.getNumberOfCarriages(), updatedTrip.getNumberOfCarriages());
    }

    @Test
    public void testDeleteMethodDao() {
        List<Trip> tripListBeforeDelete = tripDao.getAll();

        boolean checkIfDelete = tripDao.delete(trip);

        List<Trip> tripListAfterDelete = tripDao.getAll();

        assertNotEquals(tripListAfterDelete.size(), tripListBeforeDelete.size());
        assertTrue(checkIfDelete);
    }

    @Test
    public void testDeleteByIdMethodDao() {
        List<Trip> tripListBeforeDelete = tripDao.getAll();

        boolean checkIfDelete = tripDao.deleteById(trip.getId());

        List<Trip> tripListAfterDelete = tripDao.getAll();

        assertNotEquals(tripListAfterDelete.size(), tripListBeforeDelete.size());
        assertTrue(checkIfDelete);
    }

    @Test
    public void testGetByRouteIdMethodDao() {
        List<Trip> tripListGetByRouteId = tripDao.getByRouteId(trip.getRoute().getId());

        for(Trip trip : tripListGetByRouteId) {
            assertEquals(trip.getRoute().getId(), this.trip.getRoute().getId());
        }
    }


    @Test
    public void testGetByIdMethodDao() {
        Trip getByIdTrip = tripDao.getById(trip.getId());

        assertEquals(trip.getId(), getByIdTrip.getId());
    }

    @Test
    public void testSaveMethodDao() {
        Trip savedTrip = tripDao.save(trip);

        assertEquals(trip.getNumberOfCarriages(), savedTrip.getNumberOfCarriages());
        assertEquals(trip.getNumberOfPlaces(), savedTrip.getNumberOfPlaces());
        assertEquals(trip.getTicketPrice(), savedTrip.getTicketPrice());
    }

    @Test
    public void testSaveToStopTripTable() {
        boolean isSaved = tripDao.saveToStopTripTable(1, 2);

        assertTrue(isSaved);
    }

    @Test(expected = DataInsertException.class)
    public void testUpdateMethodExceptionDao() {
        trip.setId(2500);
        tripDao.update(trip);
    }

    @Test(expected = DataNotFoundException.class)
    public void testDeleteMethodExceptionDao() {
        trip.setId(2500);
        tripDao.delete(trip);
    }

    @Test(expected = DataNotFoundException.class)
    public void testDeleteByIdMethodExceptionDao() {
        tripDao.deleteById(2500);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetByRouteIdMethodExceptionDao() {
        tripDao.getByRouteId(2500);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetByIdMethodExceptionDao() {
        tripDao.getById(2500);
    }



}
