package bakery.employeeTaskManager.domain;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ApprovalRepository extends JpaRepository<Approval, Long> {

	List<Approval> findByName(String name);
}
