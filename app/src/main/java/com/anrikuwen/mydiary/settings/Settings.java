package com.anrikuwen.mydiary.settings;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.support.transition.Visibility;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.anrikuwen.mydiary.R;
import com.anrikuwen.mydiary.mainactivity.MainActivity;

public class Settings extends AppCompatActivity implements View.OnClickListener {

    private TextView loginTextTextView;
    private EditText loginTextEdit;
    private SharedPreferences prefLoginTextData;
    private SharedPreferences prefPasswordSelectData;
    private Button loginTextButton;
    private CardView loginTextCardView;
    private TextView passwordSelectTextView;
    private CardView passwordSelectCardView;
    private CheckBox bootPasswordCheckBox;
    private CheckBox enterDiaryPasswordCheckBox;
    private CheckBox enterSettingPasswordCheckBox;
    private TextView carouselFigureTextView;
    private EditText carouselFigureEdit;
    private Button carouselFigureButton;
    private CardView carouselFigureCardView;
    private Drawable afterEnterDrawable;
    private Drawable enterDrawable;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        loginTextTextView = (TextView) findViewById(R.id.setting_login_text);
        loginTextEdit = (EditText) findViewById(R.id.setting_login_text_edit);
        loginTextButton = (Button) findViewById(R.id.setting_login_text_ensure_button);
        loginTextCardView = (CardView) findViewById(R.id.setting_login_text_card_view);
        passwordSelectTextView = (TextView) findViewById(R.id.setting_password_select_text_view);
        passwordSelectCardView = (CardView) findViewById(R.id.setting_password_select_card_view);
        bootPasswordCheckBox = (CheckBox) findViewById(R.id.setting_password_select_boot_password_CB);
        enterDiaryPasswordCheckBox = (CheckBox) findViewById(R.id.setting_password_select_enter_diary_password_CB);
        enterSettingPasswordCheckBox = (CheckBox) findViewById(R.id.setting_password_enter_setting_password_CB);
        carouselFigureTextView = (TextView) findViewById(R.id.setting_carousel_figure_time_text);
        carouselFigureEdit = (EditText) findViewById(R.id.setting_carousel_figure_edit);
        carouselFigureButton = (Button) findViewById(R.id.setting_carousel_figure_button);
        carouselFigureCardView = (CardView) findViewById(R.id.setting_carousel_figure_card_view);

        afterEnterDrawable = ContextCompat.getDrawable(Settings.this, R.mipmap.ic_after_enter);
        afterEnterDrawable.setBounds(0, 0, afterEnterDrawable.getIntrinsicWidth(), afterEnterDrawable.getIntrinsicHeight());
        enterDrawable = ContextCompat.getDrawable(Settings.this, R.mipmap.ic_enter_setting);
        enterDrawable.setBounds(0, 0, enterDrawable.getIntrinsicWidth(), enterDrawable.getIntrinsicHeight());

        restoreLoginText();
        restoreCheckBox();
        restoreCarouselFigureTime();

