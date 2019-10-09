package com.sinothk.push.hw.demo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NoticeDetailActivity extends AppCompatActivity {

    TextView titleTv, contentTv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notice_detail);

        titleTv = this.findViewById(R.id.titleTv);
        contentTv = this.findViewById(R.id.contentTv);

        Uri uri = getIntent().getData();
        if (uri != null) {
            String title = uri.getQueryParameter("title");
            String content = uri.getQueryParameter("content");

            titleTv.setText(title);
            contentTv.setText(content);
        }
    }

    private void getActive() {
//        intent.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);

        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("customscheme://com.sinothk.push.hw.demo/notify_detail?message=what"));
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        String intentUri = intent.toUri(Intent.URI_INTENT_SCHEME);

        // intentUri 自定义点击操作：这个给后台使用
    }

    @Override
    public void finish() {
        if (!App.isExistMainActivity(this)) {
            startActivity(new Intent(this, PushActivity.class));

            Log.e("isExistMainActivity" , "F");
        }else {
            Log.e("isExistMainActivity" , "T");
        }
        super.finish();
    }
}
