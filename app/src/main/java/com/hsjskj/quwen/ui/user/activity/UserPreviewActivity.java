package com.hsjskj.quwen.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;

import androidx.core.widget.NestedScrollView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.ui.user.viewmodel.UserPreviewViewModel;

/**
 * @author : Jun
 * time          : 2020年12月26日 15:17
 * description   : 用户信息 这里区分主播和非主播身份
 */
public class UserPreviewActivity extends MyMvvmActivity<UserPreviewViewModel> {

    private NestedScrollView scrollView;
    private int height = 80;

    public static void start(Context context) {
        Intent intent = new Intent(context, UserPreviewActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_preview_user;
    }

    @Override
    protected void initView() {
        super.initView();
        scrollView = findViewById(R.id.nestedScrollView);
        scrollView.post(() -> {
            if (getTitleBar() != null) {
                height = getTitleBar().getHeight();
            }
        });
        scrollView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                if (scrollY > (height / 2)) {
                    setLeftIcon(R.drawable.arrows_left_ic);
                } else {
                    setLeftIcon(R.drawable.arrow_back_fff);
                }
                if (scrollY >= height) {
                    setTitleBarColor(Color.WHITE);
                } else if (scrollY >= 0) {
                    float persent = scrollY * 1f / (height);
                    int alpha = (int) (255 * persent);
                    int color = Color.argb(alpha, 255, 255, 255);
                    setTitleBarColor(color);
                }
            }
        });
    }

    public void setTitleBarColor(int color) {
        if (getTitleBar() != null) {
            getTitleBar().setBackgroundColor(color);
        }
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
}
