package com.example.test.Fragments.Operation;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.List;

public class OperationAdapter extends RecyclerView.Adapter<OperationAdapter.MyViewHolder> {
    private Context context;
    private OperationInterface operationInterface;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv, card;
        TextView userNcard, Scoreusr, usercomment, userdate, usercatrgory, usertypeoperation, locationuser;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardView);
            cv = itemView.findViewById(R.id.OperationRecycleView);
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

    public OperationAdapter(Context context, List<OperationModel> operations, OperationInterface operationInterface){
        this.context = context;
        this.operations = operations;
        this.operationInterface = operationInterface;
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

        Log.i("Info",operations.get(i).typeOperation);
        if (operations.get(i).typeOperation.equals("+")){
            personViewHolder.usertypeoperation.setTextColor(Color.parseColor("#008000"));
            personViewHolder.Scoreusr.setTextColor(Color.parseColor("#008000"));
        }
        else if (operations.get(i).typeOperation.equals("-")){
            personViewHolder.usertypeoperation.setTextColor(Color.parseColor("#B22222"));
            personViewHolder.Scoreusr.setTextColor(Color.parseColor("#B22222"));
        }
        personViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                operationInterface.onItemClick(operations.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return operations.size();
    }

}
