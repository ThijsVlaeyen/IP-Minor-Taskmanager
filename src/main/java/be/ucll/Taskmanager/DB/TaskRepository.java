package be.ucll.Taskmanager.DB;

import be.ucll.Taskmanager.Domain.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

public interface TaskRepository extends JpaRepository<Task,Long> {
@Modifying
@Transactional
@Query("update Task t set t.name = ?1,t.deadline = ?2,t.description = ?3,t.completed = ?4")
void updateTask(String name, LocalDateTime deadline,String description,boolean completed);
}
