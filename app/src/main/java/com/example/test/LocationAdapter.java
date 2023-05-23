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

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private Context context;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv;
        TextView userLocation,typelocation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            cv = itemView.findViewById(R.id.cardRecycleView);
            userLocation = itemView.findViewById(R.id.usernameLocation);
            typelocation = itemView.findViewById(R.id.userTypeLocation);
        }
    }
    List<LocationClass> locations;
    public  LocationAdapter (Context context, List<LocationClass> cards){
        this.context = context;
        this.locations = cards;
    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }

    @Override
    public LocationAdapter.MyViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.location_recycle_view, viewGroup, false);
        LocationAdapter.MyViewHolder pvh = new LocationAdapter.MyViewHolder(v);
        return pvh;
    }

    @Override
    public void onBindViewHolder(MyViewHolder personViewHolder, int i) {
        personViewHolder.userLocation.setText(locations.get(i).NameLocation);
        personViewHolder.typelocation.setText(locations.get(i).TypeLocation);
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
