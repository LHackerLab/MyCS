package hacker.l.coldstore.fragments;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.SmsManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.adapter.GetUserAdapter;
import hacker.l.coldstore.adapter.SMSAdapter;
import hacker.l.coldstore.model.MyPojo;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.utility.Contants;

public class SMSFragment extends Fragment implements CompoundButton.OnCheckedChangeListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static SMSFragment newInstance(String param1, String param2) {
        SMSFragment fragment = new SMSFragment();
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
    RadioButton rb_newyear, rb_jaman, rb_customer, rb_emp, rb_crismus, rb_raksha, rb_gandhi, rb_eid, rb_dewali, rb_indepday, rb__guru, rb_repubday, rb_holi, rb_dussehra;
    EditText edt_message, edt_phone;
    Button btn_send;
    RecyclerView recycleView;
    List<Result> resultList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_sm, container, false);
        init();
        return view;
    }

    private void init() {
        resultList = new ArrayList<>();
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle(" Send SMS");
        rb_newyear = view.findViewById(R.id.rb_newyear);
        rb_jaman = view.findViewById(R.id.rb_jaman);
        rb_crismus = view.findViewById(R.id.rb_crismus);
        rb_raksha = view.findViewById(R.id.rb_raksha);
        rb_gandhi = view.findViewById(R.id.rb_gandhi);
        rb_eid = view.findViewById(R.id.rb_eid);
        rb_dewali = view.findViewById(R.id.rb_dewali);
        rb__guru = view.findViewById(R.id.rb__guru);
        rb_repubday = view.findViewById(R.id.rb_repubday);
        rb_holi = view.findViewById(R.id.rb_holi);
        rb_dussehra = view.findViewById(R.id.rb_dussehra);
        rb_indepday = view.findViewById(R.id.rb_indepday);
        rb_emp = view.findViewById(R.id.rb_emp);
        rb_customer = view.findViewById(R.id.rb_customer);
        edt_message = view.findViewById(R.id.edt_message);
        edt_phone = view.findViewById(R.id.edt_phone);
        recycleView = view.findViewById(R.id.recycleView);
        btn_send = view.findViewById(R.id.btn_send);
        rb_newyear.setOnCheckedChangeListener(this);
        rb_jaman.setOnCheckedChangeListener(this);
        rb_crismus.setOnCheckedChangeListener(this);
        rb_raksha.setOnCheckedChangeListener(this);
        rb_gandhi.setOnCheckedChangeListener(this);
        rb_eid.setOnCheckedChangeListener(this);
        rb_dewali.setOnCheckedChangeListener(this);
        rb__guru.setOnCheckedChangeListener(this);
        rb_repubday.setOnCheckedChangeListener(this);
        rb_holi.setOnCheckedChangeListener(this);
        rb_dussehra.setOnCheckedChangeListener(this);
        rb_indepday.setOnCheckedChangeListener(this);
        rb_emp.setOnCheckedChangeListener(this);
        rb_customer.setOnCheckedChangeListener(this);
        isSmsCallPermissionGranted();
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = edt_message.getText().toString();
                String phone = edt_phone.getText().toString();
                if (phone.length() == 10) {
                    try {
                        SmsManager smsManager = SmsManager.getDefault();
                        smsManager.sendTextMessage(phone, null, message, null, null);
                        Toast.makeText(context, "SMS sent.On" + phone,
                                Toast.LENGTH_LONG).show();
                    } catch (Exception e) {
                        Toast.makeText(context,
                                "SMS faild, Please try Again.",
                                Toast.LENGTH_LONG).show();
                        e.printStackTrace();
                    }
                } else {
                    edt_phone.setError("Enter Phone Number");
                }
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleView.setLayoutManager(linearLayoutManager);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        switch (buttonView.getId()) {
            case R.id.rb_newyear:
                if (isChecked) {
                    edt_message.setText("Happy new year ");
                }
                break;
            case R.id.rb_jaman:
                if (isChecked) {
                    edt_message.setText("Happy new janamastmi ");
                }
                break;
            case R.id.rb_crismus:
                if (isChecked) {
                    edt_message.setText("Happy new crismus ");
                }
                break;
            case R.id.rb_raksha:
                if (isChecked) {
                    edt_message.setText("Happy new raksha ");
                }
                break;
            case R.id.rb_gandhi:
                if (isChecked) {
                    edt_message.setText("Happy new gandhi ");
                }
                break;
            case R.id.rb_eid:
                if (isChecked) {
                    edt_message.setText("Happy new eid ");
                }
                break;
            case R.id.rb_dewali:
                if (isChecked) {
                    edt_message.setText("Happy new dewali ");
                }
                break;
            case R.id.rb__guru:
                if (isChecked) {
                    edt_message.setText("Happy new guru ");
                }
                break;
            case R.id.rb_repubday:
                if (isChecked) {
                    edt_message.setText("Happy new republic day ");
                }
                break;
            case R.id.rb_holi:
                if (isChecked) {
                    edt_message.setText("Happy new holi ");
                }
                break;
            case R.id.rb_dussehra:
                if (isChecked) {
                    edt_message.setText("Happy new dussehra ");
                }
                break;
            case R.id.rb_indepday:
                if (isChecked) {
                    edt_message.setText("Happy new independent ");
                }
                break;
            case R.id.rb_emp:
                if (isChecked) {
                    getAllEmployee();
                }
                break;
            case R.id.rb_customer:
                if (isChecked) {
                    getAllCustomers();
                }
                break;
        }
    }

    public boolean isSmsCallPermissionGranted() {
        if (Build.VERSION.SDK_INT >= 23) {
            if (context.checkSelfPermission(Manifest.permission.SEND_SMS)
                    == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.SEND_SMS}, 4);
                return false;
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            return true;
        }
    }

    private void getAllCustomers() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAlInward,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        resultList.clear();
                        resultList.addAll(Arrays.asList(myPojo.getResult()));
                        if (resultList != null && resultList.size() != 0) {
                            SMSAdapter smsAdapter = new SMSAdapter(context, resultList, Type.Cus);
                            recycleView.setAdapter(smsAdapter);
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

    private void getAllEmployee() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getAllUsers,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                        resultList.clear();
                        resultList.addAll(Arrays.asList(myPojo.getResult()));
                        if (resultList != null && resultList.size() != 0) {
                            SMSAdapter smsAdapter = new SMSAdapter(context, resultList, Type.Emp);
                            recycleView.setAdapter(smsAdapter);
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

    public enum Type {
        Emp,
        Cus
    }
}