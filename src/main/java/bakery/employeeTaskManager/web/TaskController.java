package bakery.employeeTaskManager.web;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import bakery.employeeTaskManager.domain.Task;
import bakery.employeeTaskManager.domain.TaskRepository;
import bakery.employeeTaskManager.domain.AddressRepository;
import bakery.employeeTaskManager.domain.ApprovalRepository;
import bakery.employeeTaskManager.domain.EmployeeRepository;
import bakery.employeeTaskManager.domain.StatusRepository;

import bakery.employeeTaskManager.web.*;

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
	
	@Autowired
	private FileController fileController;
	
	@Autowired
	private ApprovalRepository approvalRepository;

	private static final Logger log = LoggerFactory.getLogger(TaskController.class);

	// login page
	@GetMapping("/login")
	public String login() {
		return "login";
	}

	// REST service that returns all tasks (JSON)
	@GetMapping("/api/tasks")
	public @ResponseBody Iterable<Task> getAllTasks() {
		return taskRepository.findAll();
	}

	// Create REST service that returns one task by ID (JSON)
	@GetMapping("/api/tasks/{id}")
	public @ResponseBody Task getTaskById(@PathVariable("id") Long id) {
		return taskRepository.findById(id).orElse(null); 
	}

	// Show task list page
	@GetMapping("/tasklist")
	public String showTaskList(Model model) {
		List<Task> tasks = (List<Task>) taskRepository.findAllByOrderByDeadlineAsc();
		tasks.forEach(Task::calculateDaysUntilDeadline);
		model.addAttribute("tasks", taskRepository.findAllByOrderByDeadlineAsc());
		return "index"; // tasklist.html template
	}

	// Show add task page
	@GetMapping("/addTask")
	public String addTask(Model model) {
		model.addAttribute("task", new Task()); // Add an empty task object for the form binding
		model.addAttribute("addresses", addressRepository.findAll());
		model.addAttribute("employees", employeeRepository.findAll());
		model.addAttribute("statuses", statusRepository.findAll());
		model.addAttribute("approvals", approvalRepository.findAll());
		return "addtask";
	}

	// Mapping to save a task with file upload
	@PostMapping("/saveTask")
	public String saveTask(@RequestParam("file") MultipartFile file, Task task) {
	    String fileName = fileController.storeFile(file);
	    task.setFileName(fileName);
	    taskRepository.save(task);
	    return "redirect:/tasklist";
	}

	// Mapping to delete a task, accessible only by users with 'ADMIN' authority
	@PreAuthorize("hasAuthority('ADMIN')")
	@GetMapping("/deleteTask/{id}")
	public String deleteTask(@PathVariable("id") Long taskId) {
		taskRepository.deleteById(taskId);
		return "redirect:/tasklist";
	}

	// Mapping to edit a task
	@GetMapping("/editTask/{id}")
	public String editTask(@PathVariable("id") Long taskId, Model model) {
		Task task = taskRepository.findById(taskId).orElse(null);
		if (task != null) {
			// Formatting date and time for display
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
	        String formattedPostedDate = task.getPostedDate().format(formatter);
	        String formattedDeadline = task.getDeadline().format(formatter);
	        log.info("Formatted Posted Date: " + formattedPostedDate);
	        log.info("Formatted Deadline: " + formattedDeadline);
	        String existingFileName = task.getFileName();
	        // Adding attributes to the model
	        model.addAttribute("existingFileName", existingFileName);
	        model.addAttribute("task", task);
	        model.addAttribute("formattedPostedDate", formattedPostedDate);
	        model.addAttribute("formattedDeadline", formattedDeadline);
	        // Adding lists of addresses, employees, statuses, and approvals to the model
			model.addAttribute("addresses", addressRepository.findAll());
			model.addAttribute("employees", employeeRepository.findAll());
			model.addAttribute("statuses", statusRepository.findAll());
			model.addAttribute("approvals", approvalRepository.findAll());
			return "edittask";
		} else {
			return "redirect:/tasklist";
		}
	}

	// Mapping to save an edited task
	@PostMapping("/saveEditedTask")
	public String saveEditedTask(@RequestParam("file") MultipartFile file, Task task) {
		String fileName = fileController.storeFile(file);
	    task.setFileName(fileName);
	    taskRepository.save(task);
		return "redirect:/tasklist";
	}
	
	// Mapping to search for tasks based on employee name
	@GetMapping("/searchTasks")
	public String searchTasks(@RequestParam String employeeName, Model model) {
	    List<Task> tasks = taskRepository.findByEmployeeName(employeeName);
	    if (tasks.isEmpty()) {
	        model.addAttribute("noResults", true); // This attribute signals that the search yielded no results
	    } else {
	        model.addAttribute("tasks", tasks);
	    }
	    return "index"; // Renders the index.html template
	}
	
	// Mapping to show overdue tasks
	@GetMapping("/overdueTasks")
	public String showOverdueTasks(Model model) {
	    LocalDateTime now = LocalDateTime.now();
	    List<Task> overdueTasks = taskRepository.findOverdueTasks(now);
	    model.addAttribute("tasks", overdueTasks);
	    return "index"; // Point to overdue tasks template
	}
	
	// Mapping to show tasks that are not approved
	@GetMapping("/notApprovedTasks")
	public String showNotApprovedTasks(Model model) {
	    List<Task> notApprovedTasks = taskRepository.findNotApprovedTasks();
	    model.addAttribute("tasks", notApprovedTasks);
	    return "index"; // Point to not approved tasks template
	}
	
	// Mapping to show approved tasks
	@GetMapping("/ApprovedTasks")
	public String showApprovedTasks(Model model) {
	    List<Task> ApprovedTasks = taskRepository.findApprovedTasks();
	    model.addAttribute("tasks", ApprovedTasks);
	    return "index"; // Point to not approved tasks template
	}
	
	// Mapping to edit a task as a user
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
	        String existingFileName = task.getFileName();
	        model.addAttribute("existingFileName", existingFileName);
	        model.addAttribute("formattedPostedDate", formattedPostedDate);
	        model.addAttribute("formattedDeadline", formattedDeadline);
			model.addAttribute("addresses", addressRepository.findAll());
			model.addAttribute("employees", employeeRepository.findAll());
			model.addAttribute("statuses", statusRepository.findAll());
			model.addAttribute("approvals", approvalRepository.findAll());
			return "edittaskUser";
		} else {
			return "redirect:/tasklist";
		}
	}

	// Mapping to save an edited task by a user
	@PostMapping("/saveEditedTaskUser")
	public String saveEditedTaskUser(@RequestParam("file") MultipartFile file, Task task) {
		String fileName = fileController.storeFile(file);
	    task.setFileName(fileName);
	    taskRepository.save(task);
		return "redirect:/tasklist";
	}
}
