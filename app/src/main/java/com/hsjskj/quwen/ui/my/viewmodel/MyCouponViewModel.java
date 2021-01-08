package com.hsjskj.quwen.ui.my.viewmodel;

import android.util.Log;

import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.CouponGetApi;
import com.hsjskj.quwen.http.request.HomeBannerApi;
import com.hsjskj.quwen.http.request.MyReleasePostApi;
import com.hsjskj.quwen.http.request.ReleaseLeftGetApi;
import com.hsjskj.quwen.http.response.BannerBean;
import com.hsjskj.quwen.http.response.CouponBean;
import com.hsjskj.quwen.http.response.HomePublishBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrator :ZB
 * 2021/1/6 0006
 * describe :
 **/
public class MyCouponViewModel extends ViewModel {
    private MutableLiveData<List<CouponBean.DataBean>> couponBeanMutableLiveData;
    public MutableLiveData<List<CouponBean.DataBean>> getMyCouponLiveData() {
        if (couponBeanMutableLiveData == null) {
            couponBeanMutableLiveData = new MutableLiveData<>();
        }
        return couponBeanMutableLiveData;
    }
    public void loadMyCoupon(LifecycleOwner lifecycleOwner,String mold,int limit,int page){
        EasyHttp.post(lifecycleOwner)
                .api(new CouponGetApi(mold,limit,page))
                .request(new HttpCallback<HttpData<CouponBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<CouponBean> data) {
                        couponBeanMutableLiveData.postValue(data.getData().getData());

                    }

                    @Override
                    public void onFail(Exception e) {
                        Log.d("TAG", "onFail: "+e.getMessage());
                        couponBeanMutableLiveData.postValue(new ArrayList<>());
                        ToastUtils.show(e.getMessage());

                    }
                });
    }

    private MutableLiveData<List<HomePublishBean.DataBean>> homePublishList;
    public MutableLiveData<List<HomePublishBean.DataBean>> getMyReleaseLiveData() {
        if (homePublishList == null) {
            homePublishList = new MutableLiveData<>();
        }
        return homePublishList;
    }
    public void loadMyReleaseLeft(LifecycleOwner lifecycleOwner,int limit,int page){
        EasyHttp.post(lifecycleOwner)
                .api(new ReleaseLeftGetApi(limit,page))
                .request(new HttpCallback<HttpData<HomePublishBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<HomePublishBean> data) {
                        homePublishList.postValue(data.getData().data);

                    }

                    @Override
                    public void onFail(Exception e) {
                        Log.d("TAG", "onFail: "+e.getMessage());
                        homePublishList.postValue(new ArrayList<>());
                        ToastUtils.show(e.getMessage());

                    }
                });
    }

    private MutableLiveData<HttpData> mutableLiveData;

    public MutableLiveData<HttpData> postReleaseDeleteLiveData(){
        if (mutableLiveData == null) {
            mutableLiveData = new MutableLiveData<>();
        }
        return mutableLiveData;
    }
    public void loadMyReleaseDelete(LifecycleOwner lifecycleOwner,String id){
        EasyHttp.post(lifecycleOwner)
                .api(new MyReleasePostApi(id))
                .request(new HttpCallback<HttpData>(null) {
                    @Override
                    public void onSucceed(HttpData data) {
                        mutableLiveData.postValue(data);

                    }

                    @Override
                    public void onFail(Exception e) {
                        Log.d("TAG", "onFail: "+e.getMessage());
                        ToastUtils.show(e.getMessage());

                    }
                });
    }
}
