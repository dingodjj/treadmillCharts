package com.nl.treadmilllauncher.ui;

import android.app.Activity;
import android.appwidget.AppWidgetHost;
import android.appwidget.AppWidgetHostView;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProviderInfo;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.nl.treadmilllauncher.R;
import com.nl.treadmilllauncher.utils.SpUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018/1/20 0020.
 */

public class TestActivity extends Activity{

    private static final String TAG = "TestActivity";

    private static final int MY_REQUEST_APPWIDGET = 1;
    private static final int MY_CREATE_APPWIDGET = 2;

    private static final String APPWIDGETID = "APPWIDGETID";

    private static final int HOST_ID = 1024;

    private AppWidgetHost mAppWidgetHost = null;
    private AppWidgetManager appWidgetManager = null;
    private AudioManager audioManager;
    private Unbinder unbinder;

    private ImageView music_pic_iv;
    private TextView music_title_tv;
    private ImageView music_last_iv;
    private ImageView music_play_or_pause_iv;
    private ImageView music_next_iv;

    @BindView(R.id.root_viewgroup)
    LinearLayout root_viewgroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //设置无标题
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置全屏
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.test_layout);

        unbinder = ButterKnife.bind(this);

        //其参数hostid大意是指定该AppWidgetHost 即本Activity的标记Id， 直接设置为一个整数值吧 。
        mAppWidgetHost = new AppWidgetHost(this, HOST_ID);

        //为了保证AppWidget的及时更新 ， 必须在Activity的onCreate/onStar方法调用该方法
        // 当然可以在onStop方法中，调用mAppWidgetHost.stopListenering() 停止AppWidget更新
        mAppWidgetHost.startListening();

        //获得AppWidgetManager对象
        appWidgetManager = AppWidgetManager.getInstance(this);
        audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

        int appWidgetId = SpUtils.load(APPWIDGETID, -1, this);
        if(appWidgetId != -1){
            addWidgetView(appWidgetId);
        }
    }

    @OnClick({R.id.add_widget_btn ,R.id.last_song_btn, R.id.start_or_pause_btn,
            R.id.next_song_btn})
    void onClick(View view){
//        Intent intent = new Intent();
//        intent.setComponent(new ComponentName("com.netease.cloudmusic", "com.netease.cloudmusic.service.PlayService"));
//        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        switch (view.getId()){
            case R.id.add_widget_btn:
                //显示所有能创建AppWidget的列表 发送此 ACTION_APPWIDGET_PICK 的Action
                Intent pickIntent = new Intent(AppWidgetManager.ACTION_APPWIDGET_PICK);

                //向系统申请一个新的appWidgetId ，该appWidgetId与我们发送Action为ACTION_APPWIDGET_PICK
                //  后所选择的AppWidget绑定 。 因此，我们可以通过这个appWidgetId获取该AppWidget的信息了

                //为当前所在进程申请一个新的appWidgetId
                int newAppWidgetId = mAppWidgetHost.allocateAppWidgetId();
                Log.i(TAG, "The new allocate appWidgetId is ----> " + newAppWidgetId);

                //作为Intent附加值 ， 该appWidgetId将会与选定的AppWidget绑定
                pickIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, newAppWidgetId);

                //选择某项AppWidget后，立即返回，即回调onActivityResult()方法
                startActivityForResult(pickIntent, MY_REQUEST_APPWIDGET);
                break;
            case R.id.last_song_btn:
                if(audioManager.isMusicActive()){
                    KeyEvent keyEvent = new KeyEvent(KeyEvent.KEYCODE_MEDIA_PREVIOUS, KeyEvent.KEYCODE_MEDIA_PREVIOUS);
                    Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    intent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
                    sendOrderedBroadcast(intent,null);
                }
//                intent.setAction("com.netease.cloudmusic.PLAYPREV");
//                startService(intent);
//                if(music_last_iv != null){
//                    music_last_iv.callOnClick();
//                }
                break;
            case R.id.start_or_pause_btn:
//                intent.setAction("com.netease.cloudmusic.TOGGLEPAUSE");
//                startService(intent);
//                if(music_play_or_pause_iv != null){
//                    music_play_or_pause_iv.callOnClick();
//                }
                break;
            case R.id.next_song_btn:
                if(audioManager.isMusicActive()){
                    KeyEvent keyEvent = new KeyEvent(KeyEvent.KEYCODE_MEDIA_NEXT, KeyEvent.KEYCODE_MEDIA_NEXT);
                    Intent intent = new Intent(Intent.ACTION_MEDIA_BUTTON);
                    intent.putExtra(Intent.EXTRA_KEY_EVENT, keyEvent);
                    sendOrderedBroadcast(intent,null);
                }

