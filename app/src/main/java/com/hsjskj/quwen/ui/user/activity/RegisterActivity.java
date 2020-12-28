package com.hsjskj.quwen.ui.user.activity;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.hjq.bar.TitleBar;
import com.hjq.base.BaseDialog;
import com.hjq.widget.view.RegexEditText;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.EditCloseAction;
import com.hsjskj.quwen.aop.CheckNet;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.helper.InputTextHelper;
import com.hsjskj.quwen.other.IntentKey;
import com.hjq.widget.view.CountdownView;
import com.hsjskj.quwen.ui.dialog.GraphicInputDialog;
import com.hsjskj.quwen.ui.user.viewmodel.RegisterViewModel;

import java.util.regex.Pattern;

/**
 * @author : Jun
 * time   : 2020年12月24日13:42:25
 * desc   : 注册界面
 */
public final class RegisterActivity extends MyActivity implements EditCloseAction {

    public static final int REGISTER_TYPE_PHONE = 0;
    public static final int REGISTER_TYPE_MAIL = 1;

    private EditText mPhoneView;
    private EditText mMailView;
    private CountdownView mCountdownView;

    private EditText mCodeView;

    private EditText mPasswordView1;
    private EditText mPasswordView2;

    private Button mCommitView;
    private LinearLayout llTypePhone;
    private LinearLayout llTypeMail;
    private RegisterViewModel registerViewModel;

    /**
     * 当前注册账号类型
     */
    private int type = REGISTER_TYPE_PHONE;
    private InputTextHelper inputTextHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.register_activity;
    }

    @Override
    protected void onDestroy() {
        mCountdownView.stop();
        super.onDestroy();
    }

    @Override
    protected void initView() {
        registerViewModel = new ViewModelProvider(this).get(RegisterViewModel.class);
        mPhoneView = findViewById(R.id.et_register_phone);
        mMailView = findViewById(R.id.et_register_mail);
        mCountdownView = findViewById(R.id.cv_register_countdown);
        mCodeView = findViewById(R.id.et_register_code);
        mPasswordView1 = findViewById(R.id.et_register_password1);
        mPasswordView2 = findViewById(R.id.et_register_password2);
        mCommitView = findViewById(R.id.btn_register_commit);
        llTypePhone = findViewById(R.id.ll_type_phone);
        llTypeMail = findViewById(R.id.ll_type_mail);
        setOnClickListener(mCountdownView, mCommitView);
        setOnClickListener(R.id.cv_register_switch_mail, R.id.cv_register_switch_phone);

        inputTextHelper = InputTextHelper.with(this)
                .addView(mPhoneView)
                .addView(mCodeView)
                .addView(mPasswordView1)
                .addView(mPasswordView2)
                .setMain(mCommitView)
                .build();

        registerViewModel.getCaptchaSend().observe(this, aBoolean -> {
            if (aBoolean) {
                mCountdownView.start();
            }
        });
    }

    @Override
    protected void initData() {

    }

    @SingleClick
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.cv_register_switch_mail) {
            type = REGISTER_TYPE_MAIL;
            upInputStatus();
        } else if (v.getId() == R.id.cv_register_switch_phone) {
            type = REGISTER_TYPE_PHONE;
            upInputStatus();
        } else if (v == mCountdownView) {
            if (!verifyAccount()) {
                return;
            }
            showGraphicInputDialog();
        } else if (v == mCommitView) {
            if (!verifyAccount()) {
                return;
            }
            if (TextUtils.isEmpty(mCodeView.getText().toString().trim())) {
                toast(R.string.common_code_input_hint);
                return;
            }
            if (!verifyPassword()) {
                return;
            }
            register();
        }
    }

    private void register() {
        showDialog();
        registerViewModel.sendRegister(this
                , mCodeView.getText().toString()
                , isPhone()
                , getAccountStr()
                , mPasswordView1.getText().toString()
        ).observe(this, aBoolean -> {
            hideDialog();
            if (aBoolean) {
                setResult(RESULT_OK, new Intent()
                        .putExtra(IntentKey.PHONE, mPhoneView.getText().toString())
                        .putExtra(IntentKey.PASSWORD, mPasswordView1.getText().toString()));
                finish();
            }
        });
    }

    private void showGraphicInputDialog() {
        new GraphicInputDialog.Builder(this)
                .setHint(R.string.home_please_enter_verification_code)
                .setUrlString(registerViewModel.userCaptcha())
                .setListener(new GraphicInputDialog.OnListener() {
                    @Override
                    public void onConfirm(BaseDialog dialog, String content) {
                        sendCodeHttp(content);
                    }

                    @Override
                    public String getCaptchaUrl() {
                        return registerViewModel.userCaptcha();
                    }
                })
                .show();
    }

    @CheckNet
    private void sendCodeHttp(String code) {
        registerViewModel.sendRegisterCode(this, code, isPhone(), getAccountStr());
    }

    private String getAccountStr() {
        if (isPhone()) {
            return mPhoneView.getText().toString();
        } else {
            return mMailView.getText().toString();
        }
    }

    private void upInputStatus() {
        if (isPhone()) {
            llTypeMail.setVisibility(View.GONE);
            llTypePhone.setVisibility(View.VISIBLE);
            inputTextHelper.removeViews(mMailView);
            inputTextHelper.addViews(mPhoneView);
        } else {
            llTypeMail.setVisibility(View.VISIBLE);
            llTypePhone.setVisibility(View.GONE);
            inputTextHelper.removeViews(mPhoneView);
            inputTextHelper.addViews(mMailView);
        }
    }

    private boolean isPhone() {
        return type == REGISTER_TYPE_PHONE;
    }

    private boolean verifyAccount() {
        if (isPhone()) {
            String phone = mPhoneView.getText().toString();
            boolean matches2 = Pattern.compile(RegexEditText.REGEX_MOBILE).matcher(phone).matches();
            if (!matches2) {
                toast(R.string.common_phone_input_error);
                return false;
            }
        } else {
            String mail = mMailView.getText().toString();
            boolean matches = Pattern.compile(RegexEditText.REGEX_EMAIL).matcher(mail).matches();
            if (!matches) {
                toast(R.string.common_mail_input_error);
                return false;
            }
        }
        return true;
    }

    private boolean verifyPassword() {
        String password = mPasswordView1.getText().toString();
        boolean matches = Pattern.compile(RegexEditText.REGEX_PSW).matcher(password).matches();
        if (!matches) {
            toast(R.string.register_password_hint1);
            return false;
        }
        if (!password.equals(mPasswordView2.getText().toString())) {
            toast(R.string.common_password_input_unlike);
            return false;
        }
        return true;
    }

    @Nullable
    @Override
    public TitleBar getTitleBar() {
        TitleBar titleBar = super.getTitleBar();
        if (titleBar != null) {
            titleBar.setLineVisible(false);
        }
        return titleBar;
    }

    @Override
    public boolean isSwipeEnable() {
        return true;
    }

    @Override
    public Activity getCurrentActivity() {
        return this;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        dispatchTouchEventEdit(ev);
        return super.dispatchTouchEvent(ev);
    }
}