package hacker.l.coldstore.fragments;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.LoginActivity;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.model.MyPojo;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.myalert.SweetAlertDialog;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;

public class AddUserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    // TODO: Rename and change types of parameters
    private boolean flag;
    private String strName;
    private String fname;
    private String emptype;
    private String empphn;
    private String gnder;
    private String quly;
    private String dob;
    private String strAddress;
    private String dateoJ;
    private String stractive;
    private String empSalry;


    // TODO: Rename and change types and number of parameters
    public static AddUserFragment newInstance(boolean param1, String empName, String fname, String emptype, String emphn, String gnder, String quly, String dob, String strAddress, String dateoJ, String stractive, String empSalry) {
        AddUserFragment fragment = new AddUserFragment();
        Bundle args = new Bundle();
        args.putBoolean("flag", param1);
        args.putString("empName", empName);
        args.putString("fname", fname);
        args.putString("emptype", emptype);
        args.putString("empphn", emphn);
        args.putString("gnder", gnder);
        args.putString("quly", quly);
        args.putString("dob", dob);
        args.putString("strAddress", strAddress);
        args.putString("dateoJ", dateoJ);
        args.putString("stractive", stractive);
        args.putString("empSalry", empSalry);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            flag = getArguments().getBoolean("flag");
            strName = getArguments().getString("empName");
            fname = getArguments().getString("fname");
            emptype = getArguments().getString("emptype");
            empphn = getArguments().getString("empphn");
            gnder = getArguments().getString("gnder");
            quly = getArguments().getString("quly");
            dob = getArguments().getString("dob");
            strAddress = getArguments().getString("strAddress");
            dateoJ = getArguments().getString("dateoJ");
            stractive = getArguments().getString("stractive");
            empSalry = getArguments().getString("empSalry");
        }
    }

    View view;
    Context context;
    RadioGroup radiogroupUser, radiogroupGender, radiogroupActive;
    TextView tv_datePicker, tv_datePickerDob;
    DatePicker datePicker;
    int day, mont, year;
    String empName, empFName, empPhone, empQualification, empsalary, empAddress, empPassword, empcPassword;
    EditText edt_password, edt_cPassword, edt_empName, edt_empFName, edt_phone, edt_qualification, edt_salary, edt_address;
    Button btn_add;
    ProgressDialog pd;
    String userType = "User", gender = "Male", active = "true";
    RadioButton radio_admin, radio_user, radio_male, radio_female, radio_true, radio_false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_add_user, container, false);
        init();
        return view;
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        radiogroupUser = view.findViewById(R.id.radiogroupUser);
        radiogroupGender = view.findViewById(R.id.radiogroupGender);
        radio_admin = view.findViewById(R.id.radio_admin);
        radio_user = view.findViewById(R.id.radio_user);
        radio_male = view.findViewById(R.id.radio_male);
        radio_female = view.findViewById(R.id.radio_female);
        radio_true = view.findViewById(R.id.radio_true);
        radio_false = view.findViewById(R.id.radio_false);
        radiogroupActive = view.findViewById(R.id.radiogroupActive);
        tv_datePicker = view.findViewById(R.id.tv_datePicker);
        tv_datePickerDob = view.findViewById(R.id.tv_datePickerDob);
        edt_password = view.findViewById(R.id.edt_password);
        edt_cPassword = view.findViewById(R.id.edt_cPassword);
        edt_empName = view.findViewById(R.id.edt_empName);
        edt_empFName = view.findViewById(R.id.edt_empFName);
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_qualification = view.findViewById(R.id.edt_qualification);
        edt_salary = view.findViewById(R.id.edt_salary);
        edt_address = view.findViewById(R.id.edt_address);
        btn_add = view.findViewById(R.id.btn_add);
        datePicker = new DatePicker(context);
        day = datePicker.getDayOfMonth();
        mont = datePicker.getMonth();
        year = datePicker.getYear();
        if (flag) {
            mainActivity.setTitle("Update User");
            btn_add.setText("Update");
            edt_empName.setText(strName);
            edt_empFName.setText(fname);
            edt_phone.setText(empphn);
            edt_qualification.setText(quly);
            tv_datePickerDob.setText(dob);
            edt_address.setText(strAddress);
            tv_datePicker.setText(dateoJ);
            edt_salary.setText(empSalry);
            if (emptype.equalsIgnoreCase("Admin")) {
                radio_admin.setChecked(true);
            } else {
                radio_user.setChecked(true);
            }
            if (stractive.equalsIgnoreCase("true")) {
                radio_true.setChecked(true);
            } else {
                radio_false.setChecked(true);
            }
            if (gnder.equalsIgnoreCase("Male")) {
                radio_male.setChecked(true);
            } else {
                radio_female.setChecked(true);
            }
        } else {
            mainActivity.setTitle("Add User");
        }
        tv_datePicker.setText(String.valueOf(day + "/" + mont + "/" + year));
        tv_datePickerDob.setText(String.valueOf(day + "/" + mont + "/" + year));
        radiogroupUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_admin) {
                    userType = "Admin";
                } else {
                    userType = "User";
                }
            }
        });
        radiogroupActive.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_true) {
                    active = "True";
                } else {
                    active = "False";
                }
            }
        });
        radiogroupGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_male) {
                    gender = "Male";
                } else {
                    gender = "Female";
                }
            }
        });
        edt_cPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!edt_password.getText().toString().equalsIgnoreCase(edt_cPassword.getText().toString())) {
                    edt_cPassword.setError("Not Match");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    updateUserData();
                } else {
                    addNewUser();
                }
            }
        });
    }

    public boolean validation() {
        empName = edt_empName.getText().toString();
        empFName = edt_empFName.getText().toString();
        empPhone = edt_phone.getText().toString();
        empQualification = edt_qualification.getText().toString();
        empsalary = edt_salary.getText().toString();
        empAddress = edt_address.getText().toString();
        empPassword = edt_password.getText().toString();
        empcPassword = edt_cPassword.getText().toString();
        if (empName.length() == 0) {
            edt_empName.setError("Enter Name");
            requestFocus(edt_empName);
        } else if (empFName.length() == 0) {
            edt_empFName.setError("Enter Father Name");
            requestFocus(edt_empFName);
        } else if (empPhone.length() == 0) {
            edt_phone.setError("Enter Phone");
            requestFocus(edt_phone);
        } else if (empPhone.length() != 10) {
            edt_phone.setError("Enter Valid Phone");
            requestFocus(edt_phone);
        } else if (empQualification.length() == 0) {
            edt_qualification.setError("Enter Qualification");
            requestFocus(edt_qualification);
        } else if (empsalary.length() == 0) {
            edt_salary.setError("Enter Salary");
            requestFocus(edt_salary);
        } else if (empAddress.length() == 0) {
            edt_address.setError("Enter Address");
            requestFocus(edt_address);
        } else if (empPassword.length() == 0) {
            edt_password.setError("Enter Password");
        }
        return true;
    }

    private void addNewUser() {
        if (Utility.isOnline(context)) {
            if (validation()) {
                pd = new ProgressDialog(context);
                pd.setCancelable(false);
                pd.show();
                pd.getWindow()
                        .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pd.setContentView(new ProgressBar(context));
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.AddUser,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Add Successfully", Toast.LENGTH_SHORT).show();
                                GetAllUsersFragment getAllUsersFragment = GetAllUsersFragment.newInstance("", "");
                                moveragment(getAllUsersFragment);
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
                        params.put("empType", userType);
                        params.put("empName", empName);
                        params.put("empFName", empFName);
                        params.put("empPhone", empPhone);
                        params.put("empAddress", empAddress);
                        params.put("empjoindate", tv_datePicker.getText().toString());
                        params.put("empdob", tv_datePickerDob.getText().toString());
                        params.put("empQly", empQualification);
                        params.put("empGender", gender);
                        params.put("empsalary", empsalary);
                        params.put("empPassword", empPassword);
                        params.put("isActive", active);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        } else {
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Sorry...")
                    .setContentText("You are Offline. Please check your Internet Connection.Thank You ")
                    .show();
        }
    }

    public void updateUserData() {
        if (Utility.isOnline(context)) {
            if (validation()) {
                pd = new ProgressDialog(context);
                pd.setCancelable(false);
                pd.show();
                pd.getWindow()
                        .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pd.setContentView(new ProgressBar(context));
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.updateUser,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                Toast.makeText(context, "Update Successfully", Toast.LENGTH_SHORT).show();
                                GetAllUsersFragment getAllUsersFragment = GetAllUsersFragment.newInstance("", "");
                                moveragment(getAllUsersFragment);
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
                        params.put("empType", userType);
                        params.put("empName", empName);
                        params.put("empFName", empFName);
                        params.put("empPhone", empPhone);
                        params.put("empAddress", empAddress);
                        params.put("empjoindate", tv_datePicker.getText().toString());
                        params.put("empdob", tv_datePickerDob.getText().toString());
                        params.put("empQly", empQualification);
                        params.put("empGender", gender);
                        params.put("empsalary", empsalary);
                        params.put("empPassword", empPassword);
                        params.put("isActive", active);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            }
        } else {
            new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
                    .setTitleText("Sorry...")
                    .setContentText("You are Offline. Please check your Internet Connection.Thank You ")
                    .show();
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
