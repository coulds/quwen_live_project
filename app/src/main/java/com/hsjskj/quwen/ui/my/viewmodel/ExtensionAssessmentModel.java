package com.hsjskj.quwen.ui.my.viewmodel;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.hjq.base.mvvm.BaseViewModel;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.ExtensionAssessPostApi;
import com.hsjskj.quwen.http.request.HomePublishApi;
import com.hsjskj.quwen.ui.my.repository.ExtensionAssessmentRepository;

/**
 * Administrator :ZB
 * 2021/1/7 0007
 * describe :
 **/
public class ExtensionAssessmentModel extends BaseViewModel<ExtensionAssessmentRepository> {
    public ExtensionAssessmentModel(@NonNull Application application) {
        super(application);
    }
    public MutableLiveData<Boolean>  submitExtension(LifecycleOwner lifecycleOwner,String front){
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
       /* if (TextUtils.isEmpty(name)) {
            ToastUtils.show(R.string.extension_name);
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }
        if (TextUtils.isEmpty(mobile)) {
            ToastUtils.show(R.string.extension_phone);
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }
        if (TextUtils.isEmpty(front)) {
            ToastUtils.show(R.string.extension_positive);
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }
        if (TextUtils.isEmpty(front)) {
            ToastUtils.show(R.string.extension_verso);
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }*/
        MutableLiveData<Boolean> picsLiveData = new MutableLiveData<>();
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
    public MutableLiveData<HttpData<Void>>  loadExtension(LifecycleOwner lifecycleOwner,String name,String mobile,String idCard,String front,String back){
        MutableLiveData<HttpData<Void>> mutableLiveData = new MutableLiveData<>();
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new ExtensionAssessPostApi(name, mobile, idCard,front,back))
                .request(new HttpCallback<HttpData<Void>>(null) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        mutableLiveData.postValue(data);
                    }

                    @Override
                    public void onFail(Exception e) {
                        mutableLiveData.postValue(null);
                        ToastUtils.show(""+e.getMessage());
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
