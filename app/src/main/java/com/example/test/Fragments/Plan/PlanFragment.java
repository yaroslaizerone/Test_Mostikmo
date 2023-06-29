package com.example.test.Fragments.Plan;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.TextView;

import com.example.test.Fragments.MoreModels.Location.EditLocation;
import com.example.test.Plan.DateModel;
import com.example.test.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import java.util.ArrayList;
import java.util.List;

public class PlanFragment extends Fragment implements PlanInterface {
    private List<DateModel> dates = new ArrayList<DateModel>();
    private List<PlanModel> plans = new ArrayList<PlanModel>();
    private List<PlanModel> Selectplans = new ArrayList<PlanModel>();
    private RecyclerView rv;
    private int list = 0;
    View view;
    LinearLayoutManager llm;
    Context context;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    TextView last, next, ListPlan;
    DateModel plan;
    PlanModel planUser;
    String typePlan, today;
    private Long datePlan, mouthPlan, yearPlan, SummaPlan;
    int Dohod, Rashod;
    PieChart pieChart;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_plan, container, false);
        context = getContext();
        rv = (RecyclerView)view.findViewById(R.id.PlanRecycleView);
        llm = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        Rashod = 0;
        Dohod = 0;

        Button Addplan = view.findViewById(R.id.BtgoAddplan);
        next = view.findViewById(R.id.listnext);
        last = view.findViewById(R.id.listback);
        ListPlan = view.findViewById(R.id.selectedMonth);
        pieChart = view.findViewById(R.id.piechart);

        last.setOnClickListener(v -> Backlist());
        next.setOnClickListener(v -> Nextlist());
        Addplan.setOnClickListener(v -> StartAddPlan());

        return view;
    }

    private void StartAddPlan(){
        startActivity(new Intent(getActivity(), AddPlansActivity.class));
    }
    // Метод для обработки дат
    private void initializeData(){
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        db = FirebaseFirestore.getInstance();
        db.collection("UserPlans")
                .whereEqualTo("email", user.getEmail()).orderBy("yearplan").orderBy("mouthplan")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                datePlan = document.getLong("dateplan");
                                mouthPlan = document.getLong("mouthplan");
                                yearPlan = document.getLong("yearplan");
                                SummaPlan = document.getLong("summa");
                                today = document.getString("today");

                                switch (document.getString("typeplan")){
                                    case "Расход":
                                        typePlan = "-";
                                        break;
                                    case "Доход":
                                        typePlan = "+";
                                        break;
                                }

                                String dateStart = datePlan+"."+mouthPlan+"."+yearPlan;
                                plan = new DateModel(mouthPlan, yearPlan);
                                planUser = new PlanModel(dateStart,SummaPlan+"",typePlan,today,datePlan,mouthPlan,yearPlan);
                                plans.add(planUser);

                                if(dates.size()==0){
                                    dates.add(plan);
                                }else{
                                    int y = 0;
                                    for(int i = 0; i<dates.size(); i++){
                                        if(dates.get(i).month != plan.month && dates.get(i).year != plan.year){
                                            y++;
                                            if (y== dates.size()){
                                                dates.add(plan);
                                            }
                                        }
                                    }
                                }
                            }
                        } else {
                            Log.d("User", "Error getting documents: ", task.getException());
                        }
                        if(dates.size() != 0){
                            ListPlan.setText(MouthSet(dates.get(0).month)+","+dates.get(0).year);
                            SetData(Integer.parseInt(String.valueOf(dates.get(0).month)), Integer.parseInt(String.valueOf(dates.get(0).year)));
                        }

                        PlanAdapter adapter = new PlanAdapter(context, Selectplans, PlanFragment.this);
                        adapter.notifyDataSetChanged();
                        rv.setAdapter(adapter);
                    }
                });
    }

    private void SetData(int month, int year){

        Selectplans.clear();
        pieChart.clearChart();
        Rashod = 0;
        Dohod = 0;

        for(int i = 0; i < plans.size();i++){
            if (month == plans.get(i).month && year == plans.get(i).year){
                Selectplans.add(plans.get(i));
            }
        }

        for(int i = 0; i < Selectplans.size(); i++){
            switch (Selectplans.get(i).TypePlan){
                case "-":
                    Rashod += Integer.parseInt(Selectplans.get(i).Summa);
                    break;
                case "+":
                    Dohod += Integer.parseInt(Selectplans.get(i).Summa);
                    break;
            }
        }

        initializeAdapter();

        pieChart.addPieSlice(
                new PieModel(
                        "Расход",
                        Rashod,
                        Color.parseColor("#B22222")));

        pieChart.addPieSlice(
                new PieModel(
                        "Доход",
                        Dohod,
                        Color.parseColor("#008000")));

        pieChart.startAnimation();
    }

    private void Backlist(){
        if(list == 0){
            return ;
        }else{
            list--;
            ListPlan.setText(MouthSet(dates.get(list).month)+","+dates.get(list).year);
            SetData(Integer.parseInt(String.valueOf(dates.get(list).month)), Integer.parseInt(String.valueOf(dates.get(list).year)));
        }
    }

    private void Nextlist(){
        if(list >= dates.size()-1){
            return;
        }else {
            list++;
            ListPlan.setText(MouthSet(dates.get(list).month) + "," + dates.get(list).year);
            SetData(Integer.parseInt(String.valueOf(dates.get(list).month)), Integer.parseInt(String.valueOf(dates.get(list).year)));
        }
    }

    String MouthSet(Long monthGet){

        String selectMonth ="";

        if (monthGet == 1) {
            selectMonth = "Январь";
        } else if (monthGet == 2) {
            selectMonth = "Февраль";
        } else if (monthGet == 3) {
            selectMonth = "Март";
        } else if (monthGet == 4) {
            selectMonth = "Апрель";
        } else if (monthGet == 5) {
            selectMonth = "Май";
        } else if (monthGet == 6) {
            selectMonth = "Июнь";
        } else if (monthGet == 7) {
            selectMonth = "Июль";
        } else if (monthGet == 8) {
            selectMonth ="Август";
        } else if (monthGet == 9) {
            selectMonth ="Сентябрь";
        } else if (monthGet == 10) {
            selectMonth ="Октябрь";
        } else if (monthGet == 11) {
            selectMonth ="Ноябрь";
        } else if (monthGet == 12) {
            selectMonth ="Декабрь";}
        return selectMonth;
    }

    private void initializeAdapter(){
        PlanAdapter adapter = new PlanAdapter(context, Selectplans, PlanFragment.this);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        dates.clear();
        plans.clear();
        initializeData();
        initializeAdapter();
    }

    @Override
    public void onItemClick(PlanModel planModel) {
        Intent intent = new Intent(getActivity(), EditPlan.class);

        intent.putExtra("STARTDATE",planModel.StartDate);
        intent.putExtra("SUMMA",planModel.Summa);
        intent.putExtra("TYPEPLAN",planModel.TypePlan);
        intent.putExtra("TODAY",planModel.Today);
        intent.putExtra("DATE",planModel.date);
        intent.putExtra("MOUTH",planModel.month);
        intent.putExtra("YEAR",planModel.year);

        startActivity(intent);
    }
}