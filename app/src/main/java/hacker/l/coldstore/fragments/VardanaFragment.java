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
import android.widget.Button;
import android.widget.EditText;
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
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.adapter.FloorAdapter;
import hacker.l.coldstore.adapter.VardanaAdapter;
import hacker.l.coldstore.model.MyPojo;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.myalert.SweetAlertDialog;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;

public class VardanaFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static VardanaFragment newInstance(String param1, String param2) {
        VardanaFragment fragment = new VardanaFragment();
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
    EditText edt_type, edt_qty, edt_phone, edt_name;
    Button add;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<Result> resultList;
    Boolean aBoolean = false;
    ProgressDialog pd;
    int vardanaId;
    String name, phone, qty, type;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_vardana, container, false);
        init();
        return view;
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Vardana");
        edt_type = view.findViewById(R.id.edt_type);
        edt_qty = view.findViewById(R.id.edt_qty);
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_name = view.findViewById(R.id.edt_name);
        add = view.findViewById(R.id.btn_add);
        recyclerView = view.findViewById(R.id.recycleView);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        resultList = new ArrayList<>();
        setVardanaAdapter();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aBoolean) {
                    updateVardanaDataServer();
                } else {
                    addVardanaDataServer();
                }

            }
        });

    }

    private void addVardanaDataServer() {
        if (validation()) {
            if (Utility.isOnline(context)) {
                pd = new ProgressDialog(context);
                pd.setCancelable(false);
                pd.show();
                pd.getWindow()
                        .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pd.setContentView(new ProgressBar(context));
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.addVardana,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();
                                setVardanaAdapter();
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
                        params.put("name", name);
                        params.put("phone", phone);
                        params.put("qty", qty);
                        params.put("type", type);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            } else {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Sorry...")
                        .setContentText("You are Offline. Please check your Internet Connection.Thank You ")
                        .show();
            }

        }
    }

    public boolean validation() {
        name = edt_name.getText().toString();
        phone = edt_phone.getText().toString();
        qty = edt_qty.getText().toString();
        type = edt_type.getText().toString();
        if (name.length() == 0) {
            edt_name.setError("Enter Name");
            return false;
        } else if (phone.length() == 0) {
            edt_phone.setError("Enter Phone");
            return false;
        } else if (phone.length() != 10) {
            edt_phone.setError("Enter valid Phone");
            return false;
        } else if (qty.length() == 0) {
            edt_qty.setError("Enter Quantity");
            return false;
        }

        return true;
    }

    public void updateVardanaData(Boolean aBoolean, String name, String phone, String qty, String type, int Id) {
        this.aBoolean = aBoolean;
        this.vardanaId = Id;
        edt_name.setText(name);
        edt_phone.setText(phone);
        edt_qty.setText(qty);
        edt_type.setText(type);
        add.setText("Update");
        edt_name.setSelection(edt_name.getText().length());
    }

    private void updateVardanaDataServer() {
        if (validation()) {
            if (Utility.isOnline(context)) {
                pd = new ProgressDialog(context);
                pd.setCancelable(false);
                pd.show();
                pd.getWindow()
                        .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pd.setContentView(new ProgressBar(context));
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.updateVardana,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                                setVardanaAdapter();
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
                        params.put("vardanaId", String.valueOf(vardanaId));
                        params.put("name", name);
                        params.put("phone", phone);
                        params.put("qty", qty);
                        params.put("type", type);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            } else {
                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                        .setTitleText("Sorry...")
                        .setContentText("You are Offline. Please check your Internet Connection.Thank You ")
                        .show();
            }
        }
    }

    public void setVardanaAdapter() {
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setCancelable(false);
            pd.show();
            pd.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pd.setContentView(new ProgressBar(context));
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllVardana,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                            resultList.clear();
                            resultList.addAll(Arrays.asList(myPojo.getResult()));
                            if (resultList != null) {
                                Collections.reverse(resultList);
                                VardanaAdapter adapter = new VardanaAdapter(context, resultList, VardanaFragment.this);
                                recyclerView.setAdapter(adapter);
                                edt_phone.setText("");
                                edt_name.setText("");
                                edt_qty.setText("");
                                edt_type.setText("");
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
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Sorry...")
                    .setContentText("You are Offline. Please check your Internet Connection.Thank You ")
                    .show();
        }
    }

}
