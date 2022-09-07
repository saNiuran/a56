package com.niule.a56.calculator.utils.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.app.Service;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.view.WindowManager;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.core.content.FileProvider;
import com.google.gson.Gson;
import com.hokaslibs.utils.HokasUtils;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.UiUtils;
import com.niule.a56.calculator.utils.update.fileload.FileCallback;
import com.niule.a56.calculator.utils.update.fileload.FileResponseBody;
import com.niule.a56.calculator.utils.update.manager.ApkVersion;
import com.niule.a56.calculator.utils.update.updata.HttpClient;
import com.niule.a56.calculator.BuildConfig;
import com.niule.a56.calculator.R;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;

import static android.app.NotificationManager.IMPORTANCE_LOW;

/**
 * 作者： Hokas
 * 时间： 2016/7/15
 * 类别： 下载apk
 */

public class DownLoadService extends Service {
    /**
     * 目标文件存储的文件夹路径
     */
    private String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "update";
    /**
     * 目标文件存储的文件名
     */
    private String destFileName = "";
    private int preProgress = 0;
    private int NOTIFY_ID = 1000;
    private NotificationCompat.Builder builder;
    private Notification.Builder builders;
    private NotificationManager mNotifacationManager;
    private Retrofit.Builder retrofit;

    private ProgressDialog pd;
    private ApkVersion bean;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        bean = new Gson().fromJson(PreferencesUtil.getDataString("version_update"), ApkVersion.class);
        if (bean != null) {
            Log.d("DownLoadService", bean.toString());
            destFileName = bean.getApkUrl();
            loadFile();
        }
        PreferencesUtil.removeDataString("version_update");
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 下载文件
     */
    private void loadFile() {
        initNotification();
        if (Build.VERSION.SDK_INT < 23) {
            initProgress();
        }
//        HokasUtils.logcat(destFileDir);
//        HokasUtils.logcat(destFileName);
        String url = destFileName.substring(0, destFileName.lastIndexOf("/") + 1);
        destFileName = destFileName.substring(destFileName.lastIndexOf("/") + 1);
        HokasUtils.logcat("url  " + url);
        provideHttpService(url).loadFile(destFileName)
                .enqueue(new FileCallback(destFileDir, destFileName) {
                    @Override
                    public void onLoading(long progress, long total) {
                        Log.e("zs", progress + "----" + total);
                        if (0 == total || progress == 0)
                            return;
                        if (Build.VERSION.SDK_INT < 23) {
                            if (progress > 0)
                                upDateProgress(progress * 100 / total);
                        }
                        updateNotification(progress * 100 / total);
                    }

                    @Override
                    public void onSuccess(File file) {
                        Log.e("zs", "请求成功");
                        if (Build.VERSION.SDK_INT < 23) {
                            cancelProgress();
                        }
                        cancelNotification();
                        installApk(file);
                    }

                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                        Log.e("zs", "请求失败");
                        if (Build.VERSION.SDK_INT < 23) {
                            cancelProgress();
                        }
                        cancelNotification();
                        UiUtils.makeText("下载失败");
                    }
                });
    }

    private void initProgress() {
        //实例化进度条对话框（ProgressDialog）
        pd = new ProgressDialog(this);
        pd.setTitle("请稍等");
        //设置对话进度条样式为水平
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        //设置提示信息
        pd.setMessage("正在玩命下载中......");
        //设置对话进度条显示在屏幕顶部（方便截图）

        pd.setMax(100);
        pd.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);//需要添加的语句
        pd.show();//调用show方法显示进度条对话框
    }


    private void upDateProgress(long progress) {
        handler.sendEmptyMessage((int) progress);
    }

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (pd != null)
                pd.setProgress(msg.what);
        }
    };

    private void cancelProgress() {
        if (pd != null)
            pd.dismiss();
    }


    /**
     * 更新通知
     */
    private void updateNotification(long progress) {
        int currProgress = (int) progress;
        if (preProgress < currProgress) {
            if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
                if (builders != null) {
                    builders.setContentText(progress + "%");
                    builders.setProgress(100, (int) progress, false);
                    mNotifacationManager.notify(NOTIFY_ID, builders.build());
                }
            } else {
                if (builder != null) {
                    builder.setContentText(progress + "%");
                    builder.setProgress(100, (int) progress, false);
                    mNotifacationManager.notify(NOTIFY_ID, builder.build());
                }
            }

        }
        preProgress = (int) progress;
    }

    /**
     * 安装文件
     *
     * @param file
     */
    private void installApk(File file) {
        Intent install = new Intent(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            install.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri uri = FileProvider.getUriForFile(this, BuildConfig.APPLICATION_ID + ".provider", file);
            install.setDataAndType(uri, "application/vnd.android.package-archive");
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);  //add by Rich
        } else {
            install.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            install.setDataAndType(Uri.fromFile(file), "application/vnd.android.package-archive");
        }
        startActivity(install);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }


    /**
     * 初始化Notification通知
     */
    private void initNotification() {
        mNotifacationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.N_MR1) {
            builders = new Notification.Builder(this, "down")
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("0%")
                    .setOnlyAlertOnce(true)
                    .setContentTitle(getString(R.string.app_name) + "下载")
                    .setProgress(100, 0, false);
            NotificationChannel channel = new NotificationChannel("down", "下载通知", IMPORTANCE_LOW);
            mNotifacationManager.createNotificationChannel(channel);
            mNotifacationManager.notify(NOTIFY_ID, builders.build());
        } else {
            builder = new NotificationCompat.Builder(this)
                    .setSmallIcon(R.mipmap.ic_launcher)
                    .setContentText("0%")
                    .setOnlyAlertOnce(true)
                    .setDefaults(Notification.FLAG_ONLY_ALERT_ONCE)
                    .setContentTitle(getString(R.string.app_name) + "下载")
                    .setProgress(100, 0, false);
            mNotifacationManager.notify(NOTIFY_ID, builder.build());
        }
    }

    /**
     * 初始化OkHttpClient
     */
    private OkHttpClient initOkHttpClient() {

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(100000, TimeUnit.SECONDS);
        builder.networkInterceptors().add(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Response originalResponse = chain.proceed(chain.request());
                return originalResponse.newBuilder().body(new FileResponseBody(originalResponse))
                        .build();
            }
        });
        return builder.build();
    }

    public interface IFileLoad {
        @GET("{fileName}")
        Call<ResponseBody> loadFile(@Path("fileName") String url);
    }

    /**
     * 取消通知
     */
    public void cancelNotification() {
        mNotifacationManager.cancel(NOTIFY_ID);
    }


    public static IFileLoad provideHttpService(String url) {
        return provideService(IFileLoad.class, url);
    }

    private static Map<Class, Object> m_service = new HashMap();

    private static <T> T provideService(Class cls, String url) {
        Object serv = m_service.get(cls);
        if (serv == null) {
            synchronized (cls) {
                serv = m_service.get(cls);
                if (serv == null) {
                    serv = HttpClient.getIns(url).createService(cls);
                    m_service.put(cls, serv);
                }
            }
        }
        return (T) serv;
    }
}
