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
import hacker.l.coldstore.adapter.VarietyAdapter;
import hacker.l.coldstore.model.MyPojo;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.myalert.SweetAlertDialog;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;

public class FloorFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static FloorFragment newInstance(String param1, String param2) {
        FloorFragment fragment = new FloorFragment();
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
    EditText edt_floor;
    Button add;
    RecyclerView recyclerView;
    LinearLayoutManager linearLayoutManager;
    List<Result> resultList;
    Boolean aBoolean = false;
    ProgressDialog pd;
    int floorId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_floor, container, false);
        init();
        return view;
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Floor");
        edt_floor = view.findViewById(R.id.edt_floor);
        add = view.findViewById(R.id.btn_add);
        recyclerView = view.findViewById(R.id.recycleView);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        resultList = new ArrayList<>();
        setFloorAdapter();
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (aBoolean) {
                    updateFloorDataServer();
                } else {
                    addFloorDataServer();
                }

            }
        });

    }

    public void setFloorAdapter() {
        if (Utility.isOnline(context)) {
            pd = new ProgressDialog(context);
            pd.setCancelable(false);
            pd.show();
            pd.getWindow()
                    .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
            pd.setContentView(new ProgressBar(context));
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllFloor,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                            resultList.clear();
                            resultList.addAll(Arrays.asList(myPojo.getResult()));
                            if (resultList != null) {
                                Collections.reverse(resultList);
                                FloorAdapter varietyAdapter = new FloorAdapter(context, resultList, FloorFragment.this);
                                recyclerView.setAdapter(varietyAdapter);
                                edt_floor.setText("");
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

    private void updateFloorDataServer() {
        final String floor = edt_floor.getText().toString();
        if (!floor.equalsIgnoreCase("")) {
            if (Utility.isOnline(context)) {
                pd = new ProgressDialog(context);
                pd.setCancelable(false);
                pd.show();
                pd.getWindow()
                        .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pd.setContentView(new ProgressBar(context));
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.updateFloor,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                                setFloorAdapter();
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
                        params.put("floorId", String.valueOf(floorId));
                        params.put("floor", floor);
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
        } else {
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Sorry...")
                    .setContentText("Enter Floor ")
                    .show();
        }
    }

    public void updateFloorData(Boolean aBoolean, int floor, int floorId) {
        this.aBoolean = aBoolean;
        this.floorId = floorId;
        edt_floor.setText(String.valueOf(floor));
        edt_floor.setSelection(edt_floor.length());
        add.setText("Update");
    }

    public void addFloorDataServer() {
        final String floor = edt_floor.getText().toString();
        if (!floor.equalsIgnoreCase("")) {
            if (Utility.isOnline(context)) {
                pd = new ProgressDialog(context);
                pd.setCancelable(false);
                pd.show();
                pd.getWindow()
                        .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pd.setContentView(new ProgressBar(context));
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.AddFloor,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();
                                setFloorAdapter();
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
                        params.put("floor", floor);
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
        } else {
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Sorry...")
                    .setContentText("Enter Floor")
                    .show();
        }
    }
}
