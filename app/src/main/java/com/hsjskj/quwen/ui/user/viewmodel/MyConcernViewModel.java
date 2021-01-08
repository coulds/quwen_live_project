package com.hsjskj.quwen.ui.user.viewmodel;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.hjq.http.EasyHttp;
import com.hjq.http.listener.HttpCallback;
import com.hsjskj.quwen.http.model.HttpData;
import com.hsjskj.quwen.http.request.MyConcernApi;
import com.hsjskj.quwen.http.request.MyFansApi;
import com.hsjskj.quwen.http.response.ConcernBean;
import com.hsjskj.quwen.http.response.FansBean;

import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月08日 11:41
 * description   : quwen_live
 */
public class MyConcernViewModel extends ViewModel {

    private MutableLiveData<List<ConcernBean.concernDataBean>> mutableLivemyconcern;
    public MutableLiveData<List<ConcernBean.concernDataBean>> getmyconcern() {
        if (mutableLivemyconcern == null) {
            mutableLivemyconcern = new MutableLiveData<>();
        }
        return mutableLivemyconcern;
    }
    public void loadmyconcern(LifecycleOwner lifecycleOwner, int limit, int page) {
        EasyHttp.post(lifecycleOwner)
                .tag(this)
                .api(new MyConcernApi().setvalue(limit,page))
                .request(new HttpCallback<HttpData<ConcernBean>>(null) {
                    @Override
                    public void onSucceed(HttpData<ConcernBean> data) {
                        List<ConcernBean.concernDataBean> data1 = data.getData().concerndata;
                        mutableLivemyconcern.postValue(data1);
                    }

                    @Override
                    public void onFail(Exception e) {
                        super.onFail(e);
                        mutableLivemyconcern.postValue(null);
                    }
                });
    }
}
