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
    public static final int CHOOSE_IMAGE_AT_CAROUSEL_FIGURE = 4;
    public ImageUtils(Context context) {
        this.context = context;
    }


    //用相机照相的一个方法
    public void takePhoto() {
        File file = new File(context.getExternalCacheDir(), "photo_image.jpg");
        if (file.exists()) {
            file.delete();
        }
        try {
            file.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (Build.VERSION.SDK_INT >= 24) {
            imageUri = FileProvider.getUriForFile(context, "com.anrikuwen.mydiary.mainactivity.fileprovider", file);
        } else {
            imageUri = Uri.fromFile(file);
        }
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //Android7.0后要加上这个
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        if (hasSDCard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        }
        ((MainActivity) context).startActivityForResult(intent, TAKE_PHOTO);
    }

    //CircleView从相册中选图片的方法
    public void chooseFromAlbum() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        ((MainActivity) context).startActivityForResult(intent, CHOOSE_FROM_ALBUM);
    }

    //轮播图从相册中选的方法
    public void chooseImageAtCarouselFigure() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.putExtra("crop", true);
        intent.putExtra("scale", true);
        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        ((MainActivity) context).startActivityForResult(intent, CHOOSE_IMAGE_AT_CAROUSEL_FIGURE);
    }


    //对从相册中取出的图片进行处理来获取图片的路径，4.4之后由于Uri进行了封装要进行很多的判断
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public String handleOnKitKat(Intent intent) {
        String imagePath = null;
        Uri uri = intent.getData();
        if (DocumentsContract.isDocumentUri(context, uri)) {
            String docId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = docId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=" + id;
                imagePath = getImagePath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, selection);
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads")
                        , Long.valueOf(docId));
                imagePath = getImagePath(contentUri, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            imagePath = getImagePath(uri, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            imagePath = uri.getPath();
        }
        return imagePath;
    }

    //4.4之前全部都当成content类型的Uri来进行处理
    public String handleBeforeKitKat(Intent intent) {
        Uri uri = intent.getData();
        String imagePath = null;
        imagePath = getImagePath(uri, null);
        return imagePath;
    }


    private String getImagePath(Uri imageUri, String selection) {
        String path = null;
        Cursor cursor = context.getContentResolver().query(imageUri, null, selection, null, null);
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            }
            cursor.close();
        }
        return path;
    }

    //对图片进行比例压缩
    public Bitmap compressImage(String imagePath, float pixelW, float pixelH) {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, options);
        options.inJustDecodeBounds = false;

        int width = options.outWidth;
        int height = options.outHeight;
        float mWidth = pixelW;
        float mHeight = pixelH;

        int be = 1;
        if (width > height && width > mWidth) {
            be = (int) (options.outWidth / mWidth);
        } else if (height > width && height > mHeight) {
            be = (int) (options.outHeight / mHeight);
        }

        if (be <= 0) {
            be = 1;
        }
        options.inSampleSize = be;
        bitmap = BitmapFactory.decodeFile(imagePath, options);
        return bitmap;
    }

    //照了相之后对图片进行剪切
    public void cropRawPhoto(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("return-data", true);
        intent.putExtra("noFaceDetection", true);
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 200);
        intent.putExtra("outputY", 200);
        ((MainActivity) context).startActivityForResult(intent, CUT_IMAGE);
    }

    //照相前对手机进行是否有SD卡的判断
    public boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        if (status.equals(Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }
}
