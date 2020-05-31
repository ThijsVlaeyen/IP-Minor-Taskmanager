package be.ucll.Taskmanager;

import be.ucll.Taskmanager.DB.SubTaskService;
import be.ucll.Taskmanager.DB.TaskService;
import be.ucll.Taskmanager.DTO.TaskDTO;
import be.ucll.Taskmanager.Domain.DTOFormatter;
import be.ucll.Taskmanager.Domain.DateFormatter;
import be.ucll.Taskmanager.Domain.Task;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class TaskServiceTest {
    @Autowired
    private TaskService service;
    @Autowired
    private SubTaskService subService;
    @Autowired
    private DateFormatter formatter;

    @BeforeEach
    public void setup(){
        Task t = new Task();
        t.setName("TestTask");
        t.setDescription("testing");
        try{
            String datum = "2018-11-04T05:07:33";
            LocalDateTime time = formatter.parse(datum, Locale.ENGLISH);
            t.setDeadline(time);
        }catch (ParseException e) {
            e.printStackTrace();
        }
        t.setSubtasks(null);
        service.add(DTOFormatter.createDTOfromTask(t));
    }

    @AfterEach
    public void clear(){
        for (TaskDTO t:service.getAll()){
            service.delete(t.getId());
        }
        assertEquals(0,service.getAll().size());
    }

    @Test
    public void testTaskAdd(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setName("test add");
        taskDTO.setDescription("test add description");
        taskDTO.setDeadline(LocalDateTime.now());
        taskDTO.setSubtasks(null);
        this.service.add(taskDTO);
        for (TaskDTO t:this.service.getAll()){
            if (DTOFormatter.DTOToTask(t).equals(DTOFormatter.DTOToTask(taskDTO))){
                assertEquals(t.getName(),taskDTO.getName());
                assertEquals(t.getDescription(),taskDTO.getDescription());
                assertEquals(t.getDeadline(),taskDTO.getDeadline());
                assertEquals(t.getSubtasks(),taskDTO.getSubtasks());
            }
        }
    }

    @Test
    public void testTaskServiceGetAll(){
        List<TaskDTO> tasks = service.getAll();
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
        assertEquals(1,tasks.size());
        assertNotNull(tasks.get(0));
    }

    @Test
    public void testTaskServiceUpdate(){
        TaskDTO task = service.getAll().get(0);
        task.setDescription("updated task description");
        service.update(task);
        Task t = DTOFormatter.DTOToTask(task);

        assertEquals(t.getDescription(),DTOFormatter.DTOToTask(service.getAll().get(0)).getDescription());
    }

}
