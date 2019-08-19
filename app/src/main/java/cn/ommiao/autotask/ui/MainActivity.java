package cn.ommiao.autotask.ui;

import android.os.Handler;
import android.os.Message;
import android.system.ErrnoException;
import android.system.Os;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.lang.ref.WeakReference;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.ActivityMainBinding;
import cn.ommiao.autotask.task.Client;
import cn.ommiao.autotask.ui.base.BaseActivity;
import cn.ommiao.base.util.FileUtil;
import cn.ommiao.base.util.OrderUtil;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private MyHandler handler = new MyHandler(this);
    private Client client;

    static class MyHandler extends Handler {

        private WeakReference<MainActivity> weakReference;

        private MyHandler(MainActivity activity) {
            this.weakReference = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(@NonNull Message msg) {
            MainActivity activity = weakReference.get();
            if(activity != null){
                Toast.makeText(activity, "Server is not running.", Toast.LENGTH_SHORT).show();
                activity.client.close();
            }
        }
    }

    @Override
    protected void init() {
        copyFiles("autotaskserver.jar");
        copyFiles("autotask.bash");
        client = new Client(message -> {
            if(Client.OK.equals(message)){
                handler.removeMessages(2);
                runOnUiThread(() -> {
                    Toast.makeText(MainActivity.this, "Task running.", Toast.LENGTH_SHORT).show();
                });
            }
        });
    }

    @Override
    protected void initViews() {
        mBinding.tv.setOnClickListener(view -> {
            FileUtil.writeTask(OrderUtil.readOrders(this));
            client.send(Client.RUN_TEST);
            handler.sendEmptyMessageDelayed(2, 3000);
        });
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    private void copyFiles(String fileName) {
        String fromPath = fileName;
        String toPath =getFilesDir().getParentFile() + "/" + fileName;
        FileUtil.copyAssetFile(this, fromPath, toPath);
        try {
            Os.chmod(getFilesDir().getParentFile().getAbsolutePath(),489);
            Os.chmod(toPath,420);
        } catch (ErrnoException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        client.close();
    }
}
