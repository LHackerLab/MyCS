package hacker.l.coldstore.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
import java.util.regex.Pattern;

import hacker.l.coldstore.R;
import hacker.l.coldstore.utility.Contants;
import hacker.l.coldstore.utility.Utility;


public class SignupActivity extends AppCompatActivity {
    Button id_bt_signup;
    ProgressDialog pd;
    EditText id_et_userName, id_phone, id_email, id_password;
    String userName, userPhone, emailId, Password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        id_bt_signup = findViewById(R.id.id_bt_signup);
        id_et_userName = findViewById(R.id.id_et_userName);
        id_phone = findViewById(R.id.id_phone);
        id_email = findViewById(R.id.id_email);
        id_password = findViewById(R.id.id_password);
        id_bt_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signUpFunction();
            }
        });
    }

    public boolean Validation() {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        userName = id_et_userName.getText().toString();
        userPhone = id_phone.getText().toString();
        emailId = id_email.getText().toString();
        Password = id_password.getText().toString();
        if (userName.isEmpty()) {
            id_et_userName.setError("Enter Username");
            return false;
        } else if (userPhone.isEmpty()) {
            id_phone.setError("Enter Phone Number");
            return false;
        } else if (userPhone.length() != 10) {
            id_phone.setError("Enter Valid Phone Number");
            return false;
        } else if (emailId.length() == 0) {
            id_email.setError("Enter Email Id");
            return false;
        } else if (!pattern.matcher(emailId).matches()) {
            id_email.setError("Enter Valid Email Id");
            return false;
        } else if (Password.length() == 0) {
            id_password.setError("Enter Password");
            return false;
        } else {
            return true;
        }
    }

    private void signUpFunction() {
        if (Utility.isOnline(this)) {
            if (Validation()) {
                pd = new ProgressDialog(SignupActivity.this);
                pd.setMessage("Sign Up wait...");
                pd.show();
                pd.setCancelable(false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.usersingup,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                pd.dismiss();
                                startActivity(new Intent(SignupActivity.this, MainActivity.class));
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
                        params.put("Username", userName);
                        params.put("UserPhone", userPhone);
                        params.put("EmailId", emailId);
                        params.put("Password", Password);
                        return params;
                    }
                };
                RequestQueue requestQueue = Volley.newRequestQueue(this);
                requestQueue.add(stringRequest);
            }
        } else

        {

            Toast.makeText(this, "You are Offline. Please check your Internet Connection.", Toast.LENGTH_SHORT).show();
        }
    }
}

