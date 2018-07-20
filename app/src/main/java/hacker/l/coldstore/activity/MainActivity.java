package hacker.l.coldstore.activity;

import android.app.FragmentManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import hacker.l.coldstore.R;
import hacker.l.coldstore.database.DbHelper;
import hacker.l.coldstore.fragments.AboutUsFragment;
import hacker.l.coldstore.fragments.AccoutnFragment;
import hacker.l.coldstore.fragments.EmployeeFragment;
import hacker.l.coldstore.fragments.FloorFragment;
import hacker.l.coldstore.fragments.HelpFragment;
import hacker.l.coldstore.fragments.HomeFragment;
import hacker.l.coldstore.fragments.InwardFragment;
import hacker.l.coldstore.fragments.OutwardFragment;
import hacker.l.coldstore.fragments.ProfileFragment;
import hacker.l.coldstore.fragments.RackFragment;
import hacker.l.coldstore.fragments.RentFragment;
import hacker.l.coldstore.fragments.SettingsFragment;
import hacker.l.coldstore.fragments.StoreRoomFragment;
import hacker.l.coldstore.fragments.VardanaFragment;
import hacker.l.coldstore.fragments.VarietyFragment;
import hacker.l.coldstore.model.Result;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    LinearLayout layout_home, layout_inward, layout_vardana, layout_storeRoom, layout_outward, layout_settings, layout_employee, layout_floor, layout_rack, layout_vareity, layout_profile;
    DrawerLayout drawer;
    public TextView tv_title, tv_admin, tv_email;
    ImageView image_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });
        // getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();
    }

    public void init() {
        tv_title = findViewById(R.id.tv_title);
        layout_home = findViewById(R.id.layout_home);
        layout_inward = findViewById(R.id.layout_inward);
        layout_outward = findViewById(R.id.layout_outward);
        layout_employee = findViewById(R.id.layout_employee);
        layout_floor = findViewById(R.id.layout_floor);
        layout_rack = findViewById(R.id.layout_rack);
        layout_vareity = findViewById(R.id.layout_vareity);
        tv_admin = findViewById(R.id.tv_admin);
        tv_email = findViewById(R.id.tv_email);
        image_profile = findViewById(R.id.image_profile);
        layout_storeRoom = findViewById(R.id.layout_storeRoom);
        layout_vardana = findViewById(R.id.layout_vardana);
        layout_settings = findViewById(R.id.layout_settings);
//        layout_account = findViewById(R.id.layout_account);
        layout_profile = findViewById(R.id.layout_profile);
        layout_home.setOnClickListener(this);
        layout_inward.setOnClickListener(this);
        layout_outward.setOnClickListener(this);
        layout_employee.setOnClickListener(this);
        layout_floor.setOnClickListener(this);
        layout_rack.setOnClickListener(this);
        layout_vareity.setOnClickListener(this);
        layout_storeRoom.setOnClickListener(this);
        layout_vardana.setOnClickListener(this);
        layout_settings.setOnClickListener(this);
//        layout_account.setOnClickListener(this);
        layout_profile.setOnClickListener(this);
        homeFrg();
        DbHelper dbHelper = new DbHelper(this);
        Result result = dbHelper.getAdminData();
        if (result != null) {
            tv_admin.setText(result.getAdminName() + "/" + result.getAdminPhone());
            tv_email.setText(result.getAdminEmail());
        }
    }

    public void homeFrg() {
        HomeFragment homeFragment = HomeFragment.newInstance("", "");
        moverHagment(homeFragment);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(Gravity.START)) {
            drawer.closeDrawer(Gravity.START);
        } else {
            super.onBackPressed();
        }
        // homeFrg();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.logout) {
            DbHelper dbHelper = new DbHelper(this);
            dbHelper.deleteAdminData();
            dbHelper.deleteInwardData();
            dbHelper.deleteRackData();
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            return true;
        }
        if (id == R.id.action_aboutus) {
            AboutUsFragment aboutUsFragment = AboutUsFragment.newInstance(false, "");
            moveragment(aboutUsFragment);
            return true;
        }
        if (id==R.id.action_help){
            HelpFragment aboutUsFragment = HelpFragment.newInstance("", "");
            moveragment(aboutUsFragment);
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(Gravity.START);
        return true;
    }

    public void navHide() {
        drawer.closeDrawer(Gravity.START);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_home:
                HomeFragment homeFragment = HomeFragment.newInstance("", "");
                moverHagment(homeFragment);
                navHide();
                break;
            case R.id.layout_inward:
                InwardFragment inwardFragment = InwardFragment.newInstance("", "");
                moveragment(inwardFragment);
                navHide();
                break;
            case R.id.layout_outward:
                OutwardFragment outwardFragment = OutwardFragment.newInstance("", "");
                moveragment(outwardFragment);
                navHide();
                break;
            case R.id.layout_employee:
                EmployeeFragment employeeFragment = EmployeeFragment.newInstance("", "");
                moveragment(employeeFragment);
                navHide();
                break;
            case R.id.layout_floor:
                FloorFragment floorFragment = FloorFragment.newInstance("", "");
                moveragment(floorFragment);
                navHide();
                break;
            case R.id.layout_rack:
                RackFragment rackFragment = RackFragment.newInstance("", "");
                moveragment(rackFragment);
                navHide();
                break;
            case R.id.layout_vareity:
                VarietyFragment varietyFragment = VarietyFragment.newInstance("", "");
                moveragment(varietyFragment);
                navHide();
                break;
//            case R.id.layout_account:
//                AccoutnFragment accoutnFragment = AccoutnFragment.newInstance("", "");
//                moveragment(accoutnFragment);
//                navHide();
//                break;
            case R.id.layout_profile:
                ProfileFragment profileFragment = ProfileFragment.newInstance("", "");
                moveragment(profileFragment);
                navHide();
                break;
            case R.id.layout_storeRoom:
                StoreRoomFragment storeFragment = StoreRoomFragment.newInstance("", "");
                moveragment(storeFragment);
                navHide();
                break;
            case R.id.layout_vardana:
                VardanaFragment vardanaFragment = VardanaFragment.newInstance("", "");
                moveragment(vardanaFragment);
                navHide();
                break;
            case R.id.layout_settings:
                SettingsFragment settingsFragment = SettingsFragment.newInstance("", "");
                moveragment(settingsFragment);
                navHide();
                break;
        }
    }

    private void moveragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager =  this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.pop_enter, R.anim.pop_exit);
        transaction.replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }

    private void moverHagment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = this.getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.enter, R.anim.exit);
        transaction.replace(R.id.container, fragment)
                //.addToBackStack(null)
                .commit();
    }

}
