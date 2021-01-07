package com.hsjskj.quwen.ui.my.viewmodel;

import android.app.Application;
import android.text.TextUtils;

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

        //上传前
        listener.upload(new UploadBean(front), new UploadCallback() {
            @Override
            public void onSuccess(UploadBean frontBean) {
                listener.upload(new UploadBean(back), new UploadCallback() {
                    @Override
                    public void onSuccess(UploadBean backBean) {

                        String fileNameFront = frontBean.getFileName();
                        String fileNameBack = backBean.getFileName();
                        if (TextUtils.isEmpty(name)) {
                            ToastUtils.show(R.string.extension_name);
                            mutableLiveData.postValue(false);
                            return ;
                        }
                        if (TextUtils.isEmpty(mobile)) {
                            ToastUtils.show(R.string.extension_phone);
                            mutableLiveData.postValue(false);
                            return ;
                        }
                        if (TextUtils.isEmpty(front)) {
                            ToastUtils.show(R.string.extension_positive);
                            mutableLiveData.postValue(false);
                            return ;
                        }
                        if (TextUtils.isEmpty(back)) {
                            ToastUtils.show(R.string.extension_verso);
                            mutableLiveData.postValue(false);
                            return ;
                        }
                        loadExtension(lifecycleOwner, name, mobile, idCard, fileNameFront, fileNameBack).observe(lifecycleOwner, mutableLiveData::postValue);
                    }

                    @Override
                    public void onFailure() {
                        ToastUtils.show("身份证后失败");
                    }
                });
            }

            @Override
            public void onFailure() {
                ToastUtils.show("身份证前失败");
            }
        });
//        picsLiveData.observeForever(o -> {
//            if (o) {
//                //图片上传成功 -》 提交信息
//                loadExtension(lifecycleOwner, ).observe(lifecycleOwner, voidHttpData -> {
//                    if (voidHttpData != null) {
//                        ToastUtils.show(voidHttpData.getMessage());
//                        mutableLiveData.postValue(true);
//                    } else {
//                        mutableLiveData.postValue(false);
//                    }
//                });
//            } else {
//                ToastUtils.show("图片上传失败");
//                mutableLiveData.postValue(false);
//            }
//        });
        return mutableLiveData;
    }

    public MutableLiveData<Boolean> loadExtension(LifecycleOwner lifecycleOwner, String name, String mobile, String idCard, String front, String back) {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new ExtensionAssessPostApi(name, mobile, idCard, front, back))
                .request(new HttpCallback<HttpData<Void>>(null) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        mutableLiveData.postValue(true);
                    }

                    @Override
                    public void onFail(Exception e) {
                        mutableLiveData.postValue(false);
                        ToastUtils.show("" + e.getMessage());
                    }
                });

        return mutableLiveData;
    }

    @Override
    protected void onCleared() {
        EasyHttp.cancel(this);
        super.onCleared();
    }
}
