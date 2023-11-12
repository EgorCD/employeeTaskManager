package bakery.employeeTaskManager.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="status_table")
public class Status {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long statusid;
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "status")
	private List<Task> tasks;

	// Default constructor
	public Status() {
	}

	public Status(Long statusid, String name, List<Task> tasks) {
		this.statusid = statusid;
		this.name = name;
		this.tasks = tasks;
	}

	public Status(String name, List<Task> tasks) {
		this.statusid = 0L;
		this.name = name;
		this.tasks = tasks;
	}

	public Status(String name) {
		this.name = name;
	}
	
	// Getters and Setters
	public Long getStatusid() {
		return statusid;
	}
	
	public void setStatusid(Long statusid) {
		this.statusid = statusid;
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
		return "Status [statusid=" + statusid + ", name=" + name + "]";
	}
}
