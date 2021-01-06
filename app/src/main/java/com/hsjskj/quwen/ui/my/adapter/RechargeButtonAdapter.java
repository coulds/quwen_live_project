package com.hsjskj.quwen.ui.my.adapter;

import android.content.Context;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.common.MyAdapter;
import com.hsjskj.quwen.model.ButtonModel;

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
    private static final int TYPE_TEXT = 1;
    private static final int TYPE_INPUT = 2;

    public RechargeButtonAdapter(@NonNull Context context, List<ButtonModel> list) {
        super(context);
        this.context = context;
        this.list = list;
    }

    @Override
    public int getItemViewType(int position) {
        if (position == (getItemCount() - 1)) {
            return TYPE_INPUT;
        } else {
            return TYPE_TEXT;
        }
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == TYPE_INPUT) {
            return new ViewHolderInput();
        } else {
            return new ViewHolder();
        }
    }

    private final class ViewHolder extends MyAdapter.ViewHolder {

        private TextView tvContent;

        private ViewHolder() {
            super(R.layout.item_button);
            tvContent = (TextView) findViewById(R.id.tv_content);
        }

        @Override
        public void onBindView(int position) {
            if (position != getPos) {//getPos当前选中的position
                tvContent.setBackgroundResource(R.drawable.shape_radius_6_gry);
                tvContent.setTextColor(context.getResources().getColor(R.color.textContentDisSelect));

            } else {
                tvContent.setBackgroundResource(R.drawable.shape_radius_6_bule);
                tvContent.setTextColor(context.getResources().getColor(R.color.textContentSelect));
            }
            Log.d("TAG", "onBindView: " + getPos);
            switch (position) {
                case 0:
                    tvContent.setText("10金币");

                    break;
                case 1:
                    tvContent.setText("50金币");

                    break;
                case 2:
                    tvContent.setText("100金币");

                    break;
                case 3:
                    tvContent.setText("500金币");

                    break;
                case 4:
                    tvContent.setText("1000金币");

                    break;
                case 5:
                    tvContent.setHint("输入数量");
                    break;


            }

        }
    }


    private final class ViewHolderInput extends MyAdapter.ViewHolder {

        private EditText edContent;

        private ViewHolderInput() {
            super(R.layout.item_button2);
            edContent = (EditText) findViewById(R.id.ed_content);
        }

        @Override
        public void onBindView(int position) {
            if (position != getPos) {//getPos当前选中的position
                edContent.setBackgroundResource(R.drawable.shape_radius_6_gry);
                edContent.setTextColor(context.getResources().getColor(R.color.textContentDisSelect));
                edContent.clearFocus();
                edContent.setOnFocusChangeListener((v, hasFocus) -> {
                    if (hasFocus) {
                        edContent.post(() -> getList(position));
                    }
                });
            } else {
                edContent.setBackgroundResource(R.drawable.shape_radius_6_bule);
                edContent.setTextColor(context.getResources().getColor(R.color.textContentSelect));
                edContent.requestFocus();
                edContent.setOnFocusChangeListener(null);
            }

        }
    }

    public void getList(int pos) {
        this.getPos = pos;
        Log.d("TAG", "onClick: " + pos);
        notifyDataSetChanged();

    }


    @Override
    public int getItemCount() {
        return list.size();
    }


}
