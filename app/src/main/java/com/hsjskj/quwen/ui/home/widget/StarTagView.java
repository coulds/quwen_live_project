package com.hsjskj.quwen.ui.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hsjskj.quwen.R;

/**
 * @author : Jun
 * time          : 2020年12月26日 09:31
 * description   : quwen_live
 */
public class StarTagView extends FrameLayout {

    public StarTagView(@NonNull Context context) {
        this(context,null);
    }

    public StarTagView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public StarTagView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_home_star_tag_view,this);
    }
}
