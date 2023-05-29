package com.example.test;

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
import android.widget.Switch;
import android.widget.TextView;

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


public class AnalysisFragment extends Fragment {
    private List<DateModel> dates = new ArrayList<DateModel>();
    private List<AnalysticModel> plans = new ArrayList<AnalysticModel>();
    private List<AnalysticModel> Selectplans = new ArrayList<AnalysticModel>();
    private List<LegendModel> legends = new ArrayList<LegendModel>();
    private List<CategoryModel> cats =  new ArrayList<CategoryModel>();
    private RecyclerView rv, rv1;
    private int list = 0;
    View view;
    LinearLayoutManager llm, llm1;
    Context context;
    FirebaseFirestore db;
    FirebaseUser user;
    FirebaseAuth mAuth;
    TextView last, next, ListPlan;
    DateModel plan;
    String typePlan;
    private Long datePlan, mouthPlan, yearPlan, SummaPlan;
    int r1, r2, r3, r4, r5, r6, r7, r8, r9, r10, r11, r12, r13, r14, r15;
    PieChart pieChart;

    //TODO Написать обработчик для кажой категории
    // Вывод организовается по сумме всех категорий
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_analysis, container, false);
        context = getContext();
        rv = view.findViewById(R.id.LegendRecycleView);
        rv1 = view.findViewById(R.id.CategoryRecycleView);
        llm = new LinearLayoutManager(context);
        llm1 = new LinearLayoutManager(context);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);
        rv1.setLayoutManager(llm1);
        rv1.setHasFixedSize(true);

        next = view.findViewById(R.id.listnext);
        last = view.findViewById(R.id.listback);
        ListPlan = view.findViewById(R.id.selectedMonth);
        pieChart = view.findViewById(R.id.piechart);

        last.setOnClickListener(v -> Backlist());
        next.setOnClickListener(v -> Nextlist());

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
        db.collection("userOperation")
                .whereEqualTo("email", user.getEmail()).orderBy("yearoperation").orderBy("monthoperation")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                datePlan = document.getLong("dateoperation");
                                mouthPlan = document.getLong("monthoperation");
                                yearPlan = document.getLong("yearoperation");
                                typePlan = document.getString("typecategoty");
                                SummaPlan = document.getLong("summa");

                                plan = new DateModel(mouthPlan, yearPlan);
                                plans.add(new AnalysticModel(datePlan,mouthPlan,yearPlan,SummaPlan,typePlan));

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
                    }
                });
    }

    private void SetData(int month, int year){
        legends.clear();
        Selectplans.clear();
        pieChart.clearChart();
        cats.clear();
        r1 = 0;
        r2 = 0;
        r3 = 0;
        r4 = 0;
        r5 = 0;
        r6 = 0;
        r7 = 0;
        r8 = 0;
        r9 = 0;
        r10 = 0;
        r11 = 0;
        r12 = 0;
        r13 = 0;
        r14 = 0;
        r15 = 0;

        for(int i = 0; i < plans.size();i++){
            if (month == plans.get(i).month && year == plans.get(i).year){
                Selectplans.add(plans.get(i));
            }
        }

        for(int i = 0; i < Selectplans.size(); i++){
            switch (Selectplans.get(i).category){
                case "Дети":
                    r1 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#261B7D"));
                    break;
                case "Забота о себе":
                    r2 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#48CD74"));
                    break;
                case "Зарплата":
                    r3 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#32EC00"));
                    break;
                case "Продукты":
                    r4 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#8C0C05"));
                    break;
                case "Кафе и рестораны":
                    r5 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#EE2894"));
                    break;
                case "Корректировка":
                    r6 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#EE2434"));
                    break;
                case "Машина":
                    r7 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#94F4CA"));
                    break;
                case "Образование":
                    r8 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#0A2350"));
                    break;
                case "Банк":
                    r9 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#6A2B20"));
                    break;
                case "Отдых и развлечения":
                    r10 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#B7B608"));
                    break;
                case "Платежи и комиссии":
                    r11 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#8B225A"));
                    break;
                case "Подарки":
                    r12 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#8A08B7"));
                    break;
                case "Покупки: одежда, техника":
                    r13 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#5FBB97"));
                    break;
                case "Проезд":
                    r14 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#CB9A1F"));
                    break;
                case "Здоровье и фитнес":
                    r15 += Integer.parseInt(String.valueOf(Selectplans.get(i).summa));
                    legendLable(Selectplans.get(i).category,Color.parseColor("#7D7E5F"));
                    break;
            }
        }
        for(int i = 0; i < legends.size(); i++){
            switch (legends.get(i).legendName){
                case "Дети":
                    cats.add(new CategoryModel(R.drawable.babysroom,legends.get(i).legendName,String.valueOf(r1)));
                    break;
                case "Забота о себе":
                    cats.add(new CategoryModel(R.drawable.womanhair,legends.get(i).legendName,String.valueOf(r2)));
                    break;
                case "Зарплата":
                    cats.add(new CategoryModel(R.drawable.receiveeuro,legends.get(i).legendName,String.valueOf(r3)));
                    break;
                case "Продукты":
                    cats.add(new CategoryModel(R.drawable.shoppingbasket,legends.get(i).legendName,String.valueOf(r4)));
                    break;
                case "Кафе и рестораны":
                    cats.add(new CategoryModel(R.drawable.diningroom,legends.get(i).legendName,String.valueOf(r5)));
                    break;
                case "Корректировка":
                    cats.add(new CategoryModel(R.drawable.help,legends.get(i).legendName,String.valueOf(r6)));
                    break;
                case "Машина":
                    cats.add(new CategoryModel(R.drawable.sedan,legends.get(i).legendName,String.valueOf(r7)));
                    break;
                case "Образование":
                    cats.add(new CategoryModel(R.drawable.openbook,legends.get(i).legendName,String.valueOf(r8)));
                    break;
                case "Банк":
                    cats.add(new CategoryModel(R.drawable.babysroom,legends.get(i).legendName,String.valueOf(r9)));
                    break;
                case "Отдых и развлечения":
                    cats.add(new CategoryModel(R.drawable.dancing,legends.get(i).legendName,String.valueOf(r10)));
                    break;
                case "Платежи и комиссии":
                    cats.add(new CategoryModel(R.drawable.smartphone,legends.get(i).legendName,String.valueOf(r11)));
                    break;
                case "Подарки":
                    cats.add(new CategoryModel(R.drawable.gift,legends.get(i).legendName,String.valueOf(r12)));
                    break;
                case "Покупки: одежда, техника":
                    cats.add(new CategoryModel(R.drawable.shirt,legends.get(i).legendName,String.valueOf(r13)));
                    break;
                case "Проезд":
                    cats.add(new CategoryModel(R.drawable.bus,legends.get(i).legendName,String.valueOf(r14)));
                    break;
                case "Здоровье и фитнес":
                    cats.add(new CategoryModel(R.drawable.medicalbag,legends.get(i).legendName,String.valueOf(r15)));
                    break;
            }
        }

        initializeAdapter();

        LegendAdapter adapter = new LegendAdapter(context, legends);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);

        CategoryAdapter adapter1 = new CategoryAdapter(context, cats);
        adapter1.notifyDataSetChanged();
        rv1.setAdapter(adapter1);

        pieChart.addPieSlice(
                new PieModel(
                        "Дети",
                        r1,
                        Color.parseColor("#261B7D")));
        pieChart.addPieSlice(
                new PieModel(
                        "Забота о себе",
                        r2,
                        Color.parseColor("#48CD74")));
        pieChart.addPieSlice(
                new PieModel(
                        "Зарплата",
                        r3,
                        Color.parseColor("#32EC00")));
        pieChart.addPieSlice(
                new PieModel(
                        "Продукты",
                        r4,
                        Color.parseColor("#8C0C05")));
        pieChart.addPieSlice(
                new PieModel(
                        "Кафе и рестораны",
                        r5,
                        Color.parseColor("#EE2894")));
        pieChart.addPieSlice(
                new PieModel(
                        "Корректировка",
                        r6,
                        Color.parseColor("#EE2434")));
        pieChart.addPieSlice(
                new PieModel(
                        "Машина",
                        r7,
                        Color.parseColor("#94F4CA")));
        pieChart.addPieSlice(
                new PieModel(
                        "Образование",
                        r8,
                        Color.parseColor("#0A2350")));
        pieChart.addPieSlice(
                new PieModel(
                        "Банк",
                        r9,
                        Color.parseColor("#6A2B20")));
        pieChart.addPieSlice(
                new PieModel(
                        "Отдых и развлечения",
                        r10,
                        Color.parseColor("#B7B608")));
        pieChart.addPieSlice(
                new PieModel(
                        "Платежи и комиссии",
                        r11,
                        Color.parseColor("#8B225A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Подарки",
                        r12,
                        Color.parseColor("#8A08B7")));
        pieChart.addPieSlice(
                new PieModel(
                        "Покупки: одежда, техника",
                        r13,
                        Color.parseColor("#5FBB97")));
        pieChart.addPieSlice(
                new PieModel(
                        "Проезд",
                        r14,
                        Color.parseColor("#CB9A1F")));
        pieChart.addPieSlice(
                new PieModel(
                        "Здоровье и фитнес",
                        r15,
                        Color.parseColor("#7D7E5F")));

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

    private void legendLable(String legend, int Color){
        if(legends.size()==0){
            legends.add(new LegendModel(Color, legend));
        }else{
            int y = 0;
            for(int i = 0; i < legends.size(); i++){
                if(legends.get(i).legendName != legend){
                    y++;
                    if (y == legends.size()){
                        legends.add(new LegendModel(Color, legend));
                    }
                }
            }
        }
    }

    private void initializeAdapter(){
        LegendAdapter adapter = new LegendAdapter(context, legends);
        adapter.notifyDataSetChanged();
        rv.setAdapter(adapter);

        CategoryAdapter adapter1 = new CategoryAdapter(context, cats);
        adapter1.notifyDataSetChanged();
        rv1.setAdapter(adapter1);
    }

    @Override
    public void onResume() {
        super.onResume();
        dates.clear();
        plans.clear();
        legends.clear();
        initializeData();
        initializeAdapter();
    }
}