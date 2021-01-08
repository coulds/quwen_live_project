package com.hsjskj.quwen.ui.my.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.hjq.base.mvvm.BaseViewModel;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.ExtensionAssessPostApi;
import com.hsjskj.quwen.http.response.TxCosBean;
import com.hsjskj.quwen.ui.home.repository.HomePublishRepository;
import com.hsjskj.quwen.ui.my.Utils.PhoneFormatCheckUtils;
import com.hsjskj.quwen.upload.UploadBean;
import com.hsjskj.quwen.upload.UploadCallback;
import com.hsjskj.quwen.upload.UploadListener;

/**
 * Administrator :ZB
 * 2021/1/7 0007
 * describe :
 **/
public class ExtensionAssessmentModel extends BaseViewModel<HomePublishRepository> {
    public ExtensionAssessmentModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<TxCosBean> getTxCosLiveBean() {
        return repository.getTxCosLiveBean();
    }

    public void loadTxCos(LifecycleOwner lifecycleOwner) {
        repository.loadTxCos(lifecycleOwner);
    }

    public MutableLiveData<Boolean> submitExtension(LifecycleOwner lifecycleOwner, String front, String back
            , String name, String mobile, String idCard, UploadListener listener
    ) {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        boolean isjudge = isjudge(front, back, name, mobile, idCard);
        if (!isjudge) {
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }
        //上传前
        listener.upload(new UploadBean(front), new UploadCallback() {
            @Override
            public void onSuccess(UploadBean frontBean) {
                listener.upload(new UploadBean(back), new UploadCallback() {
                    @Override
                    public void onSuccess(UploadBean backBean) {
                        String fileNameFront = frontBean.getFileName();
                        String fileNameBack = backBean.getFileName();
                        loadExtension(lifecycleOwner, mutableLiveData, name, mobile, idCard, fileNameFront, fileNameBack);
                    }

                    @Override
                    public void onFailure() {
                        ToastUtils.show("身份证后失败");
                        mutableLiveData.postValue(false);
                    }
                });
            }

            @Override
            public void onFailure() {
                ToastUtils.show("身份证前失败");
                mutableLiveData.postValue(false);
            }
        });
//
        return mutableLiveData;
    }

    private boolean isjudge(String front, String back
            , String name, String mobile, String idCard) {
        if (TextUtils.isEmpty(name)) {
            ToastUtils.show(R.string.extension_name);
            return false;
        }
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.show(R.string.extension_phone);

            return false;
        }
        if (TextUtils.isEmpty(idCard)) {
            ToastUtils.show(R.string.extension_code);
            return false;
        }
        if (TextUtils.isEmpty(front)) {
            ToastUtils.show(R.string.extension_positive);
            return false;
        }
        if (TextUtils.isEmpty(back)) {
            ToastUtils.show(R.string.extension_verso);
            return false;
        }
        return true;
    }

    public void loadExtension(LifecycleOwner lifecycleOwner, MutableLiveData<Boolean> mutableLiveData, String name, String mobile, String idCard, String front, String back) {
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new ExtensionAssessPostApi(name, mobile, idCard, front, back))
                .request(new HttpCallback<HttpData>(null) {
                    @Override
                    public void onSucceed(HttpData data) {
                        mutableLiveData.postValue(true);
                    }

                    @Override
                    public void onFail(Exception e) {
                        mutableLiveData.postValue(false);
                        ToastUtils.show("" + e.getMessage());
                    }
                });
    }

    @Override
    protected void onCleared() {
        EasyHttp.cancel(this);
        super.onCleared();
    }
}
