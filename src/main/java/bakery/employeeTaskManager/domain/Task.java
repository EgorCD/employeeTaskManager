package bakery.employeeTaskManager.domain;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
@Table(name = "task_table")
public class Task {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "addressid")
	private Address address;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "employeeid")
	private Employee employee;

	private String request;

	private String response;

	private String feedback;

	// New fields for task posted date and deadline
	@Column(name = "posted_date")
	private LocalDateTime postedDate;

	@Column(name = "deadline")
	private LocalDateTime deadline;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "statusid")
	private Status status;

	@ManyToOne
	@JsonIgnore
	@JoinColumn(name = "approvalid")
	private Approval approval;

	@Transient
	private long daysUntilDeadline;

	// Method to calculate the days until the deadline
	public void calculateDaysUntilDeadline() {
		if (this.deadline != null) {
			this.daysUntilDeadline = ChronoUnit.DAYS.between(LocalDateTime.now(), this.deadline);
		} else {
			this.daysUntilDeadline = Long.MAX_VALUE;
		}
	}

	public long getDaysUntilDeadline() {
		return daysUntilDeadline;
	}

	private String fileName; // Name or path of the uploaded file

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public Task() {
		this.id = null;
		this.address = null;
		this.employee = null;
		this.request = "";
		this.response = "";
		this.feedback = "";
		this.postedDate = LocalDateTime.now(); // default to current time
		this.deadline = LocalDateTime.now();
		this.status = null;
		this.approval = null;
		this.fileName = "";
	}

	// Parameterized constructor
	public Task(Address address, Employee employee, String request, String response, String feedback,
			LocalDateTime postedDate, LocalDateTime deadline, Status status, Approval approval, String fileName) {
		this.address = address;
		this.employee = employee;
		this.request = request;
		this.response = response;
		this.feedback = feedback;
		this.postedDate = LocalDateTime.now(); // default to current time
		this.deadline = LocalDateTime.now();
		this.status = status;
		this.approval = approval;
		this.fileName = fileName;
	}

	// Getters and setters
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Address getAddress() {
		return address;
	}

	public Address getName() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public String getRequest() {
		return request;
	}

	public void setRequest(String request) {
		this.request = request;
	}

	public String getResponse() {
		return response;
	}

	public void setResponse(String response) {
		this.response = response;
	}

	public String getFeedback() {
		return feedback;
	}

	public void setFeedback(String feedback) {
		this.feedback = feedback;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public Approval getApproval() {
		return approval;
	}

	public void setApproval(Approval approval) {
		this.approval = approval;
	}

	public LocalDateTime getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(LocalDateTime postedDate) {
		this.postedDate = postedDate;
	}

	public LocalDateTime getDeadline() {
		return deadline;
	}

	public void setDeadline(LocalDateTime deadline) {
		this.deadline = deadline;
	}

	@Override
	public String toString() {
		return "Report [id=" + id + ", address=" + (address != null ? address : "null") + ", username="
				+ (employee != null ? employee : "null") + ", request=" + request + ", postedDate=" + postedDate
				+ ", deadline=" + deadline + ", status=" + (status != null ? status : "null")
				+ (approval != null ? approval : "null") + "]";
	}
}
