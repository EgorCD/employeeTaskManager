package bakery.employeeTaskManager.test;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import bakery.employeeTaskManager.domain.Task;
import bakery.employeeTaskManager.domain.TaskRepository;
import bakery.employeeTaskManager.domain.Employee;
import bakery.employeeTaskManager.domain.Status;

@SpringJUnitConfig
@DataJpaTest
public class TaskRepositoryTest {

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void createNewTask() {
        Task task = new Task();
        task.setRequest("Bake bread");
        task.setPostedDate(LocalDateTime.now());
        task.setDeadline(LocalDateTime.now().plusDays(1));
        taskRepository.save(task);
        assertThat(task.getId()).isNotNull();
    }

    @Test
    public void findTaskByEmployeeName() {
        Employee employee = new Employee("John Doe");
        Task task = new Task();
        task.setEmployee(employee);
        task.setRequest("Bake cookies");
        taskRepository.save(task);
        
        List<Task> tasks = taskRepository.findByEmployeeName("John Doe");
        assertThat(tasks).isNotEmpty();
        assertThat(tasks.get(0).getRequest()).isEqualTo("Bake cookies");
    }

    @Test
    public void deleteTask() {
        Task task = new Task();
        task.setRequest("Bake cake");
        taskRepository.save(task);
        
        Long id = task.getId();
        taskRepository.deleteById(id);
        assertThat(taskRepository.findById(id)).isEmpty();
    }

    // Additional tests like findTaskByStatus, findOverdueTasks, etc.
}

