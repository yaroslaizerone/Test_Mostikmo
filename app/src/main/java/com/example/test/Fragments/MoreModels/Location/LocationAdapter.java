package com.example.test.Fragments.MoreModels.Location;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.test.Fragments.Card.CardRVInterface;
import com.example.test.R;

import java.util.List;

public class LocationAdapter extends RecyclerView.Adapter<LocationAdapter.MyViewHolder> {
    private Context context;
    private LocationInterface locationInterface;
    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView cv, card;
        TextView userLocation,typelocation;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            card = itemView.findViewById(R.id.cardView);
            cv = itemView.findViewById(R.id.locationRecycleView);
            userLocation = itemView.findViewById(R.id.usernameLocation);
            typelocation = itemView.findViewById(R.id.userTypeLocation);
        }
    }
    List<LocationClass> locations;
    public  LocationAdapter (Context context, List<LocationClass> cards, LocationInterface locationInterface){
        this.context = context;
        this.locations = cards;
        this.locationInterface = locationInterface;
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
        personViewHolder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                locationInterface.onItemClick(locations.get(i));
            }
        });
    }

    @Override
    public int getItemCount() {
        return locations.size();
    }
}
