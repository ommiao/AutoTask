package cn.ommiao.autotask.ui.main;

import android.os.Environment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;

import cn.ommiao.autotask.core.App;
import cn.ommiao.base.entity.order.Task;
import cn.ommiao.base.util.OrderUtil;

public class MainViewModel extends ViewModel {

    public static final String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/cn.ommiao.autotask/files/";

    private MutableLiveData<ArrayList<Task>> taskLiveData = new MutableLiveData<>();
    private MutableLiveData<Task> newTask = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Task>> getTasks(){
        loadTasks();
        return taskLiveData;
    }

    private void loadTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(Task.fromJson(OrderUtil.readOrders(App.getContext()), Task.class));
        taskLiveData.setValue(tasks);
    }

    public void addNewTask(Task task){
        newTask.setValue(task);
        if(taskLiveData.getValue() != null){
            taskLiveData.getValue().add(task);
        }
    }

    public MutableLiveData<Task> getNewTask() {
        return newTask;
    }

    public int getIndexByTaskId(String taskId){
        int index = -1;
        ArrayList<Task> tasks = taskLiveData.getValue();
        if(tasks != null){
            for (int i = 0; i < tasks.size(); i++) {
                if(taskId.equals(tasks.get(i).taskId)){
                    index = i;
                    break;
                }
            }
        }
        return index;
    }
}
