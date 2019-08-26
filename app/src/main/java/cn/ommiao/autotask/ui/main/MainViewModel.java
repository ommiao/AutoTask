package cn.ommiao.autotask.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.palette.graphics.Palette;

import com.orhanobut.logger.Logger;

import java.util.ArrayList;

import cn.ommiao.base.entity.order.Task;
import cn.ommiao.base.util.StringUtil;

public class MainViewModel extends ViewModel {

    private MutableLiveData<ArrayList<Task>> taskLiveData = new MutableLiveData<>();

    public MutableLiveData<ArrayList<Task>> getTasks(){
        loadTasks();
        return taskLiveData;
    }

    private void loadTasks(){
        String dir = Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/cn.ommiao.autotask/files/";
        ArrayList<Task> tasks = new ArrayList<>();
        Task taskA = new Task();
        taskA.taskId = "wsdfwerf";
        taskA.taskName = "支付宝";
        taskA.coverPath = dir + "1.jpg";
        taskA.taskDescription = "This is description of Task A.";
        tasks.add(taskA);
        Task taskB = new Task();
        taskB.taskId = "B";
        taskB.taskName = "微信";
        taskB.coverPath = dir + "2.jpg";
        taskB.taskDescription = "This is description of Task B.";
        tasks.add(taskB);
        tasks.add(taskA);
        taskLiveData.setValue(tasks);
        Task taskC = new Task();
        taskC.taskId = "C";
        taskC.taskName = "QQ阅读";
        taskC.coverPath = dir + "3.jpg";
        taskC.taskDescription = "This is description of Task C.";
        tasks.add(taskC);
        tasks.add(taskB);
        tasks.add(taskA);
        Task taskD = new Task();
        taskD.taskId = "D";
        taskD.taskName = "其他的什么东西";
        taskD.coverPath = dir + "4.jpg";
        taskD.taskDescription = "This is description of Task D.";
        tasks.add(taskD);
        taskLiveData.setValue(tasks);
        Task taskE = new Task();
        taskE.taskId = "E";
        taskE.taskName = "不知道是什么啊";
        taskE.coverPath = dir + "5.jpg";
        taskE.taskDescription = "This is description of Task E.";
        tasks.add(taskE);
        tasks.add(taskC);
        tasks.add(taskB);
        tasks.add(taskA);
        for (Task task : tasks) {
            if(StringUtil.isEmptyOrSpace(task.coverPath)){
                continue;
            }
            Bitmap bitmap = BitmapFactory.decodeFile(task.coverPath);
            if(bitmap != null){
                Palette palette = Palette.from(bitmap).generate();
                Palette.Swatch lightVibrantSwatch = palette.getVibrantSwatch();
                if(lightVibrantSwatch != null){
                    //谷歌推荐的：图片的整体的颜色rgb的混合值---主色调
                    task.taskCoverColor = lightVibrantSwatch.getRgb();
                    //谷歌推荐：图片中间的文字颜色
                    task.taskNameColor = lightVibrantSwatch.getBodyTextColor();
                    //谷歌推荐：作为标题的颜色（有一定的和图片的对比度的颜色值）
                    task.taskDescriptionColor = lightVibrantSwatch.getTitleTextColor();

                }
            }
        }
    }

}
