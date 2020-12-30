package com.hsjskj.quwen.ui.home.viewmodel;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.HomeVideoListApi;
import com.hsjskj.quwen.http.response.HomeVideoListBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:17
 * description   : AndroidProject
 */
public class HomeVideoViewModel extends ViewModel {

    private MutableLiveData<List<HomeVideoListBean.DataBean>> videoHomeLiveData;

    public MutableLiveData<List<HomeVideoListBean.DataBean>> getHomeVideoLiveData() {
        if (videoHomeLiveData == null) {
            videoHomeLiveData = new MutableLiveData<>();
        }
        return videoHomeLiveData;
    }

    public void loadHomeVideoList(LifecycleOwner lifecycleOwner) {
        loadHomeVideoList(lifecycleOwner, 20, 1);
    }

    public void loadHomeVideoList(LifecycleOwner lifecycleOwner, int limit, int page) {
        EasyHttp.post(lifecycleOwner)
                .api(new HomeVideoListApi(limit, page))
                .request(new HttpCallback<HttpData<HomeVideoListBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<HomeVideoListBean> data) {
                        videoHomeLiveData.postValue(data.getData().data);
                    }

                    @Override
                    public void onFail(Exception e) {
                        videoHomeLiveData.postValue(new ArrayList<>());
                    }
                });
    }

}
