package com.example.test;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.time.temporal.ValueRange;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link MoreFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class MoreFragment extends Fragment {

    FirebaseAuth mAuth;
    FirebaseUser user;
    TextView userEm;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public MoreFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment MoreFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static MoreFragment newInstance(String param1, String param2) {
        MoreFragment fragment = new MoreFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        LinearLayout OUT = getView().findViewById(R.id.linearLayoutOUT);
        LinearLayout Location = getView().findViewById(R.id.mapListLayout);
        LinearLayout valut = getView().findViewById(R.id.valutChengeLayout);
        LinearLayout category = getView().findViewById(R.id.categoryLayout);
        userEm = getView().findViewById(R.id.userEmail);
        userEm.setText(user.getEmail());

        OUT.setOnClickListener(v -> Logout());
        Location.setOnClickListener(v -> GoToLocationlist());
        valut.setOnClickListener(v -> GoToValutChange());
        category.setOnClickListener(v -> GoToCategory());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_more, container, false);
    }
    //TODO сделать переходы на активности из дополнительного меню
    void Logout (){
        mAuth.signOut();
        startActivity(new Intent(getActivity(),LoginActivity.class));
    }
    void GoToLocationlist(){
        startActivity(new Intent(getActivity(), LocationListActivity.class));
    }
    void GoToValutChange(){
        startActivity(new Intent(getActivity(), CurrencySelectionActivity.class));
    }
    void GoToCategory(){
        startActivity(new Intent(getActivity(), CategoryListActivity.class));
    }
}