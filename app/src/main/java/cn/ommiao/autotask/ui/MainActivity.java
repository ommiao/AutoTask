package cn.ommiao.autotask.ui;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.ActivityMainBinding;
import cn.ommiao.autotask.ui.base.BaseActivity;
import cn.ommiao.autotask.ui.main.TaskListFragment;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    @Override
    protected void initViews(Bundle savedInstanceState) {
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new TaskListFragment())
                    .commitNow();
        }
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }


}
