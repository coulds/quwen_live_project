package com.hsjskj.quwen.ui.dialog;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.StringRes;

import com.hjq.base.BaseDialog;
import com.hjq.http.EasyConfig;
import com.hjq.http.config.IRequestServer;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.http.glide.GlideApp;

import java.util.Map;
import java.util.TreeMap;

import okhttp3.HttpUrl;

import static kotlin.random.RandomKt.Random;

/**
 * 图形验证码输入对话框
 */
public final class GraphicInputDialog {

    public static final class Builder
            extends BaseDialog.Builder<Builder>
            implements BaseDialog.OnShowListener {

        private OnListener mListener;
        private final EditText mInputView;
        private final ImageView ivCode;
        private boolean mAutoDismiss = true;
        private String captchaId = "";

        public Builder(Context context) {
            super(context);
            setContentView(R.layout.user_graphic_dialog);
            mInputView = findViewById(R.id.input_verification_code);
            ivCode = findViewById(R.id.iv_code);
            setOnClickListener(R.id.btn_commit, R.id.iv_close, R.id.iv_code);
            addOnShowListener(this);
        }

        public Builder setHint(@StringRes int id) {
            return setHint(getString(id));
        }

        public Builder setHint(CharSequence text) {
            mInputView.setHint(text);
            return this;
        }

        public Builder setContent(@StringRes int id) {
            return setContent(getString(id));
        }

        public Builder setContent(CharSequence text) {
            mInputView.setText(text);
            int index = mInputView.getText().toString().length();
            if (index > 0) {
                mInputView.requestFocus();
                mInputView.setSelection(index);
            }
            return this;
        }

        public Builder setListener(OnListener listener) {
            mListener = listener;
            return this;
        }

        public Builder setUrlString(String url) {
            GlideApp.with(getContext()).load(url).into(ivCode);
            return this;
        }

        public Builder setAutoDismiss(boolean dismiss) {
            mAutoDismiss = dismiss;
            return this;
        }

        public void autoDismiss() {
            if (mAutoDismiss) {
                dismiss();
            }
        }

        /**
         * {@link BaseDialog.OnShowListener}
         */
        @Override
        public void onShow(BaseDialog dialog) {
            postDelayed(() -> getSystemService(InputMethodManager.class).showSoftInput(mInputView, 0), 500);
        }

        @SingleClick
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_commit:
                    autoDismiss();
                    if (mListener != null) {
                        mListener.onConfirm(getDialog(), mInputView.getText().toString());
                    }
                    break;
                case R.id.iv_close:
                    autoDismiss();
                    if (mListener != null) {
                        mListener.onCancel(getDialog());
                    }
                    break;
                case R.id.iv_code:
                    loadCaptcha();
                    break;
                default:
                    break;
            }
        }


        private void loadCaptcha() {
            if (mListener==null){
                return;
            }
            mInputView.setText("");
            GlideApp.with(getContext()).load(mListener.getCaptchaUrl()).into(ivCode);
        }
    }

    public interface OnListener {

        /**
         * 点击确定时回调
         */
        void onConfirm(BaseDialog dialog, String content);

        /**
         * 点击取消时回调
         */
        default void onCancel(BaseDialog dialog) {
        }

        String getCaptchaUrl();
    }
}