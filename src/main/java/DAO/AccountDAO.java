package DAO;

import Model.Account;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAO {
    public Account NewUser(Account account) throws SQLException{
        if (account.password.length() < 4){
            return null;
        } else if (account.username.trim().isEmpty()){
            return null;
        }
        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO account (username, password) VALUES (?,?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setString(1, account.username);
        preparedStatement.setString(2, account.password);
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if(rs.next()){
            int generated_user_id = (int) rs.getInt(1);
            return new Account(generated_user_id, account.getUsername(), account.getPassword());
        }     
        return null;
    }
    public Account getUsername(String username) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM account WHERE username = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, username);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Account account = new Account(rs.getInt("account_id"), 
                    rs.getString("username"), 
                    rs.getString("password"));
            return account;
        }
        return null;
    }
    public Account getLogin(Account account) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM account WHERE username = ? AND password = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, account.username);        
        preparedStatement.setString(2, account.password);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Account acc = new Account(rs.getInt("account_id"), 
                    rs.getString("username"), 
                    rs.getString("password"));
            return acc;
        }     
        return null;
    }
}
