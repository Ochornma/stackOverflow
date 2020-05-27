package org.promise.currencyconverter;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavHostController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

import android.os.Bundle;

import org.promise.currencyconverter.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        //setContentView(R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        drawerLayout = binding.drawerLayout;
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavigationUI.setupActionBarWithNavController(this, Navigation.findNavController(this, R.id.nav_host), drawerLayout);
        NavigationUI.setupWithNavController(binding.navView, Navigation.findNavController(this, R.id.nav_host));

    }

    @Override
    public boolean onSupportNavigateUp() {
return NavigationUI.navigateUp(Navigation.findNavController(this, R.id.nav_host), drawerLayout);
    }
}
