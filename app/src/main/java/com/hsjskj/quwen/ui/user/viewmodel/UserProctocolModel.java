package com.hsjskj.quwen.ui.user.viewmodel;

import android.app.Application;
import android.content.Context;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.hjq.base.mvvm.BaseViewModel;
import com.hsjskj.quwen.http.response.ProtocolBean;
import com.hsjskj.quwen.ui.user.repositioy.UserProtocolRepository;


/**
 * @author : Jun
 * time          : 2020年12月22日 14:17
 * description   : AndroidProject
 */
public class UserProctocolModel extends BaseViewModel<UserProtocolRepository> {

    public UserProctocolModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<ProtocolBean> getProtocolBean() {
        return repository.getProtocolBean();
    }

    public void loadData(LifecycleOwner lifecycleOwner, String id) {
        repository.loadProtocolBean(lifecycleOwner, id);
    }

    //1 注册协议 2隐私政策 3推广规则 4主播签约协议 5直播规则 6抢答规则
    public String getProtocolTitle(Context context, int id) {
        switch (id) {
            case 1:
                return "注册协议";
            case 2:
                return "隐私政策";
            case 3:
                return "推广规则";
            case 4:
                return "主播签约协议";
            case 5:
                return "直播规则";
            case 6:
                return "抢答规则";
            default:
                return "";
        }
    }
}