//                intent.setAction("com.netease.cloudmusic.PLAYNEXT");
//                startService(intent);

//                if(music_next_iv != null){
//                    music_next_iv.callOnClick();
//                }
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //直接返回，没有选择任何一项 ，例如按Back键
        if (resultCode == RESULT_CANCELED)
            return;

        switch (requestCode) {
            case MY_REQUEST_APPWIDGET:
                Log.i(TAG, "MY_REQUEST_APPWIDGET intent info is -----> " + data);
                int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);

                Log.i(TAG, "MY_REQUEST_APPWIDGET : appWidgetId is ----> " + appWidgetId);

                //得到的为有效的id
                if (appWidgetId != AppWidgetManager.INVALID_APPWIDGET_ID) {
                    //查询指定appWidgetId的 AppWidgetProviderInfo对象 ， 即在xml文件配置的<appwidget-provider />节点信息
                    AppWidgetProviderInfo appWidgetProviderInfo = appWidgetManager.getAppWidgetInfo(appWidgetId);

                    //如果配置了configure属性 ， 即android:configure = "" ，需要再次启动该configure指定的类文件,通常为一个Activity
                    if (appWidgetProviderInfo.configure != null) {

                        Log.i(TAG, "The AppWidgetProviderInfo configure info -----> " + appWidgetProviderInfo.configure);

                        //配置此Action
                        Intent intent = new Intent(AppWidgetManager.ACTION_APPWIDGET_CONFIGURE);
                        intent.setComponent(appWidgetProviderInfo.configure);
                        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId);


                        startActivityForResult(intent, MY_CREATE_APPWIDGET);
                    } else  //直接创建一个AppWidget
                        onActivityResult(MY_CREATE_APPWIDGET, RESULT_OK, data);  //参数不同，简单回调而已
                }
                break;
            case MY_CREATE_APPWIDGET:
                completeAddAppWidget(data);
                break;
        }
    }

    //向当前视图添加一个用户选择的
    private void completeAddAppWidget(Intent data) {
        Bundle extra = data.getExtras();
        int appWidgetId = extra.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, -1);
        //等同于上面的获取方式
        //int appWidgetId = data.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID , AppWidgetManager.INVALID_APPWIDGET_ID) ;

        Log.i(TAG, "completeAddAppWidget : appWidgetId is ----> " + appWidgetId);

        if (appWidgetId == -1) {
            Toast.makeText(this, "添加窗口小部件有误", Toast.LENGTH_SHORT);
            return;
        }

        SpUtils.save(APPWIDGETID, appWidgetId, this);

        addWidgetView(appWidgetId);
    }

    private void addWidgetView(int appWidgetId) {
        AppWidgetProviderInfo appWidgetProviderInfo = appWidgetManager.getAppWidgetInfo(appWidgetId);

        AppWidgetHostView hostView = mAppWidgetHost.createView(this, appWidgetId, appWidgetProviderInfo);

        LinearLayout.LayoutParams linearLayoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        hostView.setLayoutParams(linearLayoutParams);

        Log.d(TAG, "addWidgetView: Width = " + hostView.getWidth());
        Log.d(TAG, "addWidgetView: Height = " + hostView.getHeight());

        root_viewgroup.addView(hostView);

        getWidgetChildViews(hostView);
    }

    public void getWidgetChildViews(AppWidgetHostView parent) {
        try{
            LinearLayout linearLayout = (LinearLayout) parent.getChildAt(0);
            music_pic_iv = (ImageView) linearLayout.getChildAt(0);

            linearLayout = (LinearLayout) linearLayout.getChildAt(1);
            music_title_tv = (TextView) linearLayout.getChildAt(0);

            RelativeLayout relativeLayout = (RelativeLayout) linearLayout.getChildAt(2);
            music_play_or_pause_iv = (ImageView) relativeLayout.getChildAt(1);
            music_last_iv = (ImageView) relativeLayout.getChildAt(2);
            music_next_iv = (ImageView) relativeLayout.getChildAt(3);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
