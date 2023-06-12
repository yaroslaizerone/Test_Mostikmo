package com.example.test;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class CardFragment extends Fragment {

    private List<CardModel> cards = new ArrayList<CardModel>();
    private RecyclerView rv;
    View view;
    LinearLayoutManager llm;
    Context context;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    int imageBank = 0;
    String name, score, tcard, tval;
    TextView AddCard;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_card, container, false);
        context = getContext();
        rv = view.findViewById(R.id.cardRecycleView);
        llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        AddCard = view.findViewById(R.id.AddnewMoney);
        AddCard.setOnClickListener(v -> startActivity(new Intent(getActivity(), AddNewMoneyCardActivity.class)));
        return view;
    }
    private void initializeData(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        cards.clear();
        //Взять данные о его названиях карт
        db.collection("userScore")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                name = document.getString("namemoney");
                                score = document.getString("scoremoney");
                                tcard = document.getString("typecard");
                                switch (document.getString("typebank")){
                                    case "СберБанк":
                                        imageBank = R.drawable.sber;
                                        break;
                                    case "ВТБ":
                                        imageBank = R.drawable.vtb;
                                        break;
                                    case "АльфаБанк":
                                        imageBank = R.drawable.alfa;
                                        break;
                                    case "Тинькофф":
                                        imageBank = R.drawable.tintkoff;
                                        break;
                                }
                                switch (document.getString("typevalut")){
                                    case "Рубль":
                                        tval = "₽";
                                        break;
                                    case "Доллар":
                                        tval = "$";
                                        break;
                                    case "Фунт стерлингов":
                                        tval = "£";
                                        break;
                                    case "Юань":
                                        tval = "¥";
                                        break;
                                    case "Евро":
                                        tval = "€";
                                        break;
                                    case "Белорусский рубль":
                                        tval = "Br";
                                        break;
                                    case "Швейцарский франк":
                                        tval = "₣";
                                        break;
                                    case "Дирхам":
                                        tval = "Dh";
                                        break;
                                }
                                cards.add(new CardModel(name,imageBank,tcard,tval,score));
                            }
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                        AA_Recycle_Viev_Adapter adapter = new AA_Recycle_Viev_Adapter(context, cards);
                        adapter.notifyDataSetChanged();
                        rv.setAdapter(adapter);
                    }
                });
    }

    private void initializeAdapter(){
        AA_Recycle_Viev_Adapter adapter = new AA_Recycle_Viev_Adapter(context, cards);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeData();
        initializeAdapter();
    }
}