package be.ucll.Taskmanager;

import be.ucll.Taskmanager.DB.SubTaskService;
import be.ucll.Taskmanager.DB.TaskService;
import be.ucll.Taskmanager.DTO.SubTaskDTO;
import be.ucll.Taskmanager.DTO.TaskDTO;
import be.ucll.Taskmanager.Domain.DTOFormatter;
import be.ucll.Taskmanager.Domain.SubTask;
import be.ucll.Taskmanager.Domain.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class SubTaskServiceTests {
    @Autowired
    private TaskService service;
    @Autowired
    private SubTaskService subService;
    private Task t;

    @BeforeEach
    public void setup(){
        t = new Task();
        t.setName("TestTask");
        t.setDescription("test description");
        t.setDeadline(LocalDateTime.now());
        t.setSubtasks(null);
        service.add(DTOFormatter.createDTOfromTask(t));
        TaskDTO taskDTO = service.getAll().get(0);
        SubTask sub = new SubTask("test subtask","test description for the subtask");
        sub.setTask(DTOFormatter.DTOToTask(taskDTO));
        subService.add(DTOFormatter.createDTOfromSubtask(sub));
    }

    @Test
    public void subtaskTest(){
        assertNotNull(service.getAll().get(0).getSubtasks().get(0));
    }

    @Test
    public void getSubtaskMainTask(){
        assertNotNull(service.getAll().get(0).getSubtasks().get(0).getTask());
    }

    @Test
    public void testSubtaskDelete(){
        assertNotNull(subService.getAll().get(0));
        long id = subService.getAll().get(0).getId();
        subService.delete(id);
        assertEquals(0,subService.getAll().size());
    }

    @Test
    public void testSubtaskEdit(){
        assertNotNull(subService.getAll().get(0));
        long id = subService.getAll().get(0).getId();
        SubTaskDTO dto = subService.get(id);
        dto.setName("edit task test");
        dto.setDescription("edit task test description");
        subService.update(dto);
        SubTaskDTO dtoAfterUpdate = subService.get(id);
        assertEquals(dtoAfterUpdate.getName(), "edit task test");
        assertEquals(dtoAfterUpdate.getDescription(), "edit task test description");
    }

    @Test
    public void testGetAllFromSpecificTask(){
        TaskDTO dto = this.service.getAll().get(0);
        Task t = DTOFormatter.DTOToTask(dto);
        List<SubTaskDTO> subtasks = this.subService.getAll(t);
        assertEquals(subtasks.size(),1);
    }

    @AfterEach
    public void clear(){
        for (TaskDTO t:service.getAll()){
            service.delete(t.getId());
        }
        assertEquals(0,service.getAll().size());
    }
}
