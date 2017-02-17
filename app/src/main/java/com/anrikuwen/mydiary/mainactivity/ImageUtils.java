package com.anrikuwen.mydiary.mainactivity;

import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.annotation.RequiresApi;
import android.support.v4.content.FileProvider;

import java.io.File;
import java.io.IOException;

/**
 * Created by 10393 on 2017/2/15.
 */

public class ImageUtils {

    private Uri imageUri;
    private Context context;
    public static final int TAKE_PHOTO = 1;
    public static final int CHOOSE_FROM_ALBUM = 2;
    public static final int CUT_IMAGE = 3;

    public ImageUtils(Context context) {
        this.context = context;
    }

    public void takePhoto(){
        File file = new File(context.getExternalCacheDir(),"photo_image.jpg");
        if (file.exists()){
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24){
            imageUri = FileProvider.getUriForFile(context,"com.anrikuwen.mydiary.mainactivity.fileprovider",file);
        }else {
            imageUri = Uri.fromFile(file);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (hasSDCard()){
            intent.putExtra(MediaStore.EXTRA_OUTPUT,imageUri);
        }
        ((MainActivity)context).startActivityForResult(intent,TAKE_PHOTO);
    }

    public void chooseFromAlbum(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop",true);
        intent.putExtra("scale",true);
        intent.putExtra("return-data",true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection",true);
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",200);
        intent.putExtra("outputY",200);
        ((MainActivity)context).startActivityForResult(intent,CHOOSE_FROM_ALBUM);
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String handleOnKitKat(Intent intent){
        String imagePath = null;
        Uri uri = intent.getData();
        if (DocumentsContract.isDocumentUri(context,uri)){
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())){
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,selection);
            }else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())){
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads")
                        ,Long.valueOf(docId));
                imagePath = getImagePath(contentUri,null);
            }
        }else if ("content".equalsIgnoreCase(uri.getScheme())){
            imagePath = getImagePath(uri,null);
        }else if ("file".equalsIgnoreCase(uri.getScheme())){
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    public String handleBeforeKitKat(Intent intent){
        Uri uri = intent.getData();
        String imagePath = null;
        imagePath = getImagePath(uri,null);
        return imagePath;
    }

    private String getImagePath(Uri imageUri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(imageUri,null,selection,null,null);
        if (cursor != null){
            if (cursor.moveToFirst()){
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    public Bitmap compressImage(String imagePath,float pixelW,float pixelH){
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath,options);
        options.inJustDecodeBounds = false;

        int width = options.outWidth;
        int height = options.outHeight;
        float mWidth = pixelW;
        float mHeight = pixelH;

        int be = 1;
        if (width > height && width > mWidth){
            be = (int) (options.outWidth/mWidth);
        }else if (height > width && height > mHeight){
            be = (int) (options.outHeight/mHeight);
        }

        if (be <= 0){
            be = 1;
        }
        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(imagePath,options);
        return bitmap;
    }

    public void cropRawPhoto(Uri uri){
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri,"image/*");
        intent.putExtra("crop","true");
        intent.putExtra("return-data",true);
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY",1);
        intent.putExtra("outputX",200);
        intent.putExtra("outputY",200);
        ((MainActivity)context).startActivityForResult(intent,CUT_IMAGE);
    }

    public boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)){
            return true;
        }else {
            return false;
        }
    }
}
