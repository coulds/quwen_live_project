package com.hsjskj.quwen.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.core.content.ContextCompat;

import com.hsjskj.quwen.R;

/**
 * @author : Jun
 * time          : 2021年01月07日 16:00
 * description   : 关注按钮
 */
public class FollowAppCompatButton extends AppCompatTextView {

    private boolean isFollow = false;
    private Drawable drawableDefalut;
    private Drawable drawableFollow;

    public FollowAppCompatButton(@NonNull Context context) {
        this(context, null);
    }

    public FollowAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FollowAppCompatButton(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.FollowAppCompatButton);
        if (array.getBoolean(R.styleable.FollowAppCompatButton_isFollow, false)) {
            isFollow = true;
        }
        drawableDefalut = ContextCompat.getDrawable(context, R.drawable.me_follow_defalut_bg);
        drawableFollow = ContextCompat.getDrawable(context, R.drawable.me_follow_select_bg);

        upgradeStatusView();
        array.recycle();
    }

    public void setFollow(boolean follow) {
        isFollow = follow;
        upgradeStatusView();
    }

    public boolean isFollow() {
        return isFollow;
    }

    private void upgradeStatusView() {
        if (isFollow) {
            setBackground(drawableFollow);
            setTextColor(ContextCompat.getColor(getContext(), R.color.colorButtonStart));
        } else {
            setBackground(drawableDefalut);
            setTextColor(ContextCompat.getColor(getContext(), R.color.white));
        }
    }
}
