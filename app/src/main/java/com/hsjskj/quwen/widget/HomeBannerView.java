package com.hsjskj.quwen.widget;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月24日 16:59
 * description   : quwen_live
 */
public class HomeBannerView extends FrameLayout {

    private Banner banner;
    private List<String> bannerPic;

    public HomeBannerView(@NonNull Context context) {
        this(context, null);
    }

    public HomeBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = LayoutInflater.from(context).inflate(R.layout.layout_home_banner, this, false);

        banner = view.findViewById(R.id.banner);
        bannerPic = new ArrayList<>();
        addView(view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        banner.setAdapter(bannerImageAdapter);
        banner.setIndicator(new CircleIndicator(getContext()));
    }

    public void addBannerLifecycleObserver(FragmentActivity activity) {
        //添加生命周期观察者
        banner.addBannerLifecycleObserver(activity);
    }

    public void setBannerPic(List<String> bannerPic) {
        if (bannerPic == null) {
            return;
        }
        this.bannerPic.addAll(bannerPic);
        this.bannerImageAdapter.notifyDataSetChanged();
    }

    public BannerImageAdapter bannerImageAdapter = new BannerImageAdapter<String>(bannerPic) {
        @Override
        public void onBindView(BannerImageHolder holder, String data, int position, int size) {
            //图片加载自己实现
            GlideApp.with(holder.itemView).load("").transform(new RoundedCorners(30)).into(holder.imageView);
        }
    };
}
