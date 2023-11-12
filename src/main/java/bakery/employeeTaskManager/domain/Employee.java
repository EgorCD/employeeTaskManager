package bakery.employeeTaskManager.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="employee_table")
public class Employee {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long employeeid;
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "employee")
	private List<Task> tasks;

	// Default constructor
	public Employee() {
	}

	public Employee(Long employeeid, String name, List<Task> tasks) {
		this.employeeid = employeeid;
		this.name = name;
		this.tasks = tasks;
	}

	public Employee(String name, List<Task> tasks) {
		this.employeeid = 0L;
		this.name = name;
		this.tasks = tasks;
	}

	public Employee(String name) {
		this.name = name;
	}
	
	// Getters and Setters
	public Long getEmployeeid() {
		return employeeid;
	}
	
	public void setEmployeeid(Long employeeid) {
		this.employeeid = employeeid;
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
		return "Employee [employeeid=" + employeeid + ", name=" + name + "]";
	}
}

