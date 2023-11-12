package bakery.employeeTaskManager.domain;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class AppUserSignUpForm {
	@NotEmpty
	@Size(min = 5, max = 30)
	private String username = "";

	@NotEmpty
	@Size(min = 7, max = 30)
	private String password_hash = "";

	@NotEmpty
	@Size(min = 7, max = 30)
	private String passwordCheck = "";

	@NotEmpty
	private String role = "USER";

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password_hash;
	}

	public void setPassword(String password_hash) {
		this.password_hash = password_hash;
	}

	public String getPasswordCheck() {
		return passwordCheck;
	}

	public void setPasswordCheck(String passwordCheck) {
		this.passwordCheck = passwordCheck;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
}
