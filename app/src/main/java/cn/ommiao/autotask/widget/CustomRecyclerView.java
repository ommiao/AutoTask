package cn.ommiao.autotask.widget;

import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.AccelerateInterpolator;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.orhanobut.logger.Logger;

public class CustomRecyclerView extends RecyclerView {

    private static final long SEPARATE_RECOVER_DURATION = 300;
    private static final float MAX_DELTA_Y = 200000;
    private static final float FACTOR = 0.3f;

    private int touchSlop;

    private Rect mTouchFrame;


    //按下的位置
    private int downPosition;

    //是否是分离状态
    private boolean separate = false;

    //滑动到顶部或底部时的y
    private float startY;

    //滑动位移
    private float deltaY;

    //上次滑动位置，用于判断方向
    private float preY;

    //是否在移动
    private boolean move;

    private boolean separateAll = false;

    private boolean needUpdateDownPositionTop = true;
    private boolean needUpdateDownPositionBottom = true;

    public CustomRecyclerView(@NonNull Context context) {
        this(context, null);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        touchSlop = ViewConfiguration.get(getContext()).getScaledTouchSlop();
    }

    private boolean isReachTop(){
        return !canScrollVertically(-1);
    }

    private boolean isReachBottom(){
        return !canScrollVertically(1);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        float currentY = ev.getY();
        switch (ev.getAction()){
            case MotionEvent.ACTION_MOVE:
                //记录到达顶部或底部时手指的位置
                if (!separate) {
                    startY = currentY;
                }
                deltaY = currentY - startY;

                //到达顶部
                if (isReachTop()) {
                    if(needUpdateDownPositionTop){
                        downPosition = getDownPosition(ev.getX(), ev.getY());
                        needUpdateDownPositionTop = false;
                        needUpdateDownPositionBottom = true;
                    }
                    if (!separateFromTop(currentY)) {
                        return super.dispatchTouchEvent(ev);
                    }
                    super.dispatchTouchEvent(ev);
                    return false;
                }
                //到达底部
                if (isReachBottom()) {
                    if(needUpdateDownPositionBottom){
                        downPosition = getDownPosition(ev.getX(), ev.getY());
                        needUpdateDownPositionBottom = false;
                        needUpdateDownPositionTop = true;
                    }
                    if (!separateFromBottom(currentY)) {
                        return super.dispatchTouchEvent(ev);
                    }
                    super.dispatchTouchEvent(ev);
                    return false;
                }
                preY = currentY;
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                preY = 0;
                needUpdateDownPositionTop = true;
                needUpdateDownPositionBottom = true;
                if (separate) {
                    separate = false;
                    recoverSeparate();
                    //移动，不响应点击事件
                    if (move) {
                        move = false;
                        super.dispatchTouchEvent(ev);
                        return false;
                    }
                }
        }
        return super.dispatchTouchEvent(ev);
    }

    private boolean separateFromTop(float currentY) {
        //不能放在外部，否则在顶部滑动没有Fling效果
        if (deltaY > touchSlop) {
            move = true;
        }
        separate = true;
        //超过滑动允许的最大距离，则将起始位置向下移
        if (deltaY > MAX_DELTA_Y) {
            startY = currentY - MAX_DELTA_Y;
            //超过最大距离时，出现overScroll效果//有问题
            //return super.dispatchTouchEvent(ev);
        } else if (deltaY < 0) { //为负值时（说明反方向超过了起始位置startY）归0
            deltaY = 0;
            separate = false;
        }

        if (deltaY <= MAX_DELTA_Y) {
            for (int index = 0; index < getChildCount(); index++) {
                View child = getChildAt(index);
                int multiple = index;
                if (!separateAll) {
                    if (index > downPosition) {
                        multiple = Math.max(1, downPosition);
                    }
                }
                float distance = multiple * deltaY * FACTOR;
                child.setTranslationY(distance);
            }
            //向分离方向的反方向滑动，但位置还未复原时
            if (deltaY != 0 && currentY - preY < 0) {
                return true;
            }
            //deltaY=0，说明位置已经复原，然后交给父类处理
        }
        if (deltaY == 0) {
            return false;
        }
        return true;
    }

    private boolean separateFromBottom(float currentY) {
        if (Math.abs(deltaY) > touchSlop) {
            move = true;
        }
        separate = true;
        //超过滑动允许的最大距离，则将起始位置向上移
        if (Math.abs(deltaY) > MAX_DELTA_Y) {
            startY = currentY + MAX_DELTA_Y;
            //超过最大距离时，出现overScroll效果
            //return super.dispatchTouchEvent(ev);
        } else if (deltaY > 0) { //为正值时（说明反方向移动超过起始位置startY），归0
            deltaY = 0;
            separate = false;
        }
        if (Math.abs(deltaY) <= MAX_DELTA_Y) {
            int visibleCount = getChildCount();
            for (int index = 0; index < visibleCount; index++) {
                View child = getChildAt(index);
                int multiple = visibleCount - index - 1;
                if (!separateAll) {
                    if (index < downPosition) {
                        multiple = Math.max(1, visibleCount - downPosition - 1);
                    }
                }
                float distance = multiple * deltaY * FACTOR;
                child.setTranslationY(distance);
            }
            //向分离方向的反方向滑动，但位置还未复原时
            if (deltaY != 0 && currentY - preY > 0) {
                return true;
            }
        }
        //deltaY=0，说明位置已经复原，然后交给父类处理
        if (deltaY == 0) {
            return false;
        }
        return true;
    }

    /**
     * 恢复
     */
    private void recoverSeparate() {
        for (int i = 0; i < getChildCount(); i++) {
            View child = getChildAt(i);
            ViewCompat.animate(child)
                    .translationY(0).setDuration(SEPARATE_RECOVER_DURATION)
                    .setInterpolator(new AccelerateInterpolator());
        }
    }

    private int getDownPosition(float downX, float downY){
        int pos = -1;
        //通过点击的坐标计算当前的position
        Rect frame = mTouchFrame;
        if (frame == null) {
            mTouchFrame = new Rect();
            frame = mTouchFrame;
        }
        final int count = getChildCount();
        for (int i = count - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (child.getVisibility() == View.VISIBLE) {
                child.getHitRect(frame);
                if (frame.contains((int) child.getX(), (int) downY)) {
                    pos = i;
                    break;
                }
            }
        }
        return pos;
    }

}
