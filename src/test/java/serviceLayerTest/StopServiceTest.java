package serviceLayerTest;

import com.demo.dao.impl.PositionDaoImpl;
import com.demo.dao.impl.StopDaoImpl;
import com.demo.dao.interfaces.StopDao;
import com.demo.model.Position;
import com.demo.model.Stop;
import com.demo.service.PositionService;
import com.demo.service.StopService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

@RunWith(MockitoJUnitRunner.class)
public class StopServiceTest {


    @Mock
    StopDao stopDao;

    @InjectMocks
    StopService stopService;

    List<Stop> listStop;

    Stop stop;

    @Before
    public void setup() {
        stop = new Stop();
        stop.setId(1);
        stop.setDuration(12);
        stop.setName("Test");

        Stop stop1 = new Stop();
        stop.setId(2);
        stop.setDuration(15);
        stop.setName("Test1");

        listStop = Arrays.asList(stop1, stop);
    }

    @Test
    public void testVerifySaveMethodCalled() throws SQLException {
        Mockito.when(stopDao.save(Mockito.any(Stop.class))).thenReturn(stop);

        Stop stopToTest = stopService.save(new Stop());

        verify(stopDao, times(1)).save(any(Stop.class));
        Assert.assertEquals(stopToTest.getId(), stop.getId());
    }

    @Test
    public void testVerifyGetAllMethod() {
        when(stopDao.getAll()).thenReturn(listStop);
        List<Stop> testStopList = stopService.getAll();


        verify(stopDao, times(1)).getAll();
        assertEquals(listStop.size(), testStopList.size());
    }

    @Test
    public void testVerifyGetByNameMethod() {

        Mockito.when(stopDao.getByName(anyString())).thenReturn(stop);
        Stop stopToTest = stopService.getAllByName("stop");


        verify(stopDao, times(1)).getByName(any(String.class));
        assertEquals(stopToTest.getId(), stop.getId());
        assertEquals(stopToTest.getDuration(), stop.getDuration());
        assertEquals(stopToTest.getName(), stop.getName());
    }

    @Test
    public void testGetByIdMethodCalled() {
        when(stopDao.getById(anyInt())).thenReturn(stop);

        Stop stopToTest = stopService.getById(anyInt());

        verify(stopDao, times(1)).getById(anyInt());
        assertEquals(stop.getId(), stopToTest.getId());
    }

    @Test
    public void testDeleteMethodCalled() {

        when(stopDao.delete(any(Stop.class))).thenReturn(true);

        boolean testIfDelete = stopService.delete(new Stop());

        verify(stopDao, times(1)).delete(any(Stop.class));
        assertTrue(testIfDelete);
    }

    @Test
    public void testDeleteByIdMethodCalled() {

        when(stopDao.deleteById(anyInt())).thenReturn(true);
        boolean testIfDelete = stopService.deleteById(1);

        verify(stopDao, times(1)).deleteById(anyInt());
        assertTrue(testIfDelete);
    }

    @Test
    public void testUpdateMethodCalled() {

        when(stopDao.update(any(Stop.class))).thenReturn(true);
        boolean testIfUpdate = stopService.update(new Stop());

        verify(stopDao, times(1)).update(any(Stop.class));
        assertTrue(testIfUpdate);
    }

    @Test
    public void testGetByRouteIdMethodCalled() {
        when(stopDao.getStopByRouteId(anyInt())).thenReturn(anySet());

        stopService.getStopByRouteId(2);

        verify(stopDao, times(1)).getStopByRouteId(anyInt());
    }
}
