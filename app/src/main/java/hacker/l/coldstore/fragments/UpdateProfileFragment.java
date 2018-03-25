package hacker.l.coldstore.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.model.Result;

public class UpdateProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static UpdateProfileFragment newInstance(String param1, String param2) {
        UpdateProfileFragment fragment = new UpdateProfileFragment();
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
    Button btn_proced;
    EditText edt_cPassword, edt_password, edt_name, edt_phone, edt_email;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_update_profile, container, false);
        inti();
        return view;
    }

    private void inti() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Update Profile");
        edt_cPassword = view.findViewById(R.id.edt_cPassword);
        edt_password = view.findViewById(R.id.edt_password);
        edt_name = view.findViewById(R.id.edt_name);
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_email = view.findViewById(R.id.edt_email);
        btn_proced = view.findViewById(R.id.btn_proced);
        DbHelper dbHelper = new DbHelper(context);
        Result result = dbHelper.getAdminData();
        if (result != null) {
            edt_name.setText(result.getAdminName());
            edt_phone.setText(result.getAdminPhone());
            edt_email.setText(result.getAdminEmail());
            edt_password.setText(result.getAdminPassword());
            edt_cPassword.setText(result.getAdminPassword());
        }
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
        btn_proced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().popBackStack();
                Toast.makeText(context, "Update Profile Successfully", Toast.LENGTH_SHORT).show();
            }
        });
    }

}
