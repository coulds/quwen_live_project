package com.hsjskj.quwen.ui.user.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.hsjskj.quwen.R;
import com.hsjskj.quwen.ui.my.activity.AddBankCardActivity;
import com.hsjskj.quwen.ui.my.object.BankCard;
import com.hsjskj.quwen.ui.user.object.Evaluation;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class EvaluationAdapter extends RecyclerView.Adapter<EvaluationAdapter.EvaluationsViewHolder> {
   Context context;
   ArrayList<Evaluation> evaluations;
    public EvaluationAdapter(Context context, List<Evaluation> evaluations) {
        this.context=context;
        this.evaluations= (ArrayList<Evaluation>) evaluations;
    }

    @NonNull
    @Override
    public EvaluationsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view=null;
        EvaluationsViewHolder evaluationsViewHolder;
        if (view==null){
            view= LayoutInflater.from(context).inflate(R.layout.evaluation_item,parent,false);
            evaluationsViewHolder=new EvaluationsViewHolder(view);
            view.setTag(evaluationsViewHolder);
        }else{
            evaluationsViewHolder= (EvaluationsViewHolder) view.getTag();
        }
        return evaluationsViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull EvaluationsViewHolder holder, int position) {
        Evaluation evaluation=evaluations.get(position);
        holder.civ_userHeader.setImageResource(evaluation.getUserHeader());
        holder.tv_userName.setText(evaluation.getUserName());
        holder.tv_time.setText(evaluation.getTime());
        holder.tv_evaluation.setText(evaluation.getEvaluationMsg());
    }

    @Override
    public int getItemCount() {
        return evaluations.size();
    }


    class EvaluationsViewHolder extends RecyclerView.ViewHolder{
        CircleImageView civ_userHeader;
        TextView tv_userName;
        TextView tv_time;
        TextView tv_evaluation;
        public EvaluationsViewHolder(@NonNull View itemView) {
            super(itemView);
            civ_userHeader = itemView.findViewById(R.id.civ_userHeader);
            tv_userName = itemView.findViewById(R.id.tv_userName);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_evaluation = itemView.findViewById(R.id.tv_evaluation);
        }
    }
}
