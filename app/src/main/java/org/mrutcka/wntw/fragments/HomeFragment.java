package org.mrutcka.wntw.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.ListFragment;

import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import org.mrutcka.wntw.ProjectActivity;
import org.mrutcka.wntw.adapters.ConnectionHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HomeFragment extends ListFragment {

    Connection connection;
    String connectionResult;

    ArrayList<String> projectsName = new ArrayList<>();

    private String email, password, userID, projectID;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {

        Bundle bundle = this.getArguments();
        email = bundle.getString("email");
        password = bundle.getString("password");
        userID = bundle.getString("userID");

        location(getView());

        super.onActivityCreated(savedInstanceState);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
                android.R.layout.simple_list_item_1, projectsName);
        setListAdapter(adapter);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String projectName = projectsName.get(position);

        goProject(projectName);
    }

    private void goProject(String projectName) {
        Intent intent = new Intent(getContext(), ProjectActivity.class);
        intent.putExtra("projectName", projectName);
        intent.putExtra("userID", userID);
        intent.putExtra("email", email);

        startActivity(intent);
    }

    private void location(View v) {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection != null) {
                String query_projects = "DECLARE @id INT; " +
                        "SELECT @id = Users.UserID FROM Users WHERE Users.UserEmail = '" + email + "'; " +
                        "SELECT * from Projects JOIN Users_Projects ON Users_Projects.ProjectID = Projects.ProjectID WHERE Users_Projects.UserID = @id";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query_projects);

                while (rs.next()) {
                    projectsName.add(rs.getString(2));
                }

                Toast.makeText(getContext(), "CONNECTED", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {}
    }
}