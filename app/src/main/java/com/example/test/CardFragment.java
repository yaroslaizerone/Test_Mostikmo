package com.example.test;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
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

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CardFragment extends Fragment {

    ArrayList<CardModel> cardsUser = new ArrayList<>();
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CardFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CardFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CardFragment newInstance(String param1, String param2) {
        CardFragment fragment = new CardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView addMoney = getView().findViewById(R.id.AddnewMoney);
        addMoney.setOnClickListener(v -> GoToAddMoney());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();

        RecyclerView recyclerView = getView().findViewById(R.id.cardRecycleView);

        setUsercards();

        AA_Recycle_Viev_Adapter adapter = new AA_Recycle_Viev_Adapter(getContext(),
                cardsUser);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card, container, false);
    }
    void GoToAddMoney(){
        startActivity(new Intent(getActivity(), AddNewMoneyCardActivity.class));
    }
    void setUsercards(){

        List<String> usernamemoney = new ArrayList<String>();
        List<String> userBankmoney = new ArrayList<String>();
        List<String> userTypecard = new ArrayList<String>();
        List<String> userTypevalut = new ArrayList<String>();
        List<String> userScore = new ArrayList<String>();
        int imageBank = 0;
        int image[] = {R.drawable.alfa, R.drawable.sber, R.drawable.tintkoff, R.drawable.vtb};
        //Взять данные о его названиях карт
        db.collection("userScore")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                usernamemoney.add(document.getString("namemoney"));
                            }
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                    }
                });
        //Взять данные о его банках
        db.collection("userScore")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userBankmoney.add(document.getString("typebank"));
                            }
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                    }
                });
        //Взять данные о его типах хранения денег
        db.collection("userScore")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userTypecard.add(document.getString("typecard"));
                            }
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                    }
                });
        //Взять данные о его ватютах
        db.collection("userScore")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userTypevalut.add(document.getString("typevalut"));
                            }
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                    }
                });
        //Взять данные о его деньгах
        db.collection("userScore")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userScore.add(document.getString("scoremoney"));
                            }
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                    }
                });
        for (int i = 0; i<usernamemoney.size(); i++){
            switch (userBankmoney.get(i)){
                case "СберБанк":
                    imageBank = image[1];
                    break;
                case "ВТБ":
                    imageBank = image[3];
                    break;
                case "АльфаБанк":
                    imageBank = image[0];
                    break;
                case "Тинькофф":
                    imageBank = image[2];
                    break;

            }
            cardsUser.add(new CardModel(usernamemoney.get(i),
                    imageBank,
                    userTypecard.get(i),
                    userTypevalut.get(i),
                    userScore.get(i)));
        }

    }
}