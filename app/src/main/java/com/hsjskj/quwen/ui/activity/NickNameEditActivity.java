package com.hsjskj.quwen.ui.activity;

import android.view.View;
import android.widget.EditText;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.widget.view.ClearEditText;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.CheckNet;
import com.hsjskj.quwen.aop.SingleClick;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.common.MyUserInfo;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.UsersetNickApi;

import okhttp3.Call;

public class NickNameEditActivity extends MyActivity {
    EditText clearEditText;

    @Override
    public int getLayoutId() {
        return R.layout.activity_nickname_edit;
    }

    @Override
    public void initView() {
        clearEditText = findViewById(R.id.et_publish_title);
    }

    @Override
    public void initData() {
    }

    @CheckNet
    @SingleClick
    @Override
    public void onRightClick(View v) {
        String string = clearEditText.getText().toString();
        if (string == null){
            toast("输入不能为空");
        }else {

            EasyHttp.post(this)
                    .tag(this)
                    .api(new UsersetNickApi().setvalue(string))
                    .request(new HttpCallback<HttpData<Void>>(null) {
                        @Override
                        public void onSucceed(HttpData<Void> data) {
                            toast(data.getMessage());
                            MyUserInfo.getInstance().getLogin().user_nickname =string;
                            MyUserInfo.getInstance().upDataUserInfo();
                        }

                        @Override
                        public void onFail(Exception e) {
                            super.onFail(e);
                            toast(e.getMessage());
                        }

                        @Override
                        public void onEnd(Call call) {
                            super.onEnd(call);
                            hideDialog();
                        }
                    });
        }

        showDialog();

    }
}
