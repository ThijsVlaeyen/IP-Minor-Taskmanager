package be.ucll.Taskmanager.DB;

import be.ucll.Taskmanager.Domain.SubTask;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface SubTaskRepository extends JpaRepository<SubTask,Long>{
@Query("select s from SubTask s where s.taskid = ?1")
    List<SubTask> getAllSubTaskByTaskid(long taskid);
    @Modifying
    @Transactional
    void removeAllByTaskid(long taskid);
    @Modifying
    @Transactional
    @Query("update SubTask s set s.name=?1,s.description=?2,s.completed=?3,s.taskid=?4 where s.id = ?5")
    void update (String name,String description,boolean completed,long taskid,long id);
}
