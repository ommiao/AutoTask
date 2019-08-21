package cn.ommiao.autotask.ui.main;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import cn.ommiao.base.entity.order.Task;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Task>> taskLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Task>> getTasks(){
        loadTasks();
        return taskLiveData;
    }

    private void loadTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        Task taskA = new Task();
        taskA.taskId = "A";
        taskA.taskName = "Task A";
        taskA.taskDescription = "This is description of Task A.";
        tasks.add(taskA);
        Task taskB = new Task();
        taskB.taskId = "B";
        taskB.taskName = "Task B";
        taskB.taskDescription = "This is description of Task B.";
        tasks.add(taskB);
        taskLiveData.setValue(tasks);
    }

}
