package com.hsjskj.quwen.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.hsjskj.quwen.R;

/**
 * @author : Jun
 * time          : 2020年12月24日 16:02
 * description   : quwen_live
 */
public class HomeBottomNavigationView extends LinearLayout implements View.OnClickListener {
    private ItemSelectedListener listener;
    private ViewGroup view;

    public HomeBottomNavigationView(Context context) {
        this(context, null);
    }

    public HomeBottomNavigationView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeBottomNavigationView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        view = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.layout_home_bottom_view, this, false);
        view.findViewById(R.id.bottom_0).setOnClickListener(this);
        view.findViewById(R.id.bottom_1).setOnClickListener(this);
        view.findViewById(R.id.bottom_2).setOnClickListener(this);
        view.findViewById(R.id.bottom_3).setOnClickListener(this);
        view.findViewById(R.id.bottom_4).setOnClickListener(this);
        addView(view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

    }

    public void setOnNavigationItemSelectedListener(ItemSelectedListener listener) {
        this.listener = listener;
    }

    @Override
    public void onClick(View v) {
        if (listener == null) {
            return;
        }
        setOtherStatus();
        if (v instanceof HomeBottomNavigationChildView) {
            ((HomeBottomNavigationChildView) v).setCheck(true);
        }
        if (v.getId() == R.id.bottom_0) {
            listener.onNavigationItemSelected(0);
        } else if (v.getId() == R.id.bottom_1) {
            listener.onNavigationItemSelected(1);
        } else if (v.getId() == R.id.bottom_2) {
            listener.onNavigationItemSelected(2);
        } else if (v.getId() == R.id.bottom_3) {
            listener.onNavigationItemSelected(3);
        } else if (v.getId() == R.id.bottom_4) {
            listener.onNavigationItemSelected(4);
        }
    }

    private void setOtherStatus() {
        int childCount = view.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = view.getChildAt(i);
            if (childAt instanceof HomeBottomNavigationChildView) {
                ((HomeBottomNavigationChildView) childAt).setCheck(false);
            }
        }
    }

    public interface ItemSelectedListener {
        boolean onNavigationItemSelected(int position);
    }
}
