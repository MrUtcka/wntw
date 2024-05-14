package org.mrutcka.wntw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import org.mrutcka.wntw.adapters.ConnectionHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private String email, password, firstName, lastName, userID;

    Connection connection;
    String connectionResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = getIntent().getStringExtra("email");
        password = getIntent().getStringExtra("password");

        TextView email_editText = (TextView) findViewById(R.id.email_editText);
        TextView password_editText = (TextView) findViewById(R.id.password_editText);

        email_editText.setText(email);
        password_editText.setText(password);
    }

    public void goMain(String email, String password, String firstName, String lastName) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);

        intent.putExtra("userID", userID);
        intent.putExtra("email", email);
        intent.putExtra("password", password);
        intent.putExtra("firstName", firstName);
        intent.putExtra("lastName", lastName);
        startActivity(intent);
    }

    public void goRegistration(View v) {
        Intent i = new Intent(getApplicationContext(), RegistrationActivity.class);
        startActivity(i);
    }

    public void Login(View v) {

        TextView email_editText = (TextView) findViewById(R.id.email_editText);
        TextView password_editText = (TextView) findViewById(R.id.password_editText);

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if(connection != null) {
                String query = "Select * from Users where UserEmail = '" + email_editText.getText().toString() + "'";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                while(rs.next()) {
                    if(Objects.equals(rs.getString(3), password_editText.getText().toString())) {

                        connectionResult = "connected";
                        Toast.makeText(this, "CONNECTED", Toast.LENGTH_SHORT).show();
                        userID = rs.getString(1);
                        firstName = rs.getString(4);
                        lastName = rs.getString(5);
                    }
                    else {
                        Toast.makeText(this, "PASSWORD INCORRECT", Toast.LENGTH_SHORT).show();
                    }
                }
            }
            else {
                Toast.makeText(this, "CHECK CONNECTION", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {}

        if(Objects.equals(connectionResult, "connected")) {
            Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
            goMain(email_editText.getText().toString(), password_editText.getText().toString(), firstName, lastName);
        }
    }
}
