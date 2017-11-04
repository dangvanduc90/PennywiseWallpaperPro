package com.pennywisewallpaper.artpennywisewallpapershd.object;

/**
 * Created by dangvanduc90 on 11/4/2017.
 */

public class ObjectImage {

    private String imgThumb;
    private String imgSourc;

    public ObjectImage(String imgThumb, String imgSourc) {
        this.imgThumb = imgThumb;
        this.imgSourc = imgSourc;
    }

    public String getImgThumb() {
        return imgThumb;
    }

    public void setImgThumb(String imgThumb) {
        this.imgThumb = imgThumb;
    }

    public String getImgSourc() {
        return imgSourc;
    }

    public void setImgSourc(String imgSourc) {
        this.imgSourc = imgSourc;
    }
}
