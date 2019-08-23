package cn.ommiao.autotask.ui.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.palette.graphics.Palette;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

import java.util.List;

import cn.ommiao.autotask.R;
import cn.ommiao.base.entity.order.Task;

public class TaskListAdapter extends BaseQuickAdapter<Task, BaseViewHolder> {

    public TaskListAdapter(int layoutResId, @Nullable List<Task> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Task task) {
        ImageView iv = baseViewHolder.getView(R.id.iv);
        Glide.with(iv).load(task.coverPath).into(iv);
        Bitmap bitmap = BitmapFactory.decodeFile(task.coverPath);
        if(bitmap != null){
            ImageView ivMask = baseViewHolder.getView(R.id.iv_mask);
            Palette.from(bitmap).generate(palette -> {
                if(palette != null){
                    Palette.Swatch lightVibrantSwatch = palette.getVibrantSwatch();
                    if(lightVibrantSwatch != null){
                        //谷歌推荐的：图片的整体的颜色rgb的混合值---主色调
                        int rgb = lightVibrantSwatch.getRgb();
                        ivMask.setColorFilter(rgb);
                        //谷歌推荐：图片中间的文字颜色
                        int bodyTextColor = lightVibrantSwatch.getBodyTextColor();
                        //谷歌推荐：作为标题的颜色（有一定的和图片的对比度的颜色值）
                        int titleTextColor = lightVibrantSwatch.getTitleTextColor();
                        baseViewHolder.setTextColor(R.id.tv_task_name, bodyTextColor);
                        baseViewHolder.setTextColor(R.id.tv_task_desc, titleTextColor);
                    }
                }

            });
        }
        baseViewHolder.setText(R.id.tv_task_name, task.taskName);
        baseViewHolder.setText(R.id.tv_task_desc, task.taskDescription);
    }
}
