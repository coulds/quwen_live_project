package com.hsjskj.quwen.ui.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hjq.permissions.Permission;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.Permissions;
import com.hsjskj.quwen.http.response.NoticeBean;
import com.hsjskj.quwen.ui.activity.BrowserActivity;
import com.hsjskj.quwen.ui.home.activity.HomePublishActivity;
import com.hsjskj.quwen.ui.user.activity.UserProtocolActivity;
import com.hsjskj.quwen.widget.MarqueeView;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月25日 13:18
 * description   : quwen_live
 */
public class HomeNoticeView extends FrameLayout implements View.OnClickListener {

    private MarqueeView marqueeView;
    private List<NoticeBean> notices;

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
        view.findViewById(R.id.tv_publish).setOnClickListener(this);
        addView(view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

    public void setNotices(List<NoticeBean> notices) {
        if (marqueeView == null || notices == null) {
            return;
        }
        this.notices = notices;
        List<String> objects = new ArrayList<>();
        for (int i = 0; i < notices.size(); i++) {
            objects.add("" + notices.get(i).title);
        }
        marqueeView.startWithList(objects);
        marqueeView.setOnItemClickListener((position, textView) -> {
            UserProtocolActivity.start(getContext(), UserProtocolActivity.ID_NOTICE, notices.get(position).id);
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

    @Permissions({Permission.READ_EXTERNAL_STORAGE
            , Permission.WRITE_EXTERNAL_STORAGE
            , Permission.READ_PHONE_STATE})
    @Override
    public void onClick(View v) {
        HomePublishActivity.start(getContext());
    }
}
