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

	@RequestMapping(value = "signup")
	public String addStudent(Model model) {
		model.addAttribute("signupform", new AppUserSignUpForm());
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
		if (!bindingResult.hasErrors()) { // validation errors
			if (appusersignupForm.getPassword().equals(appusersignupForm.getPasswordCheck())) { // check password match
				String pwd = appusersignupForm.getPassword();
				BCryptPasswordEncoder bc = new BCryptPasswordEncoder();
				String hashPwd = bc.encode(pwd);

				AppUser newUser = new AppUser();
				newUser.setPasswordHash(hashPwd);
				newUser.setUsername(appusersignupForm.getUsername());
				newUser.setRole("USER");
				if (repository.findByUsername(appusersignupForm.getUsername()) == null) { // Check if user exists
					repository.save(newUser);
				} else {
					bindingResult.rejectValue("username", "err.username", "Username already exists");
					return "signup";
				}
			} else {
				bindingResult.rejectValue("passwordCheck", "err.passCheck", "Passwords does not match");
				return "signup";
			}
		} else {
			return "signup";
		}
		return "redirect:/login";
	}

}
