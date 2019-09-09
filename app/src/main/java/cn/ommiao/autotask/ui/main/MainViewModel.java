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

    public MutableLiveData<ArrayList<Task>> getTasks(){
        loadTasks();
        return taskLiveData;
    }

    private void loadTasks(){
        ArrayList<Task> tasks = new ArrayList<>();
        tasks.add(Task.fromJson(OrderUtil.readOrders(App.getContext()), Task.class));
        taskLiveData.setValue(tasks);
    }

}
