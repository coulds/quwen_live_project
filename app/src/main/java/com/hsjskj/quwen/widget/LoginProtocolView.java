package com.hsjskj.quwen.widget;

import android.content.Context;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.helper.SpannableStringHelper;

/**
 * @author : Jun
 * time          : 2020年12月24日 14:02
 * description   : quwen_live
 */
public class LoginProtocolView extends LinearLayout implements SpannableStringHelper.ProtocolHelperListener {

    private TextView tvLoginProtocol;

    public LoginProtocolView(Context context) {
        this(context, null);
    }

    public LoginProtocolView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoginProtocolView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_login_protocol_view, this, false);
        tvLoginProtocol = view.findViewById(R.id.tv_login_protocol);
        addView(view);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        initProtocol();
    }

    private void initProtocol() {
        String str1 = getResources().getString(R.string.login_protocol_str1);
        String and = getResources().getString(R.string.login_protocol_and);
        String protocol = getResources().getString(R.string.login_protocol_user_1);
        String secrecy = getResources().getString(R.string.login_protocol_user_2);
        tvLoginProtocol.setMovementMethod(LinkMovementMethod.getInstance());
        tvLoginProtocol.setText(SpannableStringHelper.setProtocolString(getContext(), str1, and, protocol, secrecy, this));
        tvLoginProtocol.setHighlightColor(ContextCompat.getColor(getContext(), android.R.color.transparent));
    }

    @Override
    public void protocolClick() {
        ToastUtils.show("用户服务协议");
    }

    @Override
    public void secrecyClick() {
        ToastUtils.show("点击隐私政策");
    }
}
