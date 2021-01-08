package com.hsjskj.quwen.ui.dialog;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;

import com.hjq.base.BaseDialog;
import com.hsjskj.quwen.R;

/**
 * Administrator :ZB
 * 2021/1/7 0007
 * describe :
 **/
public final class ExtensionDialog {
    public static final class Builder extends BaseDialog.Builder<Builder>{
        private final Button buttonSure;
        public Builder(Context context) {
            super(context);
            setContentView(R.layout.dialog_extension);
            setAnimStyle(BaseDialog.ANIM_TOAST);
            setBackgroundDimEnabled(true);
            setCancelable(false);
            setGravity(Gravity.CENTER);

            buttonSure=findViewById(R.id.button_sure);
            buttonSure.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dismiss();
                }
            });
        }
    }
}
