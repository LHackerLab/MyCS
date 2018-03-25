package hacker.l.coldstore.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.model.Result;

public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(String param1, String param2) {
        ProfileFragment fragment = new ProfileFragment();
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
    TextView tv_name, tv_phone, tv_email;
    ImageView image_profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_profile, container, false);
        init();
        return view;
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Profile");
        btn_proced = view.findViewById(R.id.btn_proced);
        tv_name = view.findViewById(R.id.tv_name);
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_email = view.findViewById(R.id.tv_email);
        image_profile = view.findViewById(R.id.image_profile);
        DbHelper dbHelper = new DbHelper(context);
        Result result = dbHelper.getAdminData();
        if (result != null) {
            tv_name.setText(result.getAdminName());
            tv_phone.setText(result.getAdminPhone());
            tv_email.setText(result.getAdminEmail());
        }
        btn_proced.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateProfileFragment updateProfileFragment = UpdateProfileFragment.newInstance("", "");
                moveragment(updateProfileFragment);
            }
        });
    }

    private void moveragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) context).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
}
