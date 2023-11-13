package bakery.employeeTaskManager.domain;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface TaskRepository extends CrudRepository<Task, Long> {

	List<Task> findByAddress(String address);

	@Query("SELECT t FROM Task t JOIN t.employee e WHERE e.name = :name")
	List<Task> findByEmployeeName(@Param("name") String name);

	List<Task> findAllByOrderByDeadlineAsc();

	@Query("SELECT t FROM Task t WHERE t.deadline < :now")
	List<Task> findOverdueTasks(@Param("now") LocalDateTime now);
	
	@Query("SELECT t FROM Task t WHERE t.approval.name <> 'approved'")
	List<Task> findNotApprovedTasks();
	
	@Query("SELECT t FROM Task t WHERE t.approval.name = 'approved'")
	List<Task> findApprovedTasks();

}