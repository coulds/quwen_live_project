package com.hsjskj.quwen.ui.user.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.UserSetBirthApi;
import com.hsjskj.quwen.http.request.UserSetSexApi;
import com.hsjskj.quwen.http.request.UsersetNickApi;

/**
 * @author : sen
 * time          : 2020年12月31日 09:17
 * description   : quwen_live
 */
public class UserInfoViewModel extends ViewModel {

    private  MutableLiveData<String> mutableLiveData;

    public MutableLiveData<String> getUserInfoBean() {
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }
    public void loadUserInfoBean(LifecycleOwner lifecycleOwner, String value) {
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new UserSetBirthApi().setValue(value))
                .request(new HttpCallback<HttpData<Void>>(null) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        mutableLiveData.postValue(value);
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        mutableLiveData.postValue("");
                    }
                });
    }



    private  MutableLiveData<Integer> mutableLiveSexData;
    public MutableLiveData<Integer> getUserInfoSexBean() {
        if (mutableLiveSexData == null) {
            mutableLiveSexData = new MutableLiveData<>();
        }
        return mutableLiveSexData;
    }
    public void loadUserInfoSexBean(LifecycleOwner lifecycleOwner, int value) {
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new UserSetSexApi().setvalue(value))
                .request(new HttpCallback<HttpData<Void>>(null) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        mutableLiveSexData.postValue(value);
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        mutableLiveSexData.postValue(0);
                    }
                });
    }


    private  MutableLiveData<String> mutableLiveNickData;
    public MutableLiveData<String> getUserInfoNickBean() {
        if (mutableLiveNickData == null) {
            mutableLiveNickData = new MutableLiveData<>();
        }
        return mutableLiveNickData;
    }
    public void loadUserInfoNickBean(LifecycleOwner lifecycleOwner, String value) {
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new UsersetNickApi().setvalue(value))
                .request(new HttpCallback<HttpData<Void>>(null) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        mutableLiveNickData.postValue("");
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        mutableLiveNickData.postValue("");
                    }
                });
    }






}
