package serviceLayerTest;

import com.demo.dao.impl.TrainDaoImpl;
import com.demo.dao.interfaces.TrainDao;
import com.demo.model.Train;
import com.demo.model.utils.TrainType;
import com.demo.service.TrainService;
import org.junit.Before;
import org.junit.Test;
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
public class TrainServiceTest {


    @Mock
    TrainDao trainDao;

    @InjectMocks
    TrainService trainService;

    List<Train> trainList;

    Train train;

    @Before
    public void setup() {
        train = new Train();
        train.setId(1);
        train.setTrainType(TrainType.PASSENGER);
        train.setTrainName("train name");
        train.setTrainNumber("train number");
        train.setMaxNumberOfCarriages(15);

        Train train1 = new Train();
        train1.setId(2);
        train1.setTrainType(TrainType.CARGO);
        train1.setTrainName("train name");
        train1.setTrainNumber("train number");
        train1.setMaxNumberOfCarriages(12);

        trainList = Arrays.asList(train, train1);
    }

    @Test
    public void testVerifySaveMethodCalled() throws SQLException {
        when(trainDao.save(any(Train.class))).thenReturn(new Train());
        trainService.save(new Train());

        verify(trainDao, times(1)).save(any(Train.class));
    }


    @Test
    public void testVerifyGetAllMethod() {
        when(trainDao.getAll()).thenReturn(trainList);
        List<Train> testTrainList = trainService.getAll();

        verify(trainDao, times(1)).getAll();
        assertEquals(trainList.size(), testTrainList.size());
    }

    @Test
    public void testGetByIdMethodCalled() {
        when(trainDao.getById(anyInt())).thenReturn(train);

        Train trainToSave = trainService.getById(anyInt());

        verify(trainDao, times(1)).getById(anyInt());
        assertEquals(trainToSave.getId(), train.getId());
    }


    @Test
    public void testDeleteMethodCalled() {
        when(trainDao.delete(any(Train.class))).thenReturn(true);
        boolean testIfDelete = trainService.delete(new Train());

        verify(trainDao, times(1)).delete(any(Train.class));
        assertTrue(testIfDelete);
    }

    @Test
    public void testDeleteByIdMethodCalled() {
        when(trainDao.deleteById(anyInt())).thenReturn(true);
        boolean testIfDelete = trainService.deleteById(1);

        verify(trainDao, times(1)).deleteById(anyInt());
        assertTrue(testIfDelete);
    }


    @Test
    public void testUpdateMethodCalled() {
        when(trainDao.update(any(Train.class))).thenReturn(true);
        boolean testIfUpdate = trainService.update(new Train());

        verify(trainDao, times(1)).update(any(Train.class));
        assertTrue(testIfUpdate);
    }

    @Test
    public void testGetByTypeMethodCalled() {
        when(trainDao.getTrainListByType(TrainType.CARGO)).thenReturn(trainList);
        List<Train> trainListToTest = trainService.getTrainListByType(TrainType.CARGO);

        verify(trainDao, times(1)).getTrainListByType(TrainType.CARGO);
        assertEquals(trainList.size(), trainListToTest.size());
    }

    @Test
    public void testGetByTrainNameMethodCalled() {
        when(trainDao.getByName(anyString())).thenReturn(train);
        Train trainToTest = trainService.getByTrainName("train name");

        verify(trainDao, times(1)).getByName(anyString());
        assertEquals(trainToTest.getId(), train.getId());
    }
}
