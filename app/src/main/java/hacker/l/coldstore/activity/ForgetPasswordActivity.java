package hacker.l.coldstore.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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


public class ForgetPasswordActivity extends AppCompatActivity {
    ProgressDialog pd;
    TextView forgot_password;
    Button id_bt_forget;
    EditText id_et_phone, id_et_email;
    String userPhone, userEmail, copyText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);
        forgot_password = findViewById(R.id.forgot_password);
        id_bt_forget = findViewById(R.id.id_bt_forget);
        id_et_phone = findViewById(R.id.id_et_phone);
        id_et_email = findViewById(R.id.id_et_email);
        id_bt_forget.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (id_bt_forget.getText().toString().equalsIgnoreCase("Copy Password")) {
//                    ClipboardManager _clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
//                    String[] cutString = copyText.split("-");
//                    String aaq = cutString[0];
//                    String aa = cutString[1];
//                    _clipboard.setText(aa);
//                    Toast.makeText(getApplicationContext(), "Copied to Clipboard!", Toast.LENGTH_SHORT).show();
//                } else {
                Pattern pattern = Patterns.EMAIL_ADDRESS;
                userPhone = id_et_phone.getText().toString();
                userEmail = id_et_email.getText().toString();
                if (userEmail.length() == 0) {
                    id_et_email.setError("Enter Email");
                } else if (!pattern.matcher(userEmail).matches()) {
                    id_et_email.setError("Enter Valid Email");
                } else if (userPhone.length() == 0) {
                    id_et_phone.setError("Enter  Phone Number ");
                } else if (userPhone.length() != 10) {
                    id_et_phone.setError("Enter  Valid Phone");
                } else

                {
                    forgetPass();
                    //}
                }
            }
        });

    }

    private void forgetPass() {
        if (Utility.isOnline(this)) {
            pd = new ProgressDialog(ForgetPasswordActivity.this);
            pd.setMessage("Checking wait...");
            pd.show();
            pd.setCancelable(false);
            StringRequest stringRequest = new StringRequest(Request.Method.POST, Contants.SERVICE_BASE_URL + Contants.forgetpassword,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            pd.dismiss();
                            copyText = response;
                            forgot_password.setText("Your Password:-" + response);
                            //id_bt_forget.setText("Copy Password");
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
                    params.put("EmailId", userEmail);
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
