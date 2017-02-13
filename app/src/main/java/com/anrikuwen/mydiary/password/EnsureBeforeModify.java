package com.anrikuwen.mydiary.password;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anrikuwen.mydiary.R;

public class EnsureBeforeModify extends AppCompatActivity {

    private EditText oldPasswordEdit;
    private Button ensureButton;
    private Button retrieveButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ensure_before_modify);
        oldPasswordEdit = (EditText) findViewById(R.id.password_ebm_old_password);
        ensureButton = (Button) findViewById(R.id.password_ebm_ensure_button);
        retrieveButton = (Button) findViewById(R.id.password_ebm_retrieve_password);

        ensureButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences pref = getSharedPreferences("passwordData",MODE_PRIVATE);
                String password = pref.getString("password","");
                if(password.equals(String.valueOf(oldPasswordEdit.getText()))){
                    Intent intent = new Intent(EnsureBeforeModify.this,CreateAndModifyPassword.class);
                    startActivity(intent);
                    SharedPreferences.Editor editor = pref.edit();
                    editor.putString("question","");
                    editor.putString("answer","");
                    editor.putString("password","");
                    editor.apply();
                    Toast.makeText(EnsureBeforeModify.this,"恭喜回答正确，密码已清空",Toast.LENGTH_SHORT).show();
                    finish();
                }else {
                    Toast.makeText(EnsureBeforeModify.this,"输入的密码错误",Toast.LENGTH_SHORT).show();
                    oldPasswordEdit.setText("");
                }
            }
        });

        retrieveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EnsureBeforeModify.this,RetrievePassword.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
