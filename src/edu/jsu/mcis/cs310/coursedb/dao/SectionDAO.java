package edu.jsu.mcis.cs310.coursedb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import com.github.cliftonlabs.json_simple.JsonArray;
import com.github.cliftonlabs.json_simple.JsonObject;
import com.github.cliftonlabs.json_simple.Jsoner;
import java.sql.SQLException;


public class SectionDAO {
    
    private static final String QUERY_FIND = "SELECT * FROM section WHERE termid = ? AND subjectid = ? AND num = ? ORDER BY crn";
    
    private final DAOFactory daoFactory;
    
    SectionDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }
    
    public String find(int termid, String subjectid, String num) {
        
        String result = "[]";
        
        PreparedStatement ps = null;
        ResultSet rs = null;
        ResultSetMetaData rsmd = null;
        
        try {
            
            Connection conn = daoFactory.getConnection();
            
            if (conn.isValid(0)) {
                
                // INSERT YOUR CODE HERE
                // Prepare the query with parameters
            ps = conn.prepareStatement(QUERY_FIND);
            ps.setInt(1, termid);
            ps.setString(2, subjectid);
            ps.setString(3, num);

            // Execute the query
            rs = ps.executeQuery();
            rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            // Create a JsonArray to store results
            JsonArray jsonArray = new JsonArray();

            // Process the ResultSet and convert to JSON
            while (rs.next()) {
                JsonObject jsonObject = new JsonObject();
                for (int i = 1; i <= columnCount; i++) {
                    String columnName = rsmd.getColumnName(i);
                    Object columnValue = rs.getObject(i);
                    jsonObject.put(columnName, columnValue);
                }
                jsonArray.add(jsonObject);
            }

            // Convert the JSON array to a string and assign it to result
            result = Jsoner.serialize(jsonArray);
        }
    } catch (Exception e) {
        e.printStackTrace();  // Print stack trace for debugging
        result = "{\"error\": \"An error occurred while processing the query.\"}";  // Inform the user of the error
    } finally {
        // Close the resources in the 'finally' block to ensure they're always closed
        try {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();  // Print any exception encountered while closing resources
        }
    }
    
    return result;  // Return the result (empty array or error message)
}
}