package com.nl.treadmilllauncher.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import com.nl.treadmilllauncher.utils.SuspendWindowManager;

public class SuspendWindowMgrService extends Service {

    public SuspendWindowMgrService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
//        SuspendWindowManager.getSuspendWindowManager(this).addTopSuspendWin();
//        SuspendWindowManager.getSuspendWindowManager(this).addRightSuspendWin();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
//        SuspendWindowManager.getSuspendWindowManager(this).removeTopSuspendWin();
//        SuspendWindowManager.getSuspendWindowManager(this).removeRightSuspendWin();
        super.onDestroy();
    }
}
