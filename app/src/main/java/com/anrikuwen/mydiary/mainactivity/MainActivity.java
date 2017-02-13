package com.anrikuwen.mydiary.mainactivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.anrikuwen.mydiary.R;
import com.anrikuwen.mydiary.diaryfragment.DiaryActivity;
import com.anrikuwen.mydiary.password.CreateAndModifyPassword;
import com.anrikuwen.mydiary.password.EnsureBeforeModify;

import org.litepal.tablemanager.Connector;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SharedPreferences pref;
    private String password;
    private EditText dialogEdit;
    private Button dialogButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pref = getSharedPreferences("passwordData",MODE_PRIVATE);
        password = pref.getString("password","");
        Connector.getDatabase();
        initView();
    }

    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView leftNavigationView = (NavigationView) findViewById(R.id.left_nav_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if(actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_nav_menu);
        }

        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.nav_menu_diary:
                        if(TextUtils.isEmpty(password)){
                            Intent intent = new Intent(MainActivity.this,DiaryActivity.class);
                            startActivity(intent);
                        }else {
                            final Dialog dialog = new Dialog(MainActivity.this);
                            Window dialogWindow = dialog.getWindow();
                            WindowManager.LayoutParams params = dialogWindow.getAttributes();
                            params.gravity = Gravity.CENTER;
                            dialogWindow.setAttributes(params);
                            dialog.setContentView(R.layout.enter_diary_dialog);
                            dialog.show();
                            dialogEdit = (EditText) dialog.findViewById(R.id.enter_diary_dialog_edit);
                            dialogButton = (Button) dialog.findViewById(R.id.enter_diary_dialog_button);
                            dialogButton.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if((String.valueOf(password)).equals(String.valueOf(dialogEdit.getText()))){
                                        Intent intent = new Intent(MainActivity.this,DiaryActivity.class);
                                        startActivity(intent);
                                        dialog.cancel();
                                    }else {
                                        Toast.makeText(MainActivity.this,"输入的密码有错哟，请从新输入",Toast.LENGTH_SHORT).show();
                                        dialogEdit.setText("");
                                    }
                                }
                            });
                        }
                        break;
                    case R.id.nav_menu_password:
                        setPassword();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });
    }

    private void setPassword() {
        if (TextUtils.isEmpty(password)){
            Intent intent = new Intent(MainActivity.this, CreateAndModifyPassword.class);
            startActivity(intent);
        }else {
            Intent intent = new Intent(MainActivity.this, EnsureBeforeModify.class);
            startActivity(intent);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }
}
