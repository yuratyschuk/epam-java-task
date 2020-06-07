package serviceLayerTest;

import com.demo.dao.impl.RouteDaoImpl;
import com.demo.dao.interfaces.RouteDao;
import com.demo.model.Places;
import com.demo.model.Route;
import com.demo.service.RouteService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

    @Mock
    RouteDao routeDao;

    @InjectMocks
    RouteService routeService;

    List<Route> routesList;

    Route route;

    Route route1;

    @Before
    public void setup() {
        route = new Route();
        route.setId(1);
        Places departurePlace = new Places();
        departurePlace.setPlaceName("Test");
        route.setDeparturePlace(departurePlace);

        Places arrivalPlace = new Places();
        arrivalPlace.setPlaceName("Test");
        route.setArrivalPlace(arrivalPlace);

        route1 = new Route();
        route1.setId(2);
        Places departurePlace1 = new Places();
        departurePlace1.setPlaceName("Test2");
        route1.setDeparturePlace(departurePlace1);

        Places arrivalPlace1 = new Places();
        arrivalPlace1.setPlaceName("Test3");
        route1.setArrivalPlace(arrivalPlace1);

        routesList = Arrays.asList(route, route1);
    }

    @Test
    public void verifyGetAllMethodCalled() {
        when(routeDao.getAll()).thenReturn(routesList);

        List<Route> routeListToTest = routeService.getAll();

        verify(routeDao, times(1)).getAll();
        assertEquals(routeListToTest.size(), routesList.size());
    }

    @Test
    public void verifySaveMethodCalled() {
        when(routeDao.save(any(Route.class))).thenReturn(route);

        Route routeToTest = routeService.save(route);

        verify(routeDao, times(1)).save(any(Route.class));
        assertEquals(route.getId(), routeToTest.getId());
    }

    @Test
    public void verifyUpdateMethodCalled() {
        when(routeDao.update(any(Route.class))).thenReturn(true);
        boolean testIfUpdated = routeService.update(route1);

        verify(routeDao, times(1)).update(any(Route.class));
        assertTrue(testIfUpdated);
    }

    @Test
    public void testUpdateMethodFailed() {
        boolean testIfUpdated = routeService.update(route);
        assertFalse(testIfUpdated);
    }

    @Test
    public void testUpdateMethodSuccess() {
        when(routeDao.update(any(Route.class))).thenReturn(true);
        boolean testIfUpdated = routeService.update(route1);

        assertTrue(testIfUpdated);
    }

    @Test
    public void verifyGetByDeparturePlaceIdAndArrivalPlaceIdMethodCalled() {
        when(routeDao.getByArrivalPlaceIdAndDeparturePlaceId(anyInt(), anyInt()))
                .thenReturn(route);
        Route routeToTest = routeService.getByDeparturePlaceIdAndArrivalPlaceId(1, 2);

        verify(routeDao, times(1))
                .getByArrivalPlaceIdAndDeparturePlaceId(anyInt(), anyInt());
        assertEquals(routeToTest.getId(), route.getId());
    }

    @Test
    public void verifyGetByIdMethodCalled() {
        when(routeDao.getById(anyInt())).thenReturn(route);

        Route routeToTest = routeService.getById(1);

        verify(routeDao, times(1)).getById(anyInt());
        assertEquals(routeToTest.getId(), route.getId());
    }

    @Test
    public void verifyDeleteByIdMethodCalled() {
        when(routeDao.deleteById(anyInt())).thenReturn(true);

        boolean testIfDeleted = routeService.deleteById(1);


        verify(routeDao, times(1)).deleteById(anyInt());
        assertTrue(testIfDeleted);
    }

    @Test
    public void verifyDeleteMethodCalled() {
        when(routeDao.delete(any(Route.class))).thenReturn(true);

        boolean testIfDeleted = routeService.delete(new Route());

        verify(routeDao, times(1))
                .delete(any(Route.class));
        assertTrue(testIfDeleted);
    }


}
