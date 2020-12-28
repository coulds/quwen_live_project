package com.hsjskj.quwen.ui.user.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.LoginApi;
import com.hsjskj.quwen.http.response.LoginBean;

/**
 * @author : Jun
 * time          : 2020年12月28日 13:55
 * description   : quwen_live
 */
public class LoginViewModel extends ViewModel {

    public MutableLiveData<LoginBean> sendLogin(LifecycleOwner lifecycleOwner, String username, String password) {
        MutableLiveData<LoginBean> liveData = new MutableLiveData<>();
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new LoginApi()
                        .setUsername(username)
                        .setPassword(password)
                )
                .request(new HttpCallback<HttpData<LoginBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<LoginBean> data) {
                        ToastUtils.show(data.getMessage());
                        liveData.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtils.show(e.getMessage());
                        liveData.postValue(null);
                    }
                });
        return liveData;
    }
}
