package bakery.employeeTaskManager.domain;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends CrudRepository<Task, Long> {

	List<Task> findByAddress(String address);
	
	@Query("SELECT t FROM Task t JOIN t.employee e WHERE e.name = :name")
	List<Task> findByEmployeeName(@Param("name") String name);

}