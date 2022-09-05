package com.niule.a56.calculator.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hokaslibs.R;

import com.hokaslibs.utils.CustomTransform;
import com.hokaslibs.utils.GlideCircleTransform;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.http.Api;
import it.liuting.imagetrans.ScaleType;
import jp.wasabeef.glide.transformations.BlurTransformation;

/**
 * @author: 蜡笔小新
 * @date: 2016-06-14 16:02
 * @GitHub: https://github.com/meikoz
 */
public class Glides {

    public static Glides instance = new Glides();

    public Glides() {
    }

    public static Glides getInstance() {
        return instance;
    }

    // 加载网络图片
    public void load(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.default_error)
                .placeholder(R.drawable.default_error)
                .transform(new CustomTransform(context, ScaleType.FIT_XY))
                .into(imageView);
    }
    // 加载网络图片
    public void load(Context context, Bitmap url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.default_error)
                .placeholder(R.drawable.default_error)
                .transform(new CustomTransform(context, ScaleType.CENTER_CROP))
                .into(imageView);
    }
    // 加载网络图片2
    public void loadWithXY(Context context, String url, ImageView imageView,int width, int height) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.default_error)
                .placeholder(R.drawable.default_error)
                .override(HokasUtils.dp2px(context,width),HokasUtils.dp2px(context,height))
                .into(imageView);
    }

    // 加载网络图片2
    public void load2GC(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(Api.getImageURL() + url)
                .error(R.drawable.default_error)
                .placeholder(R.drawable.default_error)
                .centerCrop()
                .into(imageView);
    }

    // 加载本地图片
    public void load(Context context, int url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.default_error)
                .placeholder(R.drawable.default_error)
                .centerCrop()
                .into(imageView);
    }


    // 加载头像
    public void loadHead(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_moren_touxiang)
                .error(R.mipmap.ic_moren_touxiang)
                .transform(new GlideCircleTransform(context,2f,context.getResources().getColor(R.color.white)))
                .into(imageView);
    }

    // 加载头像
    public void loadHead(Context context, int url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_moren_touxiang)
                .error(R.mipmap.ic_moren_touxiang)
                .transform(new GlideCircleTransform(context,2f,context.getResources().getColor(R.color.white)))
                .into(imageView);
    }

    // 加载圆型网络图片
    public void loadCircle(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.default_error)
                .error(R.drawable.default_error)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }
    // 加载圆型网络图片
    public void loadCircleHead(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.mipmap.ic_moren_touxiang)
                .error(R.mipmap.ic_moren_touxiang)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    // 加载圆型网络图片
    public void loadCircleHead(Context context, int url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .transform(new GlideCircleTransform(context))
                .into(imageView);
    }

    // 加载圆型网络图片 毛玻璃
    public void loadCircleMbl(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.default_error)
                .error(R.drawable.default_error)
                .transform(new BlurTransformation(context, 10),new GlideCircleTransform(context))
                .into(imageView);
    }
    // 加载圆型网络图片
    public void loadCircleMbl(Context context, int url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .placeholder(R.drawable.default_error)
                .error(R.drawable.default_error)
                .transform(new BlurTransformation(context, 10),new GlideCircleTransform(context))
                .into(imageView);
    }
    // 加载圆型图片
    public void loadCircle2(Context context, String url, ImageView imageView) {
        if (url.contains("default"))
            loadCircle2(context, R.drawable.default_error, imageView);
        else Glide.with(context)
                .load(url)
                .error(R.drawable.default_error)
                .transform(new GlideCircleTransform(context))
                .centerCrop()
                .into(imageView);
    }

    // 加载圆型本地图片
    public void loadCircle2(Context context, int url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.default_error)
                .transform(new GlideCircleTransform(context))
                .centerCrop()
                .into(imageView);
    }
    // 加载网络图片
    public void loadEase(Context context, String url, ImageView imageView) {
        Glide.with(context)
                .load(url)
                .error(R.drawable.default_error)
                .placeholder(R.drawable.default_error)
                .transform(new CustomTransform(context, ScaleType.CENTER_CROP))
                .override(160,160)
                .into(imageView);
    }
}
