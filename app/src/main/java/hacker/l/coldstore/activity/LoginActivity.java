package hacker.l.coldstore.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import java.util.HashMap;
import java.util.Map;

import hacker.l.coldstore.R;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.model.MyPojo;
import hacker.l.coldstore.model.Result;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;


public class LoginActivity extends AppCompatActivity {
    Button id_bt_login;
    TextView signUpText, forgot_password;
    EditText id_et_username, id_et_password;
    CheckBox showCheck;
    LinearLayout layout_singup;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        id_bt_login = findViewById(R.id.id_bt_login);
        signUpText = findViewById(R.id.signUpText);
        forgot_password = findViewById(R.id.forgot_password);
        id_et_username = findViewById(R.id.id_et_username);
        id_et_password = findViewById(R.id.id_et_password);
        showCheck = findViewById(R.id.show_password);
        layout_singup = findViewById(R.id.layout_singup);
        id_bt_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginFunction();
            }
        });
        layout_singup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intent);
            }
        });
        forgot_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, ForgetPasswordActivity.class);
                startActivity(intent);
            }
        });
        showCheck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (showCheck.isChecked()) {
                    id_et_password.setInputType(InputType.TYPE_CLASS_TEXT);
                    id_et_password.setSelection(id_et_password.length());
                } else {
                    id_et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    id_et_password.setSelection(id_et_password.length());
                }
            }
        });
    }

    private void loginFunction() {
        final String userPhone = id_et_username.getText().toString();
        final String userPass = id_et_password.getText().toString();
        if (userPhone.length() == 0) {
            id_et_username.setError("Enter  Phone Number ");
        } else if (userPhone.length() != 10) {
            id_et_username.setError("Enter  Valid Phone");
        } else if (userPass.length() == 0) {
            id_et_password.setError("Enter password");
        } else {
            if (Utility.isOnline(this)) {
                pd = new ProgressDialog(LoginActivity.this);
                pd.setMessage("Checking wait...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.login,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                if (!response.equalsIgnoreCase("no")) {
                                    MyPojo myPojo = new Gson().fromJson(response, MyPojo.class);
                                    if (myPojo != null) {
                                        for (Result result : myPojo.getResult()) {
                                            if (result != null) {
                                                new DbHelper(LoginActivity.this).upsertUserData(result);
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                startActivity(intent);
                                            }
                                        }
                                    }

                                } else {
                                    Toast.makeText(LoginActivity.this, "Invalid Information", Toast.LENGTH_SHORT).show();
                                    id_et_username.setError("Invalid Information");
                                }
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
                        params.put("UserPhone", userPhone);
                        params.put("Password", userPass);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            } else

            {

                Toast.makeText(this, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
