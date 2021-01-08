package com.hsjskj.quwen.ui.home.viewmodel;

import android.app.Application;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.hjq.base.mvvm.BaseViewModel;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.HomePublishApi;
import com.hsjskj.quwen.http.request.HomePublishEditApi;
import com.hsjskj.quwen.http.response.TxCosBean;
import com.hsjskj.quwen.ui.copy.CopyRepository;
import com.hsjskj.quwen.ui.home.repository.HomePublishRepository;
import com.hsjskj.quwen.upload.UploadBean;
import com.hsjskj.quwen.upload.UploadCallback;
import com.hsjskj.quwen.upload.UploadListener;
import com.hsjskj.quwen.upload.UploadTxImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:17
 * description   : AndroidProject
 */
public class HomePublishViewModel extends BaseViewModel<HomePublishRepository> {

    public HomePublishViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Boolean> submitPublish(LifecycleOwner lifecycleOwner, String id, String title, String content, List<UploadBean> list, UploadListener listener) {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        if (TextUtils.isEmpty(title)) {
            ToastUtils.show(R.string.home_please_enter_the_title_to_be_published);
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }
        if (TextUtils.isEmpty(content)) {
            ToastUtils.show(R.string.home_please_enter_what_needs_to_be_published);
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }
        if (list.size() <= 1) {
            ToastUtils.show(R.string.home_please_select_the_picture_to_be_published);
            mutableLiveData.postValue(false);
            return mutableLiveData;
        }

        //图片真实路径 过滤掉添加add icon
        ArrayList<UploadBean> objects;
        if ("".equals(list.get(0).getLocalPath()) || TextUtils.isEmpty(list.get(0).getLocalPath())) {
            objects = new ArrayList<>(list.subList(1, list.size()));
        } else {
            objects = new ArrayList<>(list);
        }
        Log.d("TAG", "submitPublish: " + objects);
        //上传图片
        AtomicInteger count = new AtomicInteger(objects.size());
        MutableLiveData<Boolean> picsLiveData = new MutableLiveData<>();
        List<String> remoteUrls = new ArrayList<>();
        picsLiveData.observeForever(o -> {
            if (o) {
                //图片上传成功 -》 提交信息
                if (null == id || TextUtils.isEmpty(id)) {
                    loadHomeVideoList(lifecycleOwner, title, content, remoteUrls).observe(lifecycleOwner, voidHttpData -> {
                        if (voidHttpData != null) {
                            ToastUtils.show(voidHttpData.getMessage());
                            mutableLiveData.postValue(true);
                        } else {
                            mutableLiveData.postValue(false);
                        }
                    });
                } else {
                    loadHomeVideoList(lifecycleOwner, id, title, content, remoteUrls).observe(lifecycleOwner, voidHttpData -> {
                        if (voidHttpData != null) {
                            ToastUtils.show(voidHttpData.getMessage());
                            mutableLiveData.postValue(true);
                        } else {
                            mutableLiveData.postValue(false);
                        }
                    });
                }


            } else {
                ToastUtils.show("图片上传失败");
                mutableLiveData.postValue(false);
            }
        });
        upLoadPics(objects, count, listener, picsLiveData, remoteUrls);
        return mutableLiveData;
    }

    private void upLoadPics(List<UploadBean> list, AtomicInteger count, UploadListener listener
            , MutableLiveData<Boolean> mutableLiveData, List<String> remoteUrls) {
        repository.upLoadPicsNew(list, count, listener, mutableLiveData, remoteUrls);
    }

    public MutableLiveData<HttpData<Void>> loadHomeVideoList(LifecycleOwner lifecycleOwner, String title, String content, List<String> enclosures) {
        MutableLiveData<HttpData<Void>> mutableLiveData = new MutableLiveData<>();
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new HomePublishApi(title, content, enclosures))
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

    public MutableLiveData<HttpData<Void>> loadHomeVideoList(LifecycleOwner lifecycleOwner, String id, String title, String content, List<String> enclosures) {
        MutableLiveData<HttpData<Void>> mutableLiveData = new MutableLiveData<>();
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new HomePublishEditApi(id, title, content, enclosures))
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
