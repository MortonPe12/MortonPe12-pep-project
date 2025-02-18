package Service;

import DAO.AccountDAO;
import Model.Account;

import java.sql.*;

public class AccountService {
    AccountDAO accountDAO;

    public AccountService(){
        accountDAO = new AccountDAO();
    }

    public AccountService(AccountDAO accountDAO){

        this.accountDAO = accountDAO;
    }

    public Account userRegistration(Account account) throws SQLException{
        /*try {
            return this.accountDAO.NewUser(account);
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }*/
        if (accountDAO.getUsername(account.getUsername()) == null){
            return this.accountDAO.NewUser(account);
        }
        return null;
    }
}
