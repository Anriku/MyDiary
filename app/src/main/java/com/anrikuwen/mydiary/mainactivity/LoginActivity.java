package com.anrikuwen.mydiary.mainactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.TextView;

import com.anrikuwen.mydiary.BaseActivity;
import com.anrikuwen.mydiary.R;

import org.litepal.tablemanager.Connector;


/**
 * 这里的Login表示启动的意思，本Activity为启动Activity，最开始忘了Boot为启动，不要理解为登陆了
 */
public class LoginActivity extends BaseActivity {

    private TextView loginTextView;
    private SharedPreferences prefPasswordData;
    private SharedPreferences prefPasswordSelectData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginTextView = (TextView) findViewById(R.id.login_activity_text_view);
        Connector.getDatabase();

        SharedPreferences pref = getSharedPreferences("LoginTextData", MODE_PRIVATE);
        if (!pref.contains("classicQuotations")) {
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("classicQuotations", "以彗星为龙，以彗星为绳结。描绘出割裂的彗星舞动的形态。又是一轮岁月。");
            editor.apply();
        }
        loginTextView.setText(pref.getString("classicQuotations", ""));

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                decideHowToEnter();
            }
        }).start();
    }

    private void decideHowToEnter() {
        prefPasswordData = getSharedPreferences("PasswordData", MODE_PRIVATE);
        String password = prefPasswordData.getString("password", "");
        prefPasswordSelectData = getSharedPreferences("PasswordSelectData", MODE_PRIVATE);
        boolean isBootPasswordChecked = prefPasswordSelectData.getBoolean("isBootPasswordChecked", true);
        if (isBootPasswordChecked) {
            if (!TextUtils.isEmpty(password)) {
                Intent intent = new Intent(LoginActivity.this, PasswordAfterLoginActivity.class);
                startActivity(intent);
                finish();
            } else {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        } else {
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
