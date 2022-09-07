package com.niule.a56.calculator.base;

import androidx.annotation.NonNull;
import com.hokaslibs.utils.HokasUtils;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.DisplayMetrics;

import androidx.multidex.MultiDex;
import com.hokaslibs.BuildConfig;
import com.niule.a56.calculator.http.Api;
import com.niule.a56.calculator.http.NetError;
import com.niule.a56.calculator.http.NetProvider;
import com.niule.a56.calculator.http.RequestHandler;
import com.niule.a56.calculator.http.XXApi;
import com.hokaslibs.utils.DataHelper;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.update.utils.DeviceUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.tencent.tauth.Tencent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMShareAPI;

import java.io.File;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import cn.bingoogolapple.swipebacklayout.BGASwipeBackHelper;
import okhttp3.CookieJar;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class BaseApplication extends XApplication implements Thread.UncaughtExceptionHandler {
//    public static final String TAG = "BaseApplication";

    private static BaseApplication instance;

    public static ExecutorService cThreadPool;

    private static final String HASH_ALGORITHM = "MD5";
    private static final int RADIX = 10 + 26; // 10 digits + 26 letters
    private static String IMAGE_CACHE_PATH;
    static DisplayMetrics mDisplayMetrics;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        initThrowableHandler();

        cThreadPool = Executors.newFixedThreadPool(5);

        mDisplayMetrics = getResources().getDisplayMetrics();

        UMConfigure.preInit(this,"59b62dd0ae1bf84243000069","Channel ID");

        if (PreferencesUtil.getDataBoolean("userAgreement")) {

            if (getExternalCacheDir() != null)
                IMAGE_CACHE_PATH = getExternalCacheDir().getPath();

            PreferencesUtil.setDataString("uminit", "1");
            UMConfigure.init(this, "59b62dd0ae1bf84243000069"
                    , "umeng", UMConfigure.DEVICE_TYPE_PHONE, "");
            UMConfigure.submitPolicyGrantResult(this, true);
            UMShareAPI.get(this);
            PlatformConfig.setWeixin("wx748a108c33b58742", "fed9a520043bf76e660bf19fb20ef796");
            PlatformConfig.setQQZone("1106537132", "9fkFBghMQ3Ku56ex");

            Tencent.setIsPermissionGranted(true);

//            JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志00000000000000000000000
//            JPushInterface.init(this);            // 初始化 JPush
//            JAnalyticsInterface.init(this);         //初始化统计

        }

        BGASwipeBackHelper.init(this, null);

        XXApi.registerProvider(new NetProvider() {
            @Override
            public String configBaseUrl() {
                return Api.getURL();
            }

            @Override
            public Interceptor[] configInterceptors() {
                return new Interceptor[0];
            }

            @Override
            public void configHttps(OkHttpClient.Builder builder) {

            }

            @Override
            public CookieJar configCookie() {
                return null;
            }

            @Override
            public RequestHandler configHandler() {
                return requestHandler;
            }

            @Override
            public long configConnectTimeoutMills() {
                return 0;
            }

            @Override
            public long configReadTimeoutMills() {
                return 0;
            }

            @Override
            public boolean configLogEnable() {
                //true 允许log  false 不允许打印Log
//                return Api.getURL().contains("192.168.105.235");
                return BuildConfig.LOG_DEBUG;
            }

            @Override
            public boolean handleError(NetError error) {
                return false;
            }

            @Override
            public File getFile() {
                return DataHelper.getCacheFile(BaseApplication.this);
            }
        });
    }

    private void initThrowableHandler() {
        Thread.setDefaultUncaughtExceptionHandler(this);
    }

    public static BaseApplication getInstance() {
        return instance;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            return new ClassicsHeader(context).setSpinnerStyle(SpinnerStyle.Translate);//指定为经典Header，默认是 贝塞尔雷达Header
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典Footer，默认是 BallPulseFooter
            return new ClassicsFooter(context).setSpinnerStyle(SpinnerStyle.Translate);
        });
    }

    @Override
    public void uncaughtException(@NonNull Thread t, @NonNull Throwable e) {
        HokasUtils.logcat("demoApp"+e.getMessage());

    }

    private final RequestHandler requestHandler = new RequestHandler() {
        @Override
        public Request onBeforeRequest(Request request, Interceptor.Chain chain) {
            return request.newBuilder()
                    .header("appVersion", String.valueOf(DeviceUtils.getVersionCode(BaseApplication.getInstance())))
                    .header("deviceId", com.blankj.utilcode.util.DeviceUtils.getAndroidID())
                    .header("lord","3")  //表示安卓端
                    .build();
        }

        @Override
        public Response onAfterRequest(Response response, String result, Interceptor.Chain chain) {
            return response;
        }
    };

    public static String getImageCachePath() {
        return IMAGE_CACHE_PATH;
    }

    public static int getMaxSizeOfBitMap(String path) {
        BitmapFactory.Options op = new BitmapFactory.Options();
        op.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, op);
        return Math.max(op.outWidth, op.outHeight);
    }

    public static String generate(String imageUri) {
        byte[] md5 = getMD5(imageUri.getBytes());
        BigInteger bi = new BigInteger(md5).abs();
        if (imageUri.endsWith(".gif") || imageUri.endsWith(".GIF")) {
            return bi.toString(RADIX) + ".itgif";
        }
        return bi.toString(RADIX) + ".it";
    }

    private static byte[] getMD5(byte[] data) {
        byte[] hash = null;
        try {
            MessageDigest digest = MessageDigest.getInstance(HASH_ALGORITHM);
            digest.update(data);
            hash = digest.digest();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return hash;
    }


    public static String getCachedPath(String url) {
        String key = generate(url);
        return getImageCachePath() + "/" + key;
    }

    public static int dpToPx(int dp) {
        return (int) (mDisplayMetrics.density * dp);
    }

    public static int getScreenWidth() {
        return mDisplayMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        return mDisplayMetrics.heightPixels;
    }

    public static void deleteFiles(String path) {
        deleteFiles(new File(path));
    }

    public static void deleteFiles(File file) {
        if (!file.exists()) {
            return;
        }
        if (file.isFile()) {
            file.delete();
            return;
        }
        //文件夹递归删除
        File[] files = file.listFiles();
        if (null == files) {
            return;
        }
        for (File subFile : files) {
            deleteFiles(subFile);
        }
        file.delete();
    }
}
