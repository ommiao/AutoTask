package cn.ommiao.autotask.ui.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;

import cn.ommiao.autotask.interfaces.OnFragmentChangedListener;

public abstract class BaseActivity<B extends ViewDataBinding> extends AppCompatActivity implements OnFragmentChangedListener {

    protected B mBinding;
    private BaseFragment focusedFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, getLayoutId());
        init();
        initViews(savedInstanceState);
        initData();
    }

    protected void init(){

    }

    protected abstract void initViews(Bundle savedInstanceState);

    protected void initData(){

    }

    protected abstract  @LayoutRes int getLayoutId();

    @Override
    public void setFocusedFragment(BaseFragment focusedFragment) {
        this.focusedFragment = focusedFragment;
    }

    @Override
    public void onBackPressed() {
        if(focusedFragment == null || !focusedFragment.listenBackPressed()){
            super.onBackPressed();
        } else {
            focusedFragment.onBackPressed();
        }
    }
}
