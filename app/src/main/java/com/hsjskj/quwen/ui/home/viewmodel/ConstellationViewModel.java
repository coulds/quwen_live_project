package com.hsjskj.quwen.ui.home.viewmodel;

import androidx.annotation.IntRange;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.HomeQuestionApi;
import com.hsjskj.quwen.http.response.HomeQuestionBean;

/**
 * @author : Jun
 * time          : 2020年12月30日 09:40
 * description   : quwen_live
 */
public class ConstellationViewModel extends ViewModel {
    private MutableLiveData<HomeQuestionBean> mLiveDataDetails;

    public LiveData<HomeQuestionBean> getLiveDataDetails() {
        if (mLiveDataDetails == null) {
            mLiveDataDetails = new MutableLiveData<>();
        }
        return mLiveDataDetails;
    }

    public void loadHomeQuestion(LifecycleOwner lifecycleOwner, @IntRange(from = 1, to = 2) int sex, String birthday) {
        EasyHttp.post(lifecycleOwner)
                .api(new HomeQuestionApi(sex, birthday))
                .request(new HttpCallback<HttpData<HomeQuestionBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<HomeQuestionBean> data) {
                        mLiveDataDetails.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mLiveDataDetails.postValue(null);
                    }
                });
    }

}
