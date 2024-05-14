package org.mrutcka.wntw;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.navigation.NavigationView;

import org.mrutcka.wntw.adapters.ConnectionHelper;
import org.mrutcka.wntw.databinding.ActivityMainBinding;
import org.mrutcka.wntw.fragments.AddNewProjectFragment;
import org.mrutcka.wntw.fragments.HomeFragment;
import org.mrutcka.wntw.fragments.ProfileFragment;
import org.mrutcka.wntw.fragments.SettingsFragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class MainActivity extends AppCompatActivity {

    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle drawerToggle;

    private String email, password, firstName, lastName, userID;

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Переход

        email = getIntent().getStringExtra("email");
        userID = getIntent().getStringExtra("userID");
        password = getIntent().getStringExtra("password");
        firstName = getIntent().getStringExtra("firstName");
        lastName = getIntent().getStringExtra("lastName");

        replaceFragment(new HomeFragment());

        //Панель

        drawerLayout = findViewById(R.id.activity_main);
        navigationView = findViewById(R.id.nav_view);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                int itemId = menuItem.getItemId();

                if(itemId == R.id.Home_nav) {
                    replaceFragment(new HomeFragment());
                }
                else if(itemId == R.id.Create_nav) {
                    replaceFragment(new AddNewProjectFragment());
                }
                else if(itemId == R.id.Profile_nav) {
                    replaceFragment(new ProfileFragment());
                }
                else if(itemId == R.id.Login_nav) {
                    goLogin();
                }
                else if(itemId == R.id.Setting_nav) {
                    replaceFragment(new SettingsFragment());
                }
                return false;
            }
        });
    }

    private Bundle tradeDATA() {
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("password", password);
        bundle.putString("userID", userID);

        return bundle;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_main, fragment);
        fragment.setArguments(tradeDATA());
        fragmentTransaction.commit();
    }

    private void goLogin() {
        Intent intent = new Intent(this, LoginActivity.class);

        intent.putExtra("email", email);
        intent.putExtra("password", password);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        super.onBackPressed();
    }
}
