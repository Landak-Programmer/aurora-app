package com.mobile.auroraai.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.google.android.material.navigation.NavigationView;
import com.google.gson.JsonObject;
import com.mobile.auroraai.GlobalHolder;
import com.mobile.auroraai.R;
import com.mobile.auroraai.api.ServerCallback;
import com.mobile.auroraai.core.PropertiesHelper;
import com.mobile.auroraai.listener.SmsListener;
import com.mobile.auroraai.service.ServiceHolder;
import com.mobile.auroraai.service.api.BaseAPIService;
import com.mobile.auroraai.service.background.MasterBackgroundService;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Objects;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // todo: move to more appropriate place
        super.onCreate(savedInstanceState);
        PropertiesHelper.init(this);
        GlobalHolder.init();
        ServiceHolder.init();
        initRequestPermission();
        initAllServices();
        setContentView(R.layout.main_activity);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);

        init();
    }

    private void init() {
        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_host_fragment);
        assert navHostFragment != null;
        final NavController navController = navHostFragment.getNavController();
        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout);
        NavigationUI.setupWithNavController(navigationView, navController);
        navigationView.setNavigationItemSelectedListener(this);
    }

    /**
     * todo: Move me appropriately
     */
    private void initAllServices() {
        startForegroundService(new Intent(this, MasterBackgroundService.class));
    }

    /**
     * todo: Move me appropriately
     */
    private void initRequestPermission() {
        ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_SMS, Manifest.permission.RECEIVE_SMS}, PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        } else {
            return false;
        }
    }

    @SuppressLint("NonConstantResourceId")
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.sidebar_homepage:

                NavOptions navOptions = new NavOptions.Builder().setPopUpTo(R.id.main, true).build();

                Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.homepage_screen, null, navOptions);
                break;
            case R.id.sidebar_wallet:

                if (isValidDestination()) {
                    Navigation.findNavController(this, R.id.nav_host_fragment).navigate(R.id.wallet_screen);
                }

                break;
            default:
                throw new UnsupportedOperationException("No such navigation found");
        }
        item.setCheckable(true);
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean isValidDestination() {
        return R.id.wallet_screen != Objects.requireNonNull(Navigation.findNavController(this, R.id.nav_host_fragment).getCurrentDestination()).getId();
    }

    @Override
    public boolean onSupportNavigateUp() {
        return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host_fragment), drawerLayout);
    }

}