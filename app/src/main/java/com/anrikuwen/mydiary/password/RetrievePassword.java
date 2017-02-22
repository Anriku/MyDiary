package com.anrikuwen.mydiary.password;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anrikuwen.mydiary.R;

public class RetrievePassword extends AppCompatActivity {

    private EditText retrieveEdit;
    private Button retrieveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_retrieve_password);
        retrieveEdit = (EditText) findViewById(R.id.password_rt_retrieve_edit);
        retrieveButton = (Button) findViewById(R.id.password_rt_retrieve_button);
        final SharedPreferences pref = getSharedPreferences("PasswordData",MODE_PRIVATE);
        String question = pref.getString("question","");
        final String answer = pref.getString("answer","");
        retrieveEdit.setHint(question);

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(TextUtils.isEmpty(String.valueOf(retrieveEdit.getText()))){
                    Toast.makeText(RetrievePassword.this,"请回答问题的答案哟",Toast.LENGTH_SHORT).show();
                }else if (String.valueOf(retrieveEdit.getText()).equals(answer)){
                    Intent intent = new Intent(RetrievePassword.this,CreateAndModifyPassword.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("password","");
                    editor.putString("answer","");
                    editor.putString("question","");
                    editor.apply();
                    Toast.makeText(RetrievePassword.this,"恭喜回答正确，密码已清空",Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
    }
}
