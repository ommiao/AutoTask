package cn.ommiao.autotask.ui.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.TransitionDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.MainFragmentBinding;
import cn.ommiao.autotask.ui.adapter.TaskListAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.autotask.widget.rv_gallery.AnimManager;
import cn.ommiao.autotask.widget.rv_gallery.GalleryRecyclerView;
import cn.ommiao.autotask.widget.rv_gallery.util.BlurBitmapUtil;
import cn.ommiao.base.entity.order.Task;

public class MainFragment extends BaseFragment<MainFragmentBinding, MainViewModel> implements GalleryRecyclerView.OnItemClickListener {


    private ArrayList<Task> tasks = new ArrayList<>();

    private TaskListAdapter adapter;

    private boolean loaded = false;

    /**
     * 获取虚化背景的位置
     */
    private int mLastDraPosition = -1;

    private Map<String, Drawable> mTSDraCacheMap = new HashMap<>();
    private static final String KEY_PRE_DRAW = "key_pre_draw";

    @Override
    protected void initViews() {
        mBinding.rvTask.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.HORIZONTAL, false));
        adapter = new TaskListAdapter(R.layout.item_task_list, tasks);
        mBinding.rvTask.setAdapter(adapter);
        mBinding.rvTask
                // 设置滑动速度（像素/s）
                .initFlingSpeed(9000)
                // 设置页边距和左右图片的可见宽度，单位dp
                .initPageParams(0, 40)
                // 设置切换动画的参数因子
                .setAnimFactor(0.1f)
                // 设置切换动画类型，目前有AnimManager.ANIM_BOTTOM_TO_TOP和目前有AnimManager.ANIM_TOP_TO_BOTTOM
                .setAnimType(AnimManager.ANIM_BOTTOM_TO_TOP)
                // 设置点击事件
                .setOnItemClickListener(this)
                // 设置自动播放
                .autoPlay(false)
                // 设置自动播放间隔时间 ms
                .intervalTime(2000)
                // 设置初始化的位置
                .initPosition(0)
                // 在设置完成之后，必须调用setUp()方法
                .setUp();
        // 背景高斯模糊 & 淡入淡出
        mBinding.rvTask.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    setBlurImage(false);
                }
            }
        });
    }

    /**
     * 设置背景高斯模糊
     */
    public void setBlurImage(boolean forceUpdate) {
        TaskListAdapter adapter = (TaskListAdapter) mBinding.rvTask.getAdapter();
        final int mCurViewPosition = mBinding.rvTask.getScrolledPosition();

        boolean isSamePosAndNotUpdate = (mCurViewPosition == mLastDraPosition) && !forceUpdate;

        if (adapter == null || mBinding.rvTask == null || isSamePosAndNotUpdate) {
            return;
        }
        mBinding.rvTask.post(() -> {
            //如果是Fragment的话，需要判断Fragment是否Attach当前Activity，否则getResource会报错
            /*if (!isAdded()) {
                // fix fragment not attached to Activity
                return;
            }*/
            // 将该资源图片转为Bitmap
            Bitmap resBmp = BitmapFactory.decodeFile(tasks.get(mCurViewPosition).coverPath);
            // 将该Bitmap高斯模糊后返回到resBlurBmp
            Bitmap resBlurBmp = BlurBitmapUtil.blurBitmap(mBinding.rvTask.getContext(), resBmp, 15f);
            // 再将resBlurBmp转为Drawable
            Drawable resBlurDrawable = new BitmapDrawable(getResources(), resBlurBmp);
            // 获取前一页的Drawable
            Drawable preBlurDrawable = mTSDraCacheMap.get(KEY_PRE_DRAW) == null ? resBlurDrawable : mTSDraCacheMap.get(KEY_PRE_DRAW);

            /* 以下为淡入淡出效果 */
            Drawable[] drawableArr = {preBlurDrawable, resBlurDrawable};
            TransitionDrawable transitionDrawable = new TransitionDrawable(drawableArr);
            mBinding.flContainer.setBackground(transitionDrawable);
            transitionDrawable.startTransition(500);

            // 存入到cache中
            mTSDraCacheMap.put(KEY_PRE_DRAW, resBlurDrawable);
            // 记录上一次高斯模糊的位置
            mLastDraPosition = mCurViewPosition;
        });
    }

    @Override
    protected void initData() {
        mViewModel.getTasks().observe(this, tasks -> {
            if(!loaded){
                this.tasks.addAll(tasks);
                adapter.notifyDataSetChanged();
                loaded = true;
                setBlurImage(false);
            }
        });
    }

    @Override
    protected Class<MainViewModel> classOfViewModel() {
        return MainViewModel.class;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.main_fragment;
    }

    @Override
    public void onItemClick(View view, int position) {
        Toast.makeText(mContext, tasks.get(position).taskName, Toast.LENGTH_SHORT).show();
    }

}
