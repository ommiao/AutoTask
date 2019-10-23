package cn.ommiao.autotask.util;

import androidx.room.Room;

import cn.ommiao.autotask.core.App;
import cn.ommiao.autotask.entity.TaskDatabase;

public class AppDatabase {

    private static TaskDatabase db;

    public static TaskDatabase getTaskDatabase(){
        if(db == null){
            db = Room.databaseBuilder(App.getContext(),
                    TaskDatabase.class, "task.db").build();
        }
        return db;
    }

}
