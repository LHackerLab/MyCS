package hacker.l.coldstore.activity;

import android.app.FragmentManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import hacker.l.coldstore.R;
import hacker.l.coldstore.fragments.AccoutnFragment;
import hacker.l.coldstore.fragments.EmployeeFragment;
import hacker.l.coldstore.fragments.FloorFragment;
import hacker.l.coldstore.fragments.HomeFragment;
import hacker.l.coldstore.fragments.InwardFragment;
import hacker.l.coldstore.fragments.OutwardFragment;
import hacker.l.coldstore.fragments.ProfileFragment;
import hacker.l.coldstore.fragments.RackFragment;
import hacker.l.coldstore.fragments.RentFragment;
import hacker.l.coldstore.fragments.VarietyFragment;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    LinearLayout layout_home, layout_inward, layout_outward, layout_employee, layout_floor, layout_rack, layout_vareity, layout_account, layout_rent, layout_profile;
    DrawerLayout drawer;
    TextView tv_title;

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

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        init();
    }

    private void init() {
        tv_title = findViewById(R.id.tv_title);
        layout_home = findViewById(R.id.layout_home);
        layout_inward = findViewById(R.id.layout_inward);
        layout_outward = findViewById(R.id.layout_outward);
        layout_employee = findViewById(R.id.layout_employee);
        layout_floor = findViewById(R.id.layout_floor);
        layout_rack = findViewById(R.id.layout_rack);
        layout_vareity = findViewById(R.id.layout_vareity);
//        layout_account = findViewById(R.id.layout_account);
        layout_profile = findViewById(R.id.layout_profile);
        layout_rent = findViewById(R.id.layout_rent);
        layout_home.setOnClickListener(this);
        layout_inward.setOnClickListener(this);
        layout_outward.setOnClickListener(this);
        layout_employee.setOnClickListener(this);
        layout_floor.setOnClickListener(this);
        layout_rack.setOnClickListener(this);
        layout_vareity.setOnClickListener(this);
//        layout_account.setOnClickListener(this);
        layout_profile.setOnClickListener(this);
        layout_rent.setOnClickListener(this);
        HomeFragment homeFragment = HomeFragment.newInstance("", "");
        moverHagment(homeFragment);
    }

    public void setTitle(String title) {
        tv_title.setText(title);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
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
            return true;
        }
        if (id == R.id.action_aboutus) {
            return true;
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
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void navHide() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.layout_home:
                HomeFragment homeFragment = HomeFragment.newInstance("", "");
                moveragment(homeFragment);
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
            case R.id.layout_rent:
                RentFragment rentFragment = RentFragment.newInstance("", "");
                moveragment(rentFragment);
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
        }
    }

    private void moveragment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                .addToBackStack(null)
                .commit();
    }
    private void moverHagment(Fragment fragment) {
        android.support.v4.app.FragmentManager fragmentManager = ((FragmentActivity) this).getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, fragment)
                //.addToBackStack(null)
                .commit();
    }
}
