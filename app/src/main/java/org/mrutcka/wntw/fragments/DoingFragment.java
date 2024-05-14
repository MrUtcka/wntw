package org.mrutcka.wntw.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import org.mrutcka.wntw.R;
import org.mrutcka.wntw.TaskActivity;
import org.mrutcka.wntw.adapters.ArrayListAdapter;
import org.mrutcka.wntw.adapters.ConnectionHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class DoingFragment extends ListFragment {

    Connection connection;
    String connectionResult;

    ArrayList<String> tasksName = new ArrayList<>();
    ArrayList<Integer> tasksSerious = new ArrayList<>();

    private String email, password, userID, projectID;

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = this.getArguments();
        projectID = bundle.getString("projectID");

        getTasks();

        ArrayListAdapter adapter = new ArrayListAdapter(getActivity(), tasksName, tasksSerious);
        setListAdapter(adapter);
    }

    private void getTasks() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection != null) {
                String query_tasks =
                        "SELECT * from Tasks JOIN Tasks_Projects ON Tasks_Projects.TaskID = Tasks.TaskID WHERE Tasks_Projects.ProjectID = " + projectID + " AND Tasks.TaskCategory =" + '2';
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query_tasks);

                while (rs.next()) {
                    tasksName.add(rs.getString(2));
                    tasksSerious.add(rs.getInt(4));
                }
            }
        } catch (Exception ex) {}
    }

    private void goTask(String name) {
        Intent i = new Intent(getContext(), TaskActivity.class);
        i.putExtra("projectID", projectID);
        i.putExtra("userID", userID);
        i.putExtra("taskName", name);
        startActivity(i);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        String item = (String) getListAdapter().getItem(position);
        goTask(tasksName.get(position));
    }
}