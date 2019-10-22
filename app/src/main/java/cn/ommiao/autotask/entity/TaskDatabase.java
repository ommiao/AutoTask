package cn.ommiao.autotask.entity;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {TaskData.class}, version = 1, exportSchema = false)
public abstract class TaskDatabase extends RoomDatabase {

    public abstract TaskDao taskDao();

}
