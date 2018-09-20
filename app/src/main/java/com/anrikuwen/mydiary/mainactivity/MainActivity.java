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
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
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

import com.anrikuwen.mydiary.BaseActivity;
import com.anrikuwen.mydiary.R;
import com.anrikuwen.mydiary.database.CarouselFigureData;
import com.anrikuwen.mydiary.database.ImageData;
import com.anrikuwen.mydiary.diaryfragment.DiaryActivity;
import com.anrikuwen.mydiary.password.CreateAndModifyPassword;
import com.anrikuwen.mydiary.password.EnsureBeforeModify;
import com.anrikuwen.mydiary.settings.Settings;

import org.litepal.crud.DataSupport;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends BaseActivity {

    private DrawerLayout drawerLayout;
    private EditText dialogEdit;
    private Button dialogButton;
    private NavigationView leftNavigationView;
    private View headerView;
    private CircleImageView circleImageView;
    private ImageView takePhoto;
    private ImageView chooseFromAlbum;
    private ImageUtils imageUtils;
    private ImageView nickNameEditImage;
    private ImageView beliefEditImage;
    private TextView nickNameText;
    private TextView beliefText;
    private int[] imagesId;
    private List<ImageView> imageViews = new ArrayList<>();
    private List<View> views = new ArrayList<>();
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private ScheduledExecutorService executor;
    private int currentItem = -1;
    private int oldPosition = 0;
    private int timeOfCarouselFigure;
    private boolean isExit;
    private Dialog circleImageDialog;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    viewPager.setCurrentItem(currentItem);
                    break;
                case 1:
                    isExit = false;
                    break;
                default:
                    break;
            }
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setNavigationViewListener();

        changeCircleImage();
        restoreCircleImage();
        changeNickNameAndBelief();
        restoreNickNameAndBelief();
        setImagesInMainActivity();
        restoreImagesInMainActivity();
    }


    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences prefTimeOfCarouselFigure = getSharedPreferences("TimeOfCarouselFigure", MODE_PRIVATE);
        timeOfCarouselFigure = prefTimeOfCarouselFigure.getInt("delayTime", 4);
        executor = Executors.newSingleThreadScheduledExecutor();
        executor.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                currentItem = (currentItem + 1) % imagesId.length;
                Message message = new Message();
                message.what = 0;
                handler.sendMessage(message);
            }
        }, 0, timeOfCarouselFigure, TimeUnit.SECONDS);
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (executor != null) {
            executor.shutdown();
            executor = null;
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


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case 1:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageUtils.chooseFromAlbum();
                    circleImageDialog.dismiss();
                } else {
                    Toast.makeText(MainActivity.this, "你拒绝了应用获取读取SD卡的权限，想要读取SD卡请授权", Toast.LENGTH_SHORT).show();
                    circleImageDialog.dismiss();
                }
                break;
            case 2:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    imageUtils.chooseImageAtCarouselFigure();
                } else {
                    Toast.makeText(MainActivity.this, "你拒绝了应用获取读取SD卡的权限，想要读取SD卡请授权", Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            //通过照相获取头像，然后进行处理
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
            //通过从相册中获取图片然后进行处理
            case ImageUtils.CHOOSE_FROM_ALBUM:
                String imagePath;
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        imagePath = imageUtils.handleOnKitKat(data);
                    } else {
                        imagePath = imageUtils.handleBeforeKitKat(data);
                    }
                    Bitmap imageBitmap = imageUtils.compressImage(imagePath, 400, 400);
                    circleImageView.setImageBitmap(imageBitmap);
                    storeImageBitmap(imageBitmap);
                }
                break;
            //对轮播图从相册中获取的图片进行出来
            case ImageUtils.CHOOSE_IMAGE_AT_CAROUSEL_FIGURE:
                String carouselFigureImagePath;
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= 19) {
                        carouselFigureImagePath = imageUtils.handleOnKitKat(data);
                    } else {
                        carouselFigureImagePath = imageUtils.handleBeforeKitKat(data);
                    }
                    Bitmap carouselFigureBitmap = imageUtils.compressImage(carouselFigureImagePath, 400, 800);
                    imageViews.get(currentItem).setImageBitmap(carouselFigureBitmap);
                    storeCarouselFigureBitmap(carouselFigureBitmap);
                }
                break;
            //照相后进行剪切
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

    //此方法用于双击back键退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    //在这里使用了handler来实现2秒内用户无操作isExit自动切换为false
    private void exit() {
        if (!isExit) {
            isExit = true;
            Toast.makeText(MainActivity.this, "再按一次退出键，退出应用", Toast.LENGTH_SHORT).show();
            Message message = new Message();
            message.what = 1;
            handler.sendMessageDelayed(message, 2000);
        } else {
            finish();
            System.exit(0);
        }
    }


    //进行初始化控件
    private void initView() {
        isExit = false;
        imageUtils = new ImageUtils(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        leftNavigationView = (NavigationView) findViewById(R.id.left_nav_view);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.mipmap.ic_nav_menu);
        }
        //获取NavigationView的HeaderView通过它去实例化HeaderView中的控件
        headerView = leftNavigationView.getHeaderView(0);
    }

    //设置NavigationView菜单的点击事件
    private void setNavigationViewListener() {
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
                    case R.id.nav_menu_settings:
                        enterSetting();
                    default:
                        break;
                }
                return true;
            }
        });
    }


    //设置密码
    private void setPassword() {
        SharedPreferences preferences = getSharedPreferences("PasswordData", MODE_PRIVATE);
        String password = preferences.getString("password", "");
        if (TextUtils.isEmpty(password)) {
            Intent intent = new Intent(MainActivity.this, CreateAndModifyPassword.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(MainActivity.this, EnsureBeforeModify.class);
            startActivity(intent);
        }
    }


    //如果自定义了图片，重启程序后进行轮播图片的恢复
    private void restoreImagesInMainActivity() {
        List<CarouselFigureData> carouselFigureDatas;
        carouselFigureDatas = DataSupport.findAll(CarouselFigureData.class);
        if (carouselFigureDatas.size() != 0) {
            for (CarouselFigureData carouselFigureData : carouselFigureDatas) {
                Bitmap carouselFigureBitmap = BitmapFactory.decodeByteArray(carouselFigureData.getImage(), 0, carouselFigureData.getImage().length);
                imageViews.get(carouselFigureData.getImageId()).setImageBitmap(carouselFigureBitmap);
            }
        }
    }


    //对轮播图片的具体设置
    private void setImagesInMainActivity() {
        imagesId = new int[]{R.drawable.main_activity_image1, R.drawable.main_activity_image2
                , R.drawable.main_activity_image3, R.drawable.main_activity_image4, R.drawable.main_activity_image5};

        for (int i = 0; i < imagesId.length; i++) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setImageResource(imagesId[i]);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                            != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 2);
                    } else {
                        imageUtils.chooseImageAtCarouselFigure();
                    }
                    return true;
                }
            });

            imageViews.add(imageView);
        }
        views.add(findViewById(R.id.main_activity_dot_1));
        views.add(findViewById(R.id.main_activity_dot_2));
        views.add(findViewById(R.id.main_activity_dot_3));
        views.add(findViewById(R.id.main_activity_dot_4));
        views.add(findViewById(R.id.main_activity_dot_5));
        views.get(0).setBackgroundResource(R.drawable.dot_focused);

        viewPager = (ViewPager) findViewById(R.id.main_activity_view_pager);
        myViewPagerAdapter = new MyViewPagerAdapter(MainActivity.this, imageViews);
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
                views.get(position).setBackgroundResource(R.drawable.dot_focused);
                views.get(oldPosition).setBackgroundResource(R.drawable.dot_normal);
                oldPosition = position;
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    //如果对昵称进行了修改，这里实现重启程序后的恢复
    private void restoreNickNameAndBelief() {
        SharedPreferences pref = getSharedPreferences("HeaderViewText", MODE_PRIVATE);
        String nickName = pref.getString("nickName", "");
        String belief = pref.getString("belief", "");
        if (!TextUtils.isEmpty(nickName)) {
            nickNameText.setText(nickName);
        }
        if (!TextUtils.isEmpty(belief)) {
            beliefText.setText(belief);
        }
    }

    //进行具体的昵称以及信仰的修改
    private void changeNickNameAndBelief() {
        nickNameEditImage = (ImageView) headerView.findViewById(R.id.left_nav_view_nickname_edit_image);
        beliefEditImage = (ImageView) headerView.findViewById(R.id.left_nav_view_belief_edit_image);
        nickNameText = (TextView) headerView.findViewById(R.id.left_nav_view_name);
        beliefText = (TextView) headerView.findViewById(R.id.left_nav_view_your_belief);

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
                        SharedPreferences pref = getSharedPreferences("HeaderViewText", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("nickName", editText.getText().toString());
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
                        SharedPreferences pref = getSharedPreferences("HeaderViewText", MODE_PRIVATE);
                        SharedPreferences.Editor editor = pref.edit();
                        editor.putString("belief", editText.getText().toString());
                        editor.apply();
                        beliefDialog.dismiss();
                    }
                });
            }
        });
    }

    //如果对头像进行了修改，在这里进行重启后的恢复
    private void restoreCircleImage() {
        List<ImageData> theImage = DataSupport.where("imageId = ?", "1").find(ImageData.class);
        if (theImage.size() != 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(theImage.get(0).getImage(), 0, theImage.get(0).getImage().length);
            circleImageView.setImageBitmap(bitmap);
        }
    }


    //对头像的具体修改，包含照相以及从相册中读取
    private void changeCircleImage() {
        circleImageView = (CircleImageView) headerView.findViewById(R.id.left_nav_circle_image);
        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                circleImageDialog = getDialog();
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


    //如果对轮播图片进行了修改，在这里进行图片的存储
    private void storeCarouselFigureBitmap(Bitmap carouselFigureBitmap) {
        CarouselFigureData carouselFigureData = new CarouselFigureData();
        List<CarouselFigureData> carouselFigureDatas;
        carouselFigureDatas = DataSupport.where("imageId = ?", "currentItem").find(CarouselFigureData.class);
        if (carouselFigureDatas.size() != 0) {
            DataSupport.deleteAll("imageId = ?", "currentItem");
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        carouselFigureBitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        carouselFigureData.setImageId(currentItem);
        carouselFigureData.setImage(baos.toByteArray());
        carouselFigureData.saveFast();
    }

    //如果对头像进行了修改，在这里进行头像的存储
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


    //进入设置前的逻辑判断
    private void enterSetting() {
        SharedPreferences prefPasswordData = getSharedPreferences("PasswordData", MODE_PRIVATE);
        final String password = prefPasswordData.getString("password", "");
        SharedPreferences prefPasswordSelectData = getSharedPreferences("PasswordSelectData", MODE_PRIVATE);
        boolean isEnterSettingPasswordChecked = prefPasswordSelectData.getBoolean("isEnterSettingPasswordChecked", true);
        if (isEnterSettingPasswordChecked) {
            if (TextUtils.isEmpty(password)) {
                Intent intent = new Intent(MainActivity.this, Settings.class);
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
                            Intent intent = new Intent(MainActivity.this, Settings.class);
                            startActivity(intent);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(MainActivity.this, "输入的密码有错哟，请从新输入", Toast.LENGTH_SHORT).show();
                            dialogEdit.setText("");
                        }
                    }
                });
            }
        } else {
            Intent intent = new Intent(MainActivity.this, Settings.class);
            startActivity(intent);
        }
    }

    //进入日记前的逻辑判断
    private void enterDiary() {
        SharedPreferences prefPasswordData = getSharedPreferences("PasswordData", MODE_PRIVATE);
        final String password = prefPasswordData.getString("password", "");
        SharedPreferences prefPasswordSelectData = getSharedPreferences("PasswordSelectData", MODE_PRIVATE);
        boolean isEnterDiaryPasswordChecked = prefPasswordSelectData.getBoolean("isEnterDiaryPasswordChecked", true);
        if (isEnterDiaryPasswordChecked) {
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
        } else {
            Intent intent = new Intent(MainActivity.this, DiaryActivity.class);
            startActivity(intent);
        }
    }


    //由于多处会用到Dialog为了减少重复代码，这里添加了一个获取Dialog的方法
    private Dialog getDialog() {
        Dialog dialog = new Dialog(MainActivity.this);
        Window dialogWindow = dialog.getWindow();
        WindowManager.LayoutParams params = dialogWindow.getAttributes();
        dialogWindow.setWindowAnimations(R.style.DialogAnim);
        params.gravity = Gravity.CENTER;
        dialogWindow.setAttributes(params);
        return dialog;
    }
}
