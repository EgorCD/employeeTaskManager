package bakery.employeeTaskManager.test;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import bakery.employeeTaskManager.domain.TaskRepository;
import bakery.employeeTaskManager.domain.Task;
import bakery.employeeTaskManager.domain.Employee;
import bakery.employeeTaskManager.domain.Status;

@SpringBootTest
@AutoConfigureMockMvc
class TaskManagerRestTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TaskRepository taskRepository;

    @BeforeEach
    public void setupTestData() {
        // Setting up test data before each test
        Employee employee1 = new Employee("Alice");
        Task task1 = new Task();
        task1.setEmployee(employee1);
        task1.setRequest("Prepare dough");
        taskRepository.save(task1);

        Employee employee2 = new Employee("Bob");
        Task task2 = new Task();
        task2.setEmployee(employee2);
        task2.setRequest("Decorate cakes");
        taskRepository.save(task2);
    }

    @WithMockUser(username = "user", roles = {"USER"})
    @Test
    void testGetTasksAsUser() throws Exception {
        mockMvc.perform(get("/api/tasks"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].request").value("Prepare dough"))
               .andExpect(jsonPath("$[1].request").value("Decorate cakes"));
    }

    @WithMockUser(username = "admin", roles = {"ADMIN"})
    @Test
    void testGetTasksAsAdmin() throws Exception {
        mockMvc.perform(get("/api/tasks"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$[0].request").value("Prepare dough"))
               .andExpect(jsonPath("$[1].request").value("Decorate cakes"));
    }

}
