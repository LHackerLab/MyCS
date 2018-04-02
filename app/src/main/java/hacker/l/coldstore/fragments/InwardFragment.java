package hacker.l.coldstore.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import hacker.l.coldstore.adapter.RackAdapter;
import hacker.l.coldstore.adapter.VarietyAdapter;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.model.MyPojo;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;

public class InwardFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static InwardFragment newInstance(String param1, String param2) {
        InwardFragment fragment = new InwardFragment();
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
    EditText edt_empName, edt_empFName, edt_phone, edt_qty, edt_rent, edt_address;
    Button btn_add;
    TextView tv_wholeSaleNo;
    AppCompatSpinner spinnerVariety, spinnerFloor, spinnerRack;
    ProgressDialog pd;
    int floor;
    String empName, empFName, phone, qty, rent, address, vareity, rack;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_inward, container, false);
        init();
        return view;
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Inward");
        edt_empName = view.findViewById(R.id.edt_empName);
        edt_empFName = view.findViewById(R.id.edt_empFName);
        edt_phone = view.findViewById(R.id.edt_phone);
//        tv_wholeSaleNo = view.findViewById(R.id.tv_wholeSaleNo);
        spinnerVariety = view.findViewById(R.id.spinnerVariety);
        edt_qty = view.findViewById(R.id.edt_qty);
        edt_rent = view.findViewById(R.id.edt_rent);
        spinnerFloor = view.findViewById(R.id.spinnerFloor);
        spinnerRack = view.findViewById(R.id.spinnerRack);
        edt_address = view.findViewById(R.id.edt_address);
        btn_add = view.findViewById(R.id.btn_add);
//        getVariety();
//        getFloor();
//        getRack();
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadInwardData();
            }
        });
    }

    public boolean validation() {
        empName = edt_empName.getText().toString();
        empFName = edt_empFName.getText().toString();
        phone = edt_phone.getText().toString();
        qty = edt_qty.getText().toString();
        rent = edt_rent.getText().toString();
        address = edt_address.getText().toString();
        if (empName.length() == 0) {
            requestFocus(edt_empName);
            edt_empName.setError("Enter Name");
            return false;
        } else if (empFName.length() == 0) {
            requestFocus(edt_empFName);
            edt_empFName.setError("Enter Father Name");
            return false;
        } else if (phone.length() == 0) {
            requestFocus(edt_phone);
            edt_phone.setError("Enter Phone");
            return false;
        } else if (phone.length() != 10) {
            requestFocus(edt_phone);
            edt_phone.setError("Enter valid Phone");
            return false;
        } else if (qty.length() == 0) {
            edt_qty.setError("Enter Quantity");
            requestFocus(edt_qty);
            return false;
        } else if (rent.length() == 0) {
            requestFocus(edt_rent);
            edt_rent.setError("Enter Rent Price");
            return false;
        } else if (address.length() == 0) {
            requestFocus(edt_address);
            edt_address.setError("Enter Address");
            return false;
        } else if (vareity != null && vareity.length() == 0) {
            Toast.makeText(context, "Select Vareity", Toast.LENGTH_SHORT).show();
            return false;
        } else if (floor == 0) {
            Toast.makeText(context, "Select Floor", Toast.LENGTH_SHORT).show();
            return false;
        } else if (rack != null && rack.length() == 0) {
            Toast.makeText(context, "Select Rack", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void uploadInwardData() {
//        if (Utility.isOnline(context)) {
        if (validation()) {
//                pd = new ProgressDialog(context);
//                pd.setMessage("Adding wait...");
//                pd.show();
//                pd.setCancelable(false);
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.AddInward,
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                pd.dismiss();
//                                Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();
            AccoutnFragment accoutnFragment = AccoutnFragment.newInstance("inward", empName, empFName, phone, address, qty, rent, vareity, rack, floor);
            moveragment(accoutnFragment);
        }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                pd.dismiss();
//                            }
//                        }) {
//                    @Override
//                    protected Map<String, String> getParams() throws AuthFailureError {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("userName", empName);
//                        params.put("fatherName", empFName);
//                        params.put("userPhone", phone);
//                        params.put("address", address);
//                        params.put("quantity", qty);
//                        params.put("rent", rent);
//                        params.put("variety", "1");
//                        params.put("floor", "2");
//                        params.put("rack", "3");
//                        return params;
//                    }
//                };
//                RequestQueue requestQueue = Volley.newRequestQueue(context);
//                requestQueue.add(stringRequest);
//            }
//        } else {
//            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
//        }
    }

    @Override
    public void onStart() {
        getRack();
        getFloor();
        getVariety();
        super.onStart();

    }

    private void moveragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    private void getVariety() {
        final List<String> stringList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllVariety,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        for (Result result : myPojo.getResult()) {
                            stringList.addAll(Arrays.asList(result.getVarietyName()));
                        }
                        if (stringList != null) {
                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, stringList);
                            spinnerVariety.setAdapter(adapter);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerVariety.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    vareity = parent.getSelectedItem().toString();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
    }

    private void getFloor() {
        final List<Integer> stringList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllFloor,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        for (Result result : myPojo.getResult()) {
                            stringList.addAll(Arrays.asList(result.getFloor()));
                        }
                        if (stringList != null) {
                            ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(context, android.R.layout.simple_spinner_dropdown_item, stringList);
                            spinnerFloor.setAdapter(adapter);
                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                            spinnerFloor.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                @Override
                                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                                    floor = Integer.parseInt(parent.getSelectedItem().toString());
                                    setRackSpinner();
                                }

                                @Override
                                public void onNothingSelected(AdapterView<?> parent) {

                                }
                            });

                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
    }

    private void getRack() {
        final List<String> stringList = new ArrayList<>();
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllRack,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        for (Result result : myPojo.getResult()) {
                            new DbHelper(context).upsertRackData(result);
//                            stringList.addAll(Arrays.asList(result.getRack()));
                        }
                        setRackSpinner();
//                        if (stringList != null) {
//                            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, stringList);
//                            spinnerRack.setAdapter(adapter);
//                            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//                            rack = spinnerRack.getSelectedItem().toString();
//                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
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
    }

    private void setRackSpinner() {
        DbHelper dbHelper = new DbHelper(context);
        Result result = dbHelper.getRackDataByFloor(floor);
        if (result != null) {
            List<String> stringList = new ArrayList<>();
            stringList.addAll(Arrays.asList(result.getRack()));
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, stringList);
            spinnerRack.setAdapter(adapter);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinnerRack.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    rack = parent.getSelectedItem().toString();
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

    }
}
