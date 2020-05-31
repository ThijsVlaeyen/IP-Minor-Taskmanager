package be.ucll.Taskmanager;

import be.ucll.Taskmanager.DTO.SubTaskDTO;
import be.ucll.Taskmanager.DTO.TaskDTO;
import be.ucll.Taskmanager.Domain.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static be.ucll.Taskmanager.Domain.DTOFormatter.*;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class DTOFormatterTests {
    @Test
    public void TaskDTOToTaskTest(){
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(1);
        taskDTO.setName("taskDTO To Task");
        taskDTO.setDescription("TaskDTO To Task Description");
        taskDTO.setDeadline(LocalDateTime.now());
        Task task = DTOToTask(taskDTO);
        assertEquals(task.getId(),taskDTO.getId());
        assertEquals(task.getName(),taskDTO.getName());
        assertEquals(task.getDescription(),taskDTO.getDescription());
        assertEquals(task.getDeadline(),taskDTO.getDeadline());
        assertEquals(task.getSubtasks(),taskDTO.getSubtasks());
    }

    @Test
    public void TaskToTaskDto(){
        Task t = new Task();
        t.setId(1);
        t.setName("Task to TaskDTo");
        t.setDescription("Task to taskdto description");
        t.setDeadline(LocalDateTime.now());
        List<SubTask> subTaskList = new ArrayList<>();
        subTaskList.add(new SubTask("subtasktest", "Description"));
        t.setSubtasks(subTaskList);
        TaskDTO dto = createDTOfromTask(t);
        assertEquals(dto.getId(),t.getId());
        assertEquals(dto.getName(),t.getName());
        assertEquals(dto.getDescription(),t.getDescription());
        assertEquals(dto.getDeadline(),t.getDeadline());
        assertEquals(dto.getSubtasks(),t.getSubtasks());
    }

    @Test
    public void SubtaskDTOToSubtask(){
        SubTaskDTO subTaskDTO = new SubTaskDTO();
        subTaskDTO.setId(1);
        subTaskDTO.setName("taskDTOToTask");
        subTaskDTO.setDescription("TaskDTOToTaskDescription");
        SubTask subtask = DTOToSubtask(subTaskDTO);
        assertEquals(subtask.getId(),subTaskDTO.getId());
        assertEquals(subtask.getName(),subTaskDTO.getName());
        assertEquals(subtask.getDescription(),subTaskDTO.getDescription());
    }

    @Test
    public void SubtaskToSubtaskDTO(){
        SubTask subTask = new SubTask();
        subTask.setId(1);
        subTask.setName("subtask name");
        subTask.setDescription("subtask description");
        subTask.setTask(new Task("task","description",LocalDateTime.now()));
        SubTaskDTO dto = createDTOfromSubtask(subTask);
        assertEquals(dto.getId(),subTask.getId());
        assertEquals(dto.getTask(),subTask.getTask());
        assertEquals(dto.getDescription(),subTask.getDescription());
        assertEquals(dto.getName(),subTask.getName());
    }

    @Test
    public void testSubtaskListFormat(){
        SubTaskDTO dto = new SubTaskDTO();
        dto.setName("subtask test");
        dto.setDescription("subtask description test");
        SubTaskDTO dto2 = new SubTaskDTO();
        dto2.setName("subtask test");
        dto2.setDescription("subtask description test");
        List<SubTaskDTO> subTaskDTOList = new ArrayList<>();
        subTaskDTOList.add(dto);
        subTaskDTOList.add(dto2);
        List<SubTask> subtask = DTOFormatter.formatToList(subTaskDTOList);
        assertEquals(subtask.get(0).getName(),dto.getName());
        assertEquals(subtask.get(0).getDescription(),dto.getDescription());
        assertEquals(subtask.get(1).getName(),dto2.getName());
        assertEquals(subtask.get(1).getDescription(),dto2.getDescription());
    }
}
