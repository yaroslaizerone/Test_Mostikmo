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

public class LegendAdapter extends RecyclerView.Adapter<LegendAdapter.MyViewHolder> {
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView Color;
        TextView userNameLegend;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.LegendRecycleView);
            userNameLegend = itemView.findViewById(R.id.LegengText);
            Color = itemView.findViewById(R.id.ColorLegend);
        }
    }
    List<LegendModel> legends;

    public  LegendAdapter (Context context, List<LegendModel>legends){
        this.context = context;
        this.legends = legends;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.legend_recycle_view, viewGroup, false);
        MyViewHolder pvh = new MyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder personViewHolder, int i) {
        personViewHolder.userNameLegend.setText(legends.get(i).legendName);
        personViewHolder.Color.setBackgroundColor(legends.get(i).legendColor);
    }

    @Override
    public int getItemCount() {
        return legends.size();
    }

}
