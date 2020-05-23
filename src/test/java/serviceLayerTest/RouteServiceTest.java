package serviceLayerTest;

import com.demo.dao.impl.RouteDaoImpl;
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

@RunWith(MockitoJUnitRunner.class)
public class RouteServiceTest {

    @Mock
    RouteDaoImpl routeDao;

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
        Mockito.when(routeDao.getAll()).thenReturn(routesList);

        List<Route> routeListToTest = routeService.getAll();

        Mockito.verify(routeDao, Mockito.times(1)).getAll();
        Assert.assertEquals(routeListToTest.size(), routesList.size());
    }

    @Test
    public void verifySaveMethodCalled() {
        Mockito.when(routeDao.save(Mockito.any(Route.class))).thenReturn(route);

        Route routeToTest = routeService.save(route);

        Mockito.verify(routeDao, Mockito.times(1)).save(Mockito.any(Route.class));
        Assert.assertEquals(route.getId(), routeToTest.getId());
    }

    @Test
    public void verifyUpdateMethodCalled() {
        Mockito.when(routeDao.update(Mockito.any(Route.class))).thenReturn(true);
        boolean testIfUpdated = routeService.update(route1);

        Mockito.verify(routeDao, Mockito.times(1)).update(Mockito.any(Route.class));
        Assert.assertTrue(testIfUpdated);
    }

    @Test
    public void testUpdateMethodFailed() {
        boolean testIfUpdated = routeService.update(route);
        Assert.assertFalse(testIfUpdated);
    }

    @Test
    public void testUpdateMethodSuccess() {
        boolean testIfUpdated = routeService.update(route1);

        Assert.assertTrue(testIfUpdated);
    }

    @Test
    public void verifyGetByDeparturePlaceIdAndArrivalPlaceIdMethodCalled() {
        Mockito.when(routeDao.getByArrivalPlaceIdAndDeparturePlaceId(Mockito.anyInt(), Mockito.anyInt()))
                .thenReturn(route);
        Route routeToTest = routeService.getByDeparturePlaceIdAndArrivalPlaceId(1, 2);

        Mockito.verify(routeDao, Mockito.times(1))
                .getByArrivalPlaceIdAndDeparturePlaceId(Mockito.anyInt(), Mockito.anyInt());
        Assert.assertEquals(routeToTest.getId(), route.getId());
    }

    @Test
    public void verifyGetByIdMethodCalled() {
        Mockito.when(routeDao.getById(Mockito.anyInt())).thenReturn(route);

        Route routeToTest = routeService.getById(1);

        Mockito.verify(routeDao, Mockito.times(1)).getById(Mockito.anyInt());
        Assert.assertEquals(routeToTest.getId(), route.getId());
    }

    @Test
    public void verifyDeleteByIdMethodCalled() {
        Mockito.when(routeDao.deleteById(Mockito.anyInt())).thenReturn(true);

        boolean testIfDeleted = routeService.deleteById(1);


        Mockito.verify(routeDao, Mockito.times(1)).deleteById(Mockito.anyInt());
        Assert.assertTrue(testIfDeleted);
    }

    @Test
    public void verifyDeleteMethodCalled() {
        Mockito.when(routeDao.delete(Mockito.any(Route.class))).thenReturn(true);

        boolean testIfDeleted = routeService.delete(new Route());

        Mockito.verify(routeDao, Mockito.times(1))
                .delete(Mockito.any(Route.class));
        Assert.assertTrue(testIfDeleted);
    }


}
