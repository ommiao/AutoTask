package cn.ommiao.autotask;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.gyf.immersionbar.ImmersionBar;

import cn.ommiao.autotask.ui.main.MainFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);
        ImmersionBar.with(this).transparentStatusBar().statusBarDarkFont(true).init();
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, new MainFragment())
                    .commitNow();
        }
    }
}
