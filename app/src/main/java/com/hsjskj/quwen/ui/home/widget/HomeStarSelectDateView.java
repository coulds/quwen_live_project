package com.hsjskj.quwen.ui.home.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;

import com.hjq.base.BaseDialog;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.ui.dialog.DateDialog;

/**
 * @author : Jun
 * time          : 2020年12月25日 16:57
 * description   : quwen_live
 */
public class HomeStarSelectDateView extends FrameLayout implements View.OnClickListener {

    private TextView tvTimeData;
    private int year;
    private int month;
    private int day;

    public HomeStarSelectDateView(@NonNull Context context) {
        this(context, null);
    }

    public HomeStarSelectDateView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public HomeStarSelectDateView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        LayoutInflater.from(context).inflate(R.layout.layout_home_star_date_select_view, this);
        tvTimeData = findViewById(R.id.tv_time_data);
        setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // 日期选择对话框
        new DateDialog.Builder(getContext())
                .setTitle(getString(R.string.date_title))
                .setConfirm(getString(R.string.common_confirm))
                .setCancel(getString(R.string.common_cancel))
                .setDay(day)
                .setMonth(month)
                .setYear(year)
                .setListener(new DateDialog.OnListener() {
                    @Override
                    public void onSelected(BaseDialog dialog, int y, int m, int d) {
                        year = y;
                        month = m;
                        day = d;
                        tvTimeData.setText(getDateFormatString());
                    }

                    @Override
                    public void onCancel(BaseDialog dialog) {

                    }
                })
                .show();
    }

    public String getDateFormatString() {
        return year + "-" + month + "-" + day;
    }

    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    private String getString(@StringRes int id) {
        return getContext().getString(id);
    }
}
