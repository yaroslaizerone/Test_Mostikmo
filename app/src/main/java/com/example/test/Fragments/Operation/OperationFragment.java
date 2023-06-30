package com.example.test.Fragments.Operation;

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
import android.widget.Button;
import android.widget.Toast;

import com.example.test.Fragments.MoreModels.Location.EditLocation;
import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OperationFragment extends Fragment implements OperationInterface {

    void StartAddOperation(){
        startActivity(new Intent(getActivity(), AddOperationActivity.class));
    }
    private List<OperationModel> operations = new ArrayList<OperationModel>();
    private RecyclerView rv;
    View view;
    LinearLayoutManager llm;
    Context context;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    String userNcard, usercomment, userdate, usercatrgory, usertypeoperation, locationuser, userMonth, userYear;
    Long Scoreuser;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_operation, container, false);
        context = getContext();
        rv = (RecyclerView)view.findViewById(R.id.OperationRecycleView);
        llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        return view;
    }
    //TODO сделать ссылку на создание карты
    private void initializeData(){
        Button addoper = getView().findViewById(R.id.BtgoAddOperation);
        addoper.setOnClickListener(v -> StartAddOperation());
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        operations.clear();
        //Взять данные о его названиях карт
        db.collection("userOperation")
                .whereEqualTo("email", user.getEmail())
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                userNcard = document.getString("moneyoperation");
                                Scoreuser = document.getLong("summa");
                                usercomment = document.getString("comment");
                                usercatrgory = document.getString("typecategoty");
                                usertypeoperation = document.getString("typeoperation");
                                locationuser = document.getString("locationoperation");
                                userdate = String.valueOf(document.getLong("dateoperation"));
                                userMonth = String.valueOf(document.getLong("monthoperation"));
                                userYear = String.valueOf(document.getLong("yearoperation"));

                                switch (document.getString("typeoperation")){
                                    case "Расход":
                                        usertypeoperation = "-";
                                        break;
                                    case "Доход":
                                        usertypeoperation = "+";
                                        break;
                                }

                                operations.add(new OperationModel(usercomment,userdate+"."+userMonth+"."+userYear,locationuser,userNcard,Scoreuser+"",usercatrgory,usertypeoperation));
                            }
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                        OperationAdapter adapter = new OperationAdapter(context, operations,OperationFragment.this);
                        adapter.notifyDataSetChanged();
                        rv.setAdapter(adapter);
                    }
                });
    }

    private void initializeAdapter(){
        OperationAdapter adapter = new OperationAdapter(context, operations, OperationFragment.this);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        initializeData();
        initializeAdapter();
    }

    @Override
    public void onItemClick(OperationModel operationModel) {
        Intent intent = new Intent(getActivity(), EditOperationsActivity.class);

        intent.putExtra("COMMENT",operationModel.comment);
        intent.putExtra("DATE",operationModel.dateoperation);
        intent.putExtra("LOCATION",operationModel.location);
        intent.putExtra("NAMEMONEY",operationModel.namemoney);
        intent.putExtra("SUMMA",operationModel.summa);
        intent.putExtra("CATEGORY",operationModel.category);
        intent.putExtra("TYPEOPERATION",operationModel.typeOperation);

        startActivity(intent);
    }
}