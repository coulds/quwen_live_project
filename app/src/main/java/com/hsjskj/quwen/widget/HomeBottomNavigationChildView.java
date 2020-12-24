package com.hsjskj.quwen.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hsjskj.quwen.R;

/**
 * @author : Jun
 * time          : 2020年12月24日 16:02
 * description   : quwen_live
 */
public class HomeBottomNavigationChildView extends LinearLayout {

    private ImageView iconBottom;
    private TextView titleBottom;
    private boolean isCheck = false;
    private ColorStateList homeBottomColorNo;
    private ColorStateList homeBottomColorYes;

    public HomeBottomNavigationChildView(Context context) {
        this(context, null);
    }

    public HomeBottomNavigationChildView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomNavigationChildView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_home_bottom_child_view, this, false);
        iconBottom = view.findViewById(R.id.icon_bottom);
        titleBottom = view.findViewById(R.id.title_bottom);
        addView(view);
        homeBottomColorNo = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.homeBottomColorNo));
        homeBottomColorYes = ColorStateList.valueOf(ContextCompat.getColor(context, R.color.homeBottomColorYes));

        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.HomeBottomNavigationChildView);
        if (array.hasValue(R.styleable.HomeBottomNavigationChildView_check)) {
            boolean isCheck = array.getBoolean(R.styleable.HomeBottomNavigationChildView_check, false);
            setCheck(isCheck);
        }
        if (array.hasValue(R.styleable.HomeBottomNavigationChildView_icon)) {
            int resourceId = array.getResourceId(R.styleable.HomeBottomNavigationChildView_icon, 0);
            setIcon(resourceId);
        }
        if (array.hasValue(R.styleable.HomeBottomNavigationChildView_title)) {
            String title = array.getString(R.styleable.HomeBottomNavigationChildView_title);
            setTitle(title);
        }
        array.recycle();
    }

    private void setTitle(String title) {
        titleBottom.setText(title);
    }

    private void setIcon(int resourceId) {
        iconBottom.setImageResource(resourceId);
    }

    public void setCheck(boolean isCheck) {
        this.isCheck = isCheck;
        upgradeCheckStatus();
    }

    private void upgradeCheckStatus() {
        if (isCheck) {
            iconBottom.setImageTintList(homeBottomColorYes);
            titleBottom.setTextColor(homeBottomColorYes);
        } else {
            iconBottom.setImageTintList(homeBottomColorNo);
            titleBottom.setTextColor(homeBottomColorNo);
        }

    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
    }

}
