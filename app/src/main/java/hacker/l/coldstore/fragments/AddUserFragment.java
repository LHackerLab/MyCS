package hacker.l.coldstore.fragments;

import android.app.DatePickerDialog;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;

public class AddUserFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static AddUserFragment newInstance(String param1, String param2) {
        AddUserFragment fragment = new AddUserFragment();
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
    RadioGroup radiogroupUser;
    TextView tv_datePicker, tv_datePickerDob;
    DatePicker datePicker;
    int day, mont, year;
    String admin, user;
    EditText edt_password, edt_cPassword;

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
        mainActivity.setTitle("Add Users");
        radiogroupUser = view.findViewById(R.id.radiogroupUser);
        tv_datePicker = view.findViewById(R.id.tv_datePicker);
        tv_datePickerDob = view.findViewById(R.id.tv_datePickerDob);
        edt_password = view.findViewById(R.id.edt_password);
        edt_cPassword = view.findViewById(R.id.edt_cPassword);
        datePicker = new DatePicker(context);
        day = datePicker.getDayOfMonth();
        mont = datePicker.getMonth();
        year = datePicker.getYear();
        tv_datePicker.setText(String.valueOf(day + "/" + mont + "/" + year));
        tv_datePickerDob.setText(String.valueOf(day + "/" + mont + "/" + year));
        radiogroupUser.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.radio_admin) {
                    admin = "Admin";
                } else {
                    user = "User";
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
    }

}
