package org.mrutcka.wntw.adapters;

import android.annotation.SuppressLint;
import android.os.StrictMode;
import android.util.Log;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionHelper {
    Connection connection;
    String name, password, ip, port, database;

    @SuppressLint("NewApi")
    public Connection connectionClass() {
        ip = "xxx.xxx.xxx.xxx";
        database = "wntwDB";
        name = "sa";
        password = "sa";
        port = "1433";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        Connection connection = null;
        String ConnectionURL;

        try {
            Class.forName("net.sourceforge.jtds.jdbc.Driver");
            ConnectionURL = "jdbc:jtds:sqlserver://" + ip + ":" + port + ";" + "databasename=" + database + ";user=" + name + ";password=" + password + ";";
            connection = DriverManager.getConnection(ConnectionURL);
        }
        catch (Exception ex) {
            Log.e("Error", ex.getMessage());
        }

        return connection;
    }
}
