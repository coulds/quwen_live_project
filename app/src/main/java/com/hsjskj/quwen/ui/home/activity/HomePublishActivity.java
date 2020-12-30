package com.hsjskj.quwen.ui.home.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;

import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hjq.toast.ToastUtils;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.DebugLog;
import com.hsjskj.quwen.common.MyActivity;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.ui.activity.ImagePreviewActivity;
import com.hsjskj.quwen.ui.activity.ImageSelectActivity;
import com.hsjskj.quwen.ui.home.adapter.HomePublishAdapter;
import com.hsjskj.quwen.ui.home.viewmodel.HomePublishViewModel;
import com.hsjskj.quwen.upload.UploadBean;
import com.hsjskj.quwen.upload.UploadCallback;
import com.hsjskj.quwen.upload.UploadListener;
import com.hsjskj.quwen.upload.UploadTxImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : Jun
 * time          : 2020年12月26日 13:08
 * description   : 首页问问发布
 */
public class HomePublishActivity extends MyMvvmActivity<HomePublishViewModel> implements BaseAdapter.OnItemClickListener, BaseAdapter.OnChildClickListener {

    private RecyclerView publishRecyclerview;
    private EditText etPublishTitle;
    private EditText etPublishContent;
    private HomePublishAdapter publishAdapter;
    private List<Object> pics;
    private static final int MAX_SELECT_NUMBER = 3;
    private UploadListener listener;

    @DebugLog
    public static void start(Context context) {
        Intent intent = new Intent(context, HomePublishActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.home_publish_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        publishRecyclerview = findViewById(R.id.publish_recyclerview);
        etPublishTitle = findViewById(R.id.et_publish_title);
        etPublishContent = findViewById(R.id.et_publish_content);
        publishAdapter = new HomePublishAdapter(this);
        publishAdapter.setOnItemClickListener(this);
        publishAdapter.setOnChildClickListener(R.id.iv_item_deleted, this);
        publishRecyclerview.setLayoutManager(new GridLayoutManager(this, MAX_SELECT_NUMBER));
        publishRecyclerview.setAdapter(publishAdapter);

        //TODO 未实现上传腾讯云服务器
//        listener=new UploadTxImpl(this,"","","");
        listener = new UploadListener() {
            @Override
            public void upload(UploadBean bean, UploadCallback callback) {
                new Handler(Looper.getMainLooper()).postDelayed(() -> {
                    bean.setmResultUrl(bean.getLocalPath());
                    callback.onSuccess(bean);
                }, 1000);
            }

            @Override
            public void cancel() {

            }
        };
    }

    @Override
    protected void initData() {
        pics = new ArrayList<>();
        pics.add(R.drawable.publish_defalut_icon);
        publishAdapter.setData(pics);

    }

    @Override
    public void onRightClick(View v) {
        String title = etPublishTitle.getText().toString();
        String content = etPublishContent.getText().toString();
        showDialog();
        mViewModel.submitPublish(this, title, content, pics, listener).observe(this, o -> {
            hideDialog();
            if (o) {
                finish();
            }
        });
    }

    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {
        if (position == 0) {
            if (!publishAdapter.isShowAdd) {
                toast("最多只能选取" + MAX_SELECT_NUMBER + "张");
                return;
            }
            ImageSelectActivity.start(this, data -> {
                String stringUrl = data.get(0);
                if (pics.size() == MAX_SELECT_NUMBER) {
                    publishAdapter.isShowAdd = false;
                    pics.remove(0);//移除第一张占位
                }
                pics.add(stringUrl);
                publishAdapter.notifyDataSetChanged();
            });
        } else {
            ImagePreviewActivity.start(this, pics.get(position).toString());
        }
    }

    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {
        if (!publishAdapter.isShowAdd) {
            publishAdapter.isShowAdd = true;
            pics.add(0, R.drawable.publish_defalut_icon);
        }
        pics.remove(position);
        publishAdapter.notifyDataSetChanged();
    }
}
