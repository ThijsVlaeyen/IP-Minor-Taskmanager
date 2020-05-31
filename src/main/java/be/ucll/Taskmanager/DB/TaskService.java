package be.ucll.Taskmanager.DB;

import be.ucll.Taskmanager.DTO.SubTaskDTO;
import be.ucll.Taskmanager.DTO.TaskDTO;
import be.ucll.Taskmanager.Domain.DTOFormatter;
import be.ucll.Taskmanager.Domain.SubTask;
import be.ucll.Taskmanager.Domain.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TaskService {
    TaskRepository taskRepository;

    SubTaskRepository subTaskRepository;

    @Autowired
    public TaskService(TaskRepository taskRepository, SubTaskRepository subTaskRepository) {
        this.taskRepository = taskRepository;
        this.subTaskRepository = subTaskRepository;
    }

    public TaskDTO get(long id) {
        Optional<Task> optional = taskRepository.findById(id);
        TaskDTO task = DTOFormatter.createDTOfromTask(optional.orElseThrow(() -> new DbException("no task with id:"+ id +" found")));
        task.setSubtasks(subTaskRepository.findByTask(DTOFormatter.DTOToTask(task)));
        return task;
    }

    public List<TaskDTO> getAll() {
        List<TaskDTO> result = new ArrayList<>();
        for (Task t:taskRepository.findAll()){

            t.setSubtasks(subTaskRepository.findByTask(t));
            result.add(DTOFormatter.createDTOfromTask(t));

        }

        return result;
    }

    public List<SubTaskDTO> getAll(long id) {
        List<SubTaskDTO> result = new ArrayList<>();

        for (SubTask t:subTaskRepository.findByTask(taskRepository.findById(id).get())){
            result.add(DTOFormatter.createDTOfromSubtask(t));
        }
        return result;
    }

    public List<SubTask> getAllSubtasks() {
        return null;
    }

    public TaskDTO add(TaskDTO dto) {
        taskRepository.save(DTOFormatter.DTOToTask(dto));
        return dto;
    }

    public void delete(long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO convert(Task t) {
        TaskDTO taskDTO = new TaskDTO();
        taskDTO.setId(t.getId());
        taskDTO.setName(t.getName());
        taskDTO.setDeadline(t.getDeadline());
        taskDTO.setDescription(t.getDescription());
        taskDTO.setSubtasks(t.getSubtasks());
        taskDTO.setCompleted(t.isCompleted());

        return taskDTO;
    }

    public TaskDTO update(TaskDTO dto){

        Task t = DTOFormatter.DTOToTask(dto);
        taskRepository.updateTask(t.getName(),t.getDeadline(),t.getDescription(),t.getId());
        return dto;

    }

    public Task toEntity(TaskDTO dto) {
        Task t = new Task();
        t.setId(dto.getId());
        t.setName(dto.getName());
        t.setDescription(dto.getDescription());
        t.setDeadline(dto.getDeadline());
        t.setSubtasks(dto.getSubtasks());
        return t;
    }
}
