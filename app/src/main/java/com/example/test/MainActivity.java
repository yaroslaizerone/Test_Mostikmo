package com.example.test;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.test.Fragments.Analystic.AnalysisFragment;
import com.example.test.Fragments.Card.CardFragment;
import com.example.test.Fragments.MoreModels.MoreFragment;
import com.example.test.Fragments.Operation.OperationFragment;
import com.example.test.Fragments.Plan.PlanFragment;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        replaceFragment(new CardFragment());
        mAuth = FirebaseAuth.getInstance();

        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.bankcards:
                    replaceFragment(new CardFragment());
                    break;
                case R.id.topuppayment:
                    replaceFragment(new OperationFragment());
                    break;
                case R.id.paperwork:
                    replaceFragment(new PlanFragment());
                    break;
                case R.id.graph:
                    replaceFragment(new AnalysisFragment());
                    break;
                case R.id.about:
                    replaceFragment(new MoreFragment());
                    break;
            }
            return true;
        });
    }
    void  replaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout,fragment);
        fragmentTransaction.commit();
    }
}