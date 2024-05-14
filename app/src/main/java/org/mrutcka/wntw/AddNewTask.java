package org.mrutcka.wntw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.mrutcka.wntw.adapters.ConnectionHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class AddNewTask extends AppCompatActivity {

    Connection connection;
    String connectionResult;

    private String taskName, projectID;
    private String taskDescription, taskSerious, taskCategory, taskID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewtask);

        EditText taskName_editText = (EditText) findViewById(R.id.taskName_editText_new);
        EditText taskDescription_editText = (EditText) findViewById(R.id.taskDescription_editText_new);
        Button save_button = (Button) findViewById(R.id.save_Task_new);

        RadioButton ez_serious_rb = (RadioButton) findViewById(R.id.ez_serious_new);
        RadioButton normal_serious_rb = (RadioButton) findViewById(R.id.normal_serious_new);
        RadioButton hard_serious_rb = (RadioButton) findViewById(R.id.hard_serious_new);
        RadioButton do_category_rb = (RadioButton) findViewById(R.id.do_category_new);
        RadioButton doing_category_rb = (RadioButton) findViewById(R.id.doing_category_new);
        RadioButton done_category_rb = (RadioButton) findViewById(R.id.done_category_new);

        projectID = getIntent().getStringExtra("projectID");
        ez_serious_rb.setChecked(true);
        do_category_rb.setChecked(true);
        taskSerious = "1";
        taskCategory = "1";


        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                taskName = taskName_editText.getText().toString();
                taskDescription = taskDescription_editText.getText().toString();

                saveTask();


            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();

                int id = view.getId();
                if(R.id.ez_serious_new == id) {
                    if(checked) {
                        taskSerious = String.valueOf(1);
                    }
                } else if (R.id.normal_serious_new == id) {
                    if(checked) {
                        taskSerious = String.valueOf(2);
                    }
                }
                else if (R.id.hard_serious_new == id) {
                    if(checked) {
                        taskSerious = String.valueOf(3);
                    }
                }
                else if (R.id.do_category_new == id) {
                    if(checked) {
                        taskSerious = String.valueOf(1);
                    }
                }
                else if (R.id.doing_category_new == id) {
                    if(checked) {
                        taskSerious = String.valueOf(2);
                    }
                }
                else {
                    if(checked) {
                        taskCategory = String.valueOf(3);
                    }
                }
            }
        };
    }

    private void saveTask() {
        String id = "1";
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection != null) {
                String add_task = "INSERT INTO Tasks (TaskName, TaskDescription, TaskSeriouss, TaskCategory) values('" + taskName + "', '" + taskDescription + "', '" + taskSerious + "', '" + taskCategory + "')";
                Statement st = connection.createStatement();
                boolean rs = st.execute(add_task);
            }
        } catch (SQLException ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection != null) {
                Statement st = connection.createStatement();

                String search_task  = "SELECT * FROM Tasks WHERE TaskName = '" + taskName + "'";
                ResultSet rs = st.executeQuery(search_task);
                while (rs.next()) {
                    id = rs.getString(1);
                    break;
                }
            }
        } catch (SQLException ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection != null) {
                Statement st = connection.createStatement();
                String add_task_project = "INSERT INTO Tasks_Projects (TaskID, ProjectID) values('" + id + "', '" + projectID + "')";
                boolean rs = st.execute(add_task_project);
            }
        } catch (SQLException ex) {
            Toast.makeText(getApplicationContext(), ex.toString(), Toast.LENGTH_LONG).show();
        }
    }
}
