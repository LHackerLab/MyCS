package hacker.l.coldstore.fragments;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
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
    EditText edt_wholeSaleNo;
    TextView tv_name,tv_address;

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
        tv_name = view.findViewById(R.id.tv_name);
        tv_address = view.findViewById(R.id.tv_address);
        btn_proced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AccoutnFragment accoutnFragment = AccoutnFragment.newInstance("", "", "", "", "", "", "", "", "", 0, 0);
                moveragment(accoutnFragment);
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
                if (!sNo.equalsIgnoreCase("")){
                    Result result = dbHelper.getInwardDataByInwardId(Integer.parseInt(sNo));
                    if (result != null) {
                        tv_name.setText(result.getUserName());
                        tv_address.setText(result.getAddress());
                    }
                }


            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void moveragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
