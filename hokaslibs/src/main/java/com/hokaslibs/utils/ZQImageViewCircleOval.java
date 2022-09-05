package com.hokaslibs.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * 实现圆形、圆角，椭圆等自定义图片View。
 *
 * @author zq
 */
@SuppressLint("AppCompatCustomView")
public class ZQImageViewCircleOval extends ImageView {

    private Paint mPaint;

    private int mWidth;

    private int mRadius;//圆半径

    private Matrix mMatrix;


    public ZQImageViewCircleOval(Context context) {
        this(context, null);
        // TODOAuto-generated constructor stub
    }

    public ZQImageViewCircleOval(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        // TODOAuto-generated constructor stub
    }

    public ZQImageViewCircleOval(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView();
    }

    private void initView() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mMatrix = new Matrix();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODOAuto-generated method stub
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        // 如果是绘制圆形，则强制宽高大小一致
        mWidth = Math.min(getMeasuredWidth(), getMeasuredHeight());
        mRadius = mWidth / 2;
        setMeasuredDimension(mWidth, mWidth);

    }

    @Override
    protected void onDraw(Canvas canvas) {

        if (null == getDrawable()) {
            return;
        }
        setBitmapShader();
        canvas.drawCircle(mRadius, mRadius, mRadius, mPaint);
    }

    /**
     * 设置BitmapShader
     */
    private void setBitmapShader() {
        Drawable drawable = getDrawable();
        if (null == drawable) {
            return;
        }
        Bitmap bitmap = drawableToBitmap(drawable);
        // 将bitmap作为着色器来创建一个BitmapShader
        //图形渲染
        BitmapShader mBitmapShader = new BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        float scale = 1.0f;
        // 拿到bitmap宽或高的小值
        int bSize = Math.min(bitmap.getWidth(), bitmap.getHeight());
        scale = mWidth * 1.0f / bSize;

        // shader的变换矩阵，我们这里主要用于放大或者缩小
        mMatrix.setScale(scale, scale);
        // 设置变换矩阵
        mBitmapShader.setLocalMatrix(mMatrix);
        mPaint.setShader(mBitmapShader);

    }

    /**
     * drawable转bitmap
     *
     * @return
     * @paramdrawable
     */
    private Bitmap drawableToBitmap(Drawable drawable) {
        if (drawable instanceof BitmapDrawable) {
            BitmapDrawable bitmapDrawable = (BitmapDrawable) drawable;
            return bitmapDrawable.getBitmap();
        }
        int w = drawable.getIntrinsicWidth();
        int h = drawable.getIntrinsicHeight();
        Bitmap bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, w, h);
        drawable.draw(canvas);
        return bitmap;
    }


}