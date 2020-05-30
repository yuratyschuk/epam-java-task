package daoLayerTest;

import com.demo.dao.impl.WorkerDaoImpl;
import com.demo.dao.interfaces.WorkerDao;
import com.demo.exceptions.WorkerException;
import com.demo.model.Position;
import com.demo.model.Worker;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;


import java.security.spec.ECField;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

public class WorkerDaoTest {

    private WorkerDao workerDao;

    private Worker worker;

    @Before
    public void setup() {
        workerDao = new WorkerDaoImpl();

        worker = new Worker();
        worker.setFirstName("FirstName");
        worker.setLastName("LastName");
        worker.setHireDate(new Date());
        Position position = new Position();
        position.setId(1);
        worker.setPosition(position);
        worker.setWorkingExperience(4);

        worker = workerDao.save(worker);
    }


    @Test
    public void testAddToWorkerDao() {
        Worker savedWorker = workerDao.save(worker);

        assertEquals(savedWorker.getHireDate(), worker.getHireDate());
        assertEquals(savedWorker.getPosition().getId(), worker.getPosition().getId());
        assertEquals(savedWorker.getFirstName(), worker.getFirstName());
        assertEquals(savedWorker.getLastName(), worker.getLastName());
        assertEquals(savedWorker.getWorkingExperience(), worker.getWorkingExperience());

    }

    @Test
    public void testUpdateWorkerDao() {

        worker.setWorkingExperience(3);
        worker.setFirstName("UpdatedFirstName");
        worker.setLastName("UpdatedLastName");


        workerDao.update(worker);

        Worker updatedWorker = workerDao.getById(worker.getId());

        assertEquals(updatedWorker.getPosition().getId(), worker.getPosition().getId());
        assertEquals(updatedWorker.getFirstName(), worker.getFirstName());
        assertEquals(updatedWorker.getLastName(), worker.getLastName());
        assertEquals(updatedWorker.getWorkingExperience(), worker.getWorkingExperience());
    }

    @Test
    public void testGetByIdMethod() {


        Worker getWorker = workerDao.getById(worker.getId());

        assertEquals(getWorker.getPosition().getId(), worker.getPosition().getId());
        assertEquals(getWorker.getFirstName(), worker.getFirstName());
        assertEquals(getWorker.getLastName(), worker.getLastName());
        assertEquals(getWorker.getWorkingExperience(), worker.getWorkingExperience());
    }

    @Test
    public void testDeleteMethodDao() {
        List<Worker> workersBeforeDelete = workerDao.getAll();

        boolean checkIfDeleted = workerDao.delete(worker);

        List<Worker> workersAfterDelete = workerDao.getAll();

        assertTrue(checkIfDeleted);
        assertNotEquals(workersAfterDelete.size(), workersBeforeDelete.size());
    }

    @Test
    public void testDeleteByIdMethodDao() {
        List<Worker> workersBeforeDelete = workerDao.getAll();

        boolean checkIfDeleted = workerDao.deleteById(worker.getId());

        List<Worker> workersAfterDelete = workerDao.getAll();

        assertTrue(checkIfDeleted);
        assertNotEquals(workersAfterDelete.size(), workersBeforeDelete.size());

    }

    @Test
    public void testGetAllMethodDao() {

    }

    @Test
    public void testGetByLastNameMethodDao() {
        Worker getByLastNameWorker = workerDao.getByLastName(worker.getLastName());


        assertEquals(getByLastNameWorker.getPosition().getId(), worker.getPosition().getId());
        assertEquals(getByLastNameWorker.getFirstName(), worker.getFirstName());
        assertEquals(getByLastNameWorker.getLastName(), worker.getLastName());
        assertEquals(getByLastNameWorker.getWorkingExperience(), worker.getWorkingExperience());
    }

    @Test(expected = WorkerException.class)
    public void testUpdateMethodExceptionDao() {
       worker.setId(2500);
        workerDao.update(worker);
    }

    @Test(expected = WorkerException.class)
    public void testDeleteMethodExceptionDao() {
        worker.setId(2500);
        workerDao.delete(worker);
    }

    @Test(expected = WorkerException.class)
    public void testDeleteByIdMethodExceptionDao() {
        worker.setId(2500);
        workerDao.deleteById(worker.getId());
    }

    @Test(expected = WorkerException.class)
    public void testGetAllMethodExceptionDao() {

    }

    @Test(expected = WorkerException.class)
    public void testGetByIdMethodExceptionDao() {
        workerDao.getById(2500);
    }

    @Test(expected = WorkerException.class)
    public void testGetByLastNameMethodExceptionDao() {
        workerDao.getByLastName("Last name Exception");
    }

}
