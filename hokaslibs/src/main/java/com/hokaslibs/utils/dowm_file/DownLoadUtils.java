//package com.hokaslibs.utils.dowm_file;
//
//import android.net.Uri;
//import android.os.Environment;
//import android.util.Log;
//
//import com.hokaslibs.utils.HokasUtils;
//import com.niule.a56.calculator.utils.UiUtils;
//import com.hokaslibs.utils.update.fileload.FileCallback;
//import com.hokaslibs.utils.update.updata.HttpClient;
//
//import java.io.File;
//import java.util.HashMap;
//import java.util.Map;
//
//import okhttp3.ResponseBody;
//import retrofit2.Call;
//import retrofit2.http.GET;
//import retrofit2.http.Path;
//
//public class DownLoadUtils {
//
//    /**
//     * 下载文件
//     */
//    public static void loadFile(String url) {
//        /**
//         * 目标文件存储的文件夹路径
//         */
//        String destFileDir = Environment.getExternalStorageDirectory().getAbsolutePath()
//                + File.separator + "跨境计算器";
//        /**
//         * 目标文件存储的文件名
//         */
//        String destFileName = url.substring(url.lastIndexOf("/") + 1);
//        HokasUtils.logcat("url  " + url);
//        HokasUtils.logcat("destFileName  " + destFileName);
//        HokasUtils.logcat("destFileDir  " + destFileDir);
//        Uri uri = Uri.parse(url);
//
//        File file = new File(destFileDir + "/" + destFileName + ".mp4");
//        if (file.exists()) {
//            UiUtils.makeText("保存成功");
//            return;
//        }
//
//        HokasUtils.logcat("getHost  " + uri.getHost());
//        provideHttpService("http://" + uri.getHost() + "/").loadFile(destFileName)
//                .enqueue(new FileCallback(destFileDir, destFileName) {
//                    @Override
//                    public void onLoading(long progress, long total) {
//                        Log.e("zs", progress + "----" + total);
//                    }
//
//                    @Override
//                    public void onSuccess(File file) {
//                        Log.e("zs", "请求成功");
//                        UiUtils.makeText("成功保存到 ".concat(file.getAbsolutePath()));
//                    }
//
//                    @Override
//                    public void onFailure(Call<ResponseBody> call, Throwable t) {
//                        Log.e("zs", "请求失败");
//                        UiUtils.makeText("保存失败");
//                    }
//                });
//    }
//
//    public static IFileLoad provideHttpService(String url) {
//        return provideService(IFileLoad.class, url);
//    }
//
//    public interface IFileLoad {
//        @GET("chatfiles/{fileName}")
//        Call<ResponseBody> loadFile(@Path("fileName") String url);
//    }
//
//    private static Map<Class, Object> m_service = new HashMap();
//
//    private static <T> T provideService(Class cls, String url) {
//        Object serv = m_service.get(cls);
//        if (serv == null) {
//            synchronized (cls) {
//                serv = m_service.get(cls);
//                if (serv == null) {
//                    serv = HttpClient.getIns(url).createService(cls);
//                    m_service.put(cls, serv);
//                }
//            }
//        }
//        return (T) serv;
//    }
//}
