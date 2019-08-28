package cn.ommiao.autotask.util;

import android.view.View;

import com.facebook.rebound.SimpleSpringListener;
import com.facebook.rebound.Spring;
import com.facebook.rebound.SpringConfig;
import com.facebook.rebound.SpringSystem;

public class UiUtil {

    private static SpringSystem mSpringSystem = SpringSystem.create();

    private static final double tension = 50;
    private static final double frictiion = 5;
    public static void scaleAnimation(final View target, float from, float to){
        Spring spring = mSpringSystem.createSpring();
        spring.setCurrentValue(from);
        spring.setSpringConfig(SpringConfig.fromOrigamiTensionAndFriction(tension, frictiion));
        spring.addListener(new SimpleSpringListener(){
            @Override
            public void onSpringUpdate(Spring spring) {
                target.setScaleX((float)spring.getCurrentValue());
                target.setScaleY((float)spring.getCurrentValue());
            }
        });
        spring.setEndValue(to);
    }

}
