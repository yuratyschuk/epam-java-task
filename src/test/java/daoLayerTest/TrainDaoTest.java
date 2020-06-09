package daoLayerTest;

import com.demo.dao.impl.TrainDaoImpl;
import com.demo.dao.interfaces.TrainDao;
import com.demo.exceptions.DataInsertException;
import com.demo.exceptions.DataNotFoundException;
import com.demo.model.Train;
import com.demo.model.utils.TrainType;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import static org.junit.Assert.*;

public class TrainDaoTest {

    TrainDao trainDao;

    Train train;

    Connection connection;

    @Before
    public void setup() throws SQLException {
        trainDao = new TrainDaoImpl();

        train = new Train();

        train.setTrainType(TrainType.PASSENGER);
        train.setMaxNumberOfCarriages(25);
        train.setTrainNumber("test train number");
        train.setTrainName("test train name");

        train = trainDao.save(train);

    }


    @Test
    public void testUpdateMethodDao() throws SQLException {
        train.setMaxNumberOfCarriages(20);
        train.setTrainType(TrainType.CARGO);
        train.setTrainNumber("updated train number");
        train.setTrainName("updated train name");
        trainDao.update(train);

        Train updateTrain = trainDao.getById(train.getId());

        assertEquals(updateTrain.getId(), train.getId());
        assertEquals(updateTrain.getTrainName(), train.getTrainName());
        assertEquals(updateTrain.getMaxNumberOfCarriages(), train.getMaxNumberOfCarriages());
        assertEquals(updateTrain.getTrainNumber(), train.getTrainNumber());
        assertEquals(updateTrain.getTrainType(), train.getTrainType());

    }

    @Test
    public void testGetByNameMethodDao() {
        Train getByNameTrain = trainDao.getByName(train.getTrainName());

        assertEquals(getByNameTrain.getTrainName(), train.getTrainName());
        assertEquals(getByNameTrain.getMaxNumberOfCarriages(), train.getMaxNumberOfCarriages());
        assertEquals(getByNameTrain.getTrainNumber(), train.getTrainNumber());
        assertEquals(getByNameTrain.getTrainType(), train.getTrainType());
    }

    @Test
    public void testGetByIdMethodDao() {
        Train getByIdTrain = trainDao.getById(train.getId());

        assertEquals(getByIdTrain.getId(), train.getId());
        assertEquals(getByIdTrain.getTrainName(), train.getTrainName());
        assertEquals(getByIdTrain.getMaxNumberOfCarriages(), train.getMaxNumberOfCarriages());
        assertEquals(getByIdTrain.getTrainNumber(), train.getTrainNumber());
        assertEquals(getByIdTrain.getTrainType(), train.getTrainType());
    }


    @Test
    public void testDeleteMethodDao() {
        List<Train> trainListBeforeDelete = trainDao.getAll();

        boolean checkIfDelete = trainDao.delete(train);

        List<Train> trainListAfterDelete = trainDao.getAll();

        assertNotEquals(trainListAfterDelete.size(), trainListBeforeDelete.size());
        assertTrue(checkIfDelete);
    }

    @Test
    public void testDeleteByIdMethodDao() {
        List<Train> trainListBeforeDelete = trainDao.getAll();

        boolean checkIfDelete = trainDao.deleteById(train.getId());

        List<Train> trainListAfterDelete = trainDao.getAll();

        assertNotEquals(trainListAfterDelete.size(), trainListBeforeDelete.size());
        assertTrue(checkIfDelete);

    }

    @Test
    public void testSaveMethodDao() {
        Train savedTrain = trainDao.getById(train.getId());


        assertEquals(savedTrain.getId(), train.getId());
        assertEquals(savedTrain.getTrainName(), train.getTrainName());
        assertEquals(savedTrain.getMaxNumberOfCarriages(), train.getMaxNumberOfCarriages());
        assertEquals(savedTrain.getTrainNumber(), train.getTrainNumber());
        assertEquals(savedTrain.getTrainType(), train.getTrainType());
    }

    @Test
    public void testGetByTrainTypeCargoMethodDao() {

        List <Train> trainListCargo = trainDao.getTrainListByType(TrainType.CARGO);

        for(Train train : trainListCargo) {
            assertEquals(train.getTrainType(), TrainType.CARGO);
        }
    }

    @Test
    public void testGetByTrainTypePassengerMethodDao() {
        List<Train> trainListPassenger = trainDao.getTrainListByType(TrainType.PASSENGER);

        for(Train train : trainListPassenger) {
            assertEquals(train.getTrainType(), TrainType.PASSENGER);
        }
    }

    @Test
    public void testGetByTrainTypeMultiMethodDao() {
        List<Train> trainListMulti = trainDao.getTrainListByType(TrainType.MULTI);

        for(Train train : trainListMulti) {
            assertEquals(train.getTrainType(), TrainType.MULTI);
        }
    }

    @Test(expected = DataInsertException.class)
    public void testUpdateMethodExceptionDao() {
        train.setId(2500);
        trainDao.update(train);
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetByNameMethodExceptionDao() {
        trainDao.getByName("exception");
    }

    @Test(expected = DataNotFoundException.class)
    public void testGetByIdMethodExceptionDao() {
        trainDao.getById(2500);
    }

    @Test(expected = DataNotFoundException.class)
    public void testDeleteMethodExceptionDao() {
        train.setTrainName("exception");
        trainDao.delete(train);
    }

    @Test(expected = DataNotFoundException.class)
    public void testDeleteByIdMethodExceptionDao() {
        trainDao.deleteById(2500);
    }


}