        loginTextTextView.setOnClickListener(this);
        loginTextButton.setOnClickListener(this);
        passwordSelectTextView.setOnClickListener(this);
        carouselFigureTextView.setOnClickListener(this);
        carouselFigureButton.setOnClickListener(this);
    }

    private void restoreCarouselFigureTime() {
        SharedPreferences prefCarouselFigureTimeData = getSharedPreferences("TimeOfCarouselFigure",MODE_PRIVATE);
        int time = prefCarouselFigureTimeData.getInt("delayTime",4);
        carouselFigureEdit.setText(String.valueOf(time));
    }

    private void restoreCheckBox() {
        prefPasswordSelectData = getSharedPreferences("PasswordSelectData", MODE_PRIVATE);
        if (prefPasswordSelectData.getBoolean("isBootPasswordChecked", true)) {
            bootPasswordCheckBox.setChecked(true);
        } else {
            bootPasswordCheckBox.setChecked(false);
        }
        if (prefPasswordSelectData.getBoolean("isEnterDiaryPasswordChecked", true)) {
            enterDiaryPasswordCheckBox.setChecked(true);
        } else {
            enterDiaryPasswordCheckBox.setChecked(false);
        }
        if (prefPasswordSelectData.getBoolean("isEnterSettingPasswordChecked", true)) {
            enterSettingPasswordCheckBox.setChecked(true);
        } else {
            enterSettingPasswordCheckBox.setChecked(false);
        }
    }

    private void restoreLoginText() {
        prefLoginTextData = getSharedPreferences("LoginTextData", MODE_PRIVATE);
        String loginText = prefLoginTextData.getString("classicQuotations", "");
        loginTextEdit.setText(loginText);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_login_text://启动界面经典语录设置的TextView
                if (loginTextEdit.getVisibility() == View.VISIBLE) {
                    closeLoginSetting();
                } else {
                    openLoginSetting();
                }
                break;

            case R.id.setting_login_text_ensure_button://设置经典语录的Button
                SharedPreferences.Editor editor = prefLoginTextData.edit();
                editor.putString("classicQuotations", loginTextEdit.getText().toString());
                editor.apply();
                Toast.makeText(Settings.this, "恭喜，保存成功啦", Toast.LENGTH_SHORT).show();
                closeLoginSetting();
                break;

            case R.id.setting_password_select_text_view://密码选择设置的TextView
                if (passwordSelectCardView.getVisibility() == View.VISIBLE) {
                    closePasswordSelectSetting();
                } else {
                    openPasswordSelectSetting();
                }
                break;

            case R.id.setting_carousel_figure_time_text://轮播图的时间设置的TextView
                if (carouselFigureEdit.getVisibility() == View.VISIBLE){
                    closeCarouselFigureSetting();
                }else {
                    openCarouselFigureSetting();
                }
                break;

            case R.id.setting_carousel_figure_button:
                SharedPreferences prefCarouselFigureTimeData = getSharedPreferences("TimeOfCarouselFigure",MODE_PRIVATE);
                SharedPreferences.Editor timeEditor = prefCarouselFigureTimeData.edit();
                timeEditor.putInt("delayTime",Integer.valueOf(carouselFigureEdit.getText().toString()));
                timeEditor.apply();
                Toast.makeText(Settings.this,"恭喜，亲，保存时间成功",Toast.LENGTH_SHORT).show();
                closeCarouselFigureSetting();
            default:
                break;
        }
    }


    private void openCarouselFigureSetting() {
        carouselFigureTextView.setCompoundDrawables(null,null,afterEnterDrawable,null);
        carouselFigureEdit.setVisibility(View.VISIBLE);
        carouselFigureButton.setVisibility(View.VISIBLE);
        carouselFigureCardView.setVisibility(View.VISIBLE);
    }

    private void closeCarouselFigureSetting() {
        carouselFigureTextView.setCompoundDrawables(null,null,enterDrawable,null);
        carouselFigureEdit.setVisibility(View.GONE);
        carouselFigureButton.setVisibility(View.GONE);
        carouselFigureCardView.setVisibility(View.GONE);
    }



    private void openPasswordSelectSetting() {
        passwordSelectTextView.setCompoundDrawables(null, null, afterEnterDrawable, null);
        passwordSelectCardView.setVisibility(View.VISIBLE);
        bootPasswordCheckBox.setVisibility(View.VISIBLE);
        enterSettingPasswordCheckBox.setVisibility(View.VISIBLE);
        enterDiaryPasswordCheckBox.setVisibility(View.VISIBLE);
    }

    private void closePasswordSelectSetting() {
        passwordSelectCardView.setVisibility(View.GONE);
        bootPasswordCheckBox.setVisibility(View.GONE);
        enterSettingPasswordCheckBox.setVisibility(View.GONE);
        enterDiaryPasswordCheckBox.setVisibility(View.GONE);
    }


    private void openLoginSetting() {
        loginTextTextView.setCompoundDrawables(null, null, afterEnterDrawable, null);
        loginTextCardView.setVisibility(View.VISIBLE);
        loginTextEdit.setVisibility(View.VISIBLE);
        loginTextButton.setVisibility(View.VISIBLE);
    }

    private void closeLoginSetting() {
        loginTextTextView.setCompoundDrawables(null, null, enterDrawable, null);
        loginTextCardView.setVisibility(View.GONE);
        loginTextEdit.setVisibility(View.GONE);
        loginTextButton.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        prefPasswordSelectData = getSharedPreferences("PasswordSelectData", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefPasswordSelectData.edit();
        editor.putBoolean("isBootPasswordChecked", bootPasswordCheckBox.isChecked());
        editor.putBoolean("isEnterDiaryPasswordChecked", enterDiaryPasswordCheckBox.isChecked());
        editor.putBoolean("isEnterSettingPasswordChecked", enterSettingPasswordCheckBox.isChecked());
        editor.apply();
    }
}
