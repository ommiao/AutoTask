package cn.ommiao.autotask.ui.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;

import java.util.List;

import cn.ommiao.autotask.interfaces.OnFragmentChangedListener;

public abstract class BaseFragment<B extends ViewDataBinding, M extends ViewModel> extends Fragment {

    protected FragmentActivity mContext;
    protected B mBinding;
    protected M mViewModel;
    private OnFragmentChangedListener backPressedListener;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getActivity();
        backPressedListener = (OnFragmentChangedListener) getActivity();
        init();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    protected void init() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        initViews();
        return mBinding.getRoot();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mViewModel = ViewModelProviders.of(mContext).get(classOfViewModel());
        initData();
    }

    protected abstract void initViews();

    protected void initData(){

    }

    protected abstract Class<M> classOfViewModel();

    protected abstract @LayoutRes int getLayoutId();

    protected void addFragmentToBackStack(@IdRes int resId, BaseFragment fragment){
        assert getFragmentManager() != null;
        getFragmentManager()
                .beginTransaction()
                .add(resId, fragment)
                .addToBackStack(fragment.getClass().getSimpleName())
                .commit();
        backPressedListener.setFocusedFragment(fragment);

    }

    protected void popBackStack(){
        assert getFragmentManager() != null;
        List<Fragment> fragments = getFragmentManager().getFragments();
        int size = fragments.size();
        if(size < 2){
            return;
        }
        for(int i = size - 2; i >= 0; i--){
            Fragment fragment = fragments.get(i);
            if(fragment instanceof BaseFragment){
                BaseFragment baseFragment = (BaseFragment)fragment;
                backPressedListener.setFocusedFragment(baseFragment);
                getFragmentManager().popBackStack();
            }
        }
    }

    public boolean listenBackPressed(){
        return false;
    }

    public void onBackPressed(){

    }
}
