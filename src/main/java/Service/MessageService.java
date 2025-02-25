package Service;

import DAO.MessageDAO;
import Model.Message;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

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

    public List<Message> getMessages() throws SQLException{
        return this.messageDAO.getAllMessage();
    }

    public Message getMessageID(int messageID) throws SQLException{
        return this.messageDAO.getMessageByID(messageID);
    }

    public Message delMessageID(int messageID) throws SQLException{
        return this.messageDAO.delMessageByID(messageID);
    }

    public Message updateMessageID(Message message, int Id) throws SQLException{
        if (message.message_text.length() > 255){
            return null;
        } else if (message.message_text.length() == 0){
            return null;
        }
        if (this.messageDAO.getMessageByID(Id) == null){
            return null;
        }
        this.messageDAO.updateMessage(message, Id);
        return this.messageDAO.getMessageByID(Id);
    }

    public List<Message> getUserMessages(int UserId) throws SQLException{
        return this.messageDAO.getAllMessageByUser(UserId);
    }
}
