package utils;

import java.sql.*;

public class DatabaseInteraction {

    public void connect() throws SQLException {
        String url = "jdbc:mysql://bhdtest.endava.com:3306/";
        Connection connection = DriverManager.getConnection(url, "admin", "admin");
        System.out.println("connected");
        Statement statement = connection.createStatement();
        ResultSet resSet;

        resSet = statement.executeQuery("select * from owners");
        System.out.println("got before while");
        while (resSet.next()) {
            System.out.println(resSet.getString(1));
        }
        connection.close();

    }
}
