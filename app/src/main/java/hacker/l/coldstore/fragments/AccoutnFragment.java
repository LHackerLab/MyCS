package hacker.l.coldstore.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;

public class AccoutnFragment extends Fragment {

    // TODO: Rename and change types of parameters
    private String empName;
    private String empFName, flag;
    private String phone, address, qty, rent, vareity, rack;
    private int floor;

    public static AccoutnFragment newInstance(String flag, String empName, String empFName, String phone, String address, String qty, String rent, String vareity, String rack, int floor) {
        AccoutnFragment fragment = new AccoutnFragment();
        Bundle args = new Bundle();
        args.putString("empName", empName);
        args.putString("empFName", empFName);
        args.putString("phone", phone);
        args.putString("address", address);
        args.putString("qty", qty);
        args.putString("rent", rent);
        args.putString("vareity", vareity);
        args.putString("rack", rack);
        args.putInt("floor", floor);
        args.putString("flag", flag);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            empName = getArguments().getString("empName");
            empFName = getArguments().getString("empFName");
            phone = getArguments().getString("phone");
            address = getArguments().getString("address");
            qty = getArguments().getString("qty");
            rent = getArguments().getString("rent");
            vareity = getArguments().getString("vareity");
            rack = getArguments().getString("rack");
            flag = getArguments().getString("flag");
            floor = getArguments().getInt("floor");
        }
    }

    View view;
    Context context;
    Button btn_proced;
    AppCompatSpinner spinner_caseType;
    ProgressDialog pd;
    TextView tv_name, tv_FName, tv_phone, tv_qty, tv_rent, tv_rBalance;
    EditText edt_advance;
    DecimalFormat decimalFormat;
    double totalPrice, grandTotal;
    String strCaseType, advanced;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_accoutn, container, false);
        init();
        return view;
    }

    private void init() {
        decimalFormat = new DecimalFormat("#.00");
        totalPrice = Double.parseDouble(decimalFormat.format(Double.parseDouble(rent) * Double.parseDouble(qty)));
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Accounts");
        btn_proced = view.findViewById(R.id.btn_proced);
        tv_name = view.findViewById(R.id.tv_name);
        tv_FName = view.findViewById(R.id.tv_FName);
        tv_phone = view.findViewById(R.id.tv_phone);
//        tv_wholeSaleNo = view.findViewById(R.id.tv_wholeSaleNo);
        tv_rent = view.findViewById(R.id.tv_rent);
        tv_qty = view.findViewById(R.id.tv_qty);
        tv_rBalance = view.findViewById(R.id.tv_rBalance);
        edt_advance = view.findViewById(R.id.edt_advance);
        setValues();
        setCasetypeAdapter();
        edt_advance.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                setRemaingBalance();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_proced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag.equalsIgnoreCase("inward")) {
                    AddInwardDataToServer();
                } else {

                }
//                Toast.makeText(context, "Print Report", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setRemaingBalance() {
        advanced = edt_advance.getText().toString();
        if (advanced.length() != 0) {
            if (strCaseType.equalsIgnoreCase("cr")) {
                grandTotal = totalPrice - Double.parseDouble(advanced);
                tv_rBalance.setText(String.valueOf(decimalFormat.format(grandTotal)));
            } else {
                grandTotal = Double.parseDouble(advanced) + totalPrice;
                tv_rBalance.setText(String.valueOf(decimalFormat.format(grandTotal)));
            }
        } else {
            grandTotal = 0 + totalPrice;
            tv_rBalance.setText(String.valueOf(decimalFormat.format(grandTotal)));
        }
    }

    private void setCasetypeAdapter() {
        final List<String> caseType = new ArrayList<>();
        caseType.add("Cr");
        caseType.add("Dr");
        ArrayAdapter<String> stringArrayAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_spinner_dropdown_item, caseType);
        spinner_caseType = view.findViewById(R.id.spinner_caseType);
        spinner_caseType.setAdapter(stringArrayAdapter);
        stringArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner_caseType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                strCaseType = parent.getSelectedItem().toString();
                setRemaingBalance();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setValues() {
        if (flag.equalsIgnoreCase("inward")) {
            tv_name.setText(empName);
            tv_FName.setText(empFName);
            tv_phone.setText(phone);
//            tv_wholeSaleNo.setText("pending");
            tv_qty.setText(qty);
            tv_rent.setText(rent + "*" + qty + "=" + totalPrice);
        }
    }

    private void moveragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void AddInwardDataToServer() {
        if (Utility.isOnline(context)) {
//            if (validation()) {
            pd = new ProgressDialog(context);
            pd.setMessage("Processing wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.AddInward,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            Toast.makeText(context, "Order Successful Done", Toast.LENGTH_SHORT).show();
                            HomeFragment accoutnFragment = HomeFragment.newInstance("inward", "");
                            moveragment(accoutnFragment);
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
                    params.put("userName", empName);
                    params.put("fatherName", empFName);
                    params.put("userPhone", phone);
                    params.put("address", address);
                    params.put("quantity", qty);
                    params.put("rent", rent);
                    params.put("variety", vareity);
                    params.put("floor", String.valueOf(floor));
                    params.put("rack", rack);
                    params.put("advanced", advanced);
                    params.put("caseType", strCaseType);
                    params.put("grandTotal", String.valueOf(grandTotal));
                    DbHelper dbHelper = new DbHelper(context);
                    Result result = dbHelper.getAdminData();
                    String byuser = null;
                    if (result != null) {
                        byuser = result.getAdminName();
                    }
                    Result uResult = dbHelper.getUserData();
                    if (uResult != null) {
                        byuser = uResult.getUserName();
                    }
                    params.put("byUser", byuser);
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(context);
            requestQueue.add(stringRequest);
//            }
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

//    private boolean validation() {
//        return true;
//    }
}
