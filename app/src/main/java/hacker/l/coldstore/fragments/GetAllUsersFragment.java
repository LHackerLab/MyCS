package hacker.l.coldstore.fragments;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.adapter.GetUserAdapter;
import hacker.l.coldstore.adapter.RackAdapter;
import hacker.l.coldstore.model.Result;
import retrofit2.http.GET;


public class GetAllUsersFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static GetAllUsersFragment newInstance(String param1, String param2) {
        GetAllUsersFragment fragment = new GetAllUsersFragment();
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
    LinearLayoutManager linearLayoutManager;
    RecyclerView recyclerView;
    List<Result> resultList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_get_all_users, container, false);
        init();
        return view;
    }


    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("Get All Users");
        recyclerView = view.findViewById(R.id.recycleView);
        linearLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
        resultList = new ArrayList<>();
        Result result = new Result();
        result.setEmpName("Lalit Singh");
//        resultList.add(result);
        result.setEmpFName("Rakesh Singh");
//        resultList.add(result);
        result.setEmpType("Admin");
//        resultList.add(result);
        result.setEmpPhone("9917218408");
//        resultList.add(result);
        result.setEmpGender("Male");
//        resultList.add(result);
        result.setEmpQly("Mca");
//        resultList.add(result);
        result.setEmpdob("10/02/1996");
//        resultList.add(result);
        result.setEmpsalary(300000);
//        resultList.add(result);
        result.setEmpAddress("Bareilly  bisalpur pilibhit up india.");
//        resultList.add(result);
        result.setEmpjoindate("24/03/2018");
//        resultList.add(result);
        result.setEmpProfile("https://static.pexels.com/photos/248797/pexels-photo-248797.jpeg");
        resultList.add(result);
        result.setEmpName("Lalit Chauhan");
//        resultList.add(result);
        result.setEmpFName("Mr. Singh");
//        resultList.add(result);
        result.setEmpType("User");
//        resultList.add(result);
        result.setEmpPhone("7895349408");
//        resultList.add(result);
        result.setEmpGender("Male");
//        resultList.add(result);
        result.setEmpQly("Mca final");
//        resultList.add(result);
        result.setEmpdob("10/02/1996");
//        resultList.add(result);
        result.setEmpsalary(200000);
//        resultList.add(result);
        result.setEmpAddress("Shajahapur  pilibhit up india.");
//        resultList.add(result);
        result.setEmpjoindate("24/03/2018");
//        resultList.add(result);
        result.setEmpProfile("https://static.pexels.com/photos/248797/pexels-photo-248797.jpeg");
        resultList.add(result);
        GetUserAdapter rackAdapter = new GetUserAdapter(context, resultList, GetAllUsersFragment.this);
        recyclerView.setAdapter(rackAdapter);

    }
}
