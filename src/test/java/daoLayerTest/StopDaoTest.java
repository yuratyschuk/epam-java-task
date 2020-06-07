package daoLayerTest;

import com.demo.dao.impl.StopDaoImpl;
import com.demo.dao.interfaces.StopDao;
import com.demo.exceptions.StopException;
import com.demo.model.Stop;
import org.junit.Before;
import org.junit.Test;

import java.security.spec.ECField;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;

public class StopDaoTest {

    private StopDao stopDao;

    private Stop stop;

    @Before
    public void setup() {
        stopDao = new StopDaoImpl();

        stop = new Stop();
        stop.setName("Test stop");
        stop.setDuration(20);

        stop = stopDao.save(stop);
    }

    @Test
    public void testUpdateMethodDao() {
        stop.setName("Updated stop");
        stop.setDuration(10);

        stopDao.update(stop);

        Stop updatedStop = stopDao.getById(stop.getId());

        assertEquals(updatedStop.getId(), stop.getId());
        assertEquals(updatedStop.getName(), stop.getName());
        assertEquals(updatedStop.getDuration(), stop.getDuration());
    }

    @Test
    public void testGetByNameMethodDao() {
        Stop getStop = stopDao.getByName(stop.getName());

        assertEquals(stop.getDuration(), getStop.getDuration());
        assertEquals(stop.getName(), getStop.getName());
    }

    @Test
    public void testGetByIdMethodDao() {
        Stop getStop = stopDao.getById(stop.getId());

        assertEquals(stop.getId(), getStop.getId());
        assertEquals(stop.getDuration(), getStop.getDuration());
        assertEquals(stop.getName(), getStop.getName());
    }


    @Test
    public void testGetByRouteIdMethodDao() {
        Set<Stop> stopSet = stopDao.getStopByRouteId(2);

        assertEquals(stopSet.size(), 4);
    }

    @Test
    public void testDeleteMethodDao() {
        List<Stop> stopListBeforeDelete = stopDao.getAll();

        boolean checkIfDeleted = stopDao.delete(stop);

        List<Stop> stopListAfterDelete = stopDao.getAll();

        assertNotEquals(stopListAfterDelete.size(), stopListBeforeDelete.size());
        assertTrue(checkIfDeleted);
    }

    @Test
    public void testDeleteByIdMethodDao() {
        List<Stop> stopListBeforeDelete = stopDao.getAll();

        boolean checkIfDeleted = stopDao.deleteById(stop.getId());

        List<Stop> stopListAfterDelete = stopDao.getAll();

        assertNotEquals(stopListAfterDelete.size(), stopListBeforeDelete.size());
        assertTrue(checkIfDeleted);
    }

    @Test
    public void testSaveMethodDao() {
        Stop savedStop = stopDao.save(stop);

        assertEquals(savedStop.getId(), stop.getId());
        assertEquals(savedStop.getName(), stop.getName());
        assertEquals(savedStop.getDuration(), stop.getDuration());
    }

    @Test(expected = StopException.class)
    public void testUpdateMethodExceptionDao() {
        stop.setId(2500);
        stopDao.update(stop);
    }

    @Test(expected = StopException.class)
    public void testGetByNameMethodExceptionDao() {
        stopDao.getByName("exception");
    }

    @Test(expected = StopException.class)
    public void testGetByIdMethodExceptionDao() {
        stopDao.getById(2500);
    }

    @Test(expected = StopException.class)
    public void testDeleteMethodExceptionDao() {
        stop.setName("exception");
        stopDao.delete(stop);
    }

    @Test(expected = StopException.class)
    public void testDeleteByIdMethodExceptionDao() {
        stopDao.deleteById(2500);
    }


}
