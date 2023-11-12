package bakery.employeeTaskManager.web;

import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import bakery.employeeTaskManager.domain.Task;
import bakery.employeeTaskManager.domain.TaskRepository;
import bakery.employeeTaskManager.domain.AddressRepository;
import bakery.employeeTaskManager.domain.EmployeeRepository;
import bakery.employeeTaskManager.domain.StatusRepository;

@Controller
public class TaskController {

	@Autowired
	private TaskRepository taskRepository;

	@Autowired
	private StatusRepository statusRepository;

	@Autowired
	private AddressRepository addressRepository;

	@Autowired
	private EmployeeRepository employeeRepository;

	private static final Logger log = LoggerFactory.getLogger(TaskController.class);

	// Show login page
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// Create REST service that returns all tasks (JSON)
	@GetMapping("/api/tasks")
	public @ResponseBody Iterable<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	// Create REST service that returns one task by ID (JSON)
	@GetMapping("/api/tasks/{id}")
	public @ResponseBody Task getTaskById(@PathVariable("id") Long id) {
		return taskRepository.findById(id).orElse(null); // Consider better error handling here
	}

	// Show task list page
	@GetMapping("/tasklist")
	public String showTaskList(Model model) {
		model.addAttribute("tasks", taskRepository.findAll());
		return "index"; // Assume there is a tasklist.html template
	}

	// Show add task page
	@GetMapping("/addTask")
	public String addTask(Model model) {
		model.addAttribute("task", new Task()); // Add an empty task object for the form binding
		model.addAttribute("addresses", addressRepository.findAll()); // Assuming you have a service method to get all															// // addresses
		model.addAttribute("employees", employeeRepository.findAll()); // Assuming you have a service method to get all
		model.addAttribute("statuses", statusRepository.findAll()); // Assuming you have a service method to get all //															// statuses
		return "addtask";
	}

	// Save new task
	@PostMapping("/saveTask")
	public String saveTask(Task task) {
		taskRepository.save(task);
		return "redirect:/tasklist";
	}

	// Delete a task
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/deleteTask/{id}")
	public String deleteTask(@PathVariable("id") Long taskId) {
		taskRepository.deleteById(taskId);
		return "redirect:/tasklist";
	}

	@GetMapping("/editTask/{id}")
	public String editTask(@PathVariable("id") Long taskId, Model model) {
		Task task = taskRepository.findById(taskId).orElse(null);
		if (task != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	        String formattedPostedDate = task.getPostedDate().format(formatter);
	        String formattedDeadline = task.getDeadline().format(formatter);
	        log.info("Formatted Posted Date: " + formattedPostedDate);
	        log.info("Formatted Deadline: " + formattedDeadline);
	        model.addAttribute("task", task);
	        model.addAttribute("formattedPostedDate", formattedPostedDate);
	        model.addAttribute("formattedDeadline", formattedDeadline);
			model.addAttribute("addresses", addressRepository.findAll()); // Assuming you have a service method to get
			model.addAttribute("employees", employeeRepository.findAll()); // Assuming you have a service method to get
			model.addAttribute("statuses", statusRepository.findAll()); // Assuming you have a service method to get all													// // // // statuses
			return "edittask";
		} else {
			return "redirect:/tasklist";
		}
	}

	@PostMapping("/saveEditedTask")
	public String saveEditedTask(@ModelAttribute Task taskForm) {
		taskRepository.save(taskForm);
		return "redirect:/tasklist";
	}
	
	@GetMapping("/editTaskUser/{id}")
	public String editTaskUser(@PathVariable("id") Long taskId, Model model) {
		Task task = taskRepository.findById(taskId).orElse(null);
		if (task != null) {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	        String formattedPostedDate = task.getPostedDate().format(formatter);
	        String formattedDeadline = task.getDeadline().format(formatter);
	        log.info("Formatted Posted Date: " + formattedPostedDate);
	        log.info("Formatted Deadline: " + formattedDeadline);
	        model.addAttribute("task", task);
	        model.addAttribute("formattedPostedDate", formattedPostedDate);
	        model.addAttribute("formattedDeadline", formattedDeadline);
			model.addAttribute("addresses", addressRepository.findAll()); // Assuming you have a service method to get
			model.addAttribute("employees", employeeRepository.findAll()); // Assuming you have a service method to get
			model.addAttribute("statuses", statusRepository.findAll()); // Assuming you have a service method to get all													// // // // statuses
			return "edittaskUser";
		} else {
			return "redirect:/tasklist";
		}
	}

	@PostMapping("/saveEditedTaskUser")
	public String saveEditedTaskUser(@ModelAttribute Task taskForm) {
		taskRepository.save(taskForm);
		return "redirect:/tasklist";
	}
}
