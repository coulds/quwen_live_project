package com.hsjskj.quwen.ui.home.viewmodel;


import android.text.TextUtils;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.alibaba.fastjson.JSON;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hsjskj.quwen.common.MyCacheInfo;
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
    public void loadHomeVideoListCache() {
        try {
            String cache = MyCacheInfo.getInstance().getHomeVideoCache();
            if (!"".equals(cache) && !TextUtils.isEmpty(cache)) {
                List<HomeVideoListBean.DataBean> dataBeans = JSON.parseArray(cache, HomeVideoListBean.DataBean.class);
                if (dataBeans!=null&&!dataBeans.isEmpty()){
                    videoHomeLiveData.postValue(dataBeans);
                }
            }
        } catch (Exception e) {
        }
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
                        videoHomeLiveData.postValue(null);
                    }
                });
    }

}
