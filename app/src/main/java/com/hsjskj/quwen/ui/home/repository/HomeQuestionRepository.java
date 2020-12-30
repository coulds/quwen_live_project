package com.hsjskj.quwen.ui.home.repository;


import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.hjq.base.mvvm.BaseRepository;
import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.HomePublishInfoApi;
import com.hsjskj.quwen.http.response.HomePublishBean;

/**
 * @author : Jun
 * time          : 2020年12月22日 14:21
 * description   : AndroidProject
 */
public class HomeQuestionRepository extends BaseRepository {
    private MutableLiveData<HomePublishBean.DataBean> mLiveDataDetails;

    public MutableLiveData<HomePublishBean.DataBean> getLiveDataDetails() {
        if (mLiveDataDetails == null) {
            mLiveDataDetails = new MutableLiveData<>();
        }
        return mLiveDataDetails;
    }

    public void loadPublishInfo(LifecycleOwner lifecycleOwner, String id) {
        EasyHttp.post(lifecycleOwner)
                .api(new HomePublishInfoApi(id))
                .request(new HttpCallback<HttpData<HomePublishBean.DataBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<HomePublishBean.DataBean> data) {
                        mLiveDataDetails.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mLiveDataDetails.postValue(null);
                    }
                });
    }


    @Override
    public void clear() {
        super.clear();
    }

}
