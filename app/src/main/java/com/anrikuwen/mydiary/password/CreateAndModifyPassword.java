package com.anrikuwen.mydiary.password;

import android.content.SharedPreferences;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anrikuwen.mydiary.BaseActivity;
import com.anrikuwen.mydiary.R;

public class CreateAndModifyPassword extends BaseActivity {

    private EditText firstEdit;
    private EditText secondEdit;
    private Button ensureButton;
    private EditText questionEdit;
    private Button questionButton;
    private String[] questions = new String[]{"让你最难忘的一部动漫是:"
            , "你最喜欢的一首歌是:", "你最想去的地方是:", "你最崇拜的人是:"};
    private int questionNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_and_modify_password);
        firstEdit = (EditText) findViewById(R.id.password_cam_input_first_edit);
        secondEdit = (EditText) findViewById(R.id.password_cam_input_second_edit);
        ensureButton = (Button) findViewById(R.id.password_cam_ensure_button);
        questionEdit = (EditText) findViewById(R.id.password_cam_question_edit);
        questionButton = (Button) findViewById(R.id.password_cam_question_button);
        questionEdit.setHint(questions[questionNum]);

        ensureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createPassword();
            }
        });
        questionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                questionNum++;
                if(questionNum == questions.length){
                    questionNum = 0;
                }
                questionEdit.setHint(questions[questionNum]);
            }
        });
    }

    private void createPassword() {
        if ((TextUtils.isEmpty(String.valueOf(firstEdit.getText())) == false) &&
                (TextUtils.isEmpty(String.valueOf(secondEdit.getText())) == false) &&
                (TextUtils.isEmpty(String.valueOf(questionEdit.getText())) == false) &&
                (String.valueOf(firstEdit.getText()).equals(String.valueOf(secondEdit.getText())) == true)) {
            SharedPreferences pref = getSharedPreferences("PasswordData", MODE_PRIVATE);
            SharedPreferences.Editor editor = pref.edit();
            editor.putString("password", String.valueOf(firstEdit.getText()));
            editor.putString("answer", String.valueOf(questionEdit.getText()));
            editor.putString("question", String.valueOf(questions[questionNum]));
            editor.apply();
            Toast.makeText(this, "设置成功啦", Toast.LENGTH_SHORT).show();
            finish();
        } else if ((TextUtils.isEmpty(String.valueOf(firstEdit.getText())) == false) &&
                (TextUtils.isEmpty(String.valueOf(secondEdit.getText())) == false) &&
                (TextUtils.isEmpty(String.valueOf(questionEdit.getText())) == true) &&
                (String.valueOf(firstEdit.getText()).equals(String.valueOf(secondEdit.getText())) == true)) {
            Toast.makeText(this, "别忘了输入辅助问题哟", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(String.valueOf(firstEdit.getText())) &&
                TextUtils.isEmpty(String.valueOf(secondEdit.getText())) &&
                (TextUtils.isEmpty(String.valueOf(questionEdit.getText())) == false)) {
            Toast.makeText(this, "别忘了最重要的密码哟", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(String.valueOf(firstEdit.getText())) &&
                TextUtils.isEmpty(String.valueOf(secondEdit.getText())) &&
                TextUtils.isEmpty(String.valueOf(questionEdit.getText()))) {
            Toast.makeText(this, "别忘了输入密码和辅助问题哟", Toast.LENGTH_SHORT).show();
        } else if ((TextUtils.isEmpty(String.valueOf(firstEdit.getText())) == false) &&
                (TextUtils.isEmpty(String.valueOf(secondEdit.getText())) == false) &&
                TextUtils.isEmpty(String.valueOf(questionEdit.getText())) &&
                (String.valueOf(firstEdit.getText()).equals(String.valueOf(secondEdit.getText())) == false)) {
            Toast.makeText(this, "前后输入的密码不相同，别忘了辅助问题哟", Toast.LENGTH_SHORT).show();
        } else if ((TextUtils.isEmpty(String.valueOf(firstEdit.getText())) == false) &&
                (TextUtils.isEmpty(String.valueOf(secondEdit.getText())) == false) &&
                (TextUtils.isEmpty(String.valueOf(questionEdit.getText())) == false) &&
                (String.valueOf(firstEdit.getText()).equals(String.valueOf(secondEdit.getText())) == false)) {
            Toast.makeText(this, "前后输入的密码不相同哟", Toast.LENGTH_SHORT).show();
        }
    }
}
