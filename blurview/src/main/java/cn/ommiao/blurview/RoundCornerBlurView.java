package cn.ommiao.blurview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;


public class RoundCornerBlurView extends RealtimeBlurView {

    private RectF mRectF;
    private Paint mPaint;
    private Path mPath = new Path();

    public RoundCornerBlurView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mRectF = new RectF();
        mPaint = new Paint();
    }

    @Override
    protected void drawBlurredBitmap(Canvas canvas, Bitmap blurBitmap, int overlayColor, float roundCornerRadius) {

        mRectF.right = getMeasuredWidth();
        mRectF.bottom = getMeasuredHeight();
        mPaint.reset();
        mPaint.setAntiAlias(true);
        BitmapShader shader = new BitmapShader(blurBitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        Matrix matrix = new Matrix();
        matrix.postScale(mRectF.width() / blurBitmap.getWidth(), mRectF.height() / blurBitmap.getHeight());
        shader.setLocalMatrix(matrix);
        mPaint.setShader(shader);

        mPath.addArc(0, 0, mRectF.bottom, mRectF.bottom, 90, 180);
        mPath.lineTo(mRectF.right, 0);
        mPath.lineTo(mRectF.right, mRectF.bottom);
        mPath.close();

        //canvas.drawRoundRect(mRectF, roundCornerRadius, roundCornerRadius, mPaint);
        canvas.drawPath(mPath, mPaint);

        mPaint.reset();
        mPaint.setAntiAlias(true);
        mPaint.setColor(overlayColor);
        //canvas.drawRoundRect(mRectF, roundCornerRadius, roundCornerRadius, mPaint);
        canvas.drawPath(mPath, mPaint);
    }
}
