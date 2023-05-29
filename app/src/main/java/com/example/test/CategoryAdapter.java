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

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.MyViewHolder> {
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        ImageView ImgCat;
        TextView userNameCategory, summa;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.CategoryRecycleView);
            userNameCategory = itemView.findViewById(R.id.typeCategory);
            ImgCat = itemView.findViewById(R.id.IMGCategory);
            summa = itemView.findViewById(R.id.SummaCategory);
        }
    }
    List<CategoryModel> categorys;

    public CategoryAdapter(Context context, List<CategoryModel> categorys){
        this.context = context;
        this.categorys = categorys;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.category_recycle_view, viewGroup, false);
        MyViewHolder pvh = new MyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder personViewHolder, int i) {
        personViewHolder.summa.setText(categorys.get(i).summaCategory);
        personViewHolder.userNameCategory.setText(categorys.get(i).nameCategory);
        personViewHolder.ImgCat.setImageResource(categorys.get(i).imageCategory);
    }

    @Override
    public int getItemCount() {
        return categorys.size();
    }

}
