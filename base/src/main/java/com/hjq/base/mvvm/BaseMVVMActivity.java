package com.hjq.base.mvvm;

import androidx.lifecycle.ViewModelProvider;

import com.hjq.base.BaseActivity;

/**
 * @author : Jun
 * time          : 2020年12月22日 16:23
 * description   : AndroidProject
 */
public abstract class BaseMVVMActivity<T extends BaseViewModel> extends BaseActivity {
    protected T mViewModel;

    @Override
    protected void initView() {
        if (mViewModel == null) {
            mViewModel = new ViewModelProvider(this).get(TUtil.getTClass(this));
        }
    }
}
