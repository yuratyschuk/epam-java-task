package daoLayerTest;

import com.demo.dao.impl.PositionDaoImpl;
import com.demo.dao.interfaces.PositionDao;
import com.demo.exceptions.DataInsertException;
import com.demo.exceptions.DataNotFoundException;
import com.demo.model.Position;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.xml.crypto.Data;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class PositionDaoTest {

    private PositionDao positionDao;

    private Position position;



    @Before
    public void setup() throws SQLException {
        positionDao = new PositionDaoImpl();

        position = new Position();
        position.setSalary(BigDecimal.TEN);
        position.setActive(true);
        position.setJobName("test job");

        position = positionDao.save(position);

    }



    @Test
    public void testUpdateMethodDao() {
        position.setSalary(BigDecimal.ZERO);
        position.setJobName("updated test job");

        positionDao.update(position);

        Position updatedPosition = positionDao.getById(position.getId());

        assertEquals(position.getId(), updatedPosition.getId());
        assertEquals(position.getJobName(), updatedPosition.getJobName());
        assertEquals(position.getSalary(), updatedPosition.getSalary());
        assertEquals(position.getActive(), updatedPosition.getActive());

    }

    @Test
    public void testGetAllMethodPositionDao() {
        List<Position> positionList = positionDao.getAll();
        assertEquals(positionList.size(), 5);
    }

    @Test
    public void testGetPositionListByActiveTrueMethodDao() {
        List<Position> positionListActive = positionDao.getPositionListByActive(true);

        Position positionActive = positionListActive.get(positionListActive.size() - 1);
        assertEquals(positionActive.getId(), position.getId());
        assertEquals(positionActive.getActive(), position.getActive());
        assertEquals(positionActive.getSalary(), position.getSalary());
        assertEquals(positionActive.getJobName(), position.getJobName());

        for (Position position : positionListActive) {
            assertTrue(position.getActive());
        }


    }

    @Test
    public void testGetPositionListByActiveFalseMethodDao() {
        List<Position> positionListActive = positionDao.getPositionListByActive(false);

        for(Position position : positionListActive) {
            assertFalse(position.getActive());
        }

    }

    @Test
    public void testDeleteMethodDao() {
        List<Position> positionListBeforeDelete = positionDao.getAll();

        positionDao.delete(position);

        List<Position> positionListAfterDelete = positionDao.getAll();

        assertNotEquals(positionListAfterDelete.size(), positionListBeforeDelete.size());


    }

    @Test
    public void testDeleteByIdMethodDao() {
        List<Position> positionListBeforeDelete = positionDao.getAll();

        positionDao.deleteById(position.getId());

        List<Position> positionListAfterDelete = positionDao.getAll();

        assertNotEquals(positionListAfterDelete.size(), positionListBeforeDelete.size());


    }



    @Test
    public void testGetByNameMethodDao() {
        Position getByNamePosition = positionDao.getByName(position.getJobName());

          assertEquals(getByNamePosition.getActive(), position.getActive());
        assertEquals(getByNamePosition.getJobName(), position.getJobName());
        assertEquals(getByNamePosition.getSalary(), position.getSalary());


    }

    @Test
    public void testSaveMethodDao() {
        List<Position> positionListBeforeSave = positionDao.getAll();

        position.setJobName("save job");
        Position savedPosition = positionDao.save(position);

        List<Position> positionListAfterSave = positionDao.getAll();

        assertNotEquals(positionListAfterSave.size(), positionListBeforeSave.size());

        assertEquals(savedPosition.getId(), position.getId());
        assertEquals(savedPosition.getActive(), position.getActive());
        assertEquals(savedPosition.getJobName(), position.getJobName());
        assertEquals(savedPosition.getSalary(), position.getSalary());

    }

    @Test(expected = DataInsertException.class)
    public void testUpdateMethodExceptionDao() {
        position.setId(2500);
        positionDao.update(position);
    }

    @Test(expected = DataNotFoundException.class)
    public void testDeleteMethodExceptionDao() {
        position.setId(2500);
        positionDao.delete(position);
    }

    @Test(expected = DataNotFoundException.class)
    public void testDeleteByIdMethodExceptionDao() {
        positionDao.deleteById(2500);
    }


    @Test(expected = DataNotFoundException.class)
    public void testGetByNameMethodExceptionDao() {
        positionDao.getByName("exception");
    }

}
