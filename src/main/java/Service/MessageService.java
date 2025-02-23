package Service;

import DAO.MessageDAO;
import Model.Message;

import java.sql.*;

public class MessageService {
    MessageDAO messageDAO;

    public MessageService(){
        messageDAO = new MessageDAO();
    }

    public MessageService(MessageDAO messageDAO){
        this.messageDAO = messageDAO;
    }

    public Message createMessage(Message message) throws SQLException{
        if  (this.messageDAO.getuser(message.getPosted_by()) == null){
            return null;
        }
        return this.messageDAO.newMessage(message);
    }
}
