package com.nl.treadmilllauncher.application;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Environment;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import com.nl.treadmilllauncher.MainActivity;
import com.nl.treadmilllauncher.MainApplication;
import com.nl.treadmilllauncher.utils.StorageUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UnCeHandler implements Thread.UncaughtExceptionHandler {
          
    public static final String TAG = "CatchExcep";
    private Thread.UncaughtExceptionHandler mDefaultHandler;
    private MainApplication application;
    private Map<String, String> info = new HashMap<String, String>();// 用来存储设备信息和异常信息
    private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss",
            Locale.getDefault());// 用于格式化日期,作为日志文件名的一部分

    public UnCeHandler(MainApplication application){
         //获取系统默认的UncaughtException处理器
         mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
         this.application = application;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        if(!handleException(ex) && mDefaultHandler != null){
            //如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        }else{
            try{
                Thread.sleep(2000);
            }catch (InterruptedException e){
                Log.e(TAG, "error : ", e);
            }
            Intent intent = new Intent(application.getApplicationContext(), MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            PendingIntent restartIntent = PendingIntent.getActivity(
                    application.getApplicationContext(), 0, intent, 0);
            //退出程序
            AlarmManager mgr = (AlarmManager)application.getSystemService(Context.ALARM_SERVICE);
            // 1秒钟后重启应用
            mgr.set(AlarmManager.RTC, System.currentTimeMillis() + 1000, restartIntent);
            application.finishActivity();
        }
    }

    /**
     * 自定义错误处理,收集错误信息 发送错误报告等操作均在此完成.
     *
     * @param ex
     * @return true:如果处理了该异常信息;否则返回false.
     */
    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        //使用Toast来显示异常信息
        new Thread(){
            @Override
            public void run() {
                Looper.prepare();
                Toast.makeText(application.getApplicationContext(), "很抱歉,程序出现异常,即将退出.",
                        Toast.LENGTH_SHORT).show();
                Looper.loop();
            }
        }.start();

        // 收集设备参数信息
        info.putAll(collectDeviceInfo(application.getApplicationContext()));
        // 保存日志文件
        saveCrashInfo2File(ex);

        return true;
    }

    /**
     * 收集设备参数信息
     *
     * @param context
     */
    public static Map<String, String> collectDeviceInfo(Context context) {
        Map<String, String> info = new HashMap<String, String>();// 用来存储设备信息和异常信息
        try {
            PackageManager pm = context.getPackageManager();// 获得包管理器
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), PackageManager.GET_ACTIVITIES);// 得到该应用的信息，即主Activity
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                info.put("versionName", versionName);
                info.put("versionCode", versionCode);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        Field[] fields = Build.class.getDeclaredFields();// 反射机制
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                info.put(field.getName(), field.get("").toString());
                Log.d(TAG, field.getName() + ":" + field.get(""));
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return info;
    }

    private String saveCrashInfo2File(Throwable ex) {
        StringBuffer sb = new StringBuffer();
        for (Map.Entry<String, String> entry : info.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key + "=" + value + "\r\n");
        }
        Writer writer = new StringWriter();
        PrintWriter pw = new PrintWriter(writer);
        ex.printStackTrace(pw);
        Throwable cause = ex.getCause();
        // 循环着把所有的异常信息写入writer中
        while (cause != null) {
            cause.printStackTrace(pw);
            cause = cause.getCause();
        }
        pw.close();// 记得关闭
        String result = writer.toString();
        sb.append(result);
        Log.e("错误日志", sb.toString());
        // 保存文件
        String fileName = "crash-" + format.format(new Date()) + "-" + System.currentTimeMillis() + ".log";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                File dirlog = new File(StorageUtils.getCacheDirectory(application.getApplicationContext(), true), "log");
                if (!dirlog.exists()) {
                    dirlog.mkdir();
                }
                File log = new File(dirlog, fileName);
                log.createNewFile();
                FileOutputStream fos = new FileOutputStream(log);
                fos.write(sb.toString().getBytes());
                fos.close();
                return fileName;
            }  catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}