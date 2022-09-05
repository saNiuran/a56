package cc.shinichi.library.tool.utility.image;

import android.content.ContentValues;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import cc.shinichi.library.tool.utility.text.MD5Util;
import cc.shinichi.library.tool.utility.ui.ToastUtil;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;

import java.io.File;
import java.io.OutputStream;

/**
 * @author 工藤
 * @email gougou@16fan.com
 * com.fan16.cn.util.picture
 * create at 2018/5/4  16:34
 * description:图片下载工具类
 */
public class DownloadPictureUtil {

    public static void downloadPicture(final Context context, final String url) {
        SimpleTarget<File> target = new SimpleTarget<File>() {
            @Override
            public void onLoadStarted(@Nullable Drawable placeholder) {
                ToastUtil.getInstance()._short(context, "开始下载...");
                super.onLoadStarted(placeholder);
            }

            @Override
            public void onLoadFailed(@Nullable Drawable errorDrawable) {
                super.onLoadFailed(errorDrawable);
                ToastUtil.getInstance()._short(context, "保存失败");
            }

            @Override
            public void onResourceReady(@NonNull File resource, @Nullable Transition<? super File> transition) {
                //直接保存到相册
                String name = "";
                try {
                    name = url.substring(url.lastIndexOf("/") + 1);
                    if (name.contains(".")) {
                        name = name.substring(0, name.lastIndexOf("."));
                    }
                    name = MD5Util.md5Encode(name);
                } catch (Exception e) {
                    e.printStackTrace();
                    name = System.currentTimeMillis() + "";
                }
                String mimeType = ImageUtil.getImageTypeWithMime(resource.getAbsolutePath());
                name = name + "." + mimeType;

                //创建ContentValues对象，准备插入数据
                ContentValues contentValues = new ContentValues();
                contentValues.put(MediaStore.Images.Media.DISPLAY_NAME, "跨境计算器_" + name);
                contentValues.put(MediaStore.Images.Media.DESCRIPTION, name);
                if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    contentValues.put(MediaStore.Images.Media.RELATIVE_PATH, "DCIM/跨境计算器");  //相册名称
                }
                contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
                //插入数据，返回所插入数据对应的Uri
                Uri uri = context.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
                //加载应用程序res下的图片bitmap
                BitmapFactory.Options opts = new BitmapFactory.Options();
                opts.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeFile(resource.getAbsolutePath(), opts);
                OutputStream outputStream = null;
                try {
                    //获取刚插入的数据的Uri对应的输出流
                    outputStream = context.getContentResolver().openOutputStream(uri);
                    //将bitmap图片保存到Uri对应的数据节点中
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, outputStream);
                    outputStream.close();
                    ToastUtil.getInstance()._short(context, "成功保存到相册");
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtil.getInstance()._short(context, "保存失败");
                }
            }
        };
        Glide.with(context).downloadOnly().load(url).into(target);
    }
}