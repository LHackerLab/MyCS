package hacker.l.coldstore.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.adapter.RackAdapter;
import hacker.l.coldstore.adapter.VarietyAdapter;
import hacker.l.coldstore.model.Result;

public class RackFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static RackFragment newInstance(String param1, String param2) {
        RackFragment fragment = new RackFragment();
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
    Button add;
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    Boolean aBoolean = false;
    EditText edt_rack, edt_capacity;
    List<Result> resultList;
    AppCompatSpinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_rack, container, false);
        init();
        return view;
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Rack");
        add = view.findViewById(R.id.btn_add);
        edt_rack = view.findViewById(R.id.edt_rack);
        edt_capacity = view.findViewById(R.id.edt_rackCapacity);
        recyclerView = view.findViewById(R.id.recycleView);
        spinner = view.findViewById(R.id.spinner);
        List<Integer> spinnerList = new ArrayList<>();
        spinnerList.add(1);
        spinnerList.add(2);
        spinnerList.add(3);
        spinnerList.add(4);
        spinnerList.add(5);
        ArrayAdapter<Integer> integerArrayAdapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_dropdown_item, spinnerList);
        spinner.setAdapter(integerArrayAdapter);
        integerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        resultList = new ArrayList<>();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aBoolean) {
                    updateRackDataServer();
                } else {
                    addRackDataServer();
                }

            }
        });
    }

    private void addRackDataServer() {
        setRackAdapter();
    }

    private void updateRackDataServer() {
        setRackAdapter();
        add.setText("Add");
    }

    public void updateRackData(Boolean aBoolean, int rack, int capacity, int rackId) {
        this.aBoolean = aBoolean;
        edt_rack.setText(String.valueOf(rack));
        edt_capacity.setText(String.valueOf(capacity));
        edt_rack.setSelection(edt_rack.length());
        edt_capacity.setSelection(edt_capacity.length());
        add.setText("Update");
    }

    public void setRackAdapter() {
        String rack = edt_rack.getText().toString();
        String capacity = edt_capacity.getText().toString();
        Result result = new Result();
        result.setRack(Integer.parseInt(rack));
        result.setCapacity(Integer.parseInt(capacity));
        resultList.add(result);
        RackAdapter rackAdapter = new RackAdapter(context, resultList, RackFragment.this);
        recyclerView.setAdapter(rackAdapter);
    }
}
