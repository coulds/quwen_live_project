package com.hjq.base;

import android.content.Context;
import android.util.TypedValue;

/**
 * @author : Jun
 * time          : 2020年12月25日 16:20
 * description   : quwen_live
 */
public class UiUtlis {
    public static int dp2px(Context context,float value) {
        return (int) (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, value, context.getResources().getDisplayMetrics()) + 0.5f);
    }
}
