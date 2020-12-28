package com.hsjskj.quwen.ui.user.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjq.http.EasyConfig;
import com.hjq.http.EasyHttp;
import com.hjq.http.callback.DownloadCallback;
import com.hjq.http.config.IRequestServer;
import com.hjq.http.listener.HttpCallback;
import com.hjq.http.listener.OnDownloadListener;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.ForgetPasswordApi;
import com.hsjskj.quwen.http.request.GetCodeApi;
import com.hsjskj.quwen.http.request.RegisterApi;

/**
 * @author : Jun
 * time          : 2020年12月28日 10:07
 * description   : quwen_live
 */
public class RegisterViewModel extends ViewModel {

    private String captchaId;

    /**
     * 当前发送验证码成功和失败
     */
    private MutableLiveData<Boolean> captchaSend = new MutableLiveData<>();

    public MutableLiveData<Boolean> getCaptchaSend() {
        return captchaSend;
    }

    public String userCaptcha() {
        captchaId = "" + System.currentTimeMillis();
        IRequestServer server = EasyConfig.getInstance().getServer();
        String url = server.getHost() + server.getPath() + "Tool/photoCode?id=" + captchaId;
        return url;
    }

    public void getCodePic(LifecycleOwner lifecycleOwner, OnDownloadListener listener) {
        EasyHttp.download(lifecycleOwner)
                .tag(this)
                .url(userCaptcha())
                .listener(listener);
    }

    public MutableLiveData<Boolean> sendRegister(LifecycleOwner lifecycleOwner, String code, boolean isPhone, String username, String password) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new RegisterApi()
                        .setUsername(username)
                        .setPassword(password)
                        .setCode(code)
                        .setMode(isPhone ? "1" : "2")
                        .setInvite("")
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
                        liveData.postValue(false);
                    }
                });
        return liveData;
    }

    public MutableLiveData<Boolean> sendForget(LifecycleOwner lifecycleOwner, String code, boolean isPhone, String username, String password) {
        MutableLiveData<Boolean> liveData = new MutableLiveData<>();
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new ForgetPasswordApi()
                        .setUsername(username)
                        .setPassword(password)
                        .setCode(code)
                        .setMode(isPhone ? "1" : "2")
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
                        liveData.postValue(false);
                    }
                });
        return liveData;
    }

    public void sendCode(LifecycleOwner lifecycleOwner, String codeCaptcha, boolean isPhone, String username, String scene) {
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new GetCodeApi()
                        .setId(captchaId)
                        .setUsername(username)
                        .setCode(codeCaptcha)
                        .setMode(isPhone ? "1" : "2")
                        .setScene(scene)
                )
                .request(new HttpCallback<HttpData>(null) {
                    @Override
                    public void onSucceed(HttpData data) {
                        ToastUtils.show(data.getMessage());
                        captchaSend.postValue(true);
                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtils.show(e.getMessage());
                        captchaSend.postValue(false);
                    }
                });
    }

    public void sendRegisterCode(LifecycleOwner lifecycleOwner, String codeCaptcha, boolean isPhone, String username) {
        sendCode(lifecycleOwner, codeCaptcha, isPhone, username, "register");
    }

    public void sendForgetCode(LifecycleOwner lifecycleOwner, String codeCaptcha, boolean isPhone, String username) {
        sendCode(lifecycleOwner, codeCaptcha, isPhone, username, "forget");
    }

    @Override
    protected void onCleared() {
        EasyHttp.cancel(this);
        super.onCleared();
    }
}
