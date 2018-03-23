package hacker.l.coldstore.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.adapter.VarietyAdapter;
import hacker.l.coldstore.model.Result;

public class VarietyFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static VarietyFragment newInstance(String param1, String param2) {
        VarietyFragment fragment = new VarietyFragment();
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

    View view;
    Context context;
    EditText edt_varietyName;
    Button add;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<Result> resultList;
    Boolean aBoolean = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_variety, container, false);
        init();
        return view;
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Variety");
        edt_varietyName = view.findViewById(R.id.edt_varietyName);
        add = view.findViewById(R.id.btn_add);
        recyclerView = view.findViewById(R.id.recycleView);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        resultList = new ArrayList<>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aBoolean) {
                    updateVarietyDataServer();
                } else {
                    addVarietyDataServer();
                }

            }
        });

    }

    private void addVarietyDataServer() {
        setVarietyAdapter();
    }

    private void updateVarietyDataServer() {
        setVarietyAdapter();
        add.setText("Add");
    }

    public void updateVarietyData(Boolean aBoolean, String varietyName, int varietyId) {
        this.aBoolean = aBoolean;
        edt_varietyName.setText(varietyName);
        edt_varietyName.setSelection(edt_varietyName.length());
        add.setText("Update");
    }

    public void setVarietyAdapter() {
        String variety = edt_varietyName.getText().toString();
        Result result = new Result();
        result.setVarietyName(variety);
        resultList.add(result);
        VarietyAdapter varietyAdapter = new VarietyAdapter(context, resultList, VarietyFragment.this);
        recyclerView.setAdapter(varietyAdapter);
    }
}
