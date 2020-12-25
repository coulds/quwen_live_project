package com.hsjskj.quwen.other;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.hsjskj.quwen.http.glide.GlideApp;
import com.lzy.ninegrid.NineGridView;

/**
 * @author : Jun
 * time          : 2020年12月25日 15:25
 * description   : quwen_live
 */
public class GlideImageLoader implements NineGridView.ImageLoader {

    @Override
    public void onDisplayImage(Context context, ImageView imageView, String url) {
        GlideApp.with(context).load(url).into(imageView);
    }

    @Override
    public Bitmap getCacheImage(String url) {
        return null;
    }
}
