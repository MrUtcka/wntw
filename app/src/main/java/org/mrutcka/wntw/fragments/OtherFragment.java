package org.mrutcka.wntw.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.google.android.material.tabs.TabLayout;

import org.mrutcka.wntw.R;
import org.mrutcka.wntw.adapters.ArrayListAdapter;
import org.mrutcka.wntw.adapters.ConnectionHelper;
import org.mrutcka.wntw.adapters.UsersListAdapter;
import org.mrutcka.wntw.adapters.ViewPagerAdapter;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class OtherFragment extends Fragment {
    Connection connection;
    String connectionResult;

    private String mParam1;
    private String mParam2;

    private String email, password, userID, projectID, projectName;
    ArrayList<String> users = new ArrayList<>();

    public static TasksFragment newInstance(String param1, String param2) {
        TasksFragment fragment = new TasksFragment();
        Bundle args = new Bundle();
        args.putString("userID", param1);
        args.putString("projectID", param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString("userID");
            mParam2 = getArguments().getString("projectID");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle result = this.getArguments();
        userID = result.getString("userID");
        projectID = result.getString("projectID");

        View inflatedView = inflater.inflate(R.layout.fragment_other, container, false);

        EditText userEmail_editText = (EditText) inflatedView.findViewById(R.id.addNewUser_editText);
        Button addNewUser_button = (Button) inflatedView.findViewById(R.id.addNewUser_button);

        users = getUsers();
        ListView users_view = (ListView) inflatedView.findViewById(R.id.users_in_projects_listview);
        UsersListAdapter adapter = new UsersListAdapter(getActivity(), users);
        users_view.setAdapter(adapter);

        addNewUser_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                email = userEmail_editText.getText().toString();
                addNewUser();
            }
        });

        return inflatedView;
    }

    private void addNewUser() {

        String id = "0";
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection != null) {
                String query_tasks =
                        "SELECT * FROM Users WHERE Users.UserEmail = '" + email + "';";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query_tasks);
                while (rs.next()) {
                    id = rs.getString(1);
                }
            }
        } catch (Exception ex) {}
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection != null) {
                String query_tasks =
                        "INSERT INTO Users_Projects (UserID, ProjectID) values ('" + id + "', '" + projectID + "')";
                Statement st = connection.createStatement();
                boolean rs = st.execute(query_tasks);
            }
        } catch (Exception ex) {}
    }

    private ArrayList<String> getUsers() {
        ArrayList<String> list = new ArrayList<>();
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection != null) {
                String query_tasks =
                        "SELECT * from Users JOIN Users_Projects ON Users_Projects.UserID = Users.UserID WHERE Users_Projects.ProjectID = '" + projectID + "'";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query_tasks);

                while (rs.next()) {
                    list.add(rs.getString(2));
                }
            }
        } catch (Exception ex) {}

        return list;
    }
}