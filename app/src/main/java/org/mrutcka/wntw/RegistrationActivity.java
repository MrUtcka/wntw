package org.mrutcka.wntw;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import org.mrutcka.wntw.adapters.ConnectionHelper;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class RegistrationActivity extends AppCompatActivity {

    Connection connection;
    String connectionResult;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }

    public void registration(View v) {

        TextView email = (TextView) findViewById(R.id.email_input_editText);
        TextView password = (TextView) findViewById(R.id.password_input_editText);
        TextView firstname = (TextView) findViewById(R.id.name_input_editText);
        TextView lastname = (TextView) findViewById(R.id.lastname_input_editText);

        try {

            ConnectionHelper connectionHelper = new ConnectionHelper();
            connection = connectionHelper.connectionClass();
            if(connection != null) {
                String query = "Select * from Users where UserEmail = '" + email.getText().toString() + "'";
                Statement st = connection.createStatement();
                ResultSet rs = st.executeQuery(query);
                //Toast.makeText(this, "CONNECTED", Toast.LENGTH_LONG).show();
                int count = 0;
                while (rs.next()) { count++; }

                if(count == 0) {
                    if(password != null && firstname != null && lastname != null) {
                        connectionResult = "connected";
                        query = "INSERT INTO users (UserEmail, UserPassword, UserFirstName, UserLastName) values('" + email.getText().toString() + "', '" + password.getText().toString()
                                + "', '" + firstname.getText().toString() + "', '" + lastname.getText().toString() + "')";
                        rs = st.executeQuery(query);
                        Toast.makeText(this, "REGISTRATION CORRECT", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
                    Toast.makeText(this, "EMAIL USED", Toast.LENGTH_SHORT).show();
                }
            }
            else {
                Toast.makeText(this, "CHECK CONNECTION", Toast.LENGTH_SHORT).show();
            }
        } catch (Exception ex) {}

        if(Objects.equals(connectionResult, "connected")) {
            Toast.makeText(this, "LOGIN", Toast.LENGTH_SHORT).show();
            goLogin(email.getText().toString(), password.getText().toString());
        }
    }

    private void goLogin(String email, String password) {
        Intent i = new Intent(getApplicationContext(), LoginActivity.class);

        i.putExtra("email", email);
        i.putExtra("password", password);
        startActivity(i);
    }
}
