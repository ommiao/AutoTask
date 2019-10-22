package cn.ommiao.autotask.entity;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM taskdata")
    List<TaskData> getAllTaskData();

    @Insert
    void insertTaskData(TaskData... taskData);

}