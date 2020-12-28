package com.hsjskj.quwen.action;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * @author : Jun
 * time          : 2020年12月28日 13:11
 * description   : 点击背景关闭输入法
 */
public interface EditCloseAction {

    Activity getCurrentActivity();

    /**
     * 点击空白区域隐藏键盘.
     */
    default void dispatchTouchEventEdit(MotionEvent me) {
        if (getCurrentActivity() != null) {
            if (me.getAction() == MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
                View v = getCurrentActivity().getCurrentFocus();      //得到当前页面的焦点,ps:有输入框的页面焦点一般会被输入框占据
                if (isShouldHideKeyboard(v, me)) { //判断用户点击的是否是输入框以外的区域
                    hideKeyboard(v.getWindowToken());   //收起键盘
                }
            }
        }
    }


    /**
     * 获取InputMethodManager，隐藏软键盘
     *
     * @param token
     */
    default void hideKeyboard(IBinder token) {
        if (token != null && getCurrentActivity() != null) {
            InputMethodManager im = (InputMethodManager) getCurrentActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
            if (im != null) im.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 根据EditText所在坐标和用户点击的坐标相对比，来判断是否隐藏键盘，因为当用户点击EditText时则不能隐藏
     *
     * @param v
     * @param event
     * @return
     */
    default boolean isShouldHideKeyboard(View v, MotionEvent event) {
        if (v != null && (v instanceof EditText)) {  //判断得到的焦点控件是否包含EditText
            int[] l = {0, 0};
            v.getLocationInWindow(l);
            int left = l[0],    //得到输入框在屏幕中上下左右的位置
                    top = l[1],
                    bottom = top + v.getHeight(),
                    right = left + v.getWidth();
            if (event.getX() > left && event.getX() < right
                    && event.getY() > top && event.getY() < bottom) {
                // 点击位置如果是EditText的区域，忽略它，不收起键盘。
                return false;
            } else {
                return true;
            }
        }
        // 如果焦点不是EditText则忽略
        return false;
    }

}
