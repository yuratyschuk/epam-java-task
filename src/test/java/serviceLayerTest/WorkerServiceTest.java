package serviceLayerTest;

import com.demo.dao.interfaces.WorkerDao;
import com.demo.model.Worker;
import com.demo.service.WorkerService;
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
public class WorkerServiceTest {
    
    @Mock
    WorkerDao workerDao;
    
    @InjectMocks
    WorkerService workerService;

    List<Worker> workerList;

    Worker worker;

    @Before
    public void setup() { 
        worker = new Worker();
        worker.setId(1);

        Worker worker1 = new Worker();
        worker1.setId(2);

        workerList = Arrays.asList(worker1, worker);
    }


    @Test
    public void testVerifySaveMethodCalled() throws SQLException {
        when(workerDao.save(any(Worker.class))).thenReturn(new Worker());
        workerService.save(new Worker());

        verify(workerDao, times(1)).save(any(Worker.class));
    }

    @Test
    public void testVerifyGetAllMethod() {
        when(workerDao.getAll()).thenReturn(workerList);
        List<Worker> testWorkerList = workerService.getAll();

        verify(workerDao, times(1)).getAll();
        assertEquals(workerList.size(), testWorkerList.size());
    }

    @Test
    public void testGetByIdMethodCalled() {
        when(workerDao.getById(anyInt())).thenReturn(worker);

        Worker workerToTest = workerService.getById(anyInt());

        verify(workerDao, times(1)).getById(anyInt());
        assertEquals(worker.getId(), workerToTest.getId());
    }

    @Test
    public void testGetByNameMethodCalled() {
        when(workerDao.getByLastName(anyString())).thenReturn(worker);
        Worker workerToTest = workerService.getByName(anyString());

        verify(workerDao, times(1)).getByLastName(anyString());
        assertEquals(worker.getId(), workerToTest.getId());
    }

    @Test
    public void testDeleteMethodCalled() {
        when(workerDao.delete(any(Worker.class))).thenReturn(true);
        boolean testIfDelete = workerService.deleteByLastName(new Worker());

        verify(workerDao, times(1)).delete(any(Worker.class));
        assertTrue(testIfDelete);
    }

    @Test
    public void testDeleteByIdMethodCalled() {
        when(workerDao.deleteById(anyInt())).thenReturn(true);
        boolean testIfDelete = workerService.deleteById(1);

        verify(workerDao, times(1)).deleteById(anyInt());
        assertTrue(testIfDelete);
    }


    @Test
    public void testUpdateMethodCalled() {
        when(workerDao.update(any(Worker.class))).thenReturn(true);
        boolean testIfUpdate = workerService.update(new Worker());

        verify(workerDao, times(1)).update(any(Worker.class));
        assertTrue(testIfUpdate);
    }


}
