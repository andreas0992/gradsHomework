package utils;

import java.sql.*;
import java.util.ArrayList;

public class DatabaseInteraction {

    public ArrayList<String> getQueryResults(String databaseUrl, String user, String password, String query) throws SQLException {
        String url = databaseUrl;
        Connection connection = null;
        ResultSet resSet;
        ResultSetMetaData resMeta;
        ArrayList<String> rows = new ArrayList<String>();
        try {
            connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();
            resSet = statement.executeQuery(query);
            resMeta = resSet.getMetaData();
            int noOfColumns = resMeta.getColumnCount();
            while (resSet.next()) {
                String row = "";
                for (int i = 1; i < noOfColumns + 1; i++) {
                    row += resSet.getString(i) + " ";
                }
                rows.add(row);
            }
        } catch (SQLException e) {
            System.out.println("there was a problem connecting to the database");
        } finally {
            connection.close();
            return rows;
        }
    }
}
