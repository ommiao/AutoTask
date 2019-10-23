package cn.ommiao.autotask.entity;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM TaskData")
    List<TaskData> getAllTaskData();

    @Insert
    void insertTaskData(TaskData... taskData);

    @Query("DELETE FROM taskdata WHERE taskId = (:taskId)")
    void deleteTask(String taskId);

}