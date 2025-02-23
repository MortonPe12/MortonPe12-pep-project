package Controller;

import java.sql.SQLClientInfoException;
import java.sql.SQLException;

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
}