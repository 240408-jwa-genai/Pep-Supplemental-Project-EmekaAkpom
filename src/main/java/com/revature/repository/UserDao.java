package com.revature.repository;

import com.revature.models.User;
import com.revature.models.UsernamePasswordAuthentication;
import com.revature.utilities.ConnectionUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDao {
    
    public User getUserByUsername(String username){
        // TODO: implement
        try (Connection connection = ConnectionUtil.createConnection()){
            // first we need to craft our sql statement
            String sql = "SELECT * FROM users WHERE username = ?";
            // next, create a PreparedStatement and pass our SQL statement into it
            PreparedStatement ps = connection.prepareStatement(sql);
            // inject the username into the SQL
            ps.setString(1, username);
            User possibleUser = new User();
            //execute with the executeQuery method
            ResultSet rs = ps.executeQuery();
            if (rs.next()){
                int retrievedID = rs.getInt("id");
                String retrievedUserName = rs.getString("username");
                String retrievedPassword = rs.getString("password");
                possibleUser.setId(retrievedID);
                possibleUser.setUsername(retrievedUserName);
                possibleUser.setPassword(retrievedPassword);
            }
            return possibleUser;
        } catch(SQLException e){
            //TODO: handle SQLException
            e.printStackTrace();
        }
        return null;
    }

    public User createUser(UsernamePasswordAuthentication registerRequest) {
        // TODO: implement
        try (Connection connection = ConnectionUtil.createConnection()){
            String sql = "INSERT INTO users (username, password) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
            ps.setString(1, registerRequest.getUsername());
            ps.setString(2, registerRequest.getPassword());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            User newUser = new User();
            newUser.setUsername(registerRequest.getUsername());
            newUser.setPassword(registerRequest.getPassword());
            if (rs.next()){
                newUser.setId(rs.getInt(1));

            }
            return newUser;
        } catch(SQLException e){
            e.printStackTrace();
        }
        return null;
    }

}
