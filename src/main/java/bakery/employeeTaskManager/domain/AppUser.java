package bakery.employeeTaskManager.domain;

import jakarta.persistence.*;

@Entity
@Table(name="user_table")
public class AppUser {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "userid", nullable = false, updatable = false)
	private Long userid;

	// Username with unique constraint
	@Column(name = "username", nullable = false, unique = true)
	private String username;

	@Column(name = "password_hash", nullable = false)
	private String password_hash;

	@Column(name = "role", nullable = false)
	private String role;

	public AppUser() {
	}

	public AppUser(String username, String password_hash, String role) {
		super();
		this.username = username;
		this.password_hash = password_hash;
		this.role = role;
	}

	public Long getId() {
		return userid;
	}

	public void setId(Long userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return password_hash;
	}

	public void setPasswordHash(String password_hash) {
		this.password_hash = password_hash;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

}