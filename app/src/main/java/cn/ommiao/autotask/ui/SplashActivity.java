package cn.ommiao.autotask.ui;

import android.content.Intent;
import android.os.Bundle;
import android.system.ErrnoException;
import android.system.Os;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.ActivitySplashBinding;
import cn.ommiao.autotask.ui.base.BaseActivity;
import cn.ommiao.base.util.FileUtil;

import static cn.ommiao.autotask.ui.MainActivity.ALREADY_LAUNCHED;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected boolean init() {
        if(ALREADY_LAUNCHED){
            startMain();
            return false;
        }
        copyFiles("autotaskserver.jar");
        copyFiles("autotask.bash");
        return true;
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mBinding.getRoot().postDelayed(this::startMain, 1000);
    }

    private void startMain() {
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }


    @Override
    protected int getLayoutId() {
        return R.layout.activity_splash;
    }

    private void copyFiles(String fileName) {
        String toPath =getFilesDir().getParentFile() + "/" + fileName;
        FileUtil.copyAssetFile(this, fileName, toPath);
        try {
            Os.chmod(getFilesDir().getParentFile().getAbsolutePath(),489);
            Os.chmod(toPath,420);
        } catch (ErrnoException e) {
            e.printStackTrace();
        }
    }
}
