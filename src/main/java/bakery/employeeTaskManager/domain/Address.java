package bakery.employeeTaskManager.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="address_table")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long addressid;
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "address")
	private List<Task> tasks;

	// Default constructor
	public Address() {
	}

	public Address(Long addressid, String name, List<Task> tasks) {
		this.addressid = addressid;
		this.name = name;
		this.tasks = tasks;
	}

	public Address(String name, List<Task> tasks) {
		this.addressid = 0L;
		this.name = name;
		this.tasks = tasks;
	}

	public Address(String name) {
		this.name = name;
	}

	// Getters and Setters
	public Long getAddressid() {
		return addressid;
	}

	public void setAddressid(Long addressid) {
		this.addressid = addressid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Task> getTasks() {
		return tasks;
	}

	public void setTasks(List<Task> tasks) {
		this.tasks = tasks;
	}

	@Override
	public String toString() {
	    return "Address [addressid=" + addressid + ", name=" + name + "]";
	}
}
