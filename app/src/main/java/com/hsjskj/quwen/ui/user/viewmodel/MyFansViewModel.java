package com.hsjskj.quwen.ui.user.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.MyFansApi;
import com.hsjskj.quwen.http.request.UserFollowApi;
import com.hsjskj.quwen.http.response.FansBean;
import com.hsjskj.quwen.ui.user.repositioy.UserPreviewRepository;

import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月08日 10:47
 * description   : quwen_live
 */
public class MyFansViewModel extends ViewModel {
   private UserPreviewRepository repository;

    public MyFansViewModel() {
        repository = new UserPreviewRepository();

    }

    private MutableLiveData<List<FansBean.DataBean>> mutableLivemyfans;

    public MutableLiveData<List<FansBean.DataBean>> getmyfans() {
        if (mutableLivemyfans == null) {
            mutableLivemyfans = new MutableLiveData<>();
        }
        return mutableLivemyfans;
    }

    public void loadmyfans(LifecycleOwner lifecycleOwner, int limit, int page) {
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new MyFansApi().setvalue(limit, page))
                .request(new HttpCallback<HttpData<FansBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<FansBean> data) {
                        List<FansBean.DataBean> data1 = data.getData().data;
                        mutableLivemyfans.postValue(data1);
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        mutableLivemyfans.postValue(null);
                    }
                });
    }


    public MutableLiveData<Boolean> loadFollowUserInfoLiveData(LifecycleOwner lifecycleOwner, String toUid) {
        MutableLiveData<Boolean> mutableLiveData=new MutableLiveData<>();
        EasyHttp.post(lifecycleOwner)
                .api(new UserFollowApi(toUid))
                .request(new HttpCallback<HttpData<Void>>(null) {
                    @Override
                    public void onSucceed(HttpData<Void> data) {
                        ToastUtils.show(data.getMessage());
                        mutableLiveData.postValue(true);
                    }

                    @Override
                    public void onFail(Exception e) {
                        ToastUtils.show(e.getMessage());
                        mutableLiveData.postValue(false);
                    }
                });
        return mutableLiveData;
    }

}
