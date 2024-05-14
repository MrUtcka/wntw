package org.mrutcka.wntw.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.mrutcka.wntw.R;
import org.mrutcka.wntw.adapters.ConnectionHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;


public class AddNewProjectFragment extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    Connection connection;
    String connectionResult;

    private String email, password, userID;

    public static AddNewProjectFragment newInstance(String param1, String param2) {
        AddNewProjectFragment fragment = new AddNewProjectFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    private void add(View v) {

        String projectID = "999";

        EditText projectName_textView = (EditText) v.findViewById(R.id.addNewProjectName_editText);
        EditText projectAuthor_textView = (EditText) v.findViewById(R.id.addNewProjectAuthor_editText);

        String projectName = projectName_textView.getText().toString();
        String projectAuthor = projectAuthor_textView.getText().toString();

        ConnectionHelper connectionHelper = new ConnectionHelper();
        connection = connectionHelper.connectionClass();

        try {
            Toast.makeText(getContext(), "CONNECT", Toast.LENGTH_SHORT).show();
            if(connection != null && projectAuthor_textView.getText() != null && projectName_textView.getText() != null) {
                String query_new_project = "INSERT INTO Projects (ProjectName, ProjectAuthor) values ('" + projectName + "', '" + projectAuthor + "')";

                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query_new_project);
            }
            else {
                Toast.makeText(getContext(), "INCORRECT DATA", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {}
        try {
            if(connection != null && projectAuthor_textView.getText() != null && projectName_textView.getText() != null) {
                Statement st = connection.createStatement();

                String query_project = "SELECT * FROM Projects WHERE Projects.ProjectName = '" + projectName + "' AND Projects.ProjectAuthor = '" + projectAuthor + "'";
                ResultSet rs = st.executeQuery(query_project);
                while (rs.next()) {
                    projectID = rs.getString(1);
                }
            }
        } catch (Exception ex) {}
        try {
            if(connection != null && projectAuthor_textView.getText() != null && projectName_textView.getText() != null) {
                String query_user = "INSERT INTO Users_Projects (UserID, ProjectID) values ('" + userID + "', '" + projectID + "')";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query_user);
            }
        } catch (Exception ex) {}
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View inflatedView = inflater.inflate(R.layout.fragment_add_new_project, container, false);
        Button addNewProject_button = (Button) inflatedView.findViewById(R.id.addNewProject_button);

        Bundle bundle = this.getArguments();
        userID = bundle.getString("userID");
        addNewProject_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                add(inflatedView);
            }
        });

        return inflatedView;
    }
}