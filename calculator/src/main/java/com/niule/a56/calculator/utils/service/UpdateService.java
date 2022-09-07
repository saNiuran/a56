package com.niule.a56.calculator.utils.service;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.captain_miao.grantap.CheckPermission;
import com.example.captain_miao.grantap.listeners.PermissionListener;
import com.example.captain_miao.grantap.utils.PermissionUtils;
import com.flyco.dialog.listener.OnOperItemClickL;
import com.flyco.dialog.widget.ActionSheetDialog;
import com.google.gson.Gson;
import com.niule.a56.calculator.utils.PreferencesUtil;
import com.niule.a56.calculator.utils.UiUtils;
import com.niule.a56.calculator.utils.update.manager.ApkVersion;
import com.niule.a56.calculator.utils.update.utils.DeviceUtils;

import java.io.File;


/**
 * 作者： Hokas
 * 时间： 2016/7/15
 * 类别：
 */

public class UpdateService {

    private Context mContext;
    private Activity activity;

    public UpdateService(Activity mContext) {
        activity = mContext;
        this.mContext = mContext;
    }

    /**
     * 检测软件更新
     */
    public void checkUpdate(ApkVersion apkVersion) {
        int mVersionCode = DeviceUtils.getVersionCode(mContext);
        int nVersionCode = Integer.parseInt(apkVersion.getVersionCode());
        if (mVersionCode < nVersionCode) {
            // 版本的更新信息
            String versionInfo = "检测到本程序有新版本" +
                    "\n当前版本:V " + DeviceUtils.getVersionName(mContext) +
                    "\n新的版本:" + apkVersion.getVersionName() +
                    "\n更新内容：" + apkVersion.getContent() +
                    "\n建议您更新！";
            // 显示提示对话
            showNoticeDialog(versionInfo, apkVersion);
        }
    }

    private void showNoticeDialog(String info, final ApkVersion apkVersion) {
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        builder.setTitle("更新提示");
        builder.setMessage(info);
        builder.setPositiveButton("更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//                down(appVersion);
//                ActionSheetDialog(appVersion);
                if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                    showRequestPermissionAlertInstall(apkVersion);
                } else
                    UiUtils.makeText("SD存储卡不存在，请插入SD存储卡");
                dialog.dismiss();
            }
        });
        builder.setNegativeButton("暂不更新", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                if (apkVersion.getUpdateForce() == 1)    //强制退出
                    UpdateService.this.activity.finish();
            }
        });
        if (apkVersion.getUpdateForce() == 1) {
            builder.setCancelable(false);
        }
        Dialog dialog = builder.create();
        dialog.show();
    }

    //弹出选择下载方式
    private void ActionSheetDialog(final ApkVersion apkVersion) {
        String[] stringItems = new String[]{"直接下载", "浏览器下载"};
        final ActionSheetDialog dialog = new ActionSheetDialog(mContext, stringItems, null);
        dialog.isTitleShow(false).show();
        dialog.setOnOperItemClickL(new OnOperItemClickL() {
            @Override
            public void onOperItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (0 == position) {
                    if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
                        showRequestPermissionAlertInstall(apkVersion);
                    } else
                        UiUtils.makeText("SD存储卡不存在，请插入SD存储卡");
                }
                if (1 == position) {
                    Intent intent = new Intent();
                    intent.setAction("android.intent.action.VIEW");
                    Uri content_url = Uri.parse(apkVersion.getApkUrl());
                    intent.setData(content_url);
                    mContext.startActivity(intent);
                }
                dialog.dismiss();
            }
        });
    }

    /**
     * 删除安装apk
     */

    public void deleteFile() {
        File ff = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "update");
        deleteDirWihtFile(ff);
        Log.d("UpdateManager", "删除文件夹");
//        mContext.startService(new Intent(mContext, DownLoadService.class).putExtra("bean", appVersion));
    }

    public static void deleteDirWihtFile(File dir) {
        if (dir == null || !dir.exists() || !dir.isDirectory()) {
            dir.mkdirs();
            return;
        }
        if (dir.listFiles() != null && dir.listFiles().length > 0) {
            for (File file : dir.listFiles()) {
                if (file != null)
                    if (file.isFile())
                        file.delete(); // 删除所有文件
                    else if (file.isDirectory())
                        deleteDirWihtFile(file); // 递规的方式删除文件夹
            }
            Log.d("UpdateManager", "删除文件夹成功");
//            dir.delete();// 删除目录本身
        }
    }

    public void down(ApkVersion apkVersion) {
        UiUtils.makeText("正在下载安装包，下载完成后直接跳到安装界面");
        Intent intent = new Intent(mContext, DownLoadService.class);
//        Bundle bundle = new Bundle();
//        bundle.putString("bean", new Gson().toJson(appVersion));
//        intent.putExtra("bundle", bundle);
        PreferencesUtil.setDataString("version_update", new Gson().toJson(apkVersion));
        mContext.startService(intent);
    }

    /**
     * 下载权限
     */
    public void showRequestPermissionAlert(final ApkVersion apkVersion) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            boolean isGranted = PermissionUtils.hasSelfPermissions(mContext,
                    Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            if (isGranted) {
                down(apkVersion);
                return;
            }
            CheckPermission.from(mContext).setPermissions
                    (Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE).setPermissionListener
                    (new PermissionListener() {
                        @Override
                        public void permissionGranted() {
                            down(apkVersion);
                        }

                        @Override
                        public void permissionDenied() {
                            openSettingActivity(mContext, "该功能需要开启权限，点击“设置”-“权限管理”打开所需权限");
                        }
                    }).check();
        } else {
            down(apkVersion);
        }
    }

    private void openSettingActivity(final Context activity, final String message) {

        showMessageOKCancel(activity, message, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                intent.setData(uri);
                if (message.contains("8.0版本以上需开启安装未知应用权限")) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
                        intent.setAction(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                    else
                        intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    UpdateService.this.activity.startActivityForResult(intent, 10086);
                } else {
                    intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                    activity.startActivity(intent);
                }
            }
        });
    }

    private void showMessageOKCancel(final Context context, final String message, DialogInterface.OnClickListener okListener) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setPositiveButton("设置", okListener)
                .setNegativeButton("关闭", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if (message.contains("8.0版本以上需开启安装未知应用权限"))
                            UpdateService.this.activity.finish();
                    }
                })
                .create()
                .show();

    }

    /**
     * 安装权限
     */
    public void showRequestPermissionAlertInstall(final ApkVersion apkVersion) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            boolean isGranted = mContext.getPackageManager().canRequestPackageInstalls();
            if (isGranted) {
                showRequestPermissionAlert(apkVersion);
                return;
            }
            CheckPermission.from(mContext).setPermissions(Manifest.permission.REQUEST_INSTALL_PACKAGES).setPermissionListener
                    (new PermissionListener() {
                        @Override
                        public void permissionGranted() {
                            showRequestPermissionAlert(apkVersion);
                        }

                        @Override
                        public void permissionDenied() {
                            openSettingActivity(mContext, "Android 8.0版本以上需开启安装未知应用权限，" +
                                    "点击“设置”-“权限管理”打开所需权限(系统各异)");
                        }
                    }).check();
        } else
            showRequestPermissionAlert(apkVersion);
    }

}