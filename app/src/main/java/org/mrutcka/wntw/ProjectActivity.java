package org.mrutcka.wntw;

import static android.content.Intent.getIntent;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.mrutcka.wntw.adapters.ConnectionHelper;
import org.mrutcka.wntw.databinding.ActivityMainBinding;
import org.mrutcka.wntw.databinding.ActivityProjectBinding;
import org.mrutcka.wntw.fragments.AddNewProjectFragment;
import org.mrutcka.wntw.fragments.HomeFragment;
import org.mrutcka.wntw.fragments.OtherFragment;
import org.mrutcka.wntw.fragments.ProfileFragment;
import org.mrutcka.wntw.fragments.ScheduleFragment;
import org.mrutcka.wntw.fragments.SettingsFragment;
import org.mrutcka.wntw.fragments.TasksFragment;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class ProjectActivity extends AppCompatActivity {

    ActivityProjectBinding binding;
    Connection connection;
    String connectionResult;

    private String email, password, userID, projectID, projectName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        email = getIntent().getStringExtra("email");
        userID = getIntent().getStringExtra("userID");
        projectName = getIntent().getStringExtra("projectName");

        getProjectID();

        binding = ActivityProjectBinding.inflate(getLayoutInflater());
        replaceFragment(new TasksFragment());
        setContentView(binding.getRoot());

        binding.botnavview.setOnItemSelectedListener(menuItem -> {
            int itemId = menuItem.getItemId();

            if(itemId == R.id.task_nav) {
                replaceFragment(new TasksFragment());
            }
            else if(itemId == R.id.schedule_nav) {
                replaceFragment(new ScheduleFragment());
            }
            else if(itemId == R.id.other_nav) {
                replaceFragment(new OtherFragment());
            }

            return true;
        });
    }

    private void getProjectID () {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection != null) {
                String query_project = "SELECT * FROM Projects WHERE Projects.ProjectName = '" + projectName + "'";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query_project);

                while (rs.next()) {
                    projectID = rs.getString(1);
                }
            }
        } catch (Exception ex) {}
    }

    private Bundle tradeDATA() {
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("userID", userID);
        bundle.putString("projectID", projectID);

        return bundle;
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_nav, fragment);
        fragment.setArguments(tradeDATA());
        fragmentTransaction.commit();
    }

    public void addNewTask(View view) {
        Intent i = new Intent(getApplicationContext(), AddNewTask.class);
        i.putExtra("projectID", projectID);

        startActivity(i);
    }
}
