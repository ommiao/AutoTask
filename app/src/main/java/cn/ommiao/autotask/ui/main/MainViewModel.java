package cn.ommiao.autotask.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import cn.ommiao.autotask.entity.TaskData;
import cn.ommiao.autotask.util.AppDatabase;
import cn.ommiao.autotask.util.AppExecutors;
import cn.ommiao.base.entity.order.Task;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Task>> taskLiveData = new MutableLiveData<>();
    private MutableLiveData<Task> newTask = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Task>> getTasks(){
        loadTasks();
        return taskLiveData;
    }

    private void loadTasks(){
        AppExecutors.getDiskIO().execute(() -> {
            ArrayList<Task> tasks = new ArrayList<>();
            List<TaskData> taskDataList = AppDatabase.getTaskDatabase().taskDao().getAllTaskData();
            if(taskDataList != null){
                for (TaskData taskData : taskDataList) {
                    Task task = Task.fromJson(taskData.getTaskString(), Task.class);
                    tasks.add(task);
                }
            }
            taskLiveData.postValue(tasks);
        });
    }
}
