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
import androidx.fragment.app.FragmentActivity;

import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.http.glide.GlideApp;
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
public class HomeBannerView extends FrameLayout implements OnBannerListener<String> {

    private Banner<String, BannerImageAdapter> banner;
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

        banner.setIndicator(new CircleIndicator(getContext()));
        banner.start();
        addView(view);
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

    public void setBannerPic(List<String> b) {
        if (b == null) {
            return;
        }
        this.bannerPic.clear();
        this.bannerPic.addAll(b);
        this.banner.setAdapter(new BannerImageAdapter<String>(bannerPic) {

            @Override
            public BannerImageHolder onCreateHolder(ViewGroup parent, int viewType) {
                RoundedImageView imageView = new RoundedImageView(parent.getContext());
                //注意，必须设置为match_parent，这个是viewpager2强制要求的
                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT);
                imageView.setLayoutParams(params);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                imageView.setCornerRadius(dp2px(8));
                return new BannerImageHolder(imageView);
            }

            @Override
            public void onBindView(BannerImageHolder holder, String data, int position, int size) {
                //图片加载自己实现
                GlideApp.with(getContext())
                        .load(data)
                        .transform(new RoundedCorners((int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP
                                , 20, getContext().getResources().getDisplayMetrics())))
                        .into(holder.imageView);
            }
        });
        banner.setOnBannerListener(this);
    }

    @Override
    public void OnBannerClick(String data, int position) {
        ToastUtils.show("轮播图点击");
        getContext().startActivity(new Intent(getContext(), ConstellationActivity.class));
    }

    public int dp2px(float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, getContext().getResources().getDisplayMetrics()) + 0.5f);
    }
}
