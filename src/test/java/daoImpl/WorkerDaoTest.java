//package daoImpl;
//
//
//import com.demo.dao.impl.WorkerDaoImpl;
//import com.demo.model.Position;
//import com.demo.model.Worker;
//import com.demo.service.WorkerService;
//import com.demo.utils.ConnectionPool;
//import org.junit.Before;
//import org.junit.Rule;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.Mockito;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.MockitoJUnit;
//import org.mockito.junit.MockitoJUnitRunner;
//import org.mockito.junit.MockitoRule;
//
//import java.math.BigDecimal;
//import java.sql.Connection;
//import java.sql.Date;
//import java.sql.PreparedStatement;
//import java.sql.ResultSet;
//import java.util.Collections;
//
//@RunWith(MockitoJUnitRunner.class)
//public class WorkerDaoTest {
//
//    @InjectMocks
//    private ConnectionPool connectionFactory;
//
//    @Mock
//    private Connection connection;
//    @Mock
//    private PreparedStatement statement;
//    @Mock
//    private ResultSet resultSet;
//
//    @Mock
//    private WorkerDaoImpl workerDao = new WorkerDaoImpl();
//
//    @Rule
//    public MockitoRule mockitoRule = MockitoJUnit.rule();
//
//
//    Worker worker;
//
//    @Before
//    public void setUp() throws Exception {
//        MockitoAnnotations.initMocks(this);
//
//        Position position = new Position();
//        position.setId(1);
//        position.setJobName("Director");
//        position.setSalary(BigDecimal.valueOf(15000));
//
//        Worker worker = new Worker();
//        worker.setId(1);
//        worker.setFirstName("Test");
//        worker.setLastName("Test Last Name");
//        worker.setWorkingExperience(2);
//        worker.setHireDate(new Date(2017, 12, 21));
//        worker.setPosition(position);
//        position.setWorkerList(Collections.singletonList(worker));
//
//    }
//
//    @Test
//    public void testFindById() {
//        MockitoAnnotations.initMocks(this);
//        WorkerService workerService = new WorkerService(workerDao);
//        workerService.getById(1);
//        Mockito.verify(workerDao).getById(1);
//    }
//
//}
