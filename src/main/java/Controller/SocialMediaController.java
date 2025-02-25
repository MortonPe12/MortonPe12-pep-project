package Controller;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import Model.Account;
import Service.AccountService;
import Model.Message;
import Service.MessageService;
import io.javalin.Javalin;
import io.javalin.http.Context;

/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    AccountService accountService;
    MessageService messageService;
    public SocialMediaController(){
        accountService = new AccountService();
        messageService = new MessageService();
    }

    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registrationHandler);
        app.post("/login", this::loginHandler);
        app.post("/messages", this::messagePostHandler);
        app.get("/messages", this::messageGetHandler);
        app.get("/messages/{message_id}", this::messageGetIDhandler);
        app.delete("/messages/{message_id}", this::messageDelIDHandler);
        app.patch("/messages/{message_id}", this::messagePatchHandler);
        app.get("/accounts/{account_id}/messages", this::userMessagesHandler);
        return app;
    }

    /**
     * This is an example handler for an example endpoint.
     * @param context The Javalin Context object manages information about both the HTTP request and response.
     */
    private void registrationHandler(Context ctx) throws JsonProcessingException, SQLException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account addAccount = accountService.userRegistration(account);
        if(addAccount==null){
            ctx.status(400);
        }else{
            ctx.json(mapper.writeValueAsString(addAccount));
        }
    }

    private void loginHandler(Context ctx) throws JsonProcessingException, SQLException{
        ObjectMapper mapper = new ObjectMapper();
        Account account = mapper.readValue(ctx.body(), Account.class);
        Account accLogin = accountService.userLogin(account);
        if(accLogin == null){
            ctx.status(401);
        }else {
            ctx.json(mapper.writeValueAsString(accLogin));
        }
    }

    private void messagePostHandler(Context ctx) throws JsonProcessingException, SQLException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        Message post = messageService.createMessage(message);
        if(post == null){
            ctx.status(400);
        }else {
            ctx.json(mapper.writeValueAsString(post));
        }
    }

    private void messageGetHandler(Context ctx) throws JsonProcessingException, SQLException{
        List<Message> post = messageService.getMessages();
        ctx.json(post);
    }

    private void messageGetIDhandler(Context ctx) throws JsonProcessingException, SQLException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message post = messageService.getMessageID(id);
        if (post == null){
            ctx.status(200);
        }else {
            ctx.json(post);
        }
    }
    private void messageDelIDHandler(Context ctx) throws JsonProcessingException, SQLException{
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message delPost = messageService.delMessageID(id);
        if (delPost == null){
            ctx.status(200);
        }else {
            ctx.json(delPost);
        }
    }

    private void messagePatchHandler(Context ctx) throws JsonProcessingException, SQLException{
        ObjectMapper mapper = new ObjectMapper();
        Message message = mapper.readValue(ctx.body(), Message.class);
        int id = Integer.parseInt(ctx.pathParam("message_id"));
        Message updatedPost = messageService.updateMessageID(message, id);
        if (updatedPost == null){
            ctx.status(400);
        } else {
            ctx.json(mapper.writeValueAsString(updatedPost));
        }
    }

    private void userMessagesHandler(Context ctx) throws JsonProcessingException, SQLException{
        int userId = Integer.parseInt(ctx.pathParam("account_id"));
        List<Message> userPosts = messageService.getUserMessages(userId);
        ctx.json(userPosts);
    }
}