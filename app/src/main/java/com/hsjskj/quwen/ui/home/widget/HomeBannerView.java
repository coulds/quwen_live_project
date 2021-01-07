package com.hsjskj.quwen.ui.home.widget;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.alibaba.fastjson.JSON;
import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyCacheInfo;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.http.response.BannerBean;
import com.hsjskj.quwen.ui.home.activity.ConstellationActivity;
import com.makeramen.roundedimageview.RoundedImageView;
import com.youth.banner.Banner;
import com.youth.banner.adapter.BannerImageAdapter;
import com.youth.banner.holder.BannerImageHolder;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.listener.OnBannerListener;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月24日 16:59
 * description   : quwen_live
 */
public class HomeBannerView extends FrameLayout implements OnBannerListener<BannerBean> {

    private Banner<BannerBean, BannerImageAdapter> banner;
    private List<BannerBean> bannerPic;
    private MarginLayoutParams params;

    public HomeBannerView(@NonNull Context context) {
        this(context, null);
    }

    public HomeBannerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBannerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayoutInflater.from(context).inflate(R.layout.layout_home_banner, this);

        banner = findViewById(R.id.banner);
        bannerPic = new ArrayList<>();
        params = new MarginLayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(UiUtlis.dp2px(getContext(), 15)
                , UiUtlis.dp2px(getContext(), 0)
                , UiUtlis.dp2px(getContext(), 15)
                , UiUtlis.dp2px(getContext(), 0));
        banner.setIndicator(new CircleIndicator(getContext()));
        banner.start();

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    @Override
    public void onViewRemoved(View child) {
        super.onViewRemoved(child);
        if (banner != null) {
            banner.stop();
        }
    }

    public void setBannerPic(List<BannerBean> b) {
        if (b == null) {
            return;
        }
        this.bannerPic.clear();
        this.bannerPic.addAll(b);
        MyCacheInfo.getInstance().setHomeBannerCache(JSON.toJSONString(b));
        this.banner.setAdapter(new BannerImageAdapter<BannerBean>(bannerPic) {

            @Override
            public BannerImageHolder onCreateHolder(ViewGroup parent, int viewType) {
                RoundedImageView imageView = new RoundedImageView(parent.getContext());
                //注意，必须设置为match_parent，这个是viewpager2强制要求的
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setCornerRadius(dp2px(8));
                return new BannerImageHolder(imageView);
            }

            @Override
            public void onBindView(BannerImageHolder holder, BannerBean data, int position, int size) {
                //图片加载自己实现
                GlideApp.with(getContext())
                        .load(data.pic)
                        .placeholder(R.mipmap.default_image)
                        .error(R.mipmap.default_image)
//                        .transform(new RoundedCorners((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
//                                , 20, getContext().getResources().getDisplayMetrics())))
                        .into(holder.imageView);
            }
        });
        banner.setOnBannerListener(this);
    }

    @Override
    public void OnBannerClick(BannerBean data, int position) {
        getContext().startActivity(new Intent(getContext(), ConstellationActivity.class));
    }

    public int dp2px(float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getContext().getResources().getDisplayMetrics()) + 0.5f);
    }
}
