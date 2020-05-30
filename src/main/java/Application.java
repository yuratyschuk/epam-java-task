import com.demo.dao.impl.WorkerDaoImpl;
import com.demo.dao.interfaces.WorkerDao;
import com.demo.model.Position;
import com.demo.model.Worker;
import com.demo.service.PositionService;
import com.demo.service.WorkerService;

import java.util.Date;

public class Application {

    public static void main(String[] args) {
        WorkerDao workerDao = new WorkerDaoImpl();


        Worker worker = new Worker();
        worker.setFirstName("FirstName");
        worker.setLastName("LastName");
        worker.setHireDate(new Date());
        Position position = new Position();
        position.setId(1);
        worker.setPosition(position);
        worker.setWorkingExperience(4);

        Worker saveworker = workerDao.save(worker);

        System.out.println(saveworker.getId());
    }
}
