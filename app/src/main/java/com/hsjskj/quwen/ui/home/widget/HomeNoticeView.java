package com.hsjskj.quwen.ui.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.widget.MarqueeView;

import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月25日 13:18
 * description   : quwen_live
 */
public class HomeNoticeView extends FrameLayout {

    private MarqueeView marqueeView;

    public HomeNoticeView(@NonNull Context context) {
        this(context, null);
    }

    public HomeNoticeView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeNoticeView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setLayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        View view = LayoutInflater.from(context).inflate(R.layout.layout_home_notice_view, this, false);
        marqueeView = view.findViewById(R.id.marqueeView);
        addView(view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setNotices(List<String> notices) {
        if (marqueeView==null){
            return;
        }
        marqueeView.startWithList(notices);
        marqueeView.setOnItemClickListener((position, textView) -> {
            ToastUtils.show("通知点击");
        });
    }

    public void startFlipping() {
        if (marqueeView != null) {
            marqueeView.startFlipping();
        }
    }

    public void stopFlipping() {
        if (marqueeView != null) {
            marqueeView.stopFlipping();
        }
    }
}
