package bakery.employeeTaskManager.domain;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface AddressRepository extends CrudRepository<Address, Long> {
	 
	List<Address> findByName(String name);
	
}
