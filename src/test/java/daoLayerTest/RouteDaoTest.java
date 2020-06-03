package daoLayerTest;

import com.demo.dao.impl.RouteDaoImpl;
import com.demo.dao.interfaces.RouteDao;
import com.demo.exceptions.RouteException;
import com.demo.model.Places;
import com.demo.model.Route;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.rules.ExpectedException;

import java.util.List;

import static org.junit.Assert.*;

public class RouteDaoTest {

    private RouteDao routeDao;

    private Route route;

    @Before
    public void setup() {
        routeDao = new RouteDaoImpl();
        route = new Route();
        Places arrivalPlace = new Places();
        arrivalPlace.setId(1);
        Places departurePlace = new Places();
        departurePlace.setId(2);
        route.setArrivalPlace(arrivalPlace);
        route.setDeparturePlace(departurePlace);

        route = routeDao.save(route);
        System.out.println("AAA: " + route.getId());
    }


    @Test
    public void testUpdateMethodDao() {
        Places departurePlace = new Places();
        departurePlace.setId(2);
        Places arrivalPlaces = new Places();
        arrivalPlaces.setId(3);
        route.setDeparturePlace(departurePlace);
        route.setArrivalPlace(arrivalPlaces);

        routeDao.update(route);

        Route updateRoute = routeDao.getById(route.getId());
        assertEquals(route.getId(), updateRoute.getId());
        assertEquals(route.getArrivalPlace().getId(), updateRoute.getArrivalPlace().getId());
        assertEquals(route.getDeparturePlace().getId(), updateRoute.getDeparturePlace().getId());
    }

    @Test
    public void testGetByArrivalPlaceIdAndDeparturePlaceIdMethodDao() {
        Route getRoute =
                routeDao.getByArrivalPlaceIdAndDeparturePlaceId(route.getDeparturePlace().getId(),
                        route.getArrivalPlace().getId());

        assertEquals(route.getArrivalPlace().getId(), getRoute.getArrivalPlace().getId());
        assertEquals(route.getDeparturePlace().getId(), getRoute.getDeparturePlace().getId());
    }

    @Test
    public void testGetByIdMethodDao() {
        Route getRoute = routeDao.getById(route.getId());


        assertEquals(getRoute.getId(), route.getId());
        assertEquals(getRoute.getDeparturePlace().getId(), route.getDeparturePlace().getId());
        assertEquals(getRoute.getArrivalPlace().getId(), route.getArrivalPlace().getId());
    }


    @Test
    public void testDeleteByIdMethodDao() {
        List<Route> routeListBeforeDelete = routeDao.getAll();

        boolean checkIfDeleted = routeDao.deleteById(route.getId());

        List<Route> routeListAfterDelete = routeDao.getAll();

        assertNotEquals(routeListAfterDelete.size(), routeListBeforeDelete.size());
        assertTrue(checkIfDeleted);
    }

    @Test
    public void testSaveMethodDao() {
        Route savedRoute = routeDao.save(route);

        assertEquals(savedRoute.getId(), route.getId());
        assertEquals(savedRoute.getDeparturePlace().getId(), route.getDeparturePlace().getId());
        assertEquals(savedRoute.getArrivalPlace().getId(), route.getArrivalPlace().getId());
    }

    @Test(expected = RouteException.class)
    public void testUpdateMethodExceptionDao() {
        route.setId(2500);
        routeDao.update(route);
    }


    @Test(expected = RouteException.class)
    public void testGetByIdMethodExceptionDao() {
        routeDao.getById(2500);
    }

    @Test(expected = RouteException.class)
    public void testDeleteByIdMethodExceptionDao() {
        routeDao.deleteById(2500);
    }






}
