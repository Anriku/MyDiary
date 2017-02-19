package com.anrikuwen.mydiary.database;

import org.litepal.crud.DataSupport;

/**
 * Created by 10393 on 2017/2/19.
 */

public class CarouselFigureData extends DataSupport {

    private byte[] image;
    private int imageId;

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }
}
