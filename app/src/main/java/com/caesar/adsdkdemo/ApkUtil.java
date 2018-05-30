package com.caesar.adsdkdemo;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;

import java.io.File;
import java.util.List;
import java.util.Map;

/**
 * author    : yangshaopeng
 * email     : ysp@btomorrow.cn
 * date      : 2017/08/01  21:08
 * desc      : <p> 关于apk的工具类 </p>
 * package   : com.hhmt.market.utils
 * project   : HHMarket
 */

public class ApkUtil {

    private static Map<String, Boolean> isInstalledMap = null;
    public static String currentInstallApk = "";

    /**
     * 下载成功之后安装apk。
     *
     * @param apkPath App对应的路径。
     */
    public static void installApk(String apkPath, Context context) {
        //防止一个页面被启动多次，因为好多页面都在监听下载状态。
        if (currentInstallApk.length() > 0) {
            return;
        }

        boolean haveInstallPermission;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                Intent intent = new Intent(Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES);
                ((Activity)context).startActivityForResult(intent, 5);
                return;
            }
        }

        currentInstallApk = apkPath;
        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        File apkFile = new File(apkPath);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(context, "com.different.guys.fileprovider", apkFile);
            intent.setDataAndType(contentUri, "application/vnd.android.package-archive");
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        context.startActivity(intent);
    }

    /**
     * 判断某个apk是否已经安装
     *
     * @param context     上下文
     * @param packageName 包名
     * @return
     */
    public static boolean isInstalled(Context context, String packageName) {
        PackageManager pm = context.getPackageManager();
        boolean installed = false;
        try {
            synchronized (ApkUtil.class) {
                pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
            }
            installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            installed = false;
        }
        return installed;
    }

    /**
     * 打开App。
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void openApp(Context context, String packageName) {
        Intent intent = context.getApplicationContext().getPackageManager().getLaunchIntentForPackage(packageName);
        context.startActivity(intent);
    }

    /**
     * 判断当前安装的应用是否需要更新
     *
     * @param oldVersion 当前安装的版本号
     * @param newVersion 后台获取的版本号
     * @return true, 需要更新
     */
    public static boolean isNeedUpdate(String oldVersion, String newVersion) {
        if (oldVersion.equals(newVersion)) {
            return false;
        }

        String[] version1Array = oldVersion.split("\\.");
        String[] version2Array = newVersion.split("\\.");

        int index = 0;
        int minLen = Math.min(version1Array.length, version2Array.length);
        int diff = 0;

        while (index < minLen && (diff = Integer.parseInt(version1Array[index]) - Integer.parseInt(version2Array[index])) == 0) {
            index++;
        }

        if (diff == 0) {
            for (int i = index; i < version1Array.length; i++) {
                if (Integer.parseInt(version1Array[i]) > 0) {
                    return false;
                }
            }

            for (int i = index; i < version2Array.length; i++) {
                if (Integer.parseInt(version2Array[i]) > 0) {
                    return true;
                }
            }

            return false;
        } else {
            return diff <= 0;
        }
    }

    /**
     * 获得apk版本号
     *
     * @param context     上下文
     * @param packageName 包名
     * @return
     */
    public static String getApkVersionName(Context context, String packageName) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        try {
            synchronized (ApkUtil.class) {
                return pm.getPackageInfo(packageName, PackageManager.GET_ACTIVITIES).versionName;
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return "0.0.0";
    }

    /**
     * 获取已经安装的应用。
     *
     * @param context
     * @return
     */
    public static List<PackageInfo> getInstalledPackages(Context context) {
        PackageManager pm = context.getApplicationContext().getPackageManager();
        List<PackageInfo> pkgList;
        pkgList = pm.getInstalledPackages(0);
        return pkgList;
    }

    /**
     * 判断是否是系统App。
     *
     * @param info
     * @return
     */
    public static boolean isSystemApp(ApplicationInfo info) {
        boolean isSystemApp = false;
        if (info != null) {
            isSystemApp = (info.flags & ApplicationInfo.FLAG_SYSTEM) != 0
                    || (info.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0;
        }
        return isSystemApp;
    }

    /**
     * 获取应用名字
     *
     * @param context
     * @param info
     * @return
     */
    public static String getApplicationName(Context context, ApplicationInfo info) {
        PackageManager pkgManager = context.getApplicationContext().getPackageManager();
        return info.loadLabel(pkgManager).toString();
    }

    /**
     * 获取应用ICON图标
     */
    public static Drawable getIconByPkgname(Context context, String pkgName) {
        if (pkgName != null) {
            PackageManager pkgManager = context.getApplicationContext().getPackageManager();
            try {
                PackageInfo pkgInfo = pkgManager.getPackageInfo(pkgName, 0);
                return pkgInfo.applicationInfo.loadIcon(pkgManager);
            } catch (PackageManager.NameNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static synchronized long getAppSize(Context context, String packageName) {
        PackageInfo packageInfo = null;
        //页面退出的之后context为空。
        if (context == null) {
            return 0;
        }
        try {
            packageInfo = context.getApplicationContext().getPackageManager().getPackageInfo(packageName, PackageManager.GET_ACTIVITIES);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return 0;
        }
        String publicSourceDir;
        try {
            publicSourceDir = packageInfo.applicationInfo.publicSourceDir;
            File file = new File(publicSourceDir);
            return file.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取应用大小
     */
    public static long getAppSize(ApplicationInfo info) {
        String publicSourceDir;
        try {
            publicSourceDir = info.publicSourceDir;
            File file = new File(publicSourceDir);
            return file.length();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 获取安装时间
     */
    public static long getInstallTime(ApplicationInfo info) {
        String sourceDir;
        try {
            sourceDir = info.sourceDir;
            File file = new File(sourceDir);
            return file.lastModified();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }


    /**
     * 卸载apk
     *
     * @param context     上下文
     * @param packageName 包名
     */
    public static void uninstallApk(Context context, String packageName) {
        Uri packageURI = Uri.parse("package:" + packageName);
        Intent intent = new Intent(Intent.ACTION_DELETE, packageURI);
        context.startActivity(intent);
    }



    private static String getSourceApkPath(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName)) {
            return null;
        }

        try {
            ApplicationInfo appInfo = context.getPackageManager().getApplicationInfo(packageName, 0);
            return appInfo.sourceDir;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return null;
    }


}
