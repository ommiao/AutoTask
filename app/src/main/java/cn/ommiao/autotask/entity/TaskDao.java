package cn.ommiao.autotask.entity;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

@Dao
public interface TaskDao {

    @Query("SELECT * FROM TaskData")
    List<TaskData> getAllTaskData();

    @Insert
    void insertTaskData(TaskData... taskData);

    @Query("DELETE FROM TaskData WHERE taskId = (:taskId)")
    void deleteTask(String taskId);

    @Insert
    long insertExecuteResult(ExecuteResultData executeResultData);

    @Query("SELECT * FROM ExecuteResultData order by startTime desc limit 0,1")
    LiveData<ExecuteResultData> getNewExecuteResult();

}