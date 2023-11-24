package bakery.employeeTaskManager.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import jakarta.validation.*;

import bakery.employeeTaskManager.domain.*;

@Controller
public class AppUserController {
	@Autowired
	private AppUserRepository repository;

	// Mapping for the signup page
	@RequestMapping(value = "signup")
	public String addStudent(Model model) {
		// Adding a new signup form to the model
		model.addAttribute("signupform", new AppUserSignUpForm());
		// Returning the signup template
		return "signup";
	}

	/**
	 * Create new user Check if user already exists & form validation
	 * 
	 * @param signupForm
	 * @param bindingResult
	 * @return
	 */
	@RequestMapping(value = "saveuser", method = RequestMethod.POST)
	public String save(@Valid @ModelAttribute("signupform") AppUserSignUpForm appusersignupForm,
			BindingResult bindingResult) {
		// Checking for form validation errors
		if (!bindingResult.hasErrors()) {
			if (appusersignupForm.getPassword().equals(appusersignupForm.getPasswordCheck())) { // check password match
				String pwd = appusersignupForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				// Encrypting the password
				String hashPwd = bc.encode(pwd);
				// Creating a new user with the provided details
				AppUser newUser = new AppUser();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(appusersignupForm.getUsername());
				newUser.setRole("USER");
				// Checking if the user already exists
				if (repository.findByUsername(appusersignupForm.getUsername()) == null) {
					// Saving the new user to the repository
					repository.save(newUser);
				} else {
					// Rejecting the form if the username already exists
					bindingResult.rejectValue("username", "err.username", "Username already exists");
					return "signup";
				}
			} else {
				// Rejecting the form if the passwords do not match
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");
				return "signup";
			}
		} else {
			// Returning to the signup form if there are form validation errors
			return "signup";
		}
		// Redirecting to the login page after successful registration
		return "redirect:/login";
	}

}
