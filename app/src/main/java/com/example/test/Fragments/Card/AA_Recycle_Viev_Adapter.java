package com.example.test.Fragments.Card;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.R;

import java.util.List;

public class AA_Recycle_Viev_Adapter extends RecyclerView.Adapter<AA_Recycle_Viev_Adapter.MyViewHolder> {
    private Context context;
    private CardRVInterface cardRVInterface;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv, card;
        ImageView bankView;
        TextView userNcard, scoreusr, usercarddl, uservalt;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardView);
            cv = itemView.findViewById(R.id.cardRecycleView);
            scoreusr = itemView.findViewById(R.id.scoremoneyUser);
            usercarddl = itemView.findViewById(R.id.userTypescore);
            uservalt = itemView.findViewById(R.id.userTypevalut);
            userNcard = itemView.findViewById(R.id.usernamecard);
            bankView = itemView.findViewById(R.id.bankImage);
        }
    }
    List<CardModel> cards;

    public AA_Recycle_Viev_Adapter (Context context, List<CardModel> cards,
                                    CardRVInterface cardRVInterface){
        this.context = context;
        this.cards = cards;
        this.cardRVInterface = cardRVInterface;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_recycle_view, viewGroup, false);
        MyViewHolder pvh = new MyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder personViewHolder, int i) {
        personViewHolder.userNcard.setText(cards.get(i).namemoney);
        personViewHolder.usercarddl.setText(cards.get(i).typemoney);
        personViewHolder.uservalt.setText(cards.get(i).valutmoney);
        personViewHolder.scoreusr.setText(cards.get(i).scoremoney);
        personViewHolder.bankView.setImageResource(cards.get(i).bankimage);
        personViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cardRVInterface.onItemClick(cards.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }
}
