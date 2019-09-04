package cn.ommiao.autotask.ui.main;

import android.Manifest;
import android.animation.Animator;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewTreeObserver;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.gyf.immersionbar.ImmersionBar;
import com.orhanobut.logger.Logger;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;

import java.util.ArrayList;

import cn.ommiao.autotask.R;
import cn.ommiao.autotask.databinding.FragmentTaskAddBinding;
import cn.ommiao.autotask.databinding.HeaderOrderListBinding;
import cn.ommiao.autotask.entity.SectionOfOrder;
import cn.ommiao.autotask.other.Glide4Engine;
import cn.ommiao.autotask.ui.adapter.OrderListAdapter;
import cn.ommiao.autotask.ui.base.BaseFragment;
import cn.ommiao.autotask.ui.listener.SimpleAnimatorListener;
import cn.ommiao.base.entity.order.Action;
import cn.ommiao.base.entity.order.FindRule;
import cn.ommiao.base.entity.order.Group;
import cn.ommiao.base.entity.order.Order;
import cn.ommiao.base.entity.order.Task;

public class TaskAddFragment extends BaseFragment<FragmentTaskAddBinding, MainViewModel> implements ViewTreeObserver.OnPreDrawListener, BaseQuickAdapter.OnItemChildClickListener {

    private static final long REVEAL_DURATION = 500;
    private static final int REQUEST_CODE_CHOOSE = 666;
    private float startRadius;
    private float endRadius;
    private int tvX;
    private int tvY;

    private ArrayList<SectionOfOrder> sectionOfOrders = new ArrayList<>();
    private OrderListAdapter adapter;
    private HeaderOrderListBinding headerBinding;

    private Task task;

    @Override
    protected void initViews() {
        ImmersionBar.with(this).statusBarView(mBinding.vStatusBar).statusBarDarkFont(true).init();
        mBinding.fabAdd.getViewTreeObserver().addOnPreDrawListener(this);
        mBinding.ivBack.setOnClickListener(view -> {
            closeReveal(mBinding.getRoot());
        });
        adapter = new OrderListAdapter(R.layout.item_order_list, R.layout.header_order_section, sectionOfOrders);
        View header = LayoutInflater.from(mContext).inflate(R.layout.header_order_list, null);
        headerBinding = DataBindingUtil.bind(header);
        adapter.addHeaderView(header);
        View footer = LayoutInflater.from(mContext).inflate(R.layout.footer_order_list, null);
        adapter.addFooterView(footer);
        adapter.setOnItemChildClickListener(this);
        mBinding.rvOrders.setLayoutManager(new LinearLayoutManager(mContext));
        mBinding.rvOrders.setAdapter(adapter);
        headerBinding.ivSelectAlbum.setOnClickListener(view -> {
            if(ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                    ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
                AndPermission.with(mContext)
                        .runtime()
                        .permission(Permission.Group.STORAGE)
                        .onGranted(permissions -> {
                            // Storage permission are allowed.
                            selectAlbum();
                        })
                        .onDenied(permissions -> {
                            // Storage permission are not allowed.
                            Toast.makeText(mContext, "No permissions!", Toast.LENGTH_SHORT).show();
                        })
                        .start();
            } else {
                selectAlbum();
            }
        });
        mBinding.fabAdd.setOnClickListener(view -> {
            Group group = new Group();
            group.groupName = "指令组" + (task.groups.size() + 1);
            group.orders = new ArrayList<>();
            group.repeatTimes = 1;
            task.groups.add(group);
            sectionOfOrders.add(new SectionOfOrder(true, group.groupName, group));
            adapter.notifyItemInserted(computeIndexForGroup(group));
        });
    }

    private int computeIndexForGroup(Group group){
        int index = -1 + adapter.getHeaderLayoutCount();
        for (int i = 0; i < task.groups.size(); i++) {
            index += 1;
            Group g = task.groups.get(i);
            boolean equal = g == group;
            if(!equal){
                index = index + g.orders.size();
            }
        }
        return index;
    }

    private void selectAlbum(){
        Matisse.from(this)
                .choose(MimeType.of(MimeType.JPEG, MimeType.PNG))
                .countable(true)
                .maxSelectable(1)
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                .thumbnailScale(0.85f)
                .imageEngine(new Glide4Engine())
                .forResult(REQUEST_CODE_CHOOSE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_CODE_CHOOSE && resultCode == Activity.RESULT_OK){
            Uri uri = Matisse.obtainResult(data).get(0);
            Glide.with(this).load(uri).into(headerBinding.ivAlbum);
        }
    }

    @Override
    protected void initData() {

        task = new Task();
        task.groups = new ArrayList<>();

        Order order1 = new Order();
        order1.findRule = FindRule.ID;
        order1.action = Action.BACK;
        Order order2= new Order();
        order2.findRule = FindRule.DESCRIPTION;
        order2.action = Action.HOME;
        Order order3 = new Order();
        order3.findRule = FindRule.DEVICE;
        order3.action = Action.FORCE_STOP;

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
        mBinding.fabAdd.getViewTreeObserver().removeOnPreDrawListener(this);
        startReveal(mBinding.fabAdd, mBinding.getRoot());
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
        animator.addListener(new SimpleAnimatorListener(){
            @Override
            public void onAnimationEnd(Animator animator) {
                ImmersionBar.with(mContext).statusBarDarkFont(false).init();
            }
        });
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
        ImmersionBar.with(mContext).statusBarDarkFont(true).init();
        animator.start();
    }

    @Override
    public boolean listenBackPressed() {
        return true;
    }

    @Override
    public void onBackPressed() {
        closeReveal(mBinding.getRoot());
    }

    @Override
    public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
        switch (view.getId()){
            case R.id.iv_add_order:
                Logger.d(i);
                Group group = sectionOfOrders.get(i).groupOfOrder;
                Order order = getNewOrder();
                group.addOrder(order);
                sectionOfOrders.add(i + group.orders.size(), new SectionOfOrder(order, group));
                adapter.notifyItemInserted(i + group.orders.size() + 1);
                break;
        }
    }

    private Order getNewOrder(){
        Order order = new Order();
        order.findRule = FindRule.ID;
        order.action = Action.CLICK;
        return order;
    }
}
