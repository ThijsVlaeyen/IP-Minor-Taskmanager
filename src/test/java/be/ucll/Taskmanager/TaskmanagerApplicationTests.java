package be.ucll.Taskmanager;

import be.ucll.Taskmanager.DB.TaskService;
import be.ucll.Taskmanager.DTO.TaskDTO;
import be.ucll.Taskmanager.Domain.Task;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class TaskmanagerApplicationTests {

@Autowired
private TaskService service;

	@Test
	public void testTaskService(){
		Task t = new Task();
	//        t.setId(1);
		t.setName("test");
		t.setDescription("test task1 for ci/cd");
		t.setDeadline(LocalDateTime.now());
		t.setCompleted(false);
		t.setSubtasks(null);
		service.add(createDTOfromTask(t));

		List<Task> tasks = service.getAll();

		assertNotNull(tasks);
		assertFalse(tasks.isEmpty());
		assertEquals(1,tasks.size());
		assertNotNull(tasks.get(0));
	}


	private TaskDTO createDTOfromTask(Task task) {
		TaskDTO dto = new TaskDTO();
		dto.setId(task.getId());
		dto.setDescription(task.getDescription());
		dto.setName(task.getName());
		dto.setDeadline(task.getDeadline());
		dto.setCompleted(task.isCompleted());
		dto.setSubtasks(task.getSubtasks());
		return dto;
	}
}
