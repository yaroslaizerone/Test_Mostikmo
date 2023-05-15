package com.example.test;

import android.content.Context;
import android.text.Layout;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class AA_Recycle_Viev_Adapter extends RecyclerView.Adapter<AA_Recycle_Viev_Adapter.MyViewHolder> {
    Context context;
    ArrayList<CardModel> cardsUser;
    public  AA_Recycle_Viev_Adapter (Context context, ArrayList<CardModel> cardsUser){
        this.context = context;
        this.cardsUser = cardsUser;
    }
    @NonNull
    @Override
    public AA_Recycle_Viev_Adapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.card_recycle_view, parent, false);

        return new AA_Recycle_Viev_Adapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AA_Recycle_Viev_Adapter.MyViewHolder holder, int position) {
        holder.userNcard.setText(cardsUser.get(position).getNamemoney());
        holder.usercarddl.setText(cardsUser.get(position).getTypemoney());
        holder.uservalt.setText(cardsUser.get(position).getValutmoney());
        holder.scoreusr.setText(cardsUser.get(position).getScoremoney());
        holder.bankView.setImageResource(cardsUser.get(position).getBankimage());
    }

    @Override
    public int getItemCount() {
        return cardsUser.size();
    }
    public static class MyViewHolder extends RecyclerView.ViewHolder{

        ImageView bankView;
        TextView txt1, txt2, userNcard, scoreusr, usercarddl, uservalt;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            bankView = itemView.findViewById(R.id.bankImage);
            txt1 = itemView.findViewById(R.id.text1);
            txt2 = itemView.findViewById(R.id.text2);
            scoreusr = itemView.findViewById(R.id.scoremoneyUser);
            usercarddl = itemView.findViewById(R.id.userTypescore);
            uservalt = itemView.findViewById(R.id.userTypevalut);
            userNcard = itemView.findViewById(R.id.usernamecard);
        }
    }
}
