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

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;

public class EmployeeFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    // TODO: Rename and change types and number of parameters
    public static EmployeeFragment newInstance(String param1, String param2) {
        EmployeeFragment fragment = new EmployeeFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_employee, container, false);
        init();
        return view;
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Employee");
        Button btn_addUser = view.findViewById(R.id.btn_addUser);
        Button btn_getAlluser = view.findViewById(R.id.btn_getAllUser);
        btn_addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserFragment addUserFragment = AddUserFragment.newInstance(false, "", "", "", "", "", "", "", "", "", "", "");
                moveragment(addUserFragment);
            }
        });
        btn_getAlluser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GetAllUsersFragment getAllUsersFragment = GetAllUsersFragment.newInstance("", "");
                moveragment(getAllUsersFragment);
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
