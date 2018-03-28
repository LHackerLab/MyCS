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

import java.util.HashMap;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
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
    String empName, empFName, phone, qty, rent, address;

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
        } else if (empFName.length() == 0) {
            requestFocus(edt_empFName);
            edt_empFName.setError("Enter Father Name");
        } else if (phone.length() == 0) {
            requestFocus(edt_phone);
            edt_phone.setError("Enter Phone");
        } else if (phone.length() != 10) {
            requestFocus(edt_phone);
            edt_phone.setError("Enter valid Phone");
        } else if (qty.length() == 0) {
            edt_qty.setError("Enter Quantity");
            requestFocus(edt_qty);
        } else if (rent.length() == 0) {
            requestFocus(edt_rent);
            edt_rent.setError("Enter Rent Price");
        } else if (address.length() == 0) {
            requestFocus(edt_address);
            edt_address.setError("Enter Address");
        }
        return true;
    }

    private void uploadInwardData() {
        if (Utility.isOnline(context)) {
            if (validation()) {
                pd = new ProgressDialog(context);
                pd.setMessage("Adding wait...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.AddInward,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();
                                AccoutnFragment accoutnFragment = AccoutnFragment.newInstance("", "");
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
                        params.put("name", empName);
                        params.put("Fname", empFName);
                        params.put("phone", phone);
                        params.put("address", address);
                        params.put("Qty", qty);
                        params.put("rent", rent);
                        params.put("variety", "");
                        params.put("floor", "");
                        params.put("rack", "");
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        } else {
            Toast.makeText(context, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
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
}
