package com.hsjskj.quwen.ui.user.viewmodel;

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
import com.hsjskj.quwen.http.request.HomePublishApi;
import com.hsjskj.quwen.http.request.ProblemBackApi;
import com.hsjskj.quwen.http.request.UserSetBirthApi;
import com.hsjskj.quwen.http.response.TxCosBean;
import com.hsjskj.quwen.ui.home.repository.HomePublishRepository;
import com.hsjskj.quwen.upload.UploadListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : sen
 * time          : 2021年01月07日 09:43
 * description   : quwen_live
 */
public class UserProblemBackViewModel extends BaseViewModel<HomePublishRepository> {


    public UserProblemBackViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> submitfback(LifecycleOwner lifecycleOwner, String phone, String content, List<Object> thumb, UploadListener listener) {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        if (TextUtils.isEmpty(phone)) {
            ToastUtils.show("请输入你的手机号");
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.show("请输入你要反馈的内容");
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }
        if (thumb.size() <= 1) {
            ToastUtils.show(R.string.home_please_select_the_picture_to_be_published);
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }

        //图片真实路径
        ArrayList<Object> objects = new ArrayList<>(thumb.subList(1, thumb.size()));
        //上传图片
        AtomicInteger count = new AtomicInteger(objects.size());
        MutableLiveData<Boolean> picsLiveData = new MutableLiveData<>();
        List<String> remoteUrls = new ArrayList<>();
        picsLiveData.observeForever(o -> {
            if (o) {
                //图片上传成功 -》 提交信息
                problembackList(lifecycleOwner, phone, content, remoteUrls).observe(lifecycleOwner, voidHttpData -> {
                    if (voidHttpData != null) {
                        ToastUtils.show(voidHttpData.getMessage());
                        mutableLiveData.postValue(true);
                    } else {
                        mutableLiveData.postValue(false);
                    }
                });
            } else {
                ToastUtils.show("图片上传失败");
                mutableLiveData.postValue(false);
            }
        });
        upLoadPics(objects, count, listener, picsLiveData, remoteUrls);
        return mutableLiveData;
    }

    private void upLoadPics(List<Object> list, AtomicInteger count, UploadListener listener
            , MutableLiveData<Boolean> mutableLiveData, List<String> remoteUrls) {
        repository.upLoadPics(list, count, listener, mutableLiveData, remoteUrls);
    }

    public MutableLiveData<HttpData<Void>> problembackList(LifecycleOwner lifecycleOwner, String phone, String content, List<String> thumb) {
        MutableLiveData<HttpData<Void>> mutableLiveData = new MutableLiveData<>();
//        EasyHttp.post(lifecycleOwner)
//                .tag(this)
//                .api(new ProblemBackApi(phone, content, thumb))
//                .request(new HttpCallback<HttpData<Void>>(null) {
//                    @Override
//                    public void onSucceed(HttpData<Void> data) {
//                        mutableLiveData.postValue(data);
//                    }
//
//                    @Override
//                    public void onFail(Exception e) {
//                        mutableLiveData.postValue(null);
//                        ToastUtils.show("" + e.getMessage());
//                    }
//                });
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new ProblemBackApi().setvalue(phone,content,thumb))
                .request(new HttpCallback<HttpData<Void>>(null) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        mutableLiveData.postValue(data);
                    }

                    @Override
                    public void onFail(Exception e) {
                        mutableLiveData.postValue(null);
                        ToastUtils.show("" + e.getMessage());
                    }
                });



        return mutableLiveData;
    }


    public MutableLiveData<TxCosBean> getTxCosLiveBean() {
        return repository.getTxCosLiveBean();
    }

    public void loadTxCos(LifecycleOwner lifecycleOwner) {
        repository.loadTxCos(lifecycleOwner);
    }

    @Override
    protected void onCleared() {
        EasyHttp.cancel(this);
        super.onCleared();
    }
}
