package com.hsjskj.quwen.action;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.View;

import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;
import androidx.core.content.ContextCompat;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.widget.HintLayout;

/**
 *    @author : Jun
 *    time   : 2020年12月31日11:10:23
 *    desc   : 界面状态提示（解决一个页面多个需要占位的问题）
 */
public interface StatusTwoAction {

    /**
     * 获取提示布局
     */
    HintLayout getHintLayoutTwo();

    /**
     * 显示加载中
     */
    default void showLoadingTwo() {
        showLoadingTwo(R.raw.loading);
    }

    default void showLoadingTwo(int hint) {
        showLoadingTwo(R.raw.loading,hint);
    }

    default void showLoadingTwo(@RawRes int id, int hintSrcId) {
        HintLayout layout = getHintLayoutTwo();
        layout.show();
        layout.setAnim(id);
        layout.setHint(hintSrcId);
        layout.setOnClickListener(null);
    }

    /**
     * 显示加载完成
     */
    default void showCompleteTwo() {
        HintLayout layout = getHintLayoutTwo();
        if (layout != null && layout.isShow()) {
            layout.hide();
        }
    }

    /**
     * 显示空提示
     */
    default void showEmptyTwo() {
        showLayoutTwo(R.drawable.hint_empty_ic, R.string.hint_layout_no_data, null);
    }

    /**
     * 显示错误提示
     */
    default void showErrorTwo(View.OnClickListener listener) {
        HintLayout layout = getHintLayoutTwo();
        Context context = layout.getContext();
        ConnectivityManager manager = ContextCompat.getSystemService(context, ConnectivityManager.class);
        if (manager != null) {
            NetworkInfo info = manager.getActiveNetworkInfo();
            // 判断网络是否连接
            if (info == null || !info.isConnected()) {
                showLayoutTwo(R.drawable.hint_nerwork_ic, R.string.hint_layout_error_network, listener);
                return;
            }
        }
        showLayoutTwo(R.drawable.hint_error_ic, R.string.hint_layout_error_request, listener);
    }

    /**
     * 显示自定义提示
     */
    default void showLayoutTwo(@DrawableRes int drawableId, @StringRes int stringId, View.OnClickListener listener) {
        HintLayout layout = getHintLayoutTwo();
        Context context = layout.getContext();
        showLayoutTwo(ContextCompat.getDrawable(context, drawableId), context.getString(stringId), listener);
    }

    default void showLayoutTwo(Drawable drawable, CharSequence hint, View.OnClickListener listener) {
        HintLayout layout = getHintLayoutTwo();
        layout.show();
        layout.setIcon(drawable);
        layout.setHint(hint);
        layout.setOnClickListener(listener);
    }
}