package hacker.l.coldstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import hacker.l.coldstore.R;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.model.Result;


public class SplashActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_ACTION_BAR);// Removes action bar
        requestWindowFeature(Window.FEATURE_NO_TITLE);// Removes title bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
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

    private static int SPLASH_TIME_OUT = 1000;
}
