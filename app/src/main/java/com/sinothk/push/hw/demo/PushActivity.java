package com.sinothk.push.hw.demo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.huawei.android.hms.agent.HMSAgent;
import com.huawei.android.hms.agent.push.handler.DeleteTokenHandler;
import com.huawei.android.hms.agent.push.handler.EnableReceiveNormalMsgHandler;
import com.huawei.android.hms.agent.push.handler.EnableReceiveNotifyMsgHandler;
import com.huawei.android.hms.agent.push.handler.GetPushStateHandler;
import com.huawei.android.hms.agent.push.handler.GetTokenHandler;
import com.huawei.android.hms.agent.push.handler.QueryAgreementHandler;

import java.text.DateFormat;
import java.util.Date;

import static com.sinothk.push.hw.demo.HuaweiPushRevicer.ACTION_TOKEN;
import static com.sinothk.push.hw.demo.HuaweiPushRevicer.ACTION_UPDATEUI;

public class PushActivity extends AppCompatActivity implements View.OnClickListener, HuaweiPushRevicer.IPushCallback {

    private String token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_push);

//        Button btn = (Button) findViewById(R.id.btn_push);
//        if (btn != null) {
//            btn.setTextColor(Color.RED);
//            btn.setEnabled(false);
//        }

        findViewById(R.id.btn_gettoken).setOnClickListener(this);
        findViewById(R.id.btn_deletetoken).setOnClickListener(this);
        findViewById(R.id.btn_getpushstatus).setOnClickListener(this);
        findViewById(R.id.btn_setnormal).setOnClickListener(this);
        findViewById(R.id.btn_setnofify).setOnClickListener(this);
        findViewById(R.id.btn_agreement).setOnClickListener(this);

        registerBroadcast();
    }

    /**
     * 获取token | get push token
     */
    private void getToken() {
        showLog("get token: begin");
        HMSAgent.Push.getToken(new GetTokenHandler() {
            @Override
            public void onResult(int rtnCode) {
                showLog("get token: end code=" + rtnCode);
            }
        });
    }

    /**
     * 删除token | delete push token
     */
    private void deleteToken() {
        showLog("deleteToken:begin");
        HMSAgent.Push.deleteToken(token, new DeleteTokenHandler() {
            @Override
            public void onResult(int rst) {
                showLog("deleteToken:end code=" + rst);
            }
        });
    }

    /**
     * 获取push状态 | Get Push State
     */
    private void getPushStatus() {
        showLog("getPushState:begin");
        HMSAgent.Push.getPushState(new GetPushStateHandler() {
            @Override
            public void onResult(int rst) {
                showLog("getPushState:end code=" + rst);
            }
        });
    }

    /**
     * 设置是否接收普通透传消息 | Set whether to receive normal pass messages
     *
     * @param enable 是否开启 | enabled or not
     */
    private void setReceiveNormalMsg(boolean enable) {
        showLog("enableReceiveNormalMsg:begin");
        HMSAgent.Push.enableReceiveNormalMsg(enable, new EnableReceiveNormalMsgHandler() {
            @Override
            public void onResult(int rst) {
                showLog("enableReceiveNormalMsg:end code=" + rst);
            }
        });
    }

    /**
     * 设置接收通知消息 | Set up receive notification messages
     *
     * @param enable 是否开启 | enabled or not
     */
    private void setReceiveNotifyMsg(boolean enable) {
        showLog("enableReceiveNotifyMsg:begin");
        HMSAgent.Push.enableReceiveNotifyMsg(enable, new EnableReceiveNotifyMsgHandler() {
            @Override
            public void onResult(int rst) {
                showLog("enableReceiveNotifyMsg:end code=" + rst);
            }
        });
    }

    /**
     * 显示push协议 | Show Push protocol
     */
    private void showAgreement() {
        showLog("queryAgreement:begin");
        HMSAgent.Push.queryAgreement(new QueryAgreementHandler() {
            @Override
            public void onResult(int rst) {
                showLog("queryAgreement:end code=" + rst);
            }
        });
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        int id = v.getId();

        switch (id) {
            case R.id.btn_gettoken:
                getToken();
                break;
            case R.id.btn_deletetoken:
                deleteToken();
                break;
            case R.id.btn_getpushstatus:
                getPushStatus();
                break;
            case R.id.btn_setnormal:
                setReceiveNormalMsg(true);
                break;
            case R.id.btn_setnofify:
                setReceiveNotifyMsg(true);
                break;
            case R.id.btn_agreement:
                showAgreement();
                break;
            default:
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        HuaweiPushRevicer.unRegisterPushCallback(this);
        ;
    }

    /**
     * 以下代码为sample自身逻辑，和业务能力不相关
     * 作用仅仅为了在sample界面上显示push相关信息
     */
    private void registerBroadcast() {
        HuaweiPushRevicer.registerPushCallback(this);
    }

    @Override
    public void onReceive(Intent intent) {
        if (intent != null) {
            String action = intent.getAction();
            Bundle b = intent.getExtras();
            if (b != null && ACTION_TOKEN.equals(action)) {
                token = b.getString(ACTION_TOKEN);
            } else if (b != null && ACTION_UPDATEUI.equals(action)) {
                String log = b.getString("log");
                showLog(log);
            }
        }
    }

    StringBuffer sbLog = new StringBuffer();

    protected void showLog(String logLine) {
        DateFormat format = new java.text.SimpleDateFormat("MMddhhmmssSSS");
        String time = format.format(new Date());

        sbLog.append(time + ":" + logLine);
        sbLog.append('\n');
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                View vText = findViewById(R.id.tv_log);

                if (vText != null && vText instanceof TextView) {
                    TextView tvLog = (TextView)vText;
                    tvLog.setText(sbLog.toString());
                }

                View vScrool = findViewById(R.id.sv_log);
                if (vScrool != null && vScrool instanceof ScrollView) {
                    ScrollView svLog = (ScrollView)vScrool;
                    svLog.fullScroll(View.FOCUS_DOWN);
                }
            }
        });
    }
}
