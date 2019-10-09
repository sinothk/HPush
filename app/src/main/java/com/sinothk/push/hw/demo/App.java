package com.sinothk.push.hw.demo;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.huawei.android.hms.agent.HMSAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class App extends Application {

    private static ArrayList<Activity> ac = new ArrayList<>();

    public static void addActivity(Activity mActivity) {
        ac.add(mActivity);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        HMSAgent.init(this);
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
        HMSAgent.destroy();
    }

    //判断某一个类是否存在任务栈里面
    public static boolean isExistMainActivity(Context mContext) {

        boolean flag = false;

        for (Activity a : ac) {
            if (a instanceof PushActivity) {
                flag = true;
            }
        }
        return flag;

//        Intent intent = new Intent(mContext, activity);
//        ComponentName cmpName = intent.resolveActivity(mContext.getPackageManager());
//        boolean flag = false;
//        if (cmpName != null) { // 说明系统中存在这个activity
//            ActivityManager am = (ActivityManager) mContext.getSystemService(ACTIVITY_SERVICE);
//            List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
//            //获取从栈顶开始往下查找的10个activity
//            for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
//                if (taskInfo.baseActivity.equals(cmpName)) { // 说明它已经启动了
//                    flag = true;
//                    break;  //跳出循环，优化效率
//                }
//            }
//        }
//        return flag;
//        ActivityManager am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
//        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(1);
//        if (list != null && list.size() > 0) {
//            ComponentName cpn = list.get(0).baseActivity;
//            Log.e("GXL", "堆栈信息：" + cpn.getClassName());
//            if ("com.yukexing.mobileticket.nettaxi.mobile_html5.platform.MainActivity"
//                    .equals(cpn.getClassName())) {
//                mCoverView.setVisibility(View.GONE);
//                finish();
//                return;
//            }
//        }

    }

}
