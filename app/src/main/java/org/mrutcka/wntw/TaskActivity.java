package org.mrutcka.wntw;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import org.mrutcka.wntw.adapters.ConnectionHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class TaskActivity extends AppCompatActivity {

    Connection connection;
    String connectionResult;

    private String taskName, projectID;
    private String taskDescription, taskSerious, taskCategory, taskID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task);

        EditText taskName_editText = (EditText) findViewById(R.id.taskName_editText);
        EditText taskDescription_editText = (EditText) findViewById(R.id.taskDescription_editText);
        Button save_button = (Button) findViewById(R.id.save_Task);

        RadioButton ez_serious_rb = (RadioButton) findViewById(R.id.ez_serious);
        RadioButton normal_serious_rb = (RadioButton) findViewById(R.id.normal_serious);
        RadioButton hard_serious_rb = (RadioButton) findViewById(R.id.hard_serious);
        RadioButton do_category_rb = (RadioButton) findViewById(R.id.do_category);
        RadioButton doing_category_rb = (RadioButton) findViewById(R.id.doing_category);
        RadioButton done_category_rb = (RadioButton) findViewById(R.id.done_category);

        taskName = getIntent().getStringExtra("taskName");
        projectID = getIntent().getStringExtra("projectID");

        getTask();
        taskName_editText.setText(taskName);
        taskDescription_editText.setText(taskDescription);

        if(taskSerious.equals(1)) {
            ez_serious_rb.setChecked(true);
            normal_serious_rb.setChecked(false);
            hard_serious_rb.setChecked(false);
        }
        else if(taskSerious.equals(2)) {
            ez_serious_rb.setChecked(false);
            normal_serious_rb.setChecked(true);
            hard_serious_rb.setChecked(false);
        }
        else {
            ez_serious_rb.setChecked(false);
            normal_serious_rb.setChecked(false);
            hard_serious_rb.setChecked(true);
        }

        if(taskCategory.equals(1)) {
            do_category_rb.setChecked(true);
            doing_category_rb.setChecked(false);
            done_category_rb.setChecked(false);
        }
        else if(taskCategory.equals(2)) {
            do_category_rb.setChecked(false);
            doing_category_rb.setChecked(true);
            done_category_rb.setChecked(false);
        }
        else {
            do_category_rb.setChecked(false);
            doing_category_rb.setChecked(false);
            done_category_rb.setChecked(true);
        }

        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveTask();
            }
        });

        View.OnClickListener listener = new View.OnClickListener() {
            @Override public void onClick(View view) {
                boolean checked = ((RadioButton) view).isChecked();

                int id = view.getId();
                if(R.id.ez_serious == id) {
                    if(checked) {
                        taskSerious = String.valueOf(1);
                    }
                } else if (R.id.normal_serious == id) {
                    if(checked) {
                        taskSerious = String.valueOf(2);
                    }
                }
                else if (R.id.hard_serious == id) {
                    if(checked) {
                        taskSerious = String.valueOf(3);
                    }
                }
                else if (R.id.do_category == id) {
                    if(checked) {
                        taskSerious = String.valueOf(1);
                    }
                }
                else if (R.id.doing_category == id) {
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

    private void getTask() {
        try {
            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();

            if(connection != null) {
                String query_task = "SELECT * from Tasks WHERE Tasks.TaskName = '" + taskName + "';";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query_task);

                while (rs.next()) {
                    taskID = rs.getString(1);
                    taskDescription = rs.getString(3);
                    taskSerious = rs.getString(4);
                    taskCategory = rs.getString(5);
                }
            }
        } catch (Exception ex) {}
    }

    private void saveTask() {
        ConnectionHelper connectionHelper = new ConnectionHelper();
        connection = connectionHelper.connectionClass();

        try {
            if(connection != null) {
                String update_task = "UPDATE Tasks SET Tasks.TaskName = '" + taskName + "', " +
                        "Tasks.TaskDescription = '" + taskDescription + "', " +
                        "Tasks.TaskSeriouss = '" + taskSerious + "', " +
                        "Tasks.TaskCategory = '" + taskCategory + "' WHERE Tasks.TaskID = '" + taskID + "';";
                Statement st = connection.createStatement();
                ResultSet
                rs = st.executeQuery(update_task);
            }
        } catch (Exception ex) {}
    }
}
