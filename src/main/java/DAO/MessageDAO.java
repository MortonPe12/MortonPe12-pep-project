package DAO;

import Model.Message;
import Util.ConnectionUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MessageDAO {
    public Message newMessage(Message message) throws SQLException{
        if (message.message_text.length() > 255){
            return null;
        } else if (message.message_text.length() == 0){
            return null;
        }

        Connection connection = ConnectionUtil.getConnection();
        String sql = "INSERT INTO message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        preparedStatement.setInt(1, message.posted_by);
        preparedStatement.setString(2, message.message_text);
        preparedStatement.setLong(3,message.time_posted_epoch);
        preparedStatement.executeUpdate();
        ResultSet rs = preparedStatement.getGeneratedKeys();
        if(rs.next()){
            int generated_message_id = (int) rs.getInt(1);
            return new Message(generated_message_id, message.getPosted_by(), 
                    message.getMessage_text(), message.getTime_posted_epoch());
        }     
        return null;
    }

    public Message getuser(int userId) throws SQLException{
        Connection connection = ConnectionUtil.getConnection();
        String sql = "SELECT * FROM message WHERE posted_by = ?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, userId);
        ResultSet rs = preparedStatement.executeQuery();
        while(rs.next()){
            Message message = new Message(rs.getInt("message_id"),
                    rs.getInt("posted_by"),
                    rs.getString("message_text"), 
                    rs.getLong("time_posted_epoch"));
            return message;
        }
        return null;
    }
}
