package com.example.test;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.MyViewHolder> {
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView userNcard, Scoreusr, usercomment, userdate, usercatrgory, usertypeoperation, locationuser;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardRecycleView);
            Scoreusr = itemView.findViewById(R.id.summaOperation);
            userdate = itemView.findViewById(R.id.userdateoperation);
            usercatrgory = itemView.findViewById(R.id.userTypeCategory);
            userNcard = itemView.findViewById(R.id.userPay);
            usercomment = itemView.findViewById(R.id.UserComment);
            locationuser = itemView.findViewById(R.id.userTypeLocation);
            usertypeoperation = itemView.findViewById(R.id.TypeOperatioUser);
        }
    }
    List<OperationModel> operations;

    public OperationAdapter(Context context, List<OperationModel> operations){
        this.context = context;
        this.operations = operations;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.operation_recycle_view, viewGroup, false);
        MyViewHolder pvh = new MyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder personViewHolder, int i) {
        personViewHolder.userNcard.setText(operations.get(i).namemoney);
        personViewHolder.userdate.setText(operations.get(i).dateoperation);
        personViewHolder.Scoreusr.setText(operations.get(i).summa);
        personViewHolder.usercatrgory.setText(operations.get(i).category);
        personViewHolder.usercomment.setText(operations.get(i).comment);
        personViewHolder.locationuser.setText(operations.get(i).location);
        personViewHolder.usertypeoperation.setText(operations.get(i).typeOperation);
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

}
