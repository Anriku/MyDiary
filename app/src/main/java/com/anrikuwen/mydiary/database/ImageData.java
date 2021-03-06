package com.anrikuwen.mydiary.database;

import android.graphics.Bitmap;

import org.litepal.crud.DataSupport;

/**
 * Created by 10393 on 2017/2/14.
 */

public class ImageData extends DataSupport {

    private byte[] image;
    private int imageId;

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
