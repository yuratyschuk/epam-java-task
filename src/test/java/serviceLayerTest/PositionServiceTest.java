package serviceLayerTest;

import com.demo.dao.impl.PositionDaoImpl;
import com.demo.dao.interfaces.PositionDao;
import com.demo.model.Position;
import com.demo.service.PositionService;
import javafx.geometry.Pos;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PositionServiceTest {

    @Mock
    PositionDao positionDao;

    @InjectMocks
    PositionService positionService;

    List<Position> listPosition;
    List<Position> listPositionActive;
    Position position;
    Position position1;
    Position position2;

    @Before
    public void setup() {
        position = new Position();
        position.setId(1);
        position.setJobName("test job");
        position.setSalary(BigDecimal.ONE);
        position.setActive(false);
        when(positionDao.getById(1)).thenReturn(position);

        position1 = new Position();
        position1.setId(2);
        position1.setJobName("test job2");
        position1.setSalary(BigDecimal.ZERO);
        position1.setActive(true);


        position2 = new Position();
        position2.setId(3);
        position2.setJobName("test job3");
        position2.setSalary(BigDecimal.TEN);
        position2.setActive(true);

        listPosition = Arrays.asList(position, position1, position2);
        listPositionActive = Arrays.asList(position1, position2);
        when(positionDao.getByName(any(String.class))).thenReturn(position2);
    }

    @Test
    public void testVerifySaveMethodCalled() throws SQLException {
        positionService.save(new Position());

        verify(positionDao, times(1)).save(any(Position.class));
    }

    @Test
    public void testVerifySaveMethod() {

        Position position1 = positionService.getById(1);
        assertEquals(position1.getId(), position.getId());
        assertEquals(position1.getJobName(), position.getJobName());
        assertEquals(position1.getSalary(), position.getSalary());
        assertEquals(position1.getActive(), position1.getActive());
    }

    @Test
    public void testVerifyGetAllMethod() {
        when(positionDao.getAll()).thenReturn(listPosition);
        List<Position> testListPositions = positionService.getAll();


        verify(positionDao, times(1)).getAll();
        assertEquals(listPosition.size(), testListPositions.size());
    }

    @Test
    public void testVerifyGetByNameMethod() {

        Position positionToTest = positionService.getByName("test job3");

        verify(positionDao, times(1)).getByName(any(String.class));
        assertEquals(positionToTest.getId(), position2.getId());
        assertEquals(positionToTest.getJobName(), position2.getJobName());
        assertEquals(positionToTest.getActive(), position2.getActive());
        assertEquals(positionToTest.getSalary(), position2.getSalary());
    }

    @Test
    public void testDeleteMethodCalled() {

        when(positionDao.delete(any(Position.class))).thenReturn(true);

        boolean testIfDelete = positionService.delete(new Position());

        verify(positionDao, times(1)).delete(any(Position.class));
        assertTrue(testIfDelete);
    }

    @Test
    public void testDeleteByIdMethodCalled() {

        when(positionDao.deleteById(anyInt())).thenReturn(true);
        boolean testIfDelete = positionService.deleteById(1);

        verify(positionDao, times(1)).deleteById(anyInt());
        assertTrue(testIfDelete);
    }

    @Test
    public void testGetListByActiveCalled() {

        when(positionDao.getPositionListByActive(anyBoolean())).thenReturn(listPositionActive);
        List<Position> activePositionListToTest = positionService.getPositionListActive(true);

        assertTrue(activePositionListToTest.stream().allMatch(Position::getActive));
        assertEquals(activePositionListToTest.size(), listPositionActive.size());
    }

    @Test
    public void testUpdateMethodCalled() {

        when(positionDao.update(any(Position.class))).thenReturn(true);
        boolean testIfUpdate = positionService.update(new Position());

        verify(positionDao, times(1)).update(any(Position.class));
        assertTrue(testIfUpdate);
    }

}
