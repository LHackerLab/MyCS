package hacker.l.coldstore.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.util.HashMap;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.model.MyPojo;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;


public class SplashActivity extends AppCompatActivity {
    ImageView image_view;
    ProgressDialog progressBar;
    Button btn_tryagain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);// Removes action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);// Removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        image_view = findViewById(R.id.image_view);
        btn_tryagain = findViewById(R.id.btn_tryagain);
        SharedPreferences sharedPreferences = getSharedPreferences("splashImage", MODE_PRIVATE);
        Picasso.with(SplashActivity.this).load(sharedPreferences.getString("url", null)).into(image_view);
        splashImage();
        if (!Utility.isOnline(this)) {
            btn_tryagain.setVisibility(View.VISIBLE);
            btn_tryagain.setOnClickListener(v -> {
                splashImage();
            });
        }
    }

    private void splashImage() {
        progressBar = new ProgressDialog(this);
        progressBar.setCancelable(false);
        progressBar.show();
        progressBar.getWindow()
                .setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        progressBar.setContentView(new ProgressBar(this));
        if (Utility.isOnline(this)) {
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.getSplashImage,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (!response.equalsIgnoreCase("")) {
                                MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                                if (myPojo != null) {
                                    for (Result result : myPojo.getResult()) {
                                        if (result.getImage() != null) {
                                            SharedPreferences sharedPreferences = getSharedPreferences("splashImage", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.putString("url", result.getImage());
                                            editor.apply();
                                            //image url......
                                            Picasso.with(SplashActivity.this).load(result.getImage()).into(image_view);
                                            new Handler().postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    DbHelper dbHelper = new DbHelper(SplashActivity.this);
                                                    Result result = dbHelper.getAdminData();
                                                    if (result == null) {
                                                        Result resultUser = dbHelper.getAdminData();//useradaa....
                                                        if (resultUser == null) {
                                                            SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
                                                            SplashActivity.this.finish();
                                                        } else {
                                                            SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                                            SplashActivity.this.finish();
                                                        }
                                                    } else {
                                                        SplashActivity.this.startActivity(new Intent(SplashActivity.this, MainActivity.class));
                                                        SplashActivity.this.finish();
                                                    }

                                                }
                                            }, SPLASH_TIME_OUT);
                                        }
                                    }
                                }

                            }
                            progressBar.dismiss();
                        }

                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            progressBar.dismiss();

                        }
                    }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();
                    return params;
                }
            };
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        } else {
            new Handler().postDelayed(() -> progressBar.dismiss(), 1000);
            Toast.makeText(this, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private static int SPLASH_TIME_OUT = 1000;
}
