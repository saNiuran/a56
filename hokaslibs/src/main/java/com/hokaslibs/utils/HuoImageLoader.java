package com.hokaslibs.utils;

import android.content.Context;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hokaslibs.R;
import com.youth.banner.loader.ImageLoader;

import it.liuting.imagetrans.ScaleType;

/**
 * 作者： Hokas
 * 时间： 2016/10/28
 * 类别：
 */

public class HuoImageLoader extends ImageLoader {
    @Override
    public void displayImage(Context context, Object path, final ImageView imageView) {

        //通过ImageOptions.Builder().set方法设置图片的属性
//        ImageOptions  imageOptions= new ImageOptions.Builder().setFadeIn(true).build(); //淡入效果
//        x.image().bind(imageView, path.toString(), imageOptions);
//        HokasUtils.logcat("HuoImageLoader: " + path.toString());
        if (context != null)
            Glide.with(context)
                    .load(path.toString())
                    .error(R.drawable.default_error)
                    .placeholder(R.drawable.default_error)
                    .transform(new CustomTransform(context, ScaleType.CENTER_CROP))
                    .into(imageView);
        else
            imageView.setImageResource(R.drawable.default_error);
//        Picasso.with(context).load(path.toString()).into(imageView);
//        x.image().loadDrawable(path.toString(), imageOptions, new Callback.CommonCallback<Drawable>() {
//            @Override
//            public void onSuccess(Drawable result) {
//                imageView.setImageDrawable(result);
//            }
//            @Override
//            public void onError(Throwable ex, boolean isOnCallback) {
//                HokasUtils.logcat("HuoImageLoader: " + ex.toString());
//            }
//            @Override
//            public void onCancelled(CancelledException cex) {
//            }
//            @Override
//            public void onFinished() {
//            }
//        });
    }

}
