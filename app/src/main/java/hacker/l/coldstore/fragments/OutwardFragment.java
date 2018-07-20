package hacker.l.coldstore.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.model.Result;

public class OutwardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static OutwardFragment newInstance(String param1, String param2) {
        OutwardFragment fragment = new OutwardFragment();
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
    Button btn_proced;
    EditText edt_wholeSaleNo, edt_departure;
    TextView tv_name, tv_address, tv_Rqty, tv_qty, tv_rack, tv_floor, tv_variety, tv_phone, tv_Fname, tv_rent;
    double quantity = 0.0;
    String name, fName, address, rack, qty, departure, phone, variety, rent;
    int floor, inwardId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_outward, container, false);
        init();
        return view;
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Outward");
        btn_proced = view.findViewById(R.id.btn_proced);
        edt_wholeSaleNo = view.findViewById(R.id.edt_wholeSaleNo);
        edt_departure = view.findViewById(R.id.edt_departure);
        tv_name = view.findViewById(R.id.tv_name);
        tv_address = view.findViewById(R.id.tv_address);
        tv_Rqty = view.findViewById(R.id.tv_Rqty);
        tv_qty = view.findViewById(R.id.tv_qty);
        tv_rack = view.findViewById(R.id.tv_rack);
        tv_floor = view.findViewById(R.id.tv_floor);
        tv_variety = view.findViewById(R.id.tv_variety);
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_Fname = view.findViewById(R.id.tv_Fname);
        tv_rent = view.findViewById(R.id.tv_rent);
        btn_proced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validation()) {
                    AccoutnFragment accoutnFragment = AccoutnFragment.newInstance("outward", name, fName, phone, address, qty, rent, variety, rack, floor, inwardId, departure);
                    moveragment(accoutnFragment);
                }
            }
        });
        edt_wholeSaleNo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                DbHelper dbHelper = new DbHelper(context);
                String sNo = edt_wholeSaleNo.getText().toString();
                if (!sNo.equalsIgnoreCase("")) {
                    Result result = dbHelper.getInwardDataByInwardId(Integer.parseInt(sNo));
                    if (result != null) {
                        quantity = Double.parseDouble(result.getQuantity());
                        tv_name.setText(result.getUserName());
                        tv_address.setText(result.getAddress());
                        tv_qty.setText(result.getQuantity());
                        tv_rack.setText(result.getRack());
                        tv_floor.setText(result.getFloor() + "");
                        tv_variety.setText(result.getVariety());
                        tv_phone.setText(result.getUserPhone());
                        tv_Fname.setText(result.getFatherName());
                        tv_rent.setText(result.getRent());
                        tv_Rqty.setText(quantity + "");
                    }
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        edt_departure.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String strDep = edt_departure.getText().toString();
                if (strDep != null && !strDep.equalsIgnoreCase("")) {
                    double dep = Double.parseDouble(strDep);
                    if (dep > quantity) {
                        edt_departure.setError("Enter Max " + quantity);
                    }
                    tv_Rqty.setText(quantity - dep + "");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    boolean validation() {
        String strinwardId = edt_wholeSaleNo.getText().toString();
        String stdep = edt_departure.getText().toString();
        if (strinwardId != null && stdep != null) {
            inwardId = Integer.parseInt(strinwardId);
            address = tv_address.getText().toString();
            name = tv_name.getText().toString();
            fName = tv_Fname.getText().toString();
            phone = tv_phone.getText().toString();
            qty = tv_qty.getText().toString();
            rack = tv_rack.getText().toString();
            rent = tv_rent.getText().toString();
            departure = stdep;
            variety = tv_variety.getText().toString();
            floor = Integer.parseInt(tv_floor.getText().toString());
        } else {
            edt_wholeSaleNo.setError("Enter Values");
            return false;
        }
        return true;
    }

    private void moveragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit);
                transaction.replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
