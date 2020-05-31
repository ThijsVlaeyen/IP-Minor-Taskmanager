package be.ucll.Taskmanager.Domain;

import be.ucll.Taskmanager.DTO.SubTaskDTO;
import be.ucll.Taskmanager.DTO.TaskDTO;

import java.util.ArrayList;
import java.util.List;

public class DTOFormatter {

    public static SubTask DTOToSubtask(SubTaskDTO dto){
        if (dto == null)
            return null;
        SubTask subTask = new SubTask();
        subTask.setId(dto.getId());
        subTask.setTask(dto.getTask());
        subTask.setName(dto.getName());
        subTask.setDescription(dto.getDescription());
        return subTask;
    }

    public static Task DTOToTask(TaskDTO dto){
        if (dto == null)
            return null;
        Task t = new Task();
        t.setId(dto.getId());
        t.setName(dto.getName());
        t.setDescription(dto.getDescription());
        t.setDeadline(dto.getDeadline());
        t.setSubtasks(dto.getSubtasks());
        return t;
    }

    public static SubTaskDTO createDTOfromSubtask(SubTask subTask) {
        if (subTask == null)
            return null;
        SubTaskDTO dto = new SubTaskDTO();
        dto.setId(subTask.getId());
        dto.setDescription(subTask.getDescription());
        dto.setName(subTask.getName());
        dto.setTask(subTask.getTask());
        return dto;
    }

    public static TaskDTO createDTOfromTask(Task task) {
        if (task == null)
            return null;
        TaskDTO dto = new TaskDTO();
        dto.setId(task.getId());
        dto.setDescription(task.getDescription());
        dto.setName(task.getName());
        dto.setDeadline(task.getDeadline());
        dto.setSubtasks(task.getSubtasks());
        return dto;
    }

    public static List<SubTask> formatToList(List<SubTaskDTO> dtos){
        List<SubTask> result = new ArrayList<>();
        for (SubTaskDTO dto: dtos){
            result.add(DTOToSubtask(dto));
        }
        return result;
    }
}
