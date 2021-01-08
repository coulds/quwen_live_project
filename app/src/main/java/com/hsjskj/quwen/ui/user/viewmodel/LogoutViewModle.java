package com.hsjskj.quwen.ui.user.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.LoginApi;
import com.hsjskj.quwen.http.request.LogoutApi;
import com.hsjskj.quwen.http.response.UserInfoBean;

/**
 * @author : sen
 * time          : 2021年01月08日 16:58
 * description   : quwen_live
 */
public class LogoutViewModle extends ViewModel {



    private  MutableLiveData<Boolean> liveData;
    public MutableLiveData<Boolean> getLiveData() {
        if (liveData == null) {
            liveData = new MutableLiveData<>();
        }
        return liveData;
    }
    public MutableLiveData<Boolean> sendLogout(LifecycleOwner lifecycleOwner, String msg, String data) {

        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new LogoutApi()
                        .setvalue(msg, data)
                )
                .request(new HttpCallback<HttpData>(null) {
                    @Override
                    public void onSucceed(HttpData data) {
                        ToastUtils.show(data.getMessage());
                        liveData.postValue(true);
                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtils.show(e.getMessage());
                        liveData.postValue(true);
                    }
                });
        return liveData;
    }
}
