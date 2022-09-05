package com.hokaslibs.utils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;
import androidx.annotation.Nullable;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


/**
 * 图片处理工具类
 * 压缩图片，把图片变成Base64编码toString。
 *
 * @author 洪开盛
 * @time 2015年04月28日 10:14:28
 */
public class BitmapUtils {

    private static Context context;

    public BitmapUtils() {
    }

    @SuppressWarnings("static-access")
    public BitmapUtils(Context context) {
        this();
        this.context = context;
    }

    public static String getBitMapWidthAndHeight(String filePath) {
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options); // 此时返回的bitmap为null
//        Bitmap bm = BitmapFactory.decodeStream(new FileInputStream(filePath));
//        if (bm != null && bm.getWidth() >= 0 && bm.getHeight() >= 0)
        int[] option = new int[]{options.outWidth, options.outHeight};
        if (option[0] >= 0 && option[1] >= 0)
            return option[0] + "_" + option[1];
        else
            return "0_0";
    }


    /**
     * 把bitmap转换成String
     *
     * @param filePath
     * @return
     */
    @Nullable
    public static String bitmapToString(String filePath,Context context) {
        File file = new File(filePath);

        int size = 0;
        String base64 = null;
        try {
            File fileNew = FileUtils.createImageFile(context);
            FileOutputStream out = new FileOutputStream(fileNew);
            FileInputStream fileInputStream = new FileInputStream(file);
            size = fileInputStream.available();
            Log.i("BitmapUtils", "图片的大小" + (size / 1024) + "KB 宽度为");

            Bitmap bm;
            if ((size / 1024) < 150) {
                bm = BitmapFactory.decodeStream(fileInputStream);
            } else {
                bm = getSmallBitmap2(filePath);
                if (bm == null) {
                    bm = BitmapFactory.decodeStream(fileInputStream);
                }
                if (bm == null)
                    return null;
            }
//            ByteArrayOutputStream bos = new ByteArrayOutputStream();
//            bm.compress(Bitmap.CompressFormat.JPEG, 80, bos);
//            byte[] b = bos.toByteArray();
            bm.compress(Bitmap.CompressFormat.JPEG, 80, out);
//            byte[] b = out.toByteArray();
//            Log.i("BitmapUtils", "压缩后图片的大小" + (b.length / 1024) + "KB 宽度为"
//                    + bm.getWidth() + "高度为" + bm.getHeight());
//            base64 = Base64.encodeToString(b, Base64.DEFAULT);
//            Log.d("BitmapUtils", base64);
            return fileNew.getPath();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file.getPath();

    }

    /**
     * 根据路径获得图片并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap2(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
//        Bitmap bitmap = BitmapFactory.decodeFile(filePath);
        BitmapFactory.decodeFile(filePath, options);
        int[] option = new int[]{options.outWidth, options.outHeight};
        int width = option[0];
        int height = option[1];
        Log.d("BitmapUtils", "bitmap.filePath:" + filePath);
        Log.d("BitmapUtils", "bitmap.getWidth():" + width);
        Log.d("BitmapUtils", "bitmap.getHeight():" + height);
        float size = 1;
        int scaleLength = 0;
        if (width > height) {
            scaleLength = width;
        } else
            scaleLength = height;
        if (scaleLength > 3000) {
            Log.d("BitmapUtils", "width > 3000");
            while (scaleLength * size > 1200) {
                size = (float) (size - 0.01);
                Log.d("BitmapUtils", "size:" + size);
                Log.d("BitmapUtils", "(int) (bitmap.getWidth() * size):" + (int) (width * size));
                Log.d("BitmapUtils", "(int) (bitmap.getHeight()*size):" + (int) (height * size));
            }
        } else if (scaleLength > 2000) {
            Log.d("BitmapUtils", "width > 2000");
            while (scaleLength * size > 1000) {
                size = (float) (size - 0.01);
                Log.d("BitmapUtils", "size:" + size);
                Log.d("BitmapUtils", "(int) (bitmap.getWidth() * size):" + (int) (width * size));
                Log.d("BitmapUtils", "(int) (bitmap.getHeight()*size):" + (int) (height * size));
            }
        } else if (scaleLength > 1000) {
            Log.d("BitmapUtils", "width > 1000");
            while (scaleLength * size > 800) {
                size = (float) (size - 0.01);
                Log.d("BitmapUtils", "size:" + size);
                Log.d("BitmapUtils", "(int) (bitmap.getWidth() * size):" + (int) (width * size));
                Log.d("BitmapUtils", "(int) (bitmap.getHeight()*size):" + (int) (height * size));
            }
        } else {
            Log.d("BitmapUtils", "width <= 1000");
            while (scaleLength * size > 600) {
                size = (float) (size - 0.01);
                Log.d("BitmapUtils", "size:" + size);
                Log.d("BitmapUtils", "(int) (bitmap.getWidth() * size):" + (int) (width * size));
                Log.d("BitmapUtils", "(int) (bitmap.getHeight()*size):" + (int) (height * size));
            }
        }
        width = (int) (width * size);
        height = (int) (height * size);
        Log.d("BitmapUtils", "size:" + size);
        Log.d("BitmapUtils", "width :" + width);
        Log.d("BitmapUtils", "height :" + height);
        //计算样本的大小
        options.inSampleSize = calculateInSampleSize(options, width, height);
        // Decode bitmap with inSampleSize set
        //解码位图在样本集
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth, int reqHeight) {
        // Raw height and width of image
        //原始图像的高度和宽度
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            // Calculate ratios of height and width to requested height and
            // width
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);

            // Choose the smallest ratio as inSampleSize value, this will
            // guarantee
            // a final image with both dimensions larger than or equal to the
            // requested height and width.
//			选择最小的比例作为样本值,这将保证一个最终的图像尺寸大于等于请求的高度和宽度。
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }

        return inSampleSize;
    }


    /**
     * 根据路径获得图片并压缩返回bitmap用于显示
     *
     * @return
     */
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate in Sample Size
        //计算样本的大小
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode bitmap with inSampleSize set
        //解码位图在样本集
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    /**
     * 根据路径删除图片
     *
     * @param path
     */
    public static void deleteTempFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            file.delete();
        }
    }

    /**
     * 添加到图库
     */
    public static void galleryAddPic(Context context, String path) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File f = new File(path);
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);
        context.sendBroadcast(mediaScanIntent);
    }

    /**
     * 获取保存图片的目录
     *
     * @return
     */
    public static File getAlbumDir() {

        File dir = null;
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            dir = new File(Environment.getExternalStorageDirectory(), getAlbumName());
        } else {
            dir = new File(context.getFilesDir().getPath(), getAlbumName());
        }
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public static String saveBitmap(Bitmap bitmap, String filePath, Context context) {
        File file = new File(filePath);
        if (file.exists()) {
            file.delete();
        }
        File mCaptureFile = new File(filePath);
        BufferedOutputStream out;
        try {
//            mCaptureFile = FileUtils.createImageFile(context);
            out = new BufferedOutputStream(new FileOutputStream(mCaptureFile));
            if (bitmap != null) {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 60, out);
                out.flush();
                out.close();
            } else
                return filePath;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mCaptureFile.getAbsolutePath();
    }

    /**
     * 获取保存 隐患检查的图片文件夹名称
     *
     * @return
     */
    public static String getAlbumName() {
        return "yjg/images/";
    }
}
