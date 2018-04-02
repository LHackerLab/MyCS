package hacker.l.coldstore.fragments;

import android.app.ProgressDialog;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.adapter.FloorAdapter;
import hacker.l.coldstore.adapter.VarietyAdapter;
import hacker.l.coldstore.model.MyPojo;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;

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
    int varietyId;
    ProgressDialog pd;

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
        setVarietyAdapter();
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
        final String variety = edt_varietyName.getText().toString();
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setMessage("Adding wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.addVariety,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();
                            setVarietyAdapter();
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
                    params.put("variety", variety);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateVarietyDataServer() {
        final String variety = edt_varietyName.getText().toString();
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setMessage("Uploading wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.updateVariety,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                            setVarietyAdapter();
                            add.setText("Add");
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
                    params.put("varietyId", String.valueOf(varietyId));
                    params.put("variety", variety);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    public void updateVarietyData(Boolean aBoolean, String varietyName, int varietyId) {
        this.aBoolean = aBoolean;
        this.varietyId = varietyId;
        edt_varietyName.setText(varietyName);
        edt_varietyName.setSelection(edt_varietyName.length());
        add.setText("Update");
    }
    public void setVarietyAdapter() {
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setMessage("Getting Variety wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllVariety,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                            resultList.clear();
                            resultList.addAll(Arrays.asList(myPojo.getResult()));
                            if (resultList != null) {
                                Collections.reverse(resultList);
                                VarietyAdapter varietyAdapter = new VarietyAdapter(context, resultList, VarietyFragment.this);
                                recyclerView.setAdapter(varietyAdapter);
                                edt_varietyName.setText("");
                            }
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
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }
}
