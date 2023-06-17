package com.example.test.Fragments.Plan;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.List;

public class PlanAdapter extends RecyclerView.Adapter<PlanAdapter.MyViewHolder> {
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView TypeUserPlan,SummaPlanUser, StartPlanUser, EndPlanUser;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.PlanRecycleView);
            TypeUserPlan = itemView.findViewById(R.id.typeplanuser);
            SummaPlanUser = itemView.findViewById(R.id.SummaPlan);
            StartPlanUser = itemView.findViewById(R.id.StartDate);
            EndPlanUser = itemView.findViewById(R.id.Enddate);
        }
    }
    List<PlanModel> Userplan;

    public PlanAdapter(Context context, List<PlanModel> plans){
        this.context = context;
        this.Userplan = plans;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public PlanAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.plan_recycle_view, viewGroup, false);
        PlanAdapter.MyViewHolder pvh = new PlanAdapter.MyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder personViewHolder, int i) {
        personViewHolder.TypeUserPlan.setText(Userplan.get(i).TypePlan);
        personViewHolder.SummaPlanUser.setText(Userplan.get(i).Summa);
        personViewHolder.StartPlanUser.setText(Userplan.get(i).StartDate);
        personViewHolder.EndPlanUser.setText(Userplan.get(i).Today);

        if (Userplan.get(i).TypePlan.equals("+")){
            personViewHolder.TypeUserPlan.setTextColor(Color.parseColor("#008000"));
            personViewHolder.SummaPlanUser.setTextColor(Color.parseColor("#008000"));
        }
        else if (Userplan.get(i).TypePlan.equals("-")){
            personViewHolder.TypeUserPlan.setTextColor(Color.parseColor("#B22222"));
            personViewHolder.SummaPlanUser.setTextColor(Color.parseColor("#B22222"));
        }
    }

    @Override
    public int getItemCount() {
        return Userplan.size();
    }
}
