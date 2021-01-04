package com.hsjskj.quwen.ui.home.viewmodel;

import androidx.annotation.IntRange;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.CouponRecevieApi;
import com.hsjskj.quwen.http.request.HomeBannerApi;
import com.hsjskj.quwen.http.request.HomePublishListApi;
import com.hsjskj.quwen.http.request.HomeVideoListApi;
import com.hsjskj.quwen.http.response.BannerBean;
import com.hsjskj.quwen.http.response.HasCouponBean;
import com.hsjskj.quwen.http.response.HomePublishBean;
import com.hsjskj.quwen.http.response.HomeVideoListBean;
import com.hsjskj.quwen.http.response.NoticeBean;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月28日 15:24
 * description   : quwen_live
 */
public class HomeFragmentViewModel extends ViewModel {

    private MutableLiveData<List<BannerBean>> bannerHomeLiveData;

    public MutableLiveData<List<BannerBean>> getHomeBannerLiveData() {
        if (bannerHomeLiveData == null) {
            bannerHomeLiveData = new MutableLiveData<>();
        }
        return bannerHomeLiveData;
    }

    private MutableLiveData<NoticeBean> noticeHomeLiveData;

    public MutableLiveData<NoticeBean> getHomeNoticeLiveData() {
        if (noticeHomeLiveData == null) {
            noticeHomeLiveData = new MutableLiveData<>();
        }
        return noticeHomeLiveData;
    }


    @Override
    protected void onCleared() {
        EasyHttp.cancel(this);
        super.onCleared();
    }

    public void loadHomeBanner(LifecycleOwner lifecycleOwner) {
        loadBanner(lifecycleOwner, 1);
    }

    //1 首页 2 问问 3 消息 4 直播
    private void loadBanner(LifecycleOwner lifecycleOwner, @IntRange(from = 1, to = 4) int id) {
        EasyHttp.post(lifecycleOwner)
                .api(new HomeBannerApi(id))
                .request(new HttpCallback<HttpData<List<BannerBean>>>(null) {
                    @Override
                    public void onSucceed(HttpData<List<BannerBean>> data) {
                        postHomeBannerValue(data.getData(), id);
                    }

                    @Override
                    public void onFail(Exception e) {
                        postHomeBannerValue(new ArrayList<>(), id);
                    }
                });
    }

    //1 首页 2 问问 3 消息 4 直播
    private void postHomeBannerValue(List<BannerBean> data, int id) {
        switch (id) {
            case 1:
                bannerHomeLiveData.postValue(data);
                break;
            default:
                break;
        }
    }

    public void loadHomeNotice(LifecycleOwner lifecycleOwner) {
        EasyHttp.post(lifecycleOwner)
                .api("Home/notice")
                .request(new HttpCallback<HttpData<NoticeBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<NoticeBean> data) {
                        noticeHomeLiveData.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        noticeHomeLiveData.postValue(new NoticeBean());
                    }
                });
    }

    public MutableLiveData<HasCouponBean> loadHomeHasCoupon(LifecycleOwner lifecycleOwner) {
        MutableLiveData<HasCouponBean> mutableLiveData = new MutableLiveData<>();

        EasyHttp.post(lifecycleOwner)
                .api("Coupon/hasCoupon")
                .request(new HttpCallback<HttpData<HasCouponBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<HasCouponBean> data) {
                        mutableLiveData.postValue(data.getData());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mutableLiveData.postValue(new HasCouponBean());
                    }
                });

        return mutableLiveData;
    }

    public MutableLiveData<Boolean> loadHomeHasCoupon(LifecycleOwner lifecycleOwner, String id) {
        MutableLiveData<Boolean> mutableLiveData = new MutableLiveData<>();
        EasyHttp.post(lifecycleOwner)
                .api(new CouponRecevieApi(id))
                .request(new HttpCallback<HttpData<Void>>(null) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        mutableLiveData.postValue(true);
                        ToastUtils.show(data.getMessage());
                    }

                    @Override
                    public void onFail(Exception e) {
                        mutableLiveData.postValue(false);
                        ToastUtils.show(e.getMessage());
                    }
                });

        return mutableLiveData;
    }


    private MutableLiveData<List<HomePublishBean.DataBean>> homePublishList;

    public MutableLiveData<List<HomePublishBean.DataBean>> getHomePublishLiveData() {
        if (homePublishList == null) {
            homePublishList = new MutableLiveData<>();
        }
        return homePublishList;
    }

    public void loadHomePublishList(LifecycleOwner lifecycleOwner) {
        loadHomePublishList(lifecycleOwner, 10, 1);
    }

    public void loadHomePublishList(LifecycleOwner lifecycleOwner, int limit, int page) {
        EasyHttp.post(lifecycleOwner)
                .api(new HomePublishListApi(limit, page))
                .request(new HttpCallback<HttpData<HomePublishBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<HomePublishBean> data) {
                        homePublishList.postValue(data.getData().data);
                    }

                    @Override
                    public void onFail(Exception e) {
                        homePublishList.postValue(new ArrayList<>());
                    }
                });
    }

}
