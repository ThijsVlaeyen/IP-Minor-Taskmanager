package be.ucll.Taskmanager;

import be.ucll.Taskmanager.DB.TaskService;
import be.ucll.Taskmanager.DTO.TaskDTO;
import be.ucll.Taskmanager.Domain.SubTask;
import be.ucll.Taskmanager.Domain.Task;
import org.junit.jupiter.api.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.Assert.assertThat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
class TaskmanagerApplicationTests {

@Autowired
private TaskService service;

	@Autowired
	private TaskService taskService;
	Task task = new Task("Juist", "Juist", LocalDateTime.of(2020, 11, 18, 12, 12));

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

	@Test()
	void nameIsNullNewTask() throws IllegalArgumentException {
		try {
			Task nameNot = new Task(null, "Name", LocalDateTime.of(2020, 11, 18, 12, 12));
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("Name must not be empty"));
		}
	}
	@Test()
	void descrIsNullNewTask() throws IllegalArgumentException {
		try {
			Task descrNot = new Task("Descr", null, LocalDateTime.of(2020, 11, 18, 12, 12));
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("Description must not be empty"));
		}
	}
	@Test()
	void deadlineIsNullNewTask() throws IllegalArgumentException {
		try {
			Task deadlNot = new Task("Deadline", "Deadline", null);
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("Deadline must not be empty"));
		}
	}

	@Test
	void correctSubTask() {

		SubTask subTask = new SubTask("sub1", "sub");
		task.addSubTask(subTask);
		assertTrue(task.getSubtasks().size() > 0);
	}
	@Test
	void nullNameSubTask() {
		try {
			SubTask subTask = new SubTask(null, "ok");
			task.addSubTask(subTask);

		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("Name must not be empty"));
		}
	}
	@Test
	void nullDescrSubTask() {
		try {
			SubTask subTask = new SubTask("sub1", null);
			task.addSubTask(subTask);
		} catch (IllegalArgumentException e) {
			assertThat(e.getMessage(), is("Description must not be empty"));
		}
	}
}
