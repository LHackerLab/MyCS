package hacker.l.coldstore.fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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
import com.squareup.picasso.Picasso;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.activity.MainActivity;
import hacker.l.coldstore.activity.SplashActivity;
import hacker.l.coldstore.adapter.GetUserAdapter;
import hacker.l.coldstore.model.MyPojo;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.myalert.SweetAlertDialog;
import hacker.l.coldstore.utility.CircleTransform;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;

public class AboutUsFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private boolean mParam1;
    private String mParam2;


    // TODO: Rename and change types and number of parameters
    public static AboutUsFragment newInstance(boolean param1, String param2) {
        AboutUsFragment fragment = new AboutUsFragment();
        Bundle args = new Bundle();
        args.putBoolean(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getBoolean(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    View view;
    Context context;
    ImageView image_profile, image_camera;
    TextView tv_update;
    EditText edt_name, edt_phone, edt_email, edt_address, edt_owner, edt_timing;
    ProgressDialog pd;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        context = getActivity();
        view = inflater.inflate(R.layout.fragment_about_us, container, false);
        init();
        return view;
    }

    private void init() {
        MainActivity mainActivity = (MainActivity) context;
        mainActivity.setTitle("AboutUs");
        image_profile = view.findViewById(R.id.image_profile);
        image_camera = view.findViewById(R.id.image_camera);
        tv_update = view.findViewById(R.id.tv_update);
        edt_name = view.findViewById(R.id.edt_name);
        edt_phone = view.findViewById(R.id.edt_phone);
        edt_email = view.findViewById(R.id.edt_email);
        edt_address = view.findViewById(R.id.edt_address);
        edt_owner = view.findViewById(R.id.edt_owner);
        edt_timing = view.findViewById(R.id.edt_timing);
        if (!mParam1) {
            image_camera.setVisibility(View.GONE);
            tv_update.setVisibility(View.GONE);
            edt_name.setEnabled(false);
            edt_phone.setEnabled(false);
            edt_email.setEnabled(false);
            edt_address.setEnabled(false);
            edt_owner.setEnabled(false);
            edt_timing.setEnabled(false);
            if (Utility.isOnline(context)) {
                pd = new ProgressDialog(context);
                pd.setCancelable(false);
                pd.show();
                pd.getWindow()
                        .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
                pd.setContentView(new ProgressBar(context));
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getStoreProfile,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                                if (myPojo != null) {
                                    for (Result result : myPojo.getResult()) {
                                        edt_name.setText(result.getStoreName());
                                        edt_phone.setText(result.getStorePhone());
                                        edt_email.setText(result.getStoreEmail());
                                        edt_address.setText(result.getStoreAddress());
                                        edt_owner.setText(result.getStoreOwner());
                                        edt_timing.setText(result.getStoreTiming());
                                        Picasso.with(context).load(result.getStoreProfile()).transform(new CircleTransform()).into(image_profile);
                                    }
                                }
                            }
                        },
                        new Response.ErrorListener()

                        {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                pd.dismiss();
                                Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
                                getFragmentManager().popBackStack();
                            }
                        })

                {
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        Map<String, String> params = new HashMap<String, String>();
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(context);
                requestQueue.add(stringRequest);
            } else

            {
                getFragmentManager().popBackStack();
//                new SweetAlertDialog(context, SweetAlertDialog.ERROR_TYPE)
//                        .setTitleText("Sorry...")
//                        .setContentText("You are Offline. Please check your Internet Connection.Thank You ")
//                        .show();
            }
        }
    }
}

