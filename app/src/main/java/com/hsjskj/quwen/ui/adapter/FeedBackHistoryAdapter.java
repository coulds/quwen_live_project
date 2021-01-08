package com.hsjskj.quwen.ui.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.http.response.BackFeedHistoryListBean;
import com.hsjskj.quwen.ui.user.activity.MessageActivity;

import java.util.List;

/**
 * @author : sen
 * time          : 2021年01月04日 16:59
 * description   : quwen_live
 */
public class FeedBackHistoryAdapter extends RecyclerView.Adapter<FeedBackHistoryAdapter.ViewHolder> {
    private List<BackFeedHistoryListBean.DataBean> messageActivityList;


    public FeedBackHistoryAdapter(List<BackFeedHistoryListBean.DataBean> messageActivityList) {
        this.messageActivityList = messageActivityList;

    }


    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.problembackhistory_message_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        BackFeedHistoryListBean.DataBean item = messageActivityList.get(position);


        if ("1".equals(item.mold)) {
            holder.rightLayout.setVisibility(View.VISIBLE);
            holder.leftLayout.setVisibility(View.GONE);
            holder.right_phone.setText(item.phone);
            holder.rightMsg.setText(item.content);
            holder.right_times.setText(item.create_time);
        } else {
            holder.leftLayout.setVisibility(View.VISIBLE);
            holder.rightLayout.setVisibility(View.GONE);
            holder.left_times.setText(item.create_time);
            holder.lefMsg.setText(item.content);
            holder.left_times.setText(item.create_time);


        }


    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout leftLayout;
        LinearLayout rightLayout;
        TextView lefMsg;
        TextView rightMsg;
        TextView right_phone;
        TextView right_times;
        TextView left_times;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            leftLayout = (LinearLayout) itemView.findViewById(R.id.left_layout);
            rightLayout = (LinearLayout) itemView.findViewById(R.id.right_layout);
            lefMsg = (TextView) itemView.findViewById(R.id.left_msg);
            rightMsg = (TextView) itemView.findViewById(R.id.right_msg);
            right_phone = (TextView) itemView.findViewById(R.id.right_phone_code);
            right_times = (TextView) itemView.findViewById(R.id.right_time);
            left_times = (TextView) itemView.findViewById(R.id.left_time);

        }
    }

    @Override
    public int getItemCount() {
        return messageActivityList.size();
    }


}
