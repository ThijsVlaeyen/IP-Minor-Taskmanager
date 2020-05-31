package be.ucll.Taskmanager.controller;


import be.ucll.Taskmanager.DB.SubTaskService;
import be.ucll.Taskmanager.DB.TaskService;
import be.ucll.Taskmanager.DTO.SubTaskDTO;
import be.ucll.Taskmanager.DTO.TaskDTO;
import be.ucll.Taskmanager.Domain.SubTask;
import be.ucll.Taskmanager.Domain.Task;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

import static be.ucll.Taskmanager.Domain.DTOFormatter.DTOToSubtask;
import static be.ucll.Taskmanager.Domain.DTOFormatter.DTOToTask;

@RestController
@RequestMapping("/api")
public class TaskRestController {

private final TaskService taskService;
private final SubTaskService subtaskService;

   public TaskRestController(TaskService taskService,SubTaskService subTaskService){
      this.taskService = taskService;
      this.subtaskService = subTaskService;
   }

   @GetMapping("/tasks/all")
   @ResponseBody
   public List<TaskDTO> getAll() {
      return taskService.getAll();
   }

   @GetMapping("/tasks/{id}")
   public TaskDTO get(@PathVariable("id") long id){
      return taskService.get(id);
   }

   @PostMapping("/tasks/add")
   public TaskDTO addTask(@ModelAttribute @Valid TaskDTO t){
      taskService.add(t);
      return t;
   }

   @PostMapping("/tasks/edit/{id}")
   public TaskDTO editTask(@PathVariable("id") long id, @ModelAttribute @Valid TaskDTO t){
      TaskDTO dto = taskService.get(id);
      dto.setName(t.getName());
      dto.setDescription(t.getDescription());
      dto.setDeadline(t.getDeadline());
      dto = taskService.update(dto);
      List<SubTask> result = new ArrayList<>();
      for (SubTaskDTO sub : taskService.getAll(t.getId())){
         result.add(DTOToSubtask(sub));
      }
      dto.setSubtasks(result);
      return dto;
   }

   @PostMapping("/tasks/{id}/sub/create")
   public SubTaskDTO addSubtask(@ModelAttribute @Valid SubTaskDTO subTask,@PathVariable("id") long id){
      subTask.setTask(DTOToTask(taskService.get(id)));
      subtaskService.add(subTask);
      System.out.println("slkfjqsdlkf");
      return subTask;
   }

   @GetMapping("/tasks/{id}/sub/all")
   public List<SubTaskDTO> getSubtasks(@PathVariable("id") long id){
      Task t = DTOToTask(taskService.get(id));
      return subtaskService.getAll(t);
   }
}
