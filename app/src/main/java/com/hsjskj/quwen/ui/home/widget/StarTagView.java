package com.hsjskj.quwen.ui.home.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;

/**
 * @author : Jun
 * time          : 2020年12月26日 09:31
 * description   : quwen_live
 */
public class StarTagView extends FrameLayout {

    private TextView tv_item_constellation;

    public StarTagView(@NonNull Context context) {
        this(context, null);
    }

    public StarTagView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StarTagView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_home_star_tag_view, this);
        tv_item_constellation = findViewById(R.id.tv_item_constellation);
    }

    /**
     * 星座 是否男性 是否设置过性别
     */
    public void setTagText(String s, boolean isMale, boolean isSetMale) {
        if (tv_item_constellation != null) {
            if (s == null || "".equals(s)|| TextUtils.isEmpty(s)) {
                setVisibility(GONE);
            } else {
                setVisibility(VISIBLE);
            }
            tv_item_constellation.setText(s);

            if (isMale) {
                Drawable drawable = null;
                if (isSetMale) {
                    drawable = getResources().getDrawable(R.drawable.home_sex_male, getContext().getTheme());
                    drawable.setBounds(0, 0, UiUtlis.dp2px(getContext(), 12), UiUtlis.dp2px(getContext(), 12));
                }
                tv_item_constellation.setTextColor(ContextCompat.getColor(getContext(), R.color.homeColorMale));
                tv_item_constellation.setBackgroundResource(R.drawable.home_question_sex_blue);
                tv_item_constellation.setCompoundDrawables(drawable, null, null, null);
            } else {
                Drawable drawable = null;
                if (isSetMale) {
                    drawable = getResources().getDrawable(R.drawable.home_sex_female, getContext().getTheme());
                    drawable.setBounds(0, 0, UiUtlis.dp2px(getContext(), 12), UiUtlis.dp2px(getContext(), 12));
                }
                tv_item_constellation.setTextColor(ContextCompat.getColor(getContext(), R.color.homeColorFaMale));
                tv_item_constellation.setBackgroundResource(R.drawable.home_question_sex_pink);
                tv_item_constellation.setCompoundDrawables(drawable, null, null, null);
            }
        }
    }
}
