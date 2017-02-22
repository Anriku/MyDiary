package com.anrikuwen.mydiary.mainactivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anrikuwen.mydiary.R;

public class PasswordAfterLoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText byPasswordEdit;
    private EditText byQuestionEdit;
    private Button byPasswordButton;
    private Button byQuestionButton;
    private SharedPreferences pref;
    private TextView byPasswordText;
    private TextView byQuestionText;
    private Drawable afterEnterDrawable;
    private Drawable enterDrawable;
    private CardView byPasswordCardView;
    private CardView byQuestionCardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_after_login);
        byPasswordEdit = (EditText) findViewById(R.id.password_after_login_by_password_edit);
        byQuestionEdit = (EditText) findViewById(R.id.password_after_login_by_question_edit);
        byPasswordButton = (Button) findViewById(R.id.password_after_login_by_password_ensure_button);
        byQuestionButton = (Button) findViewById(R.id.password_after_login_by_question_ensure_button);
        byPasswordText = (TextView) findViewById(R.id.password_after_login_by_password_text);
        byQuestionText = (TextView) findViewById(R.id.password_after_login_by_question_text);
        byPasswordCardView = (CardView) findViewById(R.id.password_after_login_by_password_card_view);
        byQuestionCardView = (CardView) findViewById(R.id.password_after_login_by_question_card_view);

        pref = getSharedPreferences("PasswordData", MODE_PRIVATE);
        String question = pref.getString("question", "");
        byQuestionEdit.setHint(question);

        afterEnterDrawable = ContextCompat.getDrawable(PasswordAfterLoginActivity.this, R.mipmap.ic_after_enter);
        afterEnterDrawable.setBounds(0, 0, afterEnterDrawable.getIntrinsicWidth(), afterEnterDrawable.getIntrinsicHeight());
        enterDrawable = ContextCompat.getDrawable(PasswordAfterLoginActivity.this, R.mipmap.ic_enter_setting);
        enterDrawable.setBounds(0, 0, enterDrawable.getIntrinsicWidth(), enterDrawable.getIntrinsicHeight());

        byPasswordText.setOnClickListener(this);
        byQuestionText.setOnClickListener(this);
        byPasswordButton.setOnClickListener(this);
        byQuestionButton.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.password_after_login_by_password_ensure_button://通过密码的button的点击事件
                enterByPassword();
                break;
            case R.id.password_after_login_by_question_ensure_button://通过问题的button的点击事件
                enterByQuestion();
                break;
            case R.id.password_after_login_by_password_text://通过密码的TextView的点击事件
                if (byPasswordEdit.getVisibility() == View.VISIBLE) {
                    closePasswordInput();
                } else {
                    openPasswordInput();
                }
                break;
            case R.id.password_after_login_by_question_text://通过问题的TextView的点击事件
                if (byQuestionEdit.getVisibility() == View.VISIBLE) {
                    closeQuestionInput();
                } else {
                    openQuestionInput();
                }
        }
    }

    private void openQuestionInput() {
        byQuestionText.setCompoundDrawables(null, null, afterEnterDrawable, null);
        byQuestionEdit.setVisibility(View.VISIBLE);
        byQuestionButton.setVisibility(View.VISIBLE);
        byQuestionCardView.setVisibility(View.VISIBLE);
        if (byPasswordEdit.getVisibility() == View.VISIBLE) {
            byPasswordText.setCompoundDrawables(null, null, enterDrawable, null);
            byPasswordEdit.setVisibility(View.INVISIBLE);
            byPasswordButton.setVisibility(View.INVISIBLE);
            byPasswordCardView.setVisibility(View.INVISIBLE);
        }
    }

    private void closeQuestionInput() {
        byQuestionText.setCompoundDrawables(null, null, enterDrawable, null);
        byQuestionEdit.setVisibility(View.INVISIBLE);
        byQuestionButton.setVisibility(View.INVISIBLE);
        byQuestionCardView.setVisibility(View.INVISIBLE);
        if (byPasswordEdit.getVisibility() == View.INVISIBLE) {
            byPasswordText.setCompoundDrawables(null, null, afterEnterDrawable, null);
            byPasswordEdit.setVisibility(View.VISIBLE);
            byPasswordButton.setVisibility(View.VISIBLE);
            byPasswordCardView.setVisibility(View.VISIBLE);
        }
    }

    private void openPasswordInput() {
        byPasswordText.setCompoundDrawables(null, null, afterEnterDrawable, null);
        byPasswordEdit.setVisibility(View.VISIBLE);
        byPasswordButton.setVisibility(View.VISIBLE);
        byPasswordCardView.setVisibility(View.VISIBLE);
        if (byQuestionEdit.getVisibility() == View.VISIBLE) {
            byQuestionText.setCompoundDrawables(null, null, enterDrawable, null);
            byQuestionEdit.setVisibility(View.INVISIBLE);
            byQuestionButton.setVisibility(View.INVISIBLE);
            byQuestionCardView.setVisibility(View.INVISIBLE);
        }
    }

    private void closePasswordInput() {
        byPasswordText.setCompoundDrawables(null, null, enterDrawable, null);
        byPasswordEdit.setVisibility(View.INVISIBLE);
        byPasswordButton.setVisibility(View.INVISIBLE);
        byPasswordCardView.setVisibility(View.INVISIBLE);
        if (byQuestionEdit.getVisibility() == View.INVISIBLE) {
            byQuestionText.setCompoundDrawables(null, null, afterEnterDrawable, null);
            byQuestionEdit.setVisibility(View.VISIBLE);
            byQuestionButton.setVisibility(View.VISIBLE);
            byQuestionCardView.setVisibility(View.VISIBLE);
        }
    }

    private void enterByQuestion() {
        String answer = pref.getString("answer", "");
        if (String.valueOf(byQuestionEdit.getText()).equals(answer)) {
            Intent intent = new Intent(PasswordAfterLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            byQuestionEdit.setText("");
            Toast.makeText(PasswordAfterLoginActivity.this, "输入的密码有错哟", Toast.LENGTH_SHORT).show();
        }
    }

    private void enterByPassword() {
        String password = pref.getString("password", "");
        if (String.valueOf(byPasswordEdit.getText()).equals(password)) {
            Intent intent = new Intent(PasswordAfterLoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        } else {
            byPasswordEdit.setText("");
            Toast.makeText(PasswordAfterLoginActivity.this, "输入的密码有错哟", Toast.LENGTH_SHORT).show();
        }
    }
}
