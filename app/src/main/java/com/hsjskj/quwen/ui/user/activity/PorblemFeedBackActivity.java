package com.hsjskj.quwen.ui.user.activity;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.EditText;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.hjq.base.BaseAdapter;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.aop.DebugLog;
import com.hsjskj.quwen.common.MyMvvmActivity;
import com.hsjskj.quwen.ui.activity.ImagePreviewActivity;
import com.hsjskj.quwen.ui.activity.ImageSelectActivity;
import com.hsjskj.quwen.ui.home.adapter.ProblemFeedBackAdapter;
import com.hsjskj.quwen.ui.home.viewmodel.HomePublishViewModel;
import com.hsjskj.quwen.upload.UploadListener;
import com.hsjskj.quwen.upload.UploadTxImpl;

import java.util.ArrayList;
import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月04日 11:28
 * description   : quwen_live
 */
public class PorblemFeedBackActivity extends MyMvvmActivity<HomePublishViewModel> implements BaseAdapter.OnItemClickListener, BaseAdapter.OnChildClickListener {

    private RecyclerView problemfeedRecyclerview;
    private EditText etproblemTitle;
    private EditText etproblemContent;
    private ProblemFeedBackAdapter problemFeedBackAdapter;
    private List<Object> problempics;
    private static final int MAX_SELECT_NUMBER1 = 3;
    private UploadListener listener1;


    @DebugLog
    public static void start(Context context) {
        Intent intent = new Intent(context, PorblemFeedBackActivity.class);
        context.startActivity(intent);
    }


    @Override
    protected int getLayoutId() {
        return R.layout.set_problem_feedback_activity;
    }

    @Override
    protected void initView() {
        super.initView();
        problemfeedRecyclerview = findViewById(R.id.problemfeedback_recyclerview);

//        etproblemTitle = findViewById(R.id.et_publish_title);
//        etproblemContent = findViewById(R.id.et_publish_content);

        problemFeedBackAdapter = new ProblemFeedBackAdapter(this);
        problemFeedBackAdapter.setOnItemClickListener(this);
        problemFeedBackAdapter.setOnChildClickListener(R.id.iv_item_deleted, this);
        problemfeedRecyclerview.setLayoutManager(new GridLayoutManager(this, MAX_SELECT_NUMBER1));
        problemfeedRecyclerview.setAdapter(problemFeedBackAdapter);

        mViewModel.getTxCosLiveBean().observe(this, txCosBean -> {
            if (txCosBean != null) {
                listener1 = new UploadTxImpl(getContext(), txCosBean);
            }
        });
        loadTxCos();
    }

    private void loadTxCos() {
        mViewModel.loadTxCos(this);
    }




    @Override
    protected void initData() {
        problempics = new ArrayList<>();
        problempics.add(R.drawable.publish_defalut_icon);
        problemFeedBackAdapter.setData(problempics);
    }



    @Override
    public void onItemClick(RecyclerView recyclerView, View itemView, int position) {

        if (position == 0) {
            if (!problemFeedBackAdapter.isShowAdd1) {
                toast("最多只能选取" + MAX_SELECT_NUMBER1 + "张");
                return;
            }
            ImageSelectActivity.start(this, data -> {
                String stringUrl = data.get(0);
                if (problempics.size() == MAX_SELECT_NUMBER1) {
                    problemFeedBackAdapter.isShowAdd1 = false;
                    problempics.remove(0);//移除第一张占位
                }
                problempics.add(stringUrl);
                problemFeedBackAdapter.notifyDataSetChanged();
            });
        } else {
            ImagePreviewActivity.start(this, problempics.get(position).toString());
        }

    }

    @Override
    public void onChildClick(RecyclerView recyclerView, View childView, int position) {

        if (!problemFeedBackAdapter.isShowAdd1) {
            problemFeedBackAdapter.isShowAdd1 = true;
            problempics.add(0, R.drawable.publish_defalut_icon);
        }
        problempics.remove(position);
        problemFeedBackAdapter.notifyDataSetChanged();
    }


}
