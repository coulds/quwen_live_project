package com.hsjskj.quwen.ui.home.viewmodel;

import android.app.Application;
import android.text.TextUtils;

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

    public MutableLiveData<Boolean> submitPublish(LifecycleOwner lifecycleOwner, String title, String content, List<Object> list, UploadListener listener) {
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

        //图片真实路径
        ArrayList<Object> objects = new ArrayList<>(list.subList(1, list.size()));
        //上传图片
        AtomicInteger count = new AtomicInteger(objects.size());
        MutableLiveData<Boolean> picsLiveData = new MutableLiveData<>();
        List<String> remoteUrls = new ArrayList<>();
        picsLiveData.observeForever(o -> {
            if (o) {
                //图片上传成功 -》 提交信息
                loadHomeVideoList(lifecycleOwner, title, content, remoteUrls).observe(lifecycleOwner, voidHttpData -> {
                    if (voidHttpData != null) {
                        ToastUtils.show(voidHttpData.getMessage());
                        mutableLiveData.postValue(true);
                    } else {
                        ToastUtils.show("发布失败");
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
        String s = (String) list.get(0);
        listener.upload(new UploadBean(s), new UploadCallback() {
            @Override
            public void onSuccess(UploadBean bean) {
                //给后台传name就行
                remoteUrls.add(bean.getFileName());
                if (count.decrementAndGet() == 0) {
                    mutableLiveData.postValue(true);
                    return;
                }
                list.remove(0);
                upLoadPics(list, count, listener, mutableLiveData, remoteUrls);
            }

            @Override
            public void onFailure() {
                mutableLiveData.postValue(false);
            }
        });

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
                    }
                });
        return mutableLiveData;
    }

    private MutableLiveData<TxCosBean> txCosLiveBean;

    public MutableLiveData<TxCosBean> getTxCosLiveBean() {
        if (txCosLiveBean == null) {
            txCosLiveBean = new MutableLiveData<>();
        }
        return txCosLiveBean;
    }

    public void loadTxCos(LifecycleOwner lifecycleOwner) {
        EasyHttp.post(lifecycleOwner)
                .api("Config/cos")
                .request(new HttpCallback<HttpData<TxCosBean>>(null) {

                    @Override
                    public void onSucceed(HttpData<TxCosBean> data) {
                        txCosLiveBean.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        txCosLiveBean.postValue(null);
                        ToastUtils.show(e.getMessage());
                    }
                });
    }

    @Override
    protected void onCleared() {
        EasyHttp.cancel(this);
        super.onCleared();
    }
}
