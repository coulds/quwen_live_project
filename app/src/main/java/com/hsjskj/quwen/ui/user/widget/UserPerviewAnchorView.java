package com.hsjskj.quwen.ui.user.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.hjq.base.UiUtlis;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.databinding.LayoutUserAnchorViewBinding;

/**
 * @author : Jun
 * time          : 2020年12月26日 16:40
 * description   : quwen_live
 */
public class UserPerviewAnchorView extends FrameLayout {

    private LayoutUserAnchorViewBinding dataBinding;

    public UserPerviewAnchorView(@NonNull Context context) {
        this(context, null);
    }

    public UserPerviewAnchorView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UserPerviewAnchorView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dataBinding = DataBindingUtil.inflate(LayoutInflater.from(context), R.layout.layout_user_anchor_view, this, true);
        dataBinding.flowLayoutTag.removeAllViews();
        MarginLayoutParams marginLayoutParams = new MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        marginLayoutParams.setMargins(0, 0, UiUtlis.dp2px(getContext(), 8), UiUtlis.dp2px(getContext(), 8));
        for (int i = 0; i < 6; i++) {
            UserPerviewTagView tagView = new UserPerviewTagView(getContext());
            tagView.setLayoutParams(marginLayoutParams);
            tagView.setTextContent("标签");
            if (i == 5) {
                tagView.setTextContent("标签标签标签标签标签标签标签");
            }
            dataBinding.flowLayoutTag.addView(tagView);
        }
    }
}
