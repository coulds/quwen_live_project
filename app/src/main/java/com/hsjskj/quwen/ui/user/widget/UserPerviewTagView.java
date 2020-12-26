package com.hsjskj.quwen.ui.user.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.hsjskj.quwen.R;

/**
 * @author : Jun
 * time          : 2020年12月26日 16:40
 * description   : quwen_live
 */
public class UserPerviewTagView extends FrameLayout {

    private TextView tv_tag_content;

    public UserPerviewTagView(@NonNull Context context) {
        this(context, null);
    }

    public UserPerviewTagView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserPerviewTagView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.item_tag_user_preview, this);
        tv_tag_content = findViewById(R.id.tv_tag_content);
    }

    public void setTextContent(String txt){
        tv_tag_content.setText(txt);
    }
}
