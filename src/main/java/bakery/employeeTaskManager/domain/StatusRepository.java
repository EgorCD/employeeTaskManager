package bakery.employeeTaskManager.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StatusRepository extends JpaRepository<Status, Long> {

	List<Status> findByName(String name);
}
