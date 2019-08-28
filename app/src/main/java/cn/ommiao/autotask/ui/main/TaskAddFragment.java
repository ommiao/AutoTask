package cn.ommiao.autotask.ui.main;

import android.animation.Animator;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentTaskAddBinding;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.autotask.ui.listener.SimpleAnimatorListener;

public class TaskAddFragment extends BaseFragment<FragmentTaskAddBinding, MainViewModel> implements ViewTreeObserver.OnPreDrawListener {

    private static final long REVEAL_DURATION = 500;
    private float startRadius;
    private float endRadius;
    private int tvX;
    private int tvY;

    @Override
    protected void initViews() {
        mBinding.fabHidden.getViewTreeObserver().addOnPreDrawListener(this);
        mBinding.ivTest.setOnClickListener(view -> {
            closeReveal(mBinding.getRoot());
        });
    }

    @Override
    protected Class<MainViewModel> classOfViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_task_add;
    }

    @Override
    public boolean onPreDraw() {
        mBinding.fabHidden.getViewTreeObserver().removeOnPreDrawListener(this);
        startReveal(mBinding.fabHidden, mBinding.getRoot());
        return true;
    }

    private void startReveal(View triggerView, View animView){
        int[] tvLocation = new int[2];
        triggerView.getLocationInWindow(tvLocation);
        tvX = tvLocation[0] + triggerView.getWidth() / 2;
        tvY = tvLocation[1] + triggerView.getHeight() / 2;
        int[] avLocation = new int[2];
        animView.getLocationInWindow(avLocation);
        int avX = avLocation[0] + animView.getWidth() / 2;
        int avY = avLocation[1] + animView.getHeight() / 2;
        int rippleW = tvX < avX ? animView.getWidth() - tvX : tvX - avLocation[0];
        int rippleY = tvY < avY ? animView.getHeight() - tvY : tvY - avLocation[1];
        startRadius = 0f;
        endRadius = (float) Math.sqrt(rippleW * rippleW + rippleY * rippleY);
        Animator animator = ViewAnimationUtils.createCircularReveal(animView, tvX, tvY, startRadius, endRadius);
        animator.setDuration(REVEAL_DURATION);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.start();
    }

    private void closeReveal(View animView){
        Animator animator = ViewAnimationUtils.createCircularReveal(animView, tvX, tvY, endRadius, startRadius);
        animator.setDuration(REVEAL_DURATION);
        animator.setInterpolator(new AccelerateDecelerateInterpolator());
        animator.addListener(new SimpleAnimatorListener() {

            @Override
            public void onAnimationEnd(Animator animator) {
                mBinding.getRoot().setVisibility(View.INVISIBLE);
                assert getFragmentManager() != null;
                getFragmentManager().popBackStack();
            }

        });
        animator.start();
    }

}
