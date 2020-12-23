package com.hsjskj.quwen.ui.copy;

import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import com.hjq.base.mvvm.BaseRepository;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.action.StatusAction;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.widget.HintLayout;

/**
 * @author : Jun
 * time          : 2020年12月22日 16:33
 * description   : AndroidProject
 */
public class CopyActivity extends MyMvvmActivity<CopyViewModel> implements StatusAction {
    @Override
    protected int getLayoutId() {
        return R.layout.copy_mvvm_activity;
    }

    @Override
    protected void initData() {
        setOnClickListener(R.id.btn_viewmodel1, R.id.btn_viewmodel2);

        mViewModel.getLiveData1().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                toast("按钮1返回" + aBoolean);
            }
        });

        mViewModel.getLiveData2().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                toast("按钮2返回" + s);
                ((TextView) findViewById(R.id.tv_content)).setText("模拟网络请求返回: " + s);
            }
        });

        //不是每个页面都有加载，也不是每个页面只有一个状态，所有不提公共，具体在viewmodel中添加
        mViewModel.getLiveDataStatus().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer status) {
                //状态判断设置 参考StatusActivity
                if (status == BaseRepository.STATUS_LOADING) {
                    showLoading();
                } else if (status == BaseRepository.STATUS_LOAD_END) {
                    showComplete();
                } else if (status == BaseRepository.STATUS_LOAD_ERROR) {
                    showError(v -> mViewModel.loadData1());
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_viewmodel1:
                mViewModel.loadData1();
                break;
            case R.id.btn_viewmodel2:
                mViewModel.loadData2();
                break;
            default:
                break;
        }
    }

    @Override
    public HintLayout getHintLayout() {
        return findViewById(R.id.hl_status_hint);
    }
}
