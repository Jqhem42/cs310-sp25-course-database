package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.*;
import com.github.cliftonlabs.json_simple.*;
import java.util.ArrayList;

public class DAOUtility {
    
    public static final int TERMID_FA24 = 1;
    
    public static String getResultSetAsJson(ResultSet rs) {
        
        JsonArray records = new JsonArray();
        
        try {
        
            if (rs != null) {

                // INSERT YOUR CODE HERE
                // Retrieve ResultSet metadata
                ResultSetMetaData metaData = rs.getMetaData();
                int columnCount = metaData.getColumnCount();

                // Loop through each row in the ResultSet
                while (rs.next()) {
                    JsonObject record = new JsonObject();
                    
                    // Loop through each column in the row
                    for (int i = 1; i <= columnCount; i++) {
                        String columnName = metaData.getColumnName(i);
                        Object columnValue = rs.getObject(i);
                        
                        // Add column name and value to the JsonObject
                        record.put(columnName, columnValue);
                    }

                    // Add the JsonObject (row) to the JsonArray
                    records.add(record);
                }
            }
        } 
        catch (SQLException e) {
            e.printStackTrace();
        }
        
        // Return the serialized JsonArray as a String
        return Jsoner.serialize(records);
    }
}