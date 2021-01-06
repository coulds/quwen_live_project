package com.hsjskj.quwen.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.http.glide.GlideApp;
import com.hsjskj.quwen.model.ButtonModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Administrator :ZB
 * 2021/1/5 0005
 * describe :
 **/
public class RechargeButtonAdapter extends MyAdapter<ButtonModel> {
    private List<ButtonModel> list;
    private Context context;
    private int getPos;//选中的item

    public RechargeButtonAdapter(@NonNull Context context, List<ButtonModel> list) {
        super(context);
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder();
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private TextView tvContent;
        private EditText edContent;

        private ViewHolder() {
            super(R.layout.item_button);
            tvContent = (TextView) findViewById(R.id.tv_content);
            edContent = (EditText) findViewById(R.id.ed_content);
        }

        @Override
        public void onBindView(int position) {
            if(position!=getPos){
                edContent.setBackgroundResource(R.drawable.shape_radius_6_gry);
                edContent.setTextColor(context.getResources().getColor(R.color.textContentDisSelect));

            }else {
                edContent.setBackgroundResource(R.drawable.shape_radius_6_bule);
                edContent.setTextColor(context.getResources().getColor(R.color.textContentSelect));
            }
            if(position!=5){
                edContent.setFocusable(false);//不可编辑
                edContent.setFocusableInTouchMode(false);//不可编辑
            }
            Log.d("TAG", "onBindView: "+getPos);
            switch (position) {
                case 0:
                    edContent.setText("10金币");

                    break;
                case 1:
                    edContent.setText("50金币");

                    break;
                case 2:
                    edContent.setText("100金币");

                    break;
                case 3:
                    edContent.setText("500金币");

                    break;
                case 4:
                    edContent.setText("1000金币");

                    break;
                case 5:
                    edContent.setHint("输入数量");
                    break;


            }
            edContent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    getList(position);
                }
            });
        }
    }

    private void getList(int pos) {
        this.getPos=pos;
        notifyDataSetChanged();

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}
