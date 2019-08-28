package cn.ommiao.autotask.ui;

import android.content.Intent;
import android.os.Bundle;
import android.system.ErrnoException;
import android.system.Os;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.ActivitySplashBinding;
import cn.ommiao.autotask.ui.base.BaseActivity;
import cn.ommiao.base.util.FileUtil;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {

    @Override
    protected void init() {
        copyFiles("autotaskserver.jar");
        copyFiles("autotask.bash");
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        mBinding.getRoot().postDelayed(() -> {
            startActivity(new Intent(this, MainActivity.class));
            finish();
        }, 1000);
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
