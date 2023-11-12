package bakery.employeeTaskManager.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name="approval_table")
public class Approval {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long approvalid;
	private String name;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "approval")
	private List<Task> tasks;

	// Default constructor
	public Approval() {
	}

	public Approval(Long approvalid, String name, List<Task> tasks) {
		this.approvalid = approvalid;
		this.name = name;
		this.tasks = tasks;
	}

	public Approval(String name, List<Task> tasks) {
		this.approvalid = 0L;
		this.name = name;
		this.tasks = tasks;
	}

	public Approval(String name) {
		this.name = name;
	}
	
	// Getters and Setters
	public Long getApprovalid() {
		return approvalid;
	}
	
	public void setApprovalid(Long approvalid) {
		this.approvalid = approvalid;
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
		return "Approval [approvalid=" + approvalid + ", name=" + name + "]";
	}
}
