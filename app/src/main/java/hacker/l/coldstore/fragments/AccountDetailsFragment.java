package hacker.l.coldstore.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.adapter.InwardAdapter;
import hacker.l.coldstore.adapter.PaymentAdapter;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.model.MyPojo;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.myalert.SweetAlertDialog;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;


public class AccountDetailsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static AccountDetailsFragment newInstance(String param1, String param2) {
        AccountDetailsFragment fragment = new AccountDetailsFragment();
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
    RecyclerView recyclerView;
    List<Result> resultList;
    DbHelper dbHelper;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_account_details, container, false);
        init();
        return view;
    }

    private void init() {
        dbHelper = new DbHelper(context);
        resultList = new ArrayList<>();
        recyclerView = view.findViewById(R.id.recycleView);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        getAllPaymentData();
        setPaymentAdapter();
    }

    private void getAllPaymentData() {
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setCancelable(false);
            pd.show();
            pd.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pd.setContentView(new ProgressBar(context));
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllPayment,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                            for (Result result : myPojo.getResult()) {
                                dbHelper.upsertPaymentData(result);
                            }
                            setPaymentAdapter();
                            pd.dismiss();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            pd.dismiss();
                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
//        } else {
//            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                    .setTitleText("Sorry...")
//                    .setContentText("You are Offline. Please check your Internet Connection.Thank You ")
//                    .show();
        }
    }

    private void setPaymentAdapter() {
        List<Result> resultList = dbHelper.getAllPaymentData();
        if (resultList != null && resultList.size() != 0) {
            Collections.reverse(resultList);
            PaymentAdapter inwardAdapter = new PaymentAdapter(context, resultList, AccountDetailsFragment.this);
            recyclerView.setAdapter(inwardAdapter);
        }
    }
}
