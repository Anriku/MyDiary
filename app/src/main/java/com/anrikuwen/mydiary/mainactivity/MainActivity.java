package com.anrikuwen.mydiary.mainactivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.anrikuwen.mydiary.R;
import com.anrikuwen.mydiary.database.ImageData;
import com.anrikuwen.mydiary.diaryfragment.DiaryActivity;
import com.anrikuwen.mydiary.password.CreateAndModifyPassword;
import com.anrikuwen.mydiary.password.EnsureBeforeModify;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private EditText dialogEdit;
    private Button dialogButton;
    private NavigationView leftNavigationView;
    private View hearView;
    private CircleImageView circleImageView;
    private ImageView takePhoto;
    private ImageView chooseFromAlbum;
    private ImageUtils imageUtils;
    private ImageView nickNameEditImage;
    private ImageView beliefEditImage;
    private TextView nickNameText;
    private TextView beliefText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageUtils = new ImageUtils(this);
        Connector.getDatabase();
        initView();

        hearView = leftNavigationView.getHeaderView(0);

        changeCircleImage();
        setCircleImage();
        changeNickNameAndBelief();
        setNickNameAndBelief();
    }

    private void setNickNameAndBelief() {
        SharedPreferences pref = getSharedPreferences("hearViewText",MODE_PRIVATE);
        String nickName = pref.getString("nickName","");
        String belief = pref.getString("belief","");
        if (!TextUtils.isEmpty(nickName)){
            nickNameText.setText(nickName);
        }
        if (!TextUtils.isEmpty(belief)){
            beliefText.setText(belief);
        }
    }

    private void changeNickNameAndBelief() {
        nickNameEditImage = (ImageView) hearView.findViewById(R.id.left_nav_view_nickname_edit_image);
        beliefEditImage = (ImageView) hearView.findViewById(R.id.left_nav_view_belief_edit_image);
        nickNameText = (TextView) hearView.findViewById(R.id.left_nav_view_name);
        beliefText = (TextView) hearView.findViewById(R.id.left_nav_view_your_belief);

        nickNameEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog nickNameEditDialog = getDialog();
                nickNameEditDialog.setContentView(R.layout.left_nav_view_edit_image_dialog);
                nickNameEditDialog.show();
                final EditText editText = (EditText) nickNameEditDialog.findViewById(R.id.left_nav_view_image_dialog_edit);
                Button ensureButton = (Button) nickNameEditDialog.findViewById(R.id.left_nav_view_image_dialog_ensure_button);
                editText.setHint("请输入亲想要的昵称:");
                ensureButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        nickNameText.setText(editText.getText());
                        SharedPreferences pref = getSharedPreferences("hearViewText",MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("nickName",editText.getText().toString());
                        editor.apply();
                        nickNameEditDialog.dismiss();
                    }
                });
            }
        });

        beliefEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog beliefDialog = getDialog();
                beliefDialog.setContentView(R.layout.left_nav_view_edit_image_dialog);
                beliefDialog.show();
                final EditText editText = (EditText) beliefDialog.findViewById(R.id.left_nav_view_image_dialog_edit);
                Button ensureButton = (Button) beliefDialog.findViewById(R.id.left_nav_view_image_dialog_ensure_button);
                editText.setHint("请输入亲的信仰:");
                ensureButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        beliefText.setText(editText.getText());
                        SharedPreferences pref = getSharedPreferences("hearViewText",MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("belief",editText.getText().toString());
                        editor.apply();
                        beliefDialog.dismiss();
                    }
                });
            }
        });
    }

    private void setCircleImage() {
        List<ImageData> theImage = DataSupport.where("imageId = ?", "1").find(ImageData.class);
        if (theImage.size() != 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(theImage.get(0).getImage(), 0, theImage.get(0).getImage().length);
            circleImageView.setImageBitmap(bitmap);
        }
    }

    private void changeCircleImage() {
        circleImageView = (CircleImageView) hearView.findViewById(R.id.left_nav_circle_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog circleImageDialog = getDialog();
                circleImageDialog.setContentView(R.layout.change_circle_image_dialog);
                circleImageDialog.show();
                takePhoto = (ImageView) circleImageDialog.findViewById(R.id.change_circle_image_dialog_take_photo_image);
                chooseFromAlbum = (ImageView) circleImageDialog.findViewById(R.id.change_circle_image_dialog_choose_from_album_image);

                takePhoto.setOnClickListener(new View.OnClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onClick(View v) {
                        imageUtils.takePhoto();
                        circleImageDialog.dismiss();
                    }
                });

                chooseFromAlbum.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission
                                .WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                            ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest
                                    .permission.WRITE_EXTERNAL_STORAGE}, 1);
                        } else {
                            imageUtils.chooseFromAlbum();
                            circleImageDialog.dismiss();
                        }
                    }
                });
            }
        });
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageUtils.chooseFromAlbum();
                } else {
                    Toast.makeText(MainActivity.this, "你拒绝了应用获取读取SD卡的权限，想要读取SD卡请授权", Toast.LENGTH_SHORT).show();
                }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case ImageUtils.TAKE_PHOTO:
                if (resultCode == RESULT_OK) {
                    if (imageUtils.hasSDCard()) {
                        File tempFile = new File(getExternalCacheDir(), "photo_image.jpg");
                        Uri tempUri = null;
                        if (Build.VERSION.SDK_INT >= 24) {
                            tempUri = FileProvider.getUriForFile(MainActivity.this, "com.anrikuwen.mydiary.mainactivity.fileprovider", tempFile);
                        } else {
                            tempUri = Uri.fromFile(tempFile);
                        }
                        imageUtils.cropRawPhoto(tempUri);
                    } else {
                        Toast.makeText(MainActivity.this, "没有SD卡", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            case ImageUtils.CHOOSE_FROM_ALBUM:
                String imagePath;
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        imagePath = imageUtils.handleOnKitKat(data);
                    } else {
                        imagePath = imageUtils.handleBeforeKitKat(data);
                    }
                    Bitmap bitmap = imageUtils.compressImage(imagePath, 200, 200);
                    circleImageView.setImageBitmap(bitmap);
                    storeImageBitmap(bitmap);
                }
                break;
            case ImageUtils.CUT_IMAGE:
                if (data != null) {
                    Bundle bundle = data.getExtras();
                    if (bundle != null) {
                        Bitmap bitmap = bundle.getParcelable("data");
                        circleImageView.setImageBitmap(bitmap);
                        storeImageBitmap(bitmap);
                    }
                }
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return true;
    }

    private void storeImageBitmap(Bitmap bitmap) {
        ImageData imageData = new ImageData();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        List<ImageData> theImage = DataSupport.where("imageId = ?", "1").find(ImageData.class);
        if (theImage.size() != 0) {
            DataSupport.deleteAll(ImageData.class, "imageId = ?", "1");
        }
        imageData.setImage(baos.toByteArray());
        imageData.setImageId(1);
        imageData.saveFast();
    }


    private void initView() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftNavigationView = (NavigationView) findViewById(R.id.left_nav_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_nav_menu);
        }

        leftNavigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_menu_diary:
                        enterDiary();
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

    private void enterDiary() {
        SharedPreferences preferences = getSharedPreferences("passwordData",MODE_PRIVATE);
        final String password = preferences.getString("password","");
        if (TextUtils.isEmpty(password)) {
            Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
            startActivity(intent);
        } else {
            final Dialog dialog = getDialog();
            dialog.setContentView(R.layout.enter_diary_dialog);
            dialog.show();
            dialogEdit = (EditText) dialog.findViewById(R.id.enter_diary_dialog_edit);
            dialogButton = (Button) dialog.findViewById(R.id.enter_diary_dialog_button);
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if ((String.valueOf(password)).equals(String.valueOf(dialogEdit.getText()))) {
                        Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
                        startActivity(intent);
                        dialog.dismiss();
                    } else {
                        Toast.makeText(MainActivity.this, "输入的密码有错哟，请从新输入", Toast.LENGTH_SHORT).show();
                        dialogEdit.setText("");
                    }
                }
            });
        }
    }

    private void setPassword() {
        SharedPreferences preferences = getSharedPreferences("passwordData",MODE_PRIVATE);
        String password = preferences.getString("password","");
        if (TextUtils.isEmpty(password)) {
            Intent intent = new Intent(MainActivity.this, CreateAndModifyPassword.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, EnsureBeforeModify.class);
            startActivity(intent);
        }
    }


    private Dialog getDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams params= dialogWindow.getAttributes();
        params.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(params);
        return dialog;
    }
}
